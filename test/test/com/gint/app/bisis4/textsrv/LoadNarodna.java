package test.com.gint.app.bisis4.textsrv;

import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.textsrv.BulkIndexer;
import com.gint.app.bisis4.textsrv.DBStorage;

public class LoadNarodna {
  public static void main(String[] args) throws Exception {
	  if(args.length!=6){
		  System.out.println("index: <address> <database> <user> <password> <recordpath> <indexpath>");
		  return;
	  }
    String address=args[0];
    String database=args[1];
    String user=args[2];
    String password=args[3];
    String recordpath=args[4];
    String indexpath=args[5];
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://"+address+"/"+database+"?characterEncoding=UTF-8", user, password);
  
    conn.setAutoCommit(false);
    HoldingsDataCodersJdbc.loadData(conn);
    RandomAccessFile in = new RandomAccessFile(recordpath, "r");
    String line = "";
    DBStorage storage = new DBStorage();
    BulkIndexer indexer = new BulkIndexer(indexpath); 
    int count = 0;
    long parsingTime = 0;
    long storingTime = 0;
    long indexingTime = 0;
    StopWatch clock = new StopWatch();
    clock.start();
    while ((line = in.readLine()) != null) {
      line = line.trim();
      if ("".equals(line))
        continue;
      line = line.substring(10);
      Record rec = RecordFactory.fromUNIMARC(0, line);
      rec.setRecordID(storage.getNewRecordID(conn));
      rec.setCreator(new Author("mbranko", "ftn"));
      rec.setCreationDate(new Date());
      clock.stop();
      parsingTime += clock.getTime();
      clock.reset();
      clock.start();
      storage.add(conn, rec);
      conn.commit();
      clock.stop();
      storingTime += clock.getTime();
      clock.reset();
      clock.start();
      indexer.add(rec, HoldingsDataCodersJdbc.getRashodCode());
      clock.stop();
      indexingTime += clock.getTime();
      if (++count % 1000 == 0)
        System.out.println(count);
      clock.reset();
      clock.start();
    }
    indexer.close();
    clock.stop();
    System.out.println("Records processed: " + count);
    System.out.println("Parsing time: " + 
        DurationFormatUtils.formatDurationHMS(parsingTime));
    System.out.println("Storage time: " + 
        DurationFormatUtils.formatDurationHMS(storingTime));
    System.out.println("Indexing time: " + 
        DurationFormatUtils.formatDurationHMS(indexingTime));
  }
}
