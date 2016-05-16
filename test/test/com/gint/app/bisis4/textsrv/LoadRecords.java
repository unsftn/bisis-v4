package test.com.gint.app.bisis4.textsrv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.textsrv.BulkIndexer;
import com.gint.app.bisis4.textsrv.DBStorage;
import com.gint.util.string.StringUtils;

public class LoadRecords {
	public static void main(String[] args) throws Exception {
		if (args.length != 5) {
			System.out
					.println("index: <address> <database> <user> <password> <recordpath>");
			return;
		}
		String address = args[0];
		String database = args[1];
		String user = args[2];
		String password = args[3];
		String recordpath = args[4];
		String indexpath;
		boolean dbaccess;
		String osname = System.getProperty("os.name");
		if (osname.equals("Linux")) {
			indexpath = System.getProperty("user.home") + "/lucene-index";
		} else {
			indexpath = "C:/lucene-index";
		}
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + address
				+ "/" + database + "?characterEncoding=UTF-8", user, password);

		conn.setAutoCommit(false);
		HoldingsDataCodersJdbc.loadData(conn);
		RandomAccessFile in = new RandomAccessFile(recordpath, "r");

		System.out.println("Counting records...");
		int totalCount = 0;
		while (in.readLine() != null) {
			totalCount++;
			if (totalCount % 1000 == 0)
				System.out.print(" " + totalCount);
		}
		in.close();
		System.out.println("\nTotal records to import: " + totalCount);
		System.out.println("Importing data...");
		in = new RandomAccessFile(recordpath, "r");

		String line = "";
		int lineCount = 0;
		int badCount = 0;
		DBStorage storage = new DBStorage();
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
		clock.start();
		while ((line = in.readLine()) != null) {
			lineCount++;
			line = line.trim();
			if ("".equals(line))
				continue;
			Record rec = RecordFactory.fromUNIMARC(0, line);
			rec.setRecordID(storage.getNewRecordID(conn));
			clock.stop();
			parsingTime += clock.getTime();
			clock.reset();
			clock.start();
			try {
//				dbaccess = storage.add(conn, rec);
//				if (dbaccess == false) {
//					badCount++;
//				}
        storage.add(conn, rec);
				conn.commit();
				clock.stop();
				storingTime += clock.getTime();
				clock.reset();
				clock.start();
				indexer.add(rec, HoldingsDataCodersJdbc.getRashodCode());
				clock.stop();
				indexingTime += clock.getTime();
				if (lineCount == 10)
					displayTime(clock, lineCount, totalCount, badCount);
				else if (lineCount == 100)
					displayTime(clock, lineCount, totalCount, badCount);
				else if (lineCount == 500)
					displayTime(clock, lineCount, totalCount, badCount);
				else if (lineCount % 1000 == 0)
					displayTime(clock, lineCount, totalCount, badCount);
				clock.reset();
				clock.start();
			} catch (Exception e) {
				badCount++;
			}

		}
		indexer.close();
		clock.stop();
		System.out.println("Records processed: " + lineCount);
		System.out.println("Parsing time: "
				+ DurationFormatUtils.formatDurationHMS(parsingTime));
		System.out.println("Storage time: "
				+ DurationFormatUtils.formatDurationHMS(storingTime));
		System.out.println("Indexing time: "
				+ DurationFormatUtils.formatDurationHMS(indexingTime));
	}

	private static void displayTime(StopWatch clock, int lineCount,
			int totalCount, int badCount) {
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
