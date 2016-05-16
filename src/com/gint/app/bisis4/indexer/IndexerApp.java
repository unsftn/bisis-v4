package com.gint.app.bisis4.indexer;

import java.io.File;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.prepis.PrepisAPV;
import com.gint.app.bisis4.prepis.PrepisAR;
import com.gint.app.bisis4.prepis.PrepisBGB;
import com.gint.app.bisis4.prepis.PrepisDIF;
import com.gint.app.bisis4.prepis.PrepisEKSU;
import com.gint.app.bisis4.prepis.PrepisFTN;
import com.gint.app.bisis4.prepis.PrepisIB;
import com.gint.app.bisis4.prepis.PrepisIG;
import com.gint.app.bisis4.prepis.PrepisIH;
import com.gint.app.bisis4.prepis.PrepisKI;
import com.gint.app.bisis4.prepis.PrepisMZ;
import com.gint.app.bisis4.prepis.PrepisNS;
import com.gint.app.bisis4.prepis.PrepisPA;
import com.gint.app.bisis4.prepis.PrepisPFSO;
import com.gint.app.bisis4.prepis.PrepisPatrijarsija;
import com.gint.app.bisis4.prepis.PrepisPolj;
import com.gint.app.bisis4.prepis.PrepisReport;
import com.gint.app.bisis4.prepis.PrepisSA;
import com.gint.app.bisis4.prepis.PrepisSZPB;
import com.gint.app.bisis4.prepis.PrepisTF;
import com.gint.app.bisis4.prepis.PrepisTFZR;
import com.gint.app.bisis4.prepis.PrepisTSSU;
import com.gint.app.bisis4.prepis.PrepisUNI;
import com.gint.app.bisis4.prepis.PrepisVladimirovci;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.textsrv.BulkIndexer;
import com.gint.app.bisis4.textsrv.DBStorageImport;
import com.gint.util.string.StringUtils;

public class IndexerApp {
	
	//private static Log log = LogFactory.getLog(IndexerApp.class.getName());
	
	public static Connection conn;
	
	public static void main(String[] args) throws Exception {
		if (args.length < 5  ||
			 (args.length==6 && 
					(!args[5].equals("im")   &&
					 !args[5].equals("ns")   &&
					 !args[5].equals("pa")   &&
					 !args[5].equals("ki")   &&
					 !args[5].equals("zr")   &&
					 !args[5].equals("bg")   &&
					 !args[5].equals("sa")   &&
					 !args[5].equals("zr")   &&
					 !args[5].equals("tfzr") &&
					 !args[5].equals("pat")  &&
					 !args[5].equals("ig")   &&
					 !args[5].equals("apv")  &&
					 !args[5].equals("ftn")  &&
					 !args[5].equals("ftn-bisis")  &&
					 !args[5].equals("uni")  &&
					 !args[5].equals("vlad") &&
					 !args[5].equals("eksu") &&
					 !args[5].equals("ib")   && 	
					 !args[5].equals("tssu") &&
					 !args[5].equals("dif")  &&					 
					 !args[5].equals("ih")   &&
					 !args[5].equals("polj") &&
					 !args[5].equals("szpb") &&
					 !args[5].equals("pfso") &&
					 !args[5].equals("mz")   &&
					 !args[5].equals("ar")   &&
					 !args[5].equals("tf")
					 ))) {			
			System.out
					.println("index: <address> <database> <user> <password> <recordpath> [tf] [line-count]");			
			return;
		}		
		String address = args[0];
		String database = args[1];
		String user = args[2];
		String password = args[3];
		String recordpath = args[4];		
		String library = "";
		if(args.length==6){
			library = args[5];
			PrepisReport.setLocaleStr(library);
		}else		
			PrepisReport.setLocaleStr("");
		
		String indexpath;
		boolean dbaccess;
		String osname = System.getProperty("os.name");
		if (osname.equals("Linux")) {
			indexpath = "/opt/lucene-index";
		} else {
			indexpath = "C:/lucene-index";
		}
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("|Connecting to database...");
		conn = DriverManager.getConnection("jdbc:mysql://" + address
				+ "/" + database + "?characterEncoding=UTF-8", user, password);

		conn.setAutoCommit(false);
		HoldingsDataCodersJdbc.loadData(conn);
		RandomAccessFile in = new RandomAccessFile(recordpath, "r");

    int totalCount = 0;
		if (args.length > 6) {
		  totalCount = Integer.parseInt(args[6]);
		} else {
	    System.out.println("Counting records...");
	    while (in.readLine() != null) {
	      totalCount++;
	      if (totalCount % 1000 == 0)
	        System.out.print(" " + totalCount);
	    }
	    in.close();
		}
		System.out.println("\nTotal records to import: " + totalCount);
		System.out.println("Importing data...");
		in = new RandomAccessFile(recordpath, "r");

		String line = "";
		int lineCount = 0;
		int badCount = 0;
    int badCountP = 0;
    int badCountG = 0;
    int[] result;
		DBStorageImport storage = new DBStorageImport();
		File indexDir = new File(indexpath); // obrise dokumente iz indeksa
		if (indexDir.exists()) {
			if (!indexDir.isDirectory())
				return;
			File[] files = indexDir.listFiles();
			for (File file : files)
				file.delete();
		}
		BulkIndexer indexer = new BulkIndexer(indexpath);
		long parsingTime = 0;
		long storingTime = 0;
		long indexingTime = 0;
		StopWatch clock = new StopWatch();
		StopWatch clock1= new StopWatch();
		clock.start();
		clock1.start();
		boolean firstCall = true;
		int lastRN = 0;
		while ((line = in.readLine()) != null) {
			lineCount++;
			line = line.trim();
			if ("".equals(line))
				continue;			
			Record rec = new Record();
			try {				
				if(library.equals("pa")){				
					Record recPA = PrepisPA.srediPolja996(RecordFactory.fromBISIS35(0, line));
					line = RecordFactory.toBISIS35(0, recPA); 
					rec = PrepisPA.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
				}else if(library.equals("ns")){					
					rec = PrepisNS.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
					rec.setRN(storage.getNewID(conn, "RN"));
				}else if(library.equals("ki")){					
					rec = PrepisKI.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
				}else if(library.equals("tfzr")){					
					rec = PrepisTFZR.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
				}else if(library.equals("sa")){
					rec = PrepisSA.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
				}else if(library.equals("zr")){
					rec = RecordFactory.fromUNIMARC(0, line);
					rec.setRN(storage.getNewID(conn, "RN"));
				}else if(library.equals("pat")){
					rec = PrepisPatrijarsija.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
					rec.setRN(storage.getNewID(conn,"RN"));
				}else if(library.equals("ig")){
					rec = PrepisIG.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
					rec.setRN(storage.getNewID(conn,"RN"));
				}else if(library.equals("apv")){
					rec = PrepisAPV.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
					rec.setRN(storage.getNewID(conn,"RN"));				
				}else if(library.equals("ftn")){
					rec = PrepisFTN.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));			
				}else if(library.equals("ib")){
					rec = PrepisIB.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));					
				}else if(library.equals("bg")){
					rec = PrepisBGB.prepisiZapis(RecordFactory.fromUNIMARC(0, line));								
				}else if(library.equals("ftn-bisis")){					
					rec = RecordFactory.fromUNIMARC(0, line);				
					rec.setRN(storage.getNewID(conn,"RN"));
					if(firstCall){
						System.out.println("First RN is "+rec.getRN());
						firstCall=false;
					}
					lastRN = rec.getRN();
				}else if(library.equals("uni")){
					rec = PrepisUNI.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
					rec.setRN(storage.getNewID(conn,"RN"));					
				}else if(library.equals("vlad")){
					rec = PrepisVladimirovci.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
					rec.setRN(storage.getNewID(conn,"RN"));				
				}else if(library.equals("eksu")){
					Record recEKSU = PrepisEKSU.sredi996(RecordFactory.fromBISIS35(0, line));
					line = RecordFactory.toBISIS35(0, recEKSU); 
					rec = PrepisEKSU.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
					rec.setRN(storage.getNewID(conn,"RN"));				
				}else if(library.equals("tssu")){
					rec = PrepisTSSU.prepisiZapis(RecordFactory.fromUNIMARC(0, line));
					rec.setRN(storage.getNewID(conn,"RN"));				
				}else if(library.equals("dif")){
					rec = PrepisDIF.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));			
				}else if(library.equals("ih")){
					rec = PrepisIH.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));			
				}else if(library.equals("polj")){
					rec = PrepisPolj.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));		
				}else if(library.equals("szpb")){
					rec = PrepisSZPB.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));		
				}else if(library.equals("pfso")){
					rec = PrepisPFSO.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));	
				}else if(library.equals("mz")){
					rec = PrepisMZ.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));	
				}else if(library.equals("ar")){
					rec = PrepisAR.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));	
				}else if(library.equals("tf")){
					rec = PrepisTF.prepisiZapis(RecordFactory.fromUNIMARC(0, line));				
					rec.setRN(storage.getNewID(conn,"RN"));	
			 }else{ 
					rec = RecordFactory.fromUNIMARC(0, line);
					rec.setRN(storage.getNewID(conn,"RN"));					
				}
				rec.setRecordID(storage.getNewRecordID(conn));				
				clock1.stop();
				parsingTime += clock1.getTime();
				clock1.reset();
				clock1.start();
        
        result = storage.add(conn, rec);
        if (result == null){
          badCount++;
        }else{
          badCountP = badCountP + result[0];
          badCountG = badCountG + result[1];
        }
			
//				dbaccess = storage.add(conn, rec);
//				if (dbaccess == false) {
//					badCount++;
//				}
				conn.commit();
				clock1.stop();
				storingTime += clock1.getTime();
				clock1.reset();
				clock1.start();
				indexer.add(rec, HoldingsDataCodersJdbc.getRashodCode());
				clock1.stop();
				indexingTime += clock1.getTime();
				if (lineCount == 10)
					displayTime(clock, lineCount, totalCount, badCount, badCountP, badCountG);
				else if (lineCount == 100)
					displayTime(clock, lineCount, totalCount, badCount, badCountP, badCountG);
				else if (lineCount == 500)
					displayTime(clock, lineCount, totalCount, badCount, badCountP, badCountG);
				else if (lineCount % 1000 == 0)
					displayTime(clock, lineCount, totalCount, badCount, badCountP, badCountG);
				clock1.reset();
				clock1.start();
			} catch (Exception e) {
				//log.fatal("Cannot add record, unimarc string: "+line);
			  PrepisReport.upisi("neispravan zapis");
			  PrepisReport.unesiZapis(rec);
			  e.printStackTrace();
				 badCount++;
			}
			
		}		
		PrepisReport.stampaj();
		indexer.optimize();
		indexer.close();
		clock.stop();
		System.out.println("Records processed: " + lineCount);
		System.out.println("Bad records: "+ badCount);
		System.out.println("Parsing time: "
				+ DurationFormatUtils.formatDurationHMS(parsingTime));
		System.out.println("Storage time: "
				+ DurationFormatUtils.formatDurationHMS(storingTime));
		System.out.println("Indexing time: "
				+ DurationFormatUtils.formatDurationHMS(indexingTime));
		
	}

	private static void displayTime(StopWatch clock, int lineCount,
			int totalCount, int badCount, int badCountP, int badCountG) {
		long timeElapsed = clock.getTime();
		long totalTime = (long) ((float) timeElapsed * ((float) totalCount / (float) lineCount));
		long timeRemaining = totalTime - timeElapsed;
		System.out.println();
		System.out.println("[" + sdf.format(new Date())
				+ "] ------------------------");
		System.out.println("Record count:     " + lineCount);
		System.out.println("  progress:       "
				+ percent(lineCount, totalCount));
		System.out.println("Bad record count: " + badCount);
    System.out.println("Bad primerci count: " + badCountP);
    System.out.println("Bad godine count: " + badCountG);
		System.out.println("Time elapsed:     " + timeToString(timeElapsed));
		System.out.println("Time remaining:   " + timeToString(timeRemaining));
	}

	private static String timeToString(long time) {
		int hours = (int) time / 3600000;
		long remainder = time % 3600000;
		int minutes = (int) remainder / 60000;
		remainder = remainder % 60000;
		int seconds = (int) remainder / 1000;
		return StringUtils.padZeros(hours, 2) + ":"
				+ StringUtils.padZeros(minutes, 2) + ":"
				+ StringUtils.padZeros(seconds, 2);
	}

	private static String percent(int value, int max) {
		int percent = (int) ((float) value / (float) max * 100.0f);
		return "" + percent + "%";
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");
}
