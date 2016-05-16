package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.reports.BookHistoryReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.view.RecordBean;
import com.gint.app.bisis4.client.circ.common.Utils;
import com.gint.app.bisis4.records.Record;

public class BookHistory {

	private static Document setXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		Object[] obj = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue((String) obj[0]);
			row.addNewColumn2().setStringValue(
					(Utils.toLocaleDate((Date) obj[1])));
			if ((Date) obj[2] != null) {
				row.addNewColumn3().setStringValue(
						(Utils.toLocaleDate((Date) obj[2])));
			}
		}

		return report.getDomNode().getOwnerDocument();

	}

	private static Document setSubXML(String ctlgNo) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Row row = report.addNewRow();
		row.addNewColumn1().setStringValue(ctlgNo);
		
		Record record = Cirkulacija.getApp().getRecordsManager().getRecord(ctlgNo);
		if (record != null) {
			RecordBean bean = new RecordBean(record);
			row.addNewColumn2().setStringValue(bean.getNaslov());
			row.addNewColumn3().setStringValue(bean.getAutor());
			row.addNewColumn4().setStringValue(bean.getIzdavac());
			row.addNewColumn5().setStringValue(bean.getMestoizdanja());
			row.addNewColumn6().setStringValue(bean.getGodinaizdanja());
			row.addNewColumn7().setStringValue(bean.getSignatura(ctlgNo));
		}
		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint(String ctlgNo, Date start, Date end,
			Object branch) throws IOException {

		JRXmlDataSource ds;
		try {
			
			if (start == null) {
				end = Utils.setMaxDate(end);
				start = Utils.setMinDate(end);
			} else if (end == null) {
				end = Utils.setMaxDate(start);
				start = Utils.setMinDate(start);
			} else {
				start = Utils.setMinDate(start);
				end = Utils.setMaxDate(end);
			}
				
				
			BookHistoryReportCommand history = new BookHistoryReportCommand(start, end, branch, ctlgNo);
			history = (BookHistoryReportCommand)Cirkulacija.getApp().getService().executeCommand(history);
			List l= history.getList();
			Document dom = setXML(l);
			Document doc = setSubXML(ctlgNo);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperReport subreport = (JasperReport) JRLoader
					.loadObject(BookHistory.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/kartica_sub.jasper")
							.openStream());

			Map<String, Object> params = new HashMap<String, Object>(5);
			if (branch instanceof Location) {
				params.put("nazivogr", "odeljenje: "
						+ ((Location) branch).getName());
			} else {
				params.put("nazivogr", "");
			}

			params.put("begdate", Utils.toLocaleDate(start));
			params.put("enddate", Utils.toLocaleDate(end));
			params.put("subreport", subreport);
			params.put("doc", doc);

			JasperPrint jp = JasperFillManager
					.fillReport(
							BookHistory.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/kartica.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
