package test.com.gint.app.bisis4.textsrv;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.textsrv.BulkIndexer;
import com.gint.app.bisis4.textsrv.DBStorage;

public class Reindex {

  public static void main(String[] args) throws Exception {
	  
	  if(args.length!=5){
		  System.out.println("reindex: <address> <database> <user> <password> <indexpath>");
		  return;
	  }
    String address=args[0];
    String database=args[1];
    String user=args[2];
    String password=args[3];
    String indexpath=args[4];
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
    HoldingsDataCodersJdbc.loadData(conn);
    
    DBStorage storage = new DBStorage();
    BulkIndexer indexer = new BulkIndexer(indexpath);
    
    // for each record in database: add to index
    Statement stmt = conn.createStatement();
    ResultSet rset = stmt.executeQuery("SELECT record_id FROM Records");
    int count = 0;
    while (rset.next()) {
      int recid = rset.getInt(1);
      Record rec = storage.get(conn, recid);
      indexer.add(rec, HoldingsDataCodersJdbc.getRashodCode());
      if (++count % 1000 == 0)
        System.out.println("Reindexed records: " + count);
    }
    rset.close();
    stmt.close();
    conn.close();
    indexer.optimize();
    indexer.close();
    System.out.println("Total reindexed records: " + count);
  }

}
