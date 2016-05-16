package com.gint.app.bisis4.indexer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.textsrv.BulkIndexer;
import com.gint.app.bisis4.textsrv.DBStorage;
import com.gint.app.bisis4.textsrv.DocFile;

public class ReindexApp {
	public static void main(String[] args) throws Exception {
		  
		  if(args.length!=4){
			  System.out.println("reindex: <address> <database> <user> <password>");
			  return;
		  }
	    String address=args[0];
	    String database=args[1];
	    String user=args[2];
	    String password=args[3];
	    String indexpath;
	    String osname = System.getProperty("os.name");
		if (osname.startsWith("Windows")) {
      indexpath = "C:/lucene-index";
		} else {
      indexpath = "/opt/lucene-index";
		}
	    // delete old index
	    File indexDir = new File(indexpath);
	    if (!indexDir.isDirectory())
	      return;
	    File[] files = indexDir.listFiles();
	    for (File file : files)
	      file.delete();
	    
	    // connect to database
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn = DriverManager.getConnection(
	        "jdbc:mysql://"+address+"/"+database+"?characterEncoding=UTF-8", user, password);
	    conn.setAutoCommit(false);

	    DBStorage storage = new DBStorage();
	    BulkIndexer indexer = new BulkIndexer(indexpath);
	    
	    HoldingsDataCodersJdbc.loadData(conn);
	    
	    // for each record in database: add to index
	    Statement stmt = conn.createStatement();
	    ResultSet rset = stmt.executeQuery("SELECT record_id FROM Records");
	    int count = 0;
	    while (rset.next()) {
	      int recid = rset.getInt(1);
	      Record rec = storage.get(conn, recid);
	      
	      if (rec != null){
		      indexer.add(rec, HoldingsDataCodersJdbc.getRashodCode());
		      List <DocFile> docFiles=DocFile.find(conn, recid);
		      // dodato kako bi se u indeksu dodala informacija o tipu elektronskog resursa koji je vezan za zapis
		      for(DocFile df:docFiles){
		    	  indexer.addField(Integer.toString(recid), "XY", df.getContentType().toLowerCase());
		      }
		      if (++count % 1000 == 0)
		        System.out.println("Reindexed records: " + count);
	      }
	    }
	    rset.close();
	    stmt.close();
	    conn.close();
	    indexer.optimize();
	    indexer.close();
	    System.out.println("Total reindexed records: " + count);
	  }

}
