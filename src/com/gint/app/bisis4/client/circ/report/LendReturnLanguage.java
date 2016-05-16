package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.reports.LendReturnLanguageReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;
import com.gint.app.bisis4.records.Record;

public class LendReturnLanguage {

	public static Document setXML(List l, Date start, Date end) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Object[] obj;
		String lng;
		Vector<String> lngsfr = new Vector<String>();
		Vector<Integer> lngnoI = new Vector<Integer>();
		Vector<Integer> lngnoV = new Vector<Integer>();
		int ind = 0;
		Date lenddate, returndate, resumedate;
		Iterator it = l.iterator();
		while (it.hasNext()) { // idem po listi rezultata upita
			obj = (Object[]) it.next();
			String ctlg = (String) obj[0];
		    Record rec = Cirkulacija.getApp().getRecordsManager().getRecord(ctlg);
				try {
					lng = "";
					if (rec != null) {
						try {
							lng = rec.getField("101").getSubfield('a')
									.getContent();
						} catch (Exception e1) {

						}

					}

					lenddate = (Date) obj[1];
					returndate = (Date) obj[2];
					resumedate = (Date) obj[3];
					ind = lngsfr.indexOf(lng);
					if (ind == -1) {
						lngsfr.add(lng);
						ind = lngsfr.indexOf(lng);
						lngnoV.add(ind, new Integer(0));
						lngnoI.add(ind, new Integer(0));
					}

					if (((lenddate.after(start)) && (lenddate.before(end)))
							|| ((resumedate!=null)&&(resumedate.after(start)) && (resumedate
									.before(end)))) {

						int tmp = ((Integer) lngnoI.get(ind)).intValue() + 1;
						lngnoI.remove(ind);
						lngnoI.add(ind, new Integer(tmp));
					}
					if ((returndate != null) && (returndate.after(start))
							&& (returndate.before(end))) {

						int tmp = ((Integer) lngnoV.get(ind)).intValue() + 1;
						lngnoV.remove(ind);
						lngnoV.add(ind, new Integer(tmp));

					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

		}
		
		int i = 0;
		int ukupnoI = 0;
		int ukupnoV = 0;
		while (i < lngsfr.size()) {
			Row row = report.addNewRow();

			if (!lngsfr.get(i).equals("")) {
				row.addNewColumn1().setStringValue(lngsfr.get(i).toString());
			} else {
				row.addNewColumn1().setStringValue("nepoznato");
			}
			row.addNewColumn2().setStringValue(lngnoI.get(i).toString());
			row.addNewColumn3().setStringValue(lngnoV.get(i).toString());

			ukupnoI += ((Integer) lngnoI.get(i)).intValue();
			ukupnoV += ((Integer) lngnoV.get(i)).intValue();

			i++;

		}

		Row row = report.addNewRow();
		row.addNewColumn1().setStringValue("UKUPNO");
		row.addNewColumn2().setStringValue(new Integer(ukupnoI).toString());
		row.addNewColumn3().setStringValue(new Integer(ukupnoV).toString());

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
			LendReturnLanguageReportCommand lendReturn = new LendReturnLanguageReportCommand(start, end, location);
			lendReturn = (LendReturnLanguageReportCommand)Cirkulacija.getApp().getService().executeCommand(lendReturn);
			List l= lendReturn.getList();
			Document dom = setXML(l, start, end);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							LendReturnLanguage.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/izdatovracenojezik.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

}
