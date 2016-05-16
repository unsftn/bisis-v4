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
import com.gint.app.bisis4.client.circ.commands.reports.GroupsReportCommand;
import com.gint.app.bisis4.client.circ.model.Groups;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;
import com.gint.util.string.StringUtils;

public class GroupsReport {
	
	private static Document setXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		while (it.hasNext()) {
			Groups obj = (Groups) it.next();
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue((String)obj.getUserId());
			row.addNewColumn2().setStringValue((String)obj.getInstName());
			if (!((String)obj.getContFname()).equals("null") || !((String)obj.getContLname()).equals("null")){
				row.addNewColumn3().setStringValue((String)obj.getContFname() + " " + (String)obj.getContLname());
			} else {
				row.addNewColumn3().setStringValue("");
			}
			row.addNewColumn4().setStringValue(obj.getPhone().equals("null")? null : (String)obj.getPhone());
			if((Date)obj.getSignDate()!=null){
			 row.addNewColumn5().setStringValue(Utils.toLocaleDate(((Date)obj.getSignDate())));
			}
		
		}
		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint( Object branch)
			throws IOException {
		Map<String, Object> params = new HashMap<String, Object>(1);
		String location = "";
		if (branch instanceof Location) {
			params.put("nazivogr", "odeljenje: "+((Location) branch).getName());
			if (Cirkulacija.getApp().getEnvironment().getUseridPrefix()){
				location = StringUtils.padChars(String.valueOf(((Location) branch).getId()), '0', Cirkulacija.getApp().getEnvironment().getUseridPrefixLength());
			}
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		System.out.println(location);
		
		try {
			GroupsReportCommand groups = new GroupsReportCommand(location);
			groups = (GroupsReportCommand)Cirkulacija.getApp().getService().executeCommand(groups);
			List l= groups.getList();
			Document dom = setXML(l);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Librarian.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/groups.jasper")
									.openStream(), params, ds);
		

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
