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
import com.gint.app.bisis4.client.circ.commands.reports.LibrarianReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;

public class Librarian {

	private static Document setXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			Row row = report.addNewRow();

			row.addNewColumn2().setStringValue((String) obj[0]);
			row.addNewColumn3().setStringValue((String) obj[1]);
			row.addNewColumn4().setStringValue((String) obj[2]);
			row.addNewColumn5().setStringValue((String) obj[3]);
			row.addNewColumn6().setStringValue(String.valueOf((Integer) obj[4]));
			row.addNewColumn7().setStringValue((String) obj[5]);
			row.addNewColumn8().setStringValue((String) obj[6]);
			row.addNewColumn9().setStringValue((String) obj[7]);
			row.addNewColumn10().setStringValue((String) obj[8]);
			row.addNewColumn1().setStringValue((String) obj[9]);
			row.addNewColumn11().setStringValue((String) obj[10]);
			if( obj[11]==null){
				row.addNewColumn20().setStringValue("0");
		    }else{
			row.addNewColumn20().setStringValue(String.valueOf((Double) obj[11]));
		 	}
			row.addNewColumn13().setStringValue((String) obj[12]);
		}
		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint(Date date, Object branch)
			throws IOException {

		try {
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("date", Utils.toLocaleDate(date));
			if (branch instanceof Location) {
				params.put("nazivogr", "odeljenje: "
						+ ((Location) branch).getName());
			} else {
				params.put("nazivogr", "");
			}
			JRXmlDataSource ds;
			
			LibrarianReportCommand librarian = new LibrarianReportCommand(date, branch);
			librarian = (LibrarianReportCommand)Cirkulacija.getApp().getService().executeCommand(librarian);
			List l= librarian.getList();

			Document dom = setXML(l);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Librarian.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/bibliotekar.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}
}
