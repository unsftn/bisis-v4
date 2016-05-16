package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.gint.app.bisis4.client.circ.commands.reports.Statistic10ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic11ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic12ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic1ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic2ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic3ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic4ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic5ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic6ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic7ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic8ReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.Statistic9ReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;

public class ZbStatistic {

	public static JasperPrint setPrint(Date start, Date end, Object location)
			throws IOException {
		
		Map<String, Object> params = new HashMap<String, Object>(3);
		
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
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		if (location instanceof Location) {
			params.put("nazivogr", "odeljenje: "
					+ ((Location) location).getName());
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		
		Statistic1ReportCommand statistic1 = new Statistic1ReportCommand(start, end, location);
		statistic1 = (Statistic1ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic1);
		List l1= statistic1.getList();
		Statistic2ReportCommand statistic2 = new Statistic2ReportCommand(start, end, location);
		statistic2 = (Statistic2ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic2);
		List l2= statistic2.getList();
		Statistic3ReportCommand statistic3 = new Statistic3ReportCommand(start, end, location);
		statistic3 = (Statistic3ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic3);
		List l3= statistic3.getList();
		Statistic4ReportCommand statistic4 = new Statistic4ReportCommand(start, end, location);
		statistic4 = (Statistic4ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic4);
		List l4= statistic4.getList();
		Statistic5ReportCommand statistic5 = new Statistic5ReportCommand(start, end, location);
		statistic5 = (Statistic5ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic5);
		List l5= statistic5.getList();
		Statistic6ReportCommand statistic6 = new Statistic6ReportCommand(start, end, location);
		statistic6 = (Statistic6ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic6);
		List l6= statistic6.getList();
		Statistic7ReportCommand statistic7 = new Statistic7ReportCommand(start, end, location);
		statistic7 = (Statistic7ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic7);
		List l7= statistic7.getList();
		Statistic8ReportCommand statistic8 = new Statistic8ReportCommand(start, end, location);
		statistic8 = (Statistic8ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic8);
		List l8= statistic8.getList();
		Statistic9ReportCommand statistic9 = new Statistic9ReportCommand(start, end, location);
		statistic9 = (Statistic9ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic9);
		List l9= statistic9.getList();
		Statistic10ReportCommand statistic10 = new Statistic10ReportCommand(start, end, location);
		statistic10 = (Statistic10ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic10);
		List l10= statistic10.getList();
		Statistic11ReportCommand statistic11 = new Statistic11ReportCommand(start, end, location);
		statistic11 = (Statistic11ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic11);
		List l11= statistic11.getList();
		Statistic12ReportCommand statistic12 = new Statistic12ReportCommand(start, end, location);
		statistic12 = (Statistic12ReportCommand)Cirkulacija.getApp().getService().executeCommand(statistic12);
		List l12= statistic12.getList();

		
		try {

			Document dom = setXML(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11,
					l12, location);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Librarian.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/zbstatistika.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Document setXML(List l1, List l2, List l3, List l4, List l5,
			List l6, List l7, List l8, List l9, List l10, List l11, List l12,
			Object loc) {
		Object[] obj;
		String id;
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Vector<String> userId = new Vector<String>();
		Iterator it = l1.iterator();
		Date lend, ret, resum;
		Long l = (Long) it.next();
		Row row = report.addNewRow();
		row.addNewColumn1().setStringValue(l.toString());

		it = l2.iterator();
		Long count = new Long(0);
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			count = count + (Long) obj[1];
		}
		row.addNewColumn2().setStringValue(count.toString());

		it = l3.iterator();
		count = new Long(0);
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			count = count + (Long) obj[1];
		}
		row.addNewColumn3().setStringValue(count.toString());
		// ovo su aktivni korisnici po danu!!!!!
		it = l4.iterator();
		Long active = new Long(0);
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			lend = Utils.setMaxDate((Date) obj[1]);
			ret = (Date) obj[2];
			resum = (Date) obj[3];
			id = (String) obj[0];
			if ((ret == null) && (!userId.contains(id))) {
				userId.add(id);
				active = active + 1;
			} else if ((ret!=null)&&(ret.after(lend)) && (!userId.contains(id))) {
				userId.add(id);
				active = active + 1;
			} else if ((resum != null) && (resum.after(lend))
					&& (!userId.contains(id))) {
				userId.add(id);
				active = active + 1;
			}

		}

		row.addNewColumn4().setStringValue(active.toString());
		// pasivni korisnici po danu
		it = l5.iterator();
		count = new Long(0);
		Date returnd, signd;
		String userid;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			returnd = (Date) obj[1];
			userid = (String) obj[0];
			count = count + (Long) obj[2];
		
		}

		it = l6.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			signd = (Date) obj[1];
			userid = (String) obj[2];
			count = count + (Long) obj[0];
		}

		row.addNewColumn5().setStringValue(count.toString());

		Long total = active + count;
		row.addNewColumn6().setStringValue(total.toString());


		count = new Long(0);
		count = count +  l7.size();
		
		row.addNewColumn7().setStringValue(count.toString());


		count = new Long(0);
    	count = count + l8.size();

		row.addNewColumn8().setStringValue(count.toString());


		count = new Long(0);
		count = count + l9.size();

		row.addNewColumn9().setStringValue(count.toString());

		it = l10.iterator();
		row.addNewColumn10().setStringValue(((Long) it.next()).toString());

		it = l11.iterator();
		row.addNewColumn11().setStringValue(((Long) it.next()).toString());

		it = l12.iterator();
		row.addNewColumn13().setStringValue(((Long) it.next()).toString());

		return report.getDomNode().getOwnerDocument();

	}
}
