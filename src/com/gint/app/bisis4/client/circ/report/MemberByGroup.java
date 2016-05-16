package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.reports.MemberByGroupReportCommand;
import com.gint.app.bisis4.client.circ.model.Groups;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.Users;
import com.gint.app.bisis4.client.circ.common.Utils;

public class MemberByGroup {
	private static Document setXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		while (it.hasNext()) {
			Users u = (Users) it.next();
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(u.getUserId());
			row.addNewColumn2().setStringValue(u.getFirstName());
			row.addNewColumn3().setStringValue(u.getLastName());
		}
		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint(Date start, Date end, Object branch,Object group) throws IOException {
				
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
			
		Map<String, Object> params = new HashMap<String, Object>(4);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		params.put("group", ((Groups) group).getInstName());
		if (branch instanceof Location) {
			params.put("nazivogr", "odeljenje: "+((Location) branch).getName());
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {
			MemberByGroupReportCommand memberByGroup = new MemberByGroupReportCommand(start, end, branch, group);
			memberByGroup = (MemberByGroupReportCommand)Cirkulacija.getApp().getService().executeCommand(memberByGroup);
			Document dom = setXML(memberByGroup.getList());
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							MemberByGroup.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/membersbygroup.jasper")
									.openStream(), params, ds);
			

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
