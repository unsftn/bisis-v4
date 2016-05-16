package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.reports.ActiveVisitorsReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.GenderVisitors1ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.GenderVisitors2ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.PassiveVisitors1ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.PassiveVisitors2ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.SigningVisitors1ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.SigningVisitors2ReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;
import com.gint.app.bisis4.reports.XMLTransformer;

public class VisitorStructure {

	public static JasperPrint setPrint(Date start, Date end, Object location)
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
		 
			ActiveVisitorsReportCommand activeVisitors = new ActiveVisitorsReportCommand(start, end, location);
			activeVisitors = (ActiveVisitorsReportCommand)Cirkulacija.getApp().getService().executeCommand(activeVisitors);
			List l1= activeVisitors.getList();
			PassiveVisitors1ReportCommand passiveVisitors1 = new PassiveVisitors1ReportCommand(start, end, location);
			passiveVisitors1 = (PassiveVisitors1ReportCommand)Cirkulacija.getApp().getService().executeCommand(passiveVisitors1);
			List l2= passiveVisitors1.getList();
			PassiveVisitors2ReportCommand passiveVisitors2 = new PassiveVisitors2ReportCommand(start, end, location);
			passiveVisitors2 = (PassiveVisitors2ReportCommand)Cirkulacija.getApp().getService().executeCommand(passiveVisitors2);
			List l3= passiveVisitors2.getList();
			GenderVisitors1ReportCommand genderVisitors1 = new GenderVisitors1ReportCommand(start, end, location);
			genderVisitors1 = (GenderVisitors1ReportCommand)Cirkulacija.getApp().getService().executeCommand(genderVisitors1);
			List l4= genderVisitors1.getList();
			GenderVisitors2ReportCommand genderVisitors2 = new GenderVisitors2ReportCommand(start, end, location);
			genderVisitors2 = (GenderVisitors2ReportCommand)Cirkulacija.getApp().getService().executeCommand(genderVisitors2);
			List l5= genderVisitors2.getList();
			SigningVisitors1ReportCommand signingVisitors1 = new SigningVisitors1ReportCommand(start, end, location);
			signingVisitors1 = (SigningVisitors1ReportCommand)Cirkulacija.getApp().getService().executeCommand(signingVisitors1);
			List l6= signingVisitors1.getList();
			SigningVisitors2ReportCommand signingVisitors2 = new SigningVisitors2ReportCommand(start, end, location);
			signingVisitors2 = (SigningVisitors2ReportCommand)Cirkulacija.getApp().getService().executeCommand(signingVisitors2);
			List l7= signingVisitors2.getList();

			
			Document dom1 = setXMLActive(l1);
			Document dom2 = setXMLPasiv(l2, l3,location);
			Document dom3 = setXML(l4, l5,location);
			Document dom4 = setXML(l6, l7,location);
           
			Map<String, Object> params = new HashMap<String, Object>(11);
			if (location instanceof Location) {
				params.put("nazivogr", "odeljenje: "
						+ ((Location) location).getName());
			} else {
				params.put("nazivogr", "");
			}
			JasperReport kategorija = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/posetiocik.jasper")
							.openStream());

			JasperReport kategorijapasivni = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/posetiocikpasiv.jasper")
							.openStream());

			JasperReport uclanjenje = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/posetiociu.jasper")
							.openStream());

			JasperReport pol = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/posetiocip.jasper")
							.openStream());

			params.put("begdate", Utils.toLocaleDate(start));
			params.put("enddate", Utils.toLocaleDate(end));
			params.put("kategorija", kategorija);
			params.put("dom1", dom1);

			params.put("kategorijapasivni", kategorijapasivni);
			params.put("dom2", dom2);

			params.put("uclanjenje", uclanjenje);
			params.put("dom3", dom3);

			params.put("pol", pol);
			params.put("dom4", dom4);

			JasperPrint jp = JasperFillManager
					.fillReport(
							Structure.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/strposetilaca.jasper")
									.openStream(), params,
							new JREmptyDataSource());

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

    private static Document setXMLPasiv(List l1, List l2, Object loc) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l1.iterator();
		Date returnd, signd;
		String userid;
		List poml;
		while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				returnd = (Date) obj[2];
				userid = (String) obj[0];
		   	    Row row = report.addNewRow();
			    row.addNewColumn1().setStringValue(userid);
			    row.addNewColumn2().setStringValue((String) obj[1]);
		}
		
		Iterator it1 = l2.iterator();
     
		while (it1.hasNext()) {
			Object[] obj = (Object[]) it1.next();
			signd = (Date) obj[2];
			userid = (String) obj[0];
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(userid);
			row.addNewColumn2().setStringValue((String) obj[1]);
		}
		
		InputStream resource;
		try {
			resource = VisitorStructure.class.getResource("/com/gint/app/bisis4/client/circ/report/xmlsort.xsl").openStream();
			 Node xmlsort = XMLTransformer.transform(report.getDomNode(), resource);
			 return xmlsort.getOwnerDocument();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	   
	  
		

	}
	 
	private static Document setXML(List l1, List l2,Object loc) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l1.iterator();
		Date signd;
		String userid;
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
				  Row row = report.addNewRow();
			      row.addNewColumn1().setStringValue((String) obj[0]);
			      row.addNewColumn2().setStringValue((String) obj[1]);
		       
		}
		it = l2.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
				signd = (Date) obj[2];
				userid = (String) obj[0];
					Row row = report.addNewRow();
					row.addNewColumn1().setStringValue(userid);
					row.addNewColumn2().setStringValue((String) obj[1]);
			}
		InputStream resource;
		try {
			 resource = VisitorStructure.class.getResource("/com/gint/app/bisis4/client/circ/report/xmlsort.xsl").openStream();
			 Node xmlsort = XMLTransformer.transform(report.getDomNode(), resource);
		     return xmlsort.getOwnerDocument();
		} catch (IOException e) {
		return null;
		}
	   

	}

	private static Document setXMLActive(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		Vector<String> userId = new Vector<String>();
		String id;
		Date lend, ret, resum;
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();

			lend = Utils.setMaxDate((Date) obj[2]);
			ret = (Date) obj[3];
			resum = (Date) obj[4];
			id = (String) obj[0];
			if ((ret == null) && (!userId.contains(id))) {
				Row row = report.addNewRow();
				userId.add(id);
				row.addNewColumn1().setStringValue(id);
				row.addNewColumn2().setStringValue((String) obj[1]);
			} else if ((ret!=null)&&(ret.after(lend)) && (!userId.contains(id))) {
				Row row = report.addNewRow();
				userId.add(id);
				row.addNewColumn1().setStringValue(id);
				row.addNewColumn2().setStringValue((String) obj[1]);

			} else if ((resum != null) && (resum.after(lend))
					&& (!userId.contains(id))) {
				Row row = report.addNewRow();
				userId.add(id);
				row.addNewColumn1().setStringValue(id);
				row.addNewColumn2().setStringValue((String) obj[1]);
			}

		}
		try{
		 InputStream resource =  VisitorStructure.class.getResource("/com/gint/app/bisis4/client/circ/report/xmlsort.xsl").openStream();
	     Node xmlsort = XMLTransformer.transform(report.getDomNode(), resource);
		 return xmlsort.getOwnerDocument();
		}catch (Exception e) {
			return null;
		}

	}

}
