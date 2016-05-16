package com.gint.app.bisis4.remoting;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.textsrv.DBStorage;
import com.gint.app.bisis4.textsrv.DocFile;
import com.gint.app.bisis4.textsrv.Indexer;
import com.gint.app.bisis4.textsrv.LockException;
import com.gint.app.bisis4.textsrv.Result;
import com.gint.app.bisis4.textsrv.Retriever;

public class RecordManagerImpl implements RecordManager {

  public RecordManagerImpl() {
    try {
		standalone = false;
		  Context ctx = new InitialContext();
		  dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/Bisis");
		  ConnectionFactory connectionFactory = (ConnectionFactory)
		      ctx.lookup("java:comp/env/jms/ConnectionFactory");
		  javax.jms.Connection connection = connectionFactory.createConnection();
		  session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		  producer = session.createProducer(
		      (Destination)ctx.lookup("java:comp/env/jms/queue/BisisIndexer"));
		  indexPath = InitializerServlet.getIndexPath();
		  HoldingsDataCodersJdbc.loadData(dataSource.getConnection());
	} catch (SQLException sqle) {
		log.warn(sqle);
    } catch (NamingException ex) {
      log.warn(ex);
    } catch (JMSException e) {
      log.warn(e);
    }
  }
  
 public void setIndexPath(String indexPath) {
    this.indexPath = indexPath;
  }
  
  public void setStandalone(boolean standalone) {
    this.standalone = standalone;
    if (standalone) {
      indexer = new Indexer(indexPath);
      log.info("Record Manager is running in standalone mode.");
      try {
  		HoldingsDataCodersJdbc.loadData(dataSource.getConnection());
  	} catch (SQLException e) {
  		log.error(e);
  	}
    }
  }
  
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  public int[] select1(String query, String sortPrefix)throws ParseException  {
    return new Retriever(indexPath).select(query, sortPrefix);
  }
 
  public int[] select2(Query query, String sortPrefix) {
    return new Retriever(indexPath).select(query, sortPrefix);
  }
  
  public int[] select2x(byte[] serializedQuery, String sortPrefix)  {
    return new Retriever(indexPath).select((Query)SerializationUtils.deserialize(serializedQuery), sortPrefix);
  }
  
  public int[] select3(Query query, Filter filter, String sortPrefix)  {
	    return new Retriever(indexPath).select(query, filter, sortPrefix);
  }
  public int[] select3x(byte[] serializedQuery, byte[] serializedFilter, String sortPrefix) {
	    return new Retriever(indexPath).select((Query)SerializationUtils.deserialize(serializedQuery),(Filter)SerializationUtils.deserialize(serializedFilter), sortPrefix);
  }
  public Result selectAll1(String query, String sortPrefix) throws ParseException {
	    return new Retriever(indexPath).selectAll1(query, sortPrefix);
	  }
  public Result selectAll2(Query query, String sortPrefix) {
    return new Retriever(indexPath).selectAll(query, sortPrefix);
  }
  public Result selectAll2x(byte[] serializedQuery, String sortPrefix) {
	    return new Retriever(indexPath).selectAll((Query)SerializationUtils.deserialize(serializedQuery), sortPrefix);
	  }
  public Result selectAll3(Query query, Filter filter, String sortPrefix) {
    return new Retriever(indexPath).selectAll(query, filter, sortPrefix);
  }
  public Result selectAll3x(byte[] serializedQuery, byte[] serializedFilter, String sortPrefix) {
	    return new Retriever(indexPath).selectAll((Query)SerializationUtils.deserialize(serializedQuery),(Filter)SerializationUtils.deserialize(serializedFilter), sortPrefix);
}
  public List<String> selectExp(String query, String prefix,String text) {
	    return new Retriever(indexPath).selectExpand(query, prefix,text);
}
  public Record getRecord(int recID) {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      Record rec = storage.get(conn, recID);
      conn.close();
      return rec;
    } catch (SQLException ex) {
      log.fatal(ex);
    }
    return null;
  }
  
  public List<DocFile> getDocFiles(int rn){
   try {
    Connection conn = dataSource.getConnection();
    List<DocFile> docFiles = DocFile.find(conn, rn);
    conn.close();
    return docFiles;
  } catch (SQLException ex) {
    log.fatal(ex);
  }
  return null;
  	
  }
  
  public Record[] getRecords(int[] recIDs) {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      Record[] recs = storage.get(conn, recIDs);
      conn.close();
      return recs;
    } catch (SQLException ex) {
      log.fatal(ex);
    }
    return new Record[0];
  }
  
  public Record getAndLock(int recID, String user) throws LockException {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      Record rec = storage.getAndLock(conn, recID, user);
      conn.close();
      return rec;
    } catch (SQLException ex) {
      log.fatal(ex);
    }
    return null;
  }
  
  public String lock(int recID, String user) {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      String owner = storage.lock(conn, recID, user);
      conn.close();
      return owner;
    } catch (SQLException ex) {
      log.fatal(ex);
    }
    return null;
  }
  
  public void unlock(int recID) {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      storage.unlock(conn, recID);
      conn.close();
    } catch (SQLException ex) {
      log.fatal(ex);
    }
  }
  
  public int getNewID(String counterName) {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      int id = storage.getNewID(conn, counterName);      
      conn.close();      
      return id;
    } catch (SQLException ex) {
      log.fatal(ex);
    }
    return -1;
  }
  
  public boolean add(Record rec) {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      Record r = storage.add(conn, rec);      
      if (r == null)
      	return false;
      conn.commit();
      if (standalone)
        indexer.add(rec, HoldingsDataCodersJdbc.getRashodCode());
      else
        sendMessage("add", rec, HoldingsDataCodersJdbc.getRashodCode());
      conn.close();
      return true;
    } catch (SQLException ex) {
      log.fatal(ex);
      return false;
    } catch (JMSException e) {
      log.fatal(e);
      return false;
    }
  }
  
  public Record update(Record rec) {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      Record r = storage.update(conn, rec);      
      if (r == null)
      	return null;
      conn.commit();      
      if (standalone)
        indexer.update(rec, HoldingsDataCodersJdbc.getRashodCode());
      else
        sendMessage("update", rec, HoldingsDataCodersJdbc.getRashodCode());
      conn.close();      
      return r;
    } catch (SQLException ex) {
      log.fatal(ex);
      return null;
    } catch (JMSException e) {
      log.fatal(e);
      return null;
    }
    
  }
  
  public boolean delete(Record rec) {
    return delete(rec.getRecordID());
  }
  
  public boolean delete(int recID) {
    try {
      Connection conn = dataSource.getConnection();
      DBStorage storage = new DBStorage();
      boolean status = storage.delete(conn, recID);
      conn.commit();
      conn.close();
      if (!status)
        return false;
      if (standalone)
        indexer.delete(recID);
      else
        sendMessage("delete", new Integer(recID), null);
      return true;
    } catch (SQLException ex) {
      log.fatal(ex);
      return false;
    } catch (JMSException e) {
      log.fatal(e);
      return false;
    }
  }

  public boolean reindex(int recID) {
  	Record rec = getRecord(recID);
  	if (rec == null) { 
  		log.warn("Record ID " + recID + " does not exist. Not reindexed.");
  		return false;
  	}
  	if (standalone)
  		indexer.update(rec, HoldingsDataCodersJdbc.getRashodCode());
		else
			try {
				sendMessage("update", rec, HoldingsDataCodersJdbc.getRashodCode());
			} catch (JMSException e) {
				log.fatal(e);
				log.fatal("Reindexing failed for record ID: " + recID);
			}
  	return false;
  }

  private void sendMessage(String command, Serializable obj, String rashodCode) throws JMSException {
    ObjectMessage addMessage = session.createObjectMessage();
    addMessage.setStringProperty("operation", command);
    addMessage.setStringProperty("rashodCode", rashodCode);
    addMessage.setObject(obj);
    producer.send(addMessage);
  }
  
  private DataSource dataSource;
  private Session session;
  private MessageProducer producer;
  private String indexPath;
  private boolean standalone;
  private Indexer indexer;
  
  private static Log log = LogFactory.getLog(RecordManagerImpl.class);
  
}

