package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.reports.LendUDKReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.ReturnUDKReportCommand;
import com.gint.app.bisis4.client.circ.manager.FilterManager;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;

public class LendReturn {
	
	public static Document setXML(List l1, List l2) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport(); 
		int  izPu = 0, izNu = 0, vrNu = 0,  vrPu = 0;
		
		FilterManager manager = new FilterManager(l1);
		Vector []udkIz =manager.lendreturnUDK(l1);
		FilterManager manager2 = new FilterManager(l2);
		Vector []udkVr =manager2.lendreturnUDK(l2);
		for(int i=0;i<udkIz.length;i++){
			izPu=izPu+(Integer)udkIz[i].get(1);
			izNu=izNu+(Integer)udkIz[i].get(0);
		}
		for(int i=0;i<udkVr.length;i++){
			vrPu=vrPu+(Integer)udkVr[i].get(1);
			vrNu=vrNu+(Integer)udkVr[i].get(0);
		}
		Row row = report.addNewRow();
		row.addNewColumn1().setStringValue("0");
		row.addNewColumn2().setStringValue(udkIz[0].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[0].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[0].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[0].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("1");
		row.addNewColumn2().setStringValue(udkIz[1].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[1].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[1].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[1].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("2");
		row.addNewColumn2().setStringValue(udkIz[2].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[2].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[2].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[2].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("3");
		row.addNewColumn2().setStringValue(udkIz[3].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[3].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[3].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[3].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("4");
		row.addNewColumn2().setStringValue(udkIz[4].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[4].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[4].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[4].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("5");
		row.addNewColumn2().setStringValue(udkIz[5].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[5].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[5].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[5].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("6");
		row.addNewColumn2().setStringValue(udkIz[6].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[6].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[6].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[6].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("7");
		row.addNewColumn2().setStringValue(udkIz[7].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[7].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[7].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[7].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("8");
		row.addNewColumn2().setStringValue(udkIz[8].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[8].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[8].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[8].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("9");
		row.addNewColumn2().setStringValue(udkIz[9].get(0).toString());
		row.addNewColumn3().setStringValue(udkIz[9].get(1).toString());
		row.addNewColumn4().setStringValue(udkVr[9].get(0).toString());
		row.addNewColumn5().setStringValue(udkVr[9].get(1).toString());

		row = report.addNewRow();
		row.addNewColumn1().setStringValue("UKUPNO");
		row.addNewColumn2().setStringValue(new Integer(izNu).toString());
		row.addNewColumn3().setStringValue(new Integer(izPu).toString());
		row.addNewColumn4().setStringValue(new Integer(vrNu).toString());
		row.addNewColumn5().setStringValue(new Integer(vrPu).toString());;
    
		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(Date start, Date end, Object location)
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
		

		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		if (location instanceof Location) {
			params.put("nazivogr", "odeljenje: "
					+ ((Location) location).getName());
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {
			LendUDKReportCommand lendUDK = new LendUDKReportCommand(start, end, location);
			lendUDK = (LendUDKReportCommand)Cirkulacija.getApp().getService().executeCommand(lendUDK);
			List l1= lendUDK.getList();
			ReturnUDKReportCommand returnUDK = new ReturnUDKReportCommand(start, end, location);
			returnUDK = (ReturnUDKReportCommand)Cirkulacija.getApp().getService().executeCommand(returnUDK);
			List l2= returnUDK.getList();
			Document dom = setXML(l1,l2 );
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							LendReturn.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/izdatovraceno.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}
}
