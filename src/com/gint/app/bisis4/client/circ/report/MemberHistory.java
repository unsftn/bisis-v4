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
import com.gint.app.bisis4.client.circ.commands.GetUserCommand;
import com.gint.app.bisis4.client.circ.commands.reports.LendingHistoryReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.Users;
import com.gint.app.bisis4.client.circ.view.RecordBean;
import com.gint.app.bisis4.client.circ.common.Utils;
import com.gint.app.bisis4.records.Record;

public class MemberHistory {

	private static Document setXML(Users u){
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Row row = report.addNewRow();
		row.addNewColumn1().setStringValue(u.getFirstName());
    row.addNewColumn2().setStringValue(u.getLastName());
    row.addNewColumn3().setStringValue(u.getAddress());
    row.addNewColumn4().setStringValue(u.getCity());
		return report.getDomNode().getOwnerDocument();

	}
	
	private static Document setSubXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		Object[] obj = null;
		String ctlgNo;
		Record rec;
		while (it.hasNext()) {
			obj = (Object[])it.next();
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(Utils.toLocaleDate((Date)obj[1]));
			if ((Date)obj[2]!=null){
			 row.addNewColumn2().setStringValue(Utils.toLocaleDate((Date)obj[2]));
			}
			ctlgNo=(String)obj[0];
			row.addNewColumn5().setStringValue(ctlgNo);
			rec= Cirkulacija.getApp().getRecordsManager().getRecord(ctlgNo);
			if (rec != null){
				RecordBean bean = new RecordBean(rec);
				row.addNewColumn4().setStringValue(bean.getNaslov());
				row.addNewColumn3().setStringValue(bean.getAutor());
			}
		}
		return report.getDomNode().getOwnerDocument();
	}
	
	
	public static JasperPrint setPrint(String userNo,Date start,Date end,Object branch)
			throws IOException {
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
				
				GetUserCommand getUser = new GetUserCommand(userNo);
		    getUser = (GetUserCommand)Cirkulacija.getApp().getService().executeCommand(getUser);
				Users user = getUser.getUser();

			if (user != null){
				JRXmlDataSource ds;
				LendingHistoryReportCommand history = new LendingHistoryReportCommand(start, end, branch, userNo);
				history = (LendingHistoryReportCommand)Cirkulacija.getApp().getService().executeCommand(history);
				Document dom=setXML(user);
				Document doc =setSubXML(history.getList());
				ds = new JRXmlDataSource(dom, "/report/row");
				JasperReport subreport = (JasperReport) JRLoader
						.loadObject(MemberHistory.class
								.getResource(
										"/com/gint/app/bisis4/client/circ/jaspers/istorija_sub.jasper")
								.openStream());
	
				Map<String, Object> params = new HashMap<String, Object>(6);
				if (branch instanceof Location) {
					params.put("nazivogr", "odeljenje: "+((Location) branch).getName());
				} else {
					params.put("nazivogr", "");
				}
				
	
				params.put("begdate", Utils.toLocaleDate(start));
				params.put("enddate", Utils.toLocaleDate(end));
				params.put("subreport",subreport);
				params.put("doc", doc);
				params.put("brojclana", userNo);
	
				JasperPrint jp = JasperFillManager
						.fillReport(
								MemberHistory.class
										.getResource(
												"/com/gint/app/bisis4/client/circ/jaspers/istorija.jasper")
										.openStream(), params,ds);		
						return jp;
			}
			return null;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}
}
