/*
 * Created on Jan 22, 2005
 *  
 */
package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;

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
import com.gint.app.bisis4.client.circ.commands.reports.BlockedReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.Users;


public class Blocked {

	private static Document setXML(List l2) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it1= l2.iterator();
		while (it1.hasNext()) {
			Users u = (Users) it1.next();
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(u.getUserId());
			row.addNewColumn2().setStringValue(u.getFirstName());
			row.addNewColumn3().setStringValue(u.getLastName());
			row.addNewColumn4().setStringValue("Blokirano od strane bibliotekara");
			row.addNewColumn5().setStringValue(u.getBlockReasons());
		}
	

		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint(Object branch)
			throws IOException {
		Map<String, Object> params = new HashMap<String, Object>(1);
		
		BlockedReportCommand blocked = new BlockedReportCommand(branch);
		blocked = (BlockedReportCommand)Cirkulacija.getApp().getService().executeCommand(blocked);
		List l2= blocked.getList();
		if (branch instanceof Location) {
			params.put("nazivogr", "odeljenje: "+((Location) branch).getName());
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {

			Document dom =setXML(l2);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Blocked.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/blocked.jasper")
									.openStream(), params, ds);
		
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}