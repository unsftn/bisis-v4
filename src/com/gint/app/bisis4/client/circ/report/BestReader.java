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
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.reports.BestReaderReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;

public class BestReader {
	private static Document setXML(List l) {
		
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		int counter=0;
		while (it.hasNext()&&(counter<20)) {
			Object[] obj = (Object[]) it.next();
			Row row = report.addNewRow();

			row.addNewColumn1().setStringValue((String)obj[0]);
			row.addNewColumn2().setStringValue((String)obj[1]);
			row.addNewColumn3().setStringValue((String)obj[2]);
			row.setColumn15((Long)obj[3]);
			counter++;

		}

		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint(Date start,Date end, Object branch)throws IOException{
		
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
		
		
		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		if (branch instanceof Location) {
			params.put("nazivogr", "odeljenje: "+((Location) branch).getName());
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {
			BestReaderReportCommand bestReader = new BestReaderReportCommand(start, end, branch);
			bestReader = (BestReaderReportCommand)Cirkulacija.getApp().getService().executeCommand(bestReader);
			List l= bestReader.getList();
			Document dom = setXML(l);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							BestReader.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/najcitaoci.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
		
		
		

	}

}
