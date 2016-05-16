package com.gint.app.bisis4.textsrv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.gint.app.bisis4.prepis.PrepisReport;
import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.records.Sveska;

public class DBStorage {

  /**
   * Retrieves a new record ID.
   * @param conn Database connection
   * @return The new record ID.
   */
  public int getNewRecordID(Connection conn) {
    return getNewID(conn, "recordid");
  }
  
  /**
   * Retrieves a record.
   * @param conn Database connection
   * @param id The record id
   * @return The retrieved record; null if not found or an error occured.
   */
  public Record get(Connection conn, int id) {
  	Record rec = null;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(
          "SELECT creator, modifier, date_created, date_modified, pub_type, content FROM Records WHERE record_id=" + id);
      
      if (rset.next()) {
        Author creator = null;
        String sCreator = rset.getString(1);
        if (!rset.wasNull())
          creator = new Author(sCreator);
        Author modifier = null;
        String sModifier = rset.getString(2);
        if (!rset.wasNull())
          modifier = new Author(sModifier);
        Date dateCreated = rset.getDate(3);
        Date dateModified = rset.getDate(4);
        int pubType = rset.getInt(5);
        String recStr = rset.getString(6);
        rec = RecordFactory.fromBISIS35(pubType, recStr);
        if (rec != null) {
          rec.setRecordID(id);
          rec.setCreator(creator);
          rec.setModifier(modifier);
          rec.setCreationDate(dateCreated);
          rec.setLastModifiedDate(dateModified);
          rec.setPrimerci(ucitajPrimerke(conn, rec));
          rec.setGodine(ucitajGodine(conn, rec));
        }
      }
      rset.close();
      stmt.close();
      return rec;
    } catch (Exception ex) {
      log.fatal("Cannot read record with id: " + id);
      log.fatal(ex);     
      //ex.printStackTrace();      
      return null;
    }
  }
  
  /**
   * Retrieves an array of records.
   * @param conn Database connection
   * @param ids The record ids
   * @return The array of records; elements may be null if record is not found
   *   or an error occured
   */
  public Record[] get(Connection conn, int[] ids) {
    Record[] retVal = new Record[ids.length];
    try {
      PreparedStatement stmt = conn.prepareStatement(
          "SELECT creator, modifier, date_created, date_modified, pub_type, content FROM Records WHERE record_id=?");
      for (int i = 0; i < ids.length; i++) {
        stmt.setInt(1, ids[i]);
        ResultSet rset = stmt.executeQuery();
        if (rset.next()) {
          Author creator = null;
          String sCreator = rset.getString(1);
          if (!rset.wasNull())
            creator = new Author(sCreator);
          Author modifier = null;
          String sModifier = rset.getString(2);
          if (!rset.wasNull())
            modifier = new Author(sModifier);
          Date dateCreated = rset.getDate(3);
          Date dateModified = rset.getDate(4);
          int pubType = rset.getInt(5);
          String recStr = rset.getString(6);
          retVal[i] = RecordFactory.fromBISIS35(pubType, recStr);
          if (retVal[i] != null) {
            retVal[i].setRecordID(ids[i]);
            retVal[i].setCreator(creator);
            retVal[i].setModifier(modifier);
            retVal[i].setCreationDate(dateCreated);
            retVal[i].setLastModifiedDate(dateModified);
            retVal[i].setPrimerci(ucitajPrimerke(conn, retVal[i]));
            retVal[i].setGodine(ucitajGodine(conn, retVal[i]));
          }
        } else
          retVal[i] = null;
        rset.close();
      }
      stmt.close();
      return retVal;
    } catch (Exception ex) {
      String s = "";
      for (int i: ids)
        s += i + ",";
      log.fatal("Cannot read multiple records with id: " + s);
      log.fatal(ex);      
      return null;
    }
  }

  /**
   * Retrieves a record with an exclusive lock for editing.
   * @param conn Database connection
   * @param id Record ID
   * @param user User trying to lock the record
   * @return The locked record
   * @throws LockException if the record is already locked
   */
  public Record getAndLock(Connection conn, int id, String user) 
      throws LockException {
    String inUseBy = lock(conn, id, user);
    if (inUseBy.toLowerCase().equals(user.toLowerCase()))
      return get(conn, id);
    else
      throw new LockException(inUseBy);
  }
  
  /**
   * Locks the given record.
   * @param conn Database connection
   * @param id Record ID
   * @param user User trying to lock the record
   * @return The user that owns the lock
   */
  public String lock(Connection conn, int id, String user) {
    String inUseBy = null;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT in_use_by FROM Records WHERE record_id=" + id + " FOR UPDATE");
      if (rset.next()) {
        inUseBy = rset.getString(1);
        if (inUseBy == null || rset.wasNull()) {
          PreparedStatement pstmt = conn.prepareStatement("UPDATE Records SET in_use_by=? WHERE record_id=?");
          pstmt.setString(1, user);
          pstmt.setInt(2, id);
          pstmt.executeUpdate();
          pstmt.close();
          inUseBy = user;
        }
      }
      rset.close();
      stmt.close();
      conn.commit();
      return inUseBy;
    } catch (SQLException ex) {
      log.fatal("User " + user + " Cannot lock record with id: " + id);
      log.fatal(ex);
      return null;
    }
  }
  
  /**
   * Releases the exclusive lock from the given record.
   * @param conn Database connection
   * @param id Record ID
   */
  public void unlock(Connection conn, int id) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("UPDATE Records SET in_use_by=NULL WHERE record_id=" + id);
      stmt.close();
      conn.commit();
    } catch (SQLException ex) {
      log.fatal("Cannot unlock record with id: " + id);
      log.fatal(ex);
    }
  }

  /**
   * Adds a new record to the database.
   * @param conn Database connection
   * @param rec Record to add
   * @return true if successful
   * @throws SQLException
   */
  public Record  add(Connection conn, Record rec) {
    try {
      PreparedStatement stmt = conn.prepareStatement(
          "INSERT INTO Records (record_id, pub_type, creator, modifier, date_created, date_modified, archived, in_use_by, content) VALUES (?, ?, ?, ?, ?, ?, 0, NULL, ?)");
      stmt.setInt(1, rec.getRecordID());
      stmt.setInt(2, rec.getPubType());
      if (rec.getCreator() == null)
        stmt.setNull(3, Types.VARCHAR);
      else
        stmt.setString(3, rec.getCreator().getCompact());
      if (rec.getModifier() == null)
        stmt.setNull(4, Types.VARCHAR);
      else
        stmt.setString(4, rec.getModifier().getCompact());
      if (rec.getCreationDate() == null)
        stmt.setNull(5, Types.DATE);
      else
        stmt.setDate(5, new java.sql.Date(rec.getCreationDate().getTime()));
      if (rec.getLastModifiedDate() == null)
        stmt.setNull(6, Types.DATE);
      else
        stmt.setDate(6, new java.sql.Date(rec.getLastModifiedDate().getTime()));
      stmt.setString(7, RecordFactory.toBISIS35(rec.getPubType(), rec));
      stmt.executeUpdate();
      stmt.close();   
      dodajPrimerke(conn, rec);
      dodajGodine(conn, rec);
      return rec;
    } catch (SQLException ex) {
      ex.printStackTrace();
      log.fatal(ex);
      log.fatal("Cannot add record: " + rec);
      //PrepisReport.upisi("Neispravan zapis");
      //PrepisReport.unesiZapis(rec);
      return null;
    }
  }

  /**
   * Updates the record in the database.
   * @param conn Database connection
   * @param rec Record to update
   * @return true if successful
   */
  public Record update(Connection conn, Record rec) {
    try {
      PreparedStatement stmt = conn.prepareStatement(
          "UPDATE Records SET pub_type=?, creator=?, modifier=?, date_created=?, date_modified=?, archived=0, in_use_by=NULL, content=? WHERE record_id=?");
      stmt.setInt(1, rec.getPubType());
      if (rec.getCreator() == null)
        stmt.setNull(2, Types.VARCHAR);
      else
        stmt.setString(2, rec.getCreator().getCompact());
      if (rec.getModifier() == null)
        stmt.setNull(3, Types.VARCHAR);
      else
        stmt.setString(3, rec.getModifier().getCompact());
      if (rec.getCreationDate() == null)
        stmt.setNull(4, Types.DATE);
      else
        stmt.setDate(4, new java.sql.Date(rec.getCreationDate().getTime()));
      if (rec.getLastModifiedDate() == null)
        stmt.setNull(5, Types.DATE);
      else
        stmt.setDate(5, new java.sql.Date(rec.getLastModifiedDate().getTime()));
      stmt.setString(6, RecordFactory.toBISIS35(rec.getPubType(), rec));
      stmt.setInt(7, rec.getRecordID());
      stmt.executeUpdate();
      stmt.close();      
      azurirajPrimerke(conn, rec);
      azurirajGodine(conn, rec);      
      return rec;
    } catch (SQLException ex) {      
      log.fatal("Cannot update record: " + rec);
      log.fatal(ex);
      return null;
    }
  }

  /**
   * Deletes a record from the database.
   * @param conn Database connection
   * @param rec Record to delete
   * @return true if successful
   */
  public boolean delete(Connection conn, Record rec) {
    return delete(conn, rec.getRecordID());
  }
  
  /**
   * Deletes a record from the database.
   * @param conn Database connection
   * @param id Record ID
   * @return true if successful
   */
  public boolean delete(Connection conn, int id) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("DELETE FROM Records WHERE record_id=" + id);
      stmt.close();
      return true;
    } catch (SQLException ex) {
      log.fatal("Cannot delete record with id: " + id);
      log.fatal(ex);
      return false;
    }
  }
  
  public int getNewID(Connection conn, String counter) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(
          "SELECT counter_value FROM Counters WHERE counter_name='"+counter+"' FOR UPDATE");
      int recID = 0;
      if(rset.next()){
	      recID = rset.getInt(1) + 1;
	      rset.close();
	      stmt.executeUpdate(
	          "UPDATE Counters SET counter_value=counter_value+1 WHERE counter_name='"+counter+"'");
      }
	    stmt.close();
	    conn.commit();      
      return recID;      
    } catch (Exception ex) {
    	//ex.printStackTrace();
    	log.fatal("Cannot create new id, counter name = "+counter);
      log.fatal(ex);
      return -1;
    }
  }
  
  private List<Primerak> ucitajPrimerke(Connection conn, Record rec)
      throws SQLException {
    List<Primerak> retVal = new ArrayList<Primerak>();
    PreparedStatement pstmt = conn.prepareStatement(SELECT_PRIMERAK);
    pstmt.setInt(1, rec.getRecordID());
    ResultSet rset = pstmt.executeQuery();
    while (rset.next()) {
      Primerak p = new Primerak();
      p.setPrimerakID(rset.getInt(1));
      p.setSigFormat(rset.getString(2));
      p.setSigPodlokacija(rset.getString(3));
      p.setSigIntOznaka(rset.getString(4));
      p.setOdeljenje(rset.getString(5));
      p.setNacinNabavke(rset.getString(6));
      p.setPovez(rset.getString(7));
      p.setStatus(rset.getString(8));
      p.setDatumStatusa(rset.getDate(9));
      p.setInvBroj(rset.getString(10));
      p.setDatumRacuna(rset.getDate(11));
      p.setBrojRacuna(rset.getString(12));
      p.setDobavljac(rset.getString(13));
      p.setCena(rset.getBigDecimal(14));
      p.setFinansijer(rset.getString(15));
      p.setUsmeravanje(rset.getString(16));
      p.setSigDublet(rset.getString(17));
      p.setSigNumerusCurens(rset.getString(18));
      p.setSigUDK(rset.getString(19));
      p.setDatumInventarisanja(rset.getDate(20));
      p.setVersion(rset.getInt(21));
      p.setNapomene(rset.getString(22));
      p.setDostupnost(rset.getString(23));
      p.setStanje(rset.getInt(24));
      p.setInventator(rset.getString(25));
      retVal.add(p);
    }
    rset.close();
    pstmt.close();
    return retVal;
  }

  private int dodajPrimerke(Connection conn, Record rec) throws SQLException {
    int badCount = 0;
    PreparedStatement pstmt = conn.prepareStatement(INSERT_PRIMERAK);
    for (Primerak p : rec.getPrimerci())
    	try{   		
    			insertPrimerak(conn, pstmt, rec, p);
    	}catch(SQLException e){    		
    		//e.printStackTrace();
        log.fatal(e);
    		log.fatal("Cannot add/update primerak: "
    				+p.toString()+" recordId="+rec.getRecordID()+".");
        badCount++;       
    	}
    pstmt.close();
    return badCount;
  }
  
  private void azurirajPrimerke(Connection conn, Record rec) 
      throws SQLException {
    PreparedStatement updateThisVersion = conn.prepareStatement(
        UPDATE_PRIMERAK_THIS_VERSION);
    PreparedStatement updateOldVersion = conn.prepareStatement(
        UPDATE_PRIMERAK_OLD_VERSION);
    PreparedStatement insert = conn.prepareStatement(INSERT_PRIMERAK);
    for (Primerak p : rec.getPrimerci())
    	try{
	      if (p.getPrimerakID() != 0)	      	
	        updatePrimerak(conn, updateThisVersion, updateOldVersion, rec, p);      
	      else	      	
	      	insertPrimerak(conn, insert, rec, p);
    	}catch(Exception e){    		
    		log.fatal("Cannot add/update primerak: "
    				+p.toString()+" recordId="+rec.getRecordID()+".");
    		log.fatal(e);  
    		//e.printStackTrace();
    	}
    updateThisVersion.close();
    updateOldVersion.close();
    obrisiObrisanePrimerke(conn, rec);    
  }
  
  private void insertPrimerak(Connection conn, PreparedStatement pstmt,
      Record rec, Primerak p) throws SQLException {  	
    pstmt.clearParameters();
    int primerakID = getNewID(conn, "primerakid");
    p.setPrimerakID(primerakID);
    pstmt.setInt(1, primerakID);
    pstmt.setInt(2, rec.getRecordID());
    pstmt.setString(3, p.getSigFormat());
    pstmt.setString(4, p.getSigPodlokacija());
    pstmt.setString(5, p.getSigIntOznaka());
    pstmt.setString(6, p.getOdeljenje());
    pstmt.setString(7, p.getNacinNabavke());
    pstmt.setString(8, p.getPovez());    
    pstmt.setString(9, p.getStatus());
    pstmt.setDate(10, p.getDatumStatusa()== null ? null : new java.sql.Date(p.getDatumStatusa().getTime()));
    pstmt.setString(11, p.getInvBroj());
    pstmt.setDate(12, p.getDatumRacuna() == null ? null : new java.sql.Date(p.getDatumRacuna().getTime()));
    pstmt.setString(13, p.getBrojRacuna());
    pstmt.setString(14, p.getDobavljac());
    pstmt.setBigDecimal(15, p.getCena());
    pstmt.setString(16, p.getFinansijer());
    pstmt.setString(17, p.getUsmeravanje());
    pstmt.setString(18, p.getSigDublet());
    pstmt.setString(19, p.getSigNumerusCurens());
    pstmt.setString(20, p.getSigUDK());
    pstmt.setDate(21, p.getDatumInventarisanja() == null ? null : new java.sql.Date(p.getDatumInventarisanja().getTime()));
    pstmt.setInt(22, p.getVersion());
    pstmt.setString(23,p.getNapomene());
    pstmt.setString(24,p.getDostupnost());
    pstmt.setInt(25, p.getStanje());
    pstmt.setString(26, p.getInventator());
    pstmt.executeUpdate();
  }
  
  private void updatePrimerak(Connection conn, PreparedStatement thisVersion, 
      PreparedStatement oldVersion, Record rec, Primerak p) throws SQLException {   
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(
        "SELECT version FROM Primerci WHERE Primerak_ID=" + p.getPrimerakID() +
        " FOR UPDATE");
    if (rs.next()) {
      int version = rs.getInt(1);
      if (version > p.getVersion()) {
        p.setVersion(version + 1);
        oldVersion.clearParameters();
        oldVersion.setString(1, p.getSigFormat());
        oldVersion.setString(2, p.getSigPodlokacija());
        oldVersion.setString(3, p.getSigIntOznaka());
        oldVersion.setString(4, p.getOdeljenje());
        oldVersion.setString(5, p.getNacinNabavke());
        oldVersion.setString(6, p.getPovez());
        oldVersion.setString(7, p.getStatus());
        oldVersion.setDate(8, p.getDatumStatusa()== null ? null : new java.sql.Date(p.getDatumStatusa().getTime()));
        oldVersion.setString(9, p.getInvBroj());
        oldVersion.setDate(10, p.getDatumRacuna() == null ? null : new java.sql.Date(p.getDatumRacuna().getTime()));
        oldVersion.setString(11, p.getBrojRacuna());
        oldVersion.setString(12, p.getDobavljac());
        oldVersion.setBigDecimal(13, p.getCena());
        oldVersion.setString(14, p.getFinansijer());
        oldVersion.setString(15, p.getUsmeravanje());
        oldVersion.setString(16, p.getSigDublet());
        oldVersion.setString(17, p.getSigNumerusCurens());
        oldVersion.setString(18, p.getSigUDK());
        oldVersion.setDate(19, p.getDatumInventarisanja() == null ? null : new java.sql.Date(p.getDatumInventarisanja().getTime()));
        oldVersion.setInt(20, p.getVersion());        
        oldVersion.setString(21, p.getNapomene());
        oldVersion.setString(22, p.getDostupnost());
        oldVersion.setInt(23,p.getStanje());
        oldVersion.setString(24, p.getInventator());
        oldVersion.setInt(25, p.getPrimerakID());
        oldVersion.executeUpdate();
      } else {
        thisVersion.clearParameters();
        thisVersion.setString(1, p.getSigFormat());
        thisVersion.setString(2, p.getSigPodlokacija());
        thisVersion.setString(3, p.getSigIntOznaka());
        thisVersion.setString(4, p.getOdeljenje());
        thisVersion.setString(5, p.getNacinNabavke());
        thisVersion.setString(6, p.getPovez());
        thisVersion.setString(7, p.getStatus());
        thisVersion.setDate(8, p.getDatumStatusa()== null ? null : new java.sql.Date(p.getDatumStatusa().getTime()));
        thisVersion.setString(9, p.getInvBroj());
        thisVersion.setDate(10, p.getDatumRacuna() == null ? null : new java.sql.Date(p.getDatumRacuna().getTime()));
        thisVersion.setString(11, p.getBrojRacuna());
        thisVersion.setString(12, p.getDobavljac());
        thisVersion.setBigDecimal(13, p.getCena());
        thisVersion.setString(14, p.getFinansijer());
        thisVersion.setString(15, p.getUsmeravanje());
        thisVersion.setString(16, p.getSigDublet());
        thisVersion.setString(17, p.getSigNumerusCurens());
        thisVersion.setString(18, p.getSigUDK());
        thisVersion.setDate(19, p.getDatumInventarisanja() == null ? null : new java.sql.Date(p.getDatumInventarisanja().getTime()));        
        thisVersion.setString(20, p.getNapomene());
        thisVersion.setString(21, p.getDostupnost());
        thisVersion.setInt(22, p.getStanje());
        thisVersion.setString(23, p.getInventator());
        thisVersion.setInt(24, p.getPrimerakID());
        thisVersion.executeUpdate();
        p.setVersion(p.getVersion() + 1);
      }
    } else {
    }
    rs.close();
    stmt.close();  
  }
  
  private void obrisiObrisanePrimerke(Connection conn, Record rec)
      throws SQLException {
    PreparedStatement delete = conn.prepareStatement(
        "DELETE FROM Primerci WHERE primerak_id=?");
    List<Integer> pids = new ArrayList<Integer>();
    Statement stmt = conn.createStatement();
    ResultSet rset = stmt.executeQuery(
        "SELECT primerak_id FROM Primerci WHERE record_id="+rec.getRecordID());
    while (rset.next()) {
      pids.add(rset.getInt(1));
    }
    rset.close();
    stmt.close();
    for (int id : pids) {
      boolean found = false;
      for (Primerak p : rec.getPrimerci())
        if (p.getPrimerakID() == id) {
          found = true;
          break;
        }
      if (!found) {
        delete.setInt(1, id);
        delete.executeUpdate();
      }
    }
    delete.close();
  }
  
  private List<Godina> ucitajGodine(Connection conn, Record rec)
      throws SQLException {
    List<Godina> retVal = new ArrayList<Godina>();
    PreparedStatement pstmt1 = conn.prepareStatement(SELECT_GODINA);
    PreparedStatement pstmt2 = conn.prepareStatement(SELECT_SVESKA);
    pstmt1.setInt(1, rec.getRecordID());
    ResultSet rset1 = pstmt1.executeQuery();
    while (rset1.next()) {
      Godina g = new Godina();
      g.setGodinaID(rset1.getInt(1));
      g.setSigFormat(rset1.getString(2));
      g.setSigPodlokacija(rset1.getString(3));
      g.setSigIntOznaka(rset1.getString(4));
      g.setOdeljenje(rset1.getString(5));
      g.setNacinNabavke(rset1.getString(6));
      g.setPovez(rset1.getString(7));
      g.setInvBroj(rset1.getString(8));
      g.setDatumRacuna(rset1.getDate(9));
      g.setBrojRacuna(rset1.getString(10));
      g.setDobavljac(rset1.getString(11));
      g.setCena(rset1.getBigDecimal(12));
      g.setFinansijer(rset1.getString(13));
      g.setSigDublet(rset1.getString(14));
      g.setSigNumerusCurens(rset1.getString(15));
      g.setSigNumeracija(rset1.getString(16));
      g.setSigUDK(rset1.getString(17));
      g.setGodiste(rset1.getString(18));
      g.setGodina(rset1.getString(19));
      g.setBroj(rset1.getString(20));
      g.setDatumInventarisanja(rset1.getDate(21));
      g.setNapomene(rset1.getString(22));
      g.setDostupnost(rset1.getString(23));      
      pstmt2.clearParameters();
      pstmt2.setInt(1, g.getGodinaID());
      ResultSet rset2 = pstmt2.executeQuery();
      while (rset2.next()) {
        Sveska s = new Sveska();
        g.add(s);
        s.setSveskaID(rset2.getInt(1));
        s.setStatus(rset2.getString(2));
        s.setDatumStatusa(rset2.getDate(3));
        s.setInvBroj(rset2.getString(4));
        s.setSignatura(rset2.getString(5));
        s.setBrojSveske(rset2.getString(6));
        s.setCena(rset2.getBigDecimal(7));
        s.setStanje(rset2.getInt(8));
        s.setVersion(rset2.getInt(9)); 
        s.setInventator(rset2.getString(10));
        s.setKnjiga(rset2.getString(11));
        
      }
      rset2.close();
      retVal.add(g);
    }
    rset1.close();
    pstmt1.close();
    pstmt2.close();
    return retVal;
  }

  private int dodajGodine(Connection conn, Record rec) throws SQLException {
    int badCount = 0;
    PreparedStatement godina = conn.prepareStatement(INSERT_GODINA);
    PreparedStatement sveska = conn.prepareStatement(INSERT_SVESKA);
    for (Godina g : rec.getGodine())
    	try{    		
    			insertGodina(conn, godina, sveska, rec, g);
    	}catch(Exception e){    		
      log.fatal(e);
    		log.fatal("Cannot add/update godina: "
    				+g.toString()+" recordId="+rec.getRecordID()+".");
    		//e.printStackTrace();
        badCount++;       
    	}
      
    godina.close();
    sveska.close();
    return badCount;
  }
  
  private void azurirajGodine(Connection conn, Record rec) throws SQLException {
    PreparedStatement insertGodina = conn.prepareStatement(INSERT_GODINA);
    PreparedStatement insertSveska = conn.prepareStatement(INSERT_SVESKA);
    PreparedStatement updateGodina = conn.prepareStatement(UPDATE_GODINA);
    PreparedStatement updateSveskaThis = conn.prepareStatement(
        UPDATE_SVESKA_THIS_VERSION);
    PreparedStatement updateSveskaOld = conn.prepareStatement(
        UPDATE_SVESKA_OLD_VERSION);
    for (Godina g : rec.getGodine()) {
    	try{
	      if (g.getGodinaID() != 0)
	        updateGodina(conn, updateGodina, updateSveskaThis, updateSveskaOld, 
	            insertSveska, rec, g);        
	      else	      	
	      	insertGodina(conn, insertGodina, insertSveska, rec, g);
    	}catch(Exception e){    	
    		log.fatal("Cannot add/update godina: "
    				+g.toString()+" recordId="+rec.getRecordID()+".");
    		log.fatal(e);  
    		//e.printStackTrace();
    	}
      
    }
    insertGodina.close();
    insertSveska.close();
    updateGodina.close();
    updateSveskaThis.close();
    updateSveskaOld.close();
    obrisiObrisaneGodine(conn, rec);
  }
  
  private void insertGodina(Connection conn, PreparedStatement godina,
      PreparedStatement sveska, Record rec, Godina g) throws SQLException {
    int godinaID = getNewID(conn, "godinaid");
    g.setGodinaID(godinaID);
    godina.clearParameters();
    godina.setInt(1, godinaID);
    godina.setInt(2, rec.getRecordID());
    godina.setString(3, g.getSigFormat());
    godina.setString(4, g.getSigPodlokacija());
    godina.setString(5, g.getSigIntOznaka());
    godina.setString(6, g.getOdeljenje());
    godina.setString(7, g.getNacinNabavke());
    godina.setString(8, g.getPovez());
    godina.setString(9, g.getInvBroj());
    godina.setDate(10, g.getDatumRacuna() == null ? null : new java.sql.Date(g.getDatumRacuna().getTime()));
    godina.setString(11, g.getBrojRacuna());
    godina.setString(12, g.getDobavljac());
    godina.setBigDecimal(13, g.getCena());
    godina.setString(14, g.getFinansijer());
    godina.setString(15, g.getSigDublet());
    godina.setString(16, g.getSigNumerusCurens());
    godina.setString(17, g.getSigUDK());
    godina.setString(18, g.getSigNumeracija());
    godina.setString(19, g.getGodiste());
    godina.setString(20, g.getGodina());
    godina.setString(21, g.getBroj());
    godina.setDate(22, g.getDatumInventarisanja() == null ? null : new java.sql.Date(g.getDatumInventarisanja().getTime()));
    godina.setString(23, g.getDostupnost());
    godina.setString(24, g.getInventator());
    godina.setString(25,g.getNapomene());
    godina.executeUpdate();
    for (Sveska s : g.getSveske()) {
      int sveskaID = getNewID(conn, "sveskaid");
      s.setSveskaID(sveskaID);
      sveska.clearParameters();
      sveska.setInt(1, sveskaID);
      sveska.setString(2, s.getStatus());
      sveska.setDate(3, s.getDatumStatusa()== null ? null : new java.sql.Date(s.getDatumStatusa().getTime()));
      sveska.setInt(4, godinaID);
      sveska.setString(5, s.getInvBroj());      
      sveska.setString(6, s.getSignatura());
      sveska.setString(7, s.getBrojSveske());
      sveska.setBigDecimal(8, s.getCena());
      sveska.setInt(9, s.getStanje());
      sveska.setInt(10, s.getVersion());
      sveska.setString(11, s.getInventator());
      sveska.setString(12, s.getKnjiga());
      sveska.executeUpdate();
    }
  }
  
  private void updateGodina(Connection conn, PreparedStatement godina,
      PreparedStatement sveskaThis, PreparedStatement sveskaOld,
      PreparedStatement insertSveska, Record rec, Godina g) throws SQLException {
    godina.clearParameters();
    godina.setString(1, g.getSigFormat());
    godina.setString(2, g.getSigPodlokacija());
    godina.setString(3, g.getSigIntOznaka());
    godina.setString(4, g.getOdeljenje());
    godina.setString(5, g.getNacinNabavke());
    godina.setString(6, g.getPovez());
    godina.setString(7, g.getInvBroj());
    godina.setDate(8, g.getDatumRacuna() == null ? null : new java.sql.Date(g.getDatumRacuna().getTime()));
    godina.setString(9,g.getBrojRacuna());
    godina.setString(10, g.getDobavljac());
    godina.setBigDecimal(11, g.getCena());
    godina.setString(12, g.getFinansijer());
    godina.setString(13, g.getSigDublet());
    godina.setString(14, g.getSigNumerusCurens());
    godina.setString(15, g.getSigNumeracija());
    godina.setString(16, g.getSigUDK());
    godina.setString(17, g.getGodiste());
    godina.setString(18, g.getGodina());
    godina.setString(19, g.getBroj());
    godina.setDate(20, g.getDatumInventarisanja() == null ? null : new java.sql.Date(g.getDatumInventarisanja().getTime()));
    godina.setString(21, g.getNapomene());
    godina.setString(22, g.getDostupnost());
    godina.setString(23, g.getInventator());
    godina.setInt(24, g.getGodinaID());
    godina.executeUpdate();
    for (Sveska s : g.getSveske()) {    	
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(
          "SELECT version FROM Sveske WHERE sveska_id="+s.getSveskaID() + 
          " FOR UPDATE");
      if (rset.next()) {
        int version = rset.getInt(1);
        if (version != s.getVersion()) {
          sveskaOld.clearParameters();
          sveskaOld.setString(1, s.getStatus());
          sveskaOld.setDate(2, s.getDatumStatusa()== null ? null : new java.sql.Date(s.getDatumStatusa().getTime()));
          sveskaOld.setString(3, s.getInvBroj());
          sveskaOld.setString(4, s.getSignatura());
          sveskaOld.setString(5, s.getBrojSveske());
          sveskaOld.setBigDecimal(6, s.getCena());
          sveskaOld.setInt(7, s.getStanje());
          sveskaOld.setInt(8, version+1);
          sveskaOld.setString(9, s.getInventator());
          sveskaOld.setString(10, s.getKnjiga());
          sveskaOld.setInt(11,s.getSveskaID());
          sveskaOld.executeUpdate();
        } else {
          sveskaThis.clearParameters();
          sveskaThis.setString(1, s.getStatus());
          sveskaThis.setDate(2, s.getDatumStatusa()== null ? null : new java.sql.Date(s.getDatumStatusa().getTime()));
          sveskaThis.setString(3, s.getInvBroj());
          sveskaThis.setString(4, s.getSignatura());
          sveskaThis.setString(5, s.getBrojSveske());
          sveskaThis.setBigDecimal(6, s.getCena());
          sveskaThis.setInt(7, s.getStanje());
          sveskaThis.setString(8, s.getInventator());
          sveskaThis.setString(9, s.getKnjiga());
          sveskaThis.setInt(10, s.getSveskaID());
          s.setVersion(version+1);
          sveskaThis.executeUpdate();
        }
      } else {
        int sveskaID = getNewID(conn, "sveskaid");
        s.setSveskaID(sveskaID);
        insertSveska.clearParameters();
        insertSveska.setInt(1, sveskaID);
        insertSveska.setString(2, s.getStatus());
        insertSveska.setDate(3, s.getDatumStatusa()== null ? null : new java.sql.Date(s.getDatumStatusa().getTime()));
        insertSveska.setInt(4, g.getGodinaID());
        insertSveska.setString(5, s.getInvBroj());
        insertSveska.setString(6, s.getSignatura());
        insertSveska.setString(7, s.getBrojSveske());
        insertSveska.setBigDecimal(8, s.getCena());
        insertSveska.setInt(9, s.getStanje());
        insertSveska.setInt(10, s.getVersion());
        insertSveska.setString(11, s.getInventator());
        insertSveska.setString(12, s.getKnjiga());
        insertSveska.executeUpdate();
      }
      rset.close();
      stmt.close();
    }
  }
  
  private void obrisiObrisaneGodine(Connection conn, Record rec)
      throws SQLException {
    PreparedStatement deleteGodina = conn.prepareStatement(
        "DELETE FROM Godine WHERE godina_id=?");
    List<Integer> gids = new ArrayList<Integer>();
    Statement stmt = conn.createStatement();
    ResultSet rset = stmt.executeQuery(
        "SELECT godina_id FROM Godine WHERE record_id="+ rec.getRecordID());
    while (rset.next()) {
      gids.add(rset.getInt(1));
    }
    rset.close();
    stmt.close();
    Godina nijeObrisana = null;
    for (int id : gids) {
      boolean found = false;
      for (Godina g : rec.getGodine())
        if (g.getGodinaID() == id) {
          found = true;
          nijeObrisana = g;
          break;
        }
      if (!found) {
        deleteGodina.setInt(1, id);
        deleteGodina.executeUpdate();
      } else {
        obrisiObrisaneSveske(conn, rec, nijeObrisana);
      }
    }
    deleteGodina.close();
  }
  
  private void obrisiObrisaneSveske(Connection conn, Record rec, Godina g)
      throws SQLException {
    PreparedStatement deleteSveska = conn.prepareStatement(
        "DELETE FROM Sveske WHERE sveska_id=?");
    List<Integer> sids = new ArrayList<Integer>();
    Statement stmt = conn.createStatement();
    ResultSet rset = stmt.executeQuery(
        "SELECT sveska_id FROM Sveske WHERE godina_id=" + g.getGodinaID());
    while (rset.next()) {
      sids.add(rset.getInt(1));
    }
    rset.close();
    stmt.close();
    for (int id : sids) {
      boolean found = false;
      for (Sveska s : g.getSveske())
        if (s.getSveskaID() == id) {
          found = true;
          break;
        }
      if (!found) {
        deleteSveska.setInt(1, id);
        deleteSveska.executeUpdate();
      }
    }
    deleteSveska.close();
  }

  private static String INSERT_PRIMERAK = "INSERT INTO Primerci ("+
    "primerak_id, record_id, sigformat_id, podlokacija_id, "+
    "intozn_id, odeljenje_id, nacin_id, povez_id, status_id, datum_statusa, "+
    "inv_broj, datum_racuna, broj_racuna, dobavljac, cena, "+
    "finansijer, usmeravanje, sig_dublet, sig_numerus_curens, sig_udk, "+
    "datum_inventarisanja, version, napomene, dostupnost_id, stanje, inventator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  private static String UPDATE_PRIMERAK_THIS_VERSION = "UPDATE Primerci SET " +
    "sigformat_id=?, podlokacija_id=?, intozn_id=?, odeljenje_id=?, " +
    "nacin_id=?, povez_id=?, status_id=?, datum_statusa=?, inv_broj=?, datum_racuna=?, " +
    "broj_racuna=?, dobavljac=?, cena=?, finansijer=?, usmeravanje=?, " +
    "sig_dublet=?, sig_numerus_curens=?, sig_udk=?, datum_inventarisanja=?, " +
    "version=version+1, napomene=?, dostupnost_id=?, stanje=?, inventator=?  WHERE primerak_id=?";
  
  private static String UPDATE_PRIMERAK_OLD_VERSION = "UPDATE Primerci SET " +
    "sigformat_id=?, podlokacija_id=?, intozn_id=?, odeljenje_id=?, " +
    "nacin_id=?, povez_id=?, status_id=?, datum_statusa=?, inv_broj=?, datum_racuna=?, " +
    "broj_racuna=?, dobavljac=?, cena=?, finansijer=?, usmeravanje=?, " +
    "sig_dublet=?, sig_numerus_curens=?, sig_udk=?, datum_inventarisanja=?, " +
    "version=?, napomene=?, dostupnost_id=?, stanje=?, inventator=? WHERE primerak_id=?";

  private static String SELECT_PRIMERAK = "SELECT primerak_id, sigformat_id, " +
    "podlokacija_id, intozn_id, odeljenje_id, nacin_id, povez_id, status_id, " +
    "datum_statusa, inv_broj, datum_racuna, broj_racuna, dobavljac, cena, "+
    "finansijer, usmeravanje, sig_dublet, sig_numerus_curens, sig_udk, "+
    "datum_inventarisanja, version, napomene, dostupnost_id, stanje, inventator FROM Primerci WHERE record_id=?";
  
  private static String INSERT_GODINA = "INSERT INTO Godine ("+
    "godina_id, record_id, sigformat_id, podlokacija_id, "+
    "intozn_id, odeljenje_id, nacin_id, povez_id, "+
    "inv_broj, datum_racuna, broj_racuna, dobavljac, cena, "+
    "finansijer, sig_dublet, sig_numerus_curens, sig_udk, "+
    "sig_numeracija, godiste, godina, broj, datum_inventarisanja,dostupnost_id, inventator, napomene) " +
    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  private static String INSERT_SVESKA = "INSERT INTO Sveske (" +
    "sveska_id, status_id, datum_statusa, godina_id, inv_br, signatura, broj_sveske, cena, stanje, " +
    "version, inventator, knjiga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  private static String UPDATE_GODINA = "UPDATE Godine SET " +
    "sigformat_id=?, podlokacija_id=?, intozn_id=?, odeljenje_id=?, " +
    "nacin_id=?, povez_id=?, inv_broj=?, datum_racuna=?, " +
    "broj_racuna=?, dobavljac=?, cena=?, finansijer=?, sig_dublet=?, " +
    "sig_numerus_curens=?, sig_numeracija=?, sig_udk=?, godiste=?, godina=?,"+
    "broj=?, datum_inventarisanja=?, napomene=?, dostupnost_id=?, inventator=? WHERE godina_id=?";
  
  private static String UPDATE_SVESKA_THIS_VERSION = "UPDATE Sveske SET " +
    "status_id=?, datum_statusa=?, inv_br=?, signatura=?, broj_sveske=?, cena=?, stanje=?, inventator=?, knjiga=?, " +
    "version=version+1 WHERE sveska_id=?"; 

  private static String UPDATE_SVESKA_OLD_VERSION = "UPDATE Sveske SET " +
    "status_id=?, datum_statusa=?, inv_br=?, signatura=?, broj_sveske=?, " +
    "cena=?, stanje=?, version=?, inventator=?, knjiga=? WHERE sveska_id=?"; 
  
  private static String SELECT_GODINA = "SELECT godina_id, sigformat_id, " +
    "podlokacija_id, intozn_id, odeljenje_id, nacin_id, povez_id, inv_broj, " +
    "datum_racuna, broj_racuna, dobavljac, cena, finansijer, sig_dublet, " +
    "sig_numerus_curens, sig_numeracija, sig_udk, godiste, godina, broj, " +
    "datum_inventarisanja, napomene, dostupnost_id, inventator FROM Godine WHERE record_id=?";

  private static String SELECT_SVESKA = "SELECT sveska_id, status_id, datum_statusa," +
    "inv_br, signatura, broj_sveske, cena, stanje, version, inventator, knjiga FROM Sveske " +
    "WHERE godina_id=?";
  
  private static Log log = LogFactory.getLog(DBStorage.class.getName());
}
