package com.gint.app.bisis4.reports;

import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.MatchAllDocsQuery;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.textsrv.BisisFilter;
import com.gint.app.bisis4.textsrv.DBStorage;
import com.gint.app.bisis4.textsrv.Result;
import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.format.*;
import com.gint.util.xml.XMLUtils;

public class ReportRunner {

	public ReportRunner(ReportCollection reportCollection) {
		this.reportCollection = reportCollection;
	}

	public void run() {
		try {
			Class.forName(reportCollection.getDriver());
			conn = DriverManager.getConnection(reportCollection.getJdbcUrl(),
					reportCollection.getUsername(), reportCollection.getPassword());
			HoldingsDataCodersJdbc.loadData(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt = null;
		ResultSet rset = null;
		int recId;
		try {
			log.info("Opening database");
            
			stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
		              java.sql.ResultSet.CONCUR_READ_ONLY);
			rset = stmt
					.executeQuery("SELECT creator, modifier, date_created, "
							+ "date_modified, pub_type, content, record_id FROM Records");

			log.info("Initializing reports");
			for (Report r : reportCollection.getReports()) {
				try {
					r.init();
				} catch (Exception ex) {
					log.fatal(ex);
				}
			}
			DBStorage storage = new DBStorage();
			log.info("Fetching records...");
			int count = 0;
			while (rset.next()) {
				count++;
				Record rec = null;
				recId = rset.getInt("record_id");
				try {
					rec = storage.get(conn, recId);
				} catch (Exception ex) {
					log.warn("Problem with loading record ID: "
							+ rec.getRecordID());
					log.warn("Row count: " + count);
					log.warn(ex);
					continue;
				}
				if (rec == null)
					continue;
				for (Report r : reportCollection.getReports()) {
					if (r.getReportSettings().getParam("type").equals("online"))
						continue;
					try {
						r.handleRecord(rec);
						if ((r.getName().startsWith("Inventarna knjiga"))
								&& (count % 2000 == 0)) {
						r.finishInv();

						}

					} catch (Exception ex) {
						ex.printStackTrace();
						log
								.warn("Problem with record ID: "
										+ rec.getRecordID());
						log.warn("Report: "
								+ r.getReportSettings().getReportName());
						log.warn(ex);
					}
					if (count % 1000 == 0) {
						System.out.println("Records processed: " + count);
					}
				}
			}
			log.info("Closing database");
			if (rset != null)
				rset.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			log.info("Finishing reports");
			for (Report r : reportCollection.getReports()) {
				try {
					r.finish();

				} catch (Exception ex) {
					log.fatal(ex);
				}
			}

			log.info("Done reporting with " + count + " records.");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.fatal(ex);
		}
	}

	//za generisanje online izvestaja
	public JasperPrint run(String odInvBr, String doInvBr, String reportName) {
		JasperPrint jp = null;
		try {
			List<String> listaInventarnih = new ArrayList<String>();
			int odBr = Integer.parseInt(odInvBr.substring(4));
			String odeljenje = odInvBr.substring(0, 2);
			String tippubl = odInvBr.substring(2, 4);
			int doBr = Integer.parseInt(doInvBr.substring(4));
			for (int brojac = odBr; brojac <= doBr; brojac++) {
				String brojStr = String.valueOf(brojac);
				brojStr = "00000000000".substring(0, 7 - brojStr.length())
						+ brojStr;
				listaInventarnih.add(odeljenje + tippubl + brojStr);
			}
			CachingWrapperFilter filter = new CachingWrapperFilter(
					new BisisFilter(listaInventarnih));
			Result result = BisisApp.getRecordManager().selectAll3x(
					SerializationUtils.serialize(new MatchAllDocsQuery()),
					SerializationUtils.serialize(filter), null);
			Record[] records = BisisApp.getRecordManager().getRecords(result.getRecords());
			StringBuffer buff = new StringBuffer();
			
			for (Report r : reportCollection.getReports()) {
				
				if (!r.getReportSettings().getParam("menuitem").equalsIgnoreCase(reportName)) 
					continue;
				    buff.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				    buff.append("<report>");
					r.init();
					for (Record rec : records) {
						r.handleRecord(rec);					
					}
					r.finishOnline(buff);
					buff.append("</report>");
					JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils
							.getDocumentFromString(buff.toString()),
							"/report/item");
					Map param = new HashMap();
					if (r.getReportSettings().getParam("subjasper") != null) {
						JasperReport subreport = (JasperReport) JRLoader
								.loadObject(ReportRunner.class.getResource(
										r.getReportSettings().getParam(
												"subjasper")).openStream());
						param.put("subjasper", subreport);
					}
					jp = JasperFillManager.fillReport(ReportRunner.class
							.getResource(
									r.getReportSettings().getParam("jasper"))
							.openStream(), param, dataSource);
				}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jp;
	}

	public void runFromFile(String fileName) {
		try {
			for (Report r : reportCollection.getReports())
				r.init();
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			int count = 0;
			String line = "";
			while ((line = in.readLine()) != null) {

				count++;
				line = line.trim();
				if (line.length() == 0)
					continue;
				Record rec = null;
				try {
					rec = RecordFactory.fromUNIMARC(0, line);

				} catch (Exception ex) {
					log.warn(ex);
				}
				if (rec == null)
					continue;
				for (Report r : reportCollection.getReports()) {
					if (r.getReportSettings().getParam("type").equals("online"))
						continue;
					try {
						r.handleRecord(rec);

						if ((r.getName().startsWith("Inventarna knjiga"))
								&& (count % 2000 == 0)) {
							r.finishInv();

						}
					} catch (Exception ex) {
						log.warn(ex);
						ex.printStackTrace();
					}
					if (count % 1000 == 0) {
						System.out.println("Records processed: " + count);
					}
				}

			}
			for (Report r : reportCollection.getReports()) {
				r.finish();
			}
			System.out.println("Done reporting with " + count + " records.");
		} catch (Exception ex) {
			log.fatal(ex);
		}
	}

	private static ReportCollection reportCollection;

	public static Connection conn;

	private static Log log = LogFactory.getLog(ReportRunner.class);

}
