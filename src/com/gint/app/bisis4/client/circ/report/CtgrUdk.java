package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.util.ArrayList;
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
import com.gint.app.bisis4.client.circ.commands.reports.CtgrUdkGroupReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.CtgrUdkReportCommand;
import com.gint.app.bisis4.client.circ.manager.FilterManager;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;

public class CtgrUdk {

	private static Document setXML(List l,List l1) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		
		Iterator it = l.iterator();
		Iterator it1 = l1.iterator();
		Object[] obj = null;
		String usergroup;
		Long brojctlg;
		List<String> pom=new ArrayList<String>();		
		it = l.iterator();
		FilterManager manager = new FilterManager(l);
    
		while(it1.hasNext()){  //grupe korisnika + zbir invent.brojeva iz svake grupe
			obj = (Object[]) it1.next();
			brojctlg= (Long) obj[0];
			usergroup=(String)obj[1];
			int i=0;
			pom.clear();
			while((i<brojctlg)&&(it.hasNext())){
				pom.add((String)it.next());  //lista inventarnih brojeva iz jedne grupe korisnika
			    i++;
			}
			int []udk =manager.selectUDKcountGroup(pom);
		    Row row=report.addNewRow();
		    row.addNewColumn1().setStringValue(usergroup);
		    row.addNewColumn2().setStringValue(new Integer(udk[0]).toString());
		    row.addNewColumn3().setStringValue(new Integer(udk[1]).toString());
		    row.addNewColumn4().setStringValue(new Integer(udk[2]).toString());
		    row.addNewColumn5().setStringValue(new Integer(udk[3]).toString());
		    row.addNewColumn6().setStringValue(new Integer(udk[4]).toString());
		    row.addNewColumn7().setStringValue(new Integer(udk[5]).toString());
		    row.addNewColumn8().setStringValue(new Integer(udk[6]).toString());
		    row.addNewColumn9().setStringValue(new Integer(udk[7]).toString());
		    row.addNewColumn10().setStringValue(new Integer(udk[8]).toString());
		    row.addNewColumn11().setStringValue(new Integer(udk[9]).toString());
		} 	
		
		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(Date start, Date end, Object branch)
			throws IOException {

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

		CtgrUdkReportCommand ctgrUdk = new CtgrUdkReportCommand(start, end, branch);
		ctgrUdk = (CtgrUdkReportCommand)Cirkulacija.getApp().getService().executeCommand(ctgrUdk);
		List l= ctgrUdk.getList();
		CtgrUdkGroupReportCommand ctgrUdkGroup = new CtgrUdkGroupReportCommand(start, end, branch);
		ctgrUdkGroup = (CtgrUdkGroupReportCommand)Cirkulacija.getApp().getService().executeCommand(ctgrUdkGroup);
		List l1= ctgrUdkGroup.getList();
		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		if (branch instanceof Location) {
			params.put("nazivogr", "odeljenje: "
					+ ((Location) branch).getName());
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {

			Document dom = setXML(l,l1);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							CtgrUdk.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/ctgrudk.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
