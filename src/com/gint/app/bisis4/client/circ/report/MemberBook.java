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
import com.gint.app.bisis4.client.circ.commands.reports.MemberBookReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.SubMemberBookReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;

public class MemberBook {

	private static Document setSubXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		Iterator it1;
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			Row row = report.addNewRow();

			row.addNewColumn2().setStringValue((String) obj[0]);

			if((Double) obj[1]==null){
				row.addNewColumn20().setStringValue("0");
		    }else{
			row.addNewColumn20().setStringValue(String.valueOf((Double)obj[1]));
		 	}
		
			row.addNewColumn1().setStringValue((String) obj[2]);

		}

		return report.getDomNode().getOwnerDocument();

	}

	private static Document setXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		Iterator it1;
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			Row row = report.addNewRow();

			row.addNewColumn1().setStringValue((String) obj[0]);
			row.addNewColumn2().setStringValue((String) obj[1]);
			row.addNewColumn3().setStringValue((String) obj[2]);
			row.addNewColumn4().setStringValue((String) obj[3]);
			if((Integer) obj[4]==null){
			 row.addNewColumn6().setStringValue(null);
			}else{
			 row.addNewColumn6().setStringValue(String.valueOf((Integer) obj[4]));
			}
			row.addNewColumn5().setStringValue((String) obj[5]);
			row.addNewColumn7().setStringValue((String) obj[6]);
			row.addNewColumn8().setStringValue((String) obj[7]);
			row.addNewColumn9().setStringValue((String) obj[8]);

			if((Double) obj[9]==null){
				row.addNewColumn20().setStringValue("0");
		    }else{
			row.addNewColumn20().setStringValue(String.valueOf((Double) obj[9]));
		 	}
			

		}

		return report.getDomNode().getOwnerDocument();

	}
	public static JasperPrint setPrint(Date start, Date end, Object branch)
			throws IOException {

		JRXmlDataSource ds;
		try {
			MemberBookReportCommand memberBook = new MemberBookReportCommand(start, end, branch);
			memberBook = (MemberBookReportCommand)Cirkulacija.getApp().getService().executeCommand(memberBook);
			SubMemberBookReportCommand subMemberBook = new SubMemberBookReportCommand(start, end, branch);
			subMemberBook = (SubMemberBookReportCommand)Cirkulacija.getApp().getService().executeCommand(subMemberBook);
			Document dom = setXML(memberBook.getList());
			Document dom1 = setSubXML(subMemberBook.getList());
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperReport subreport = (JasperReport) JRLoader
					.loadObject(MemberBook.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/submemberbook.jasper")
							.openStream());

			Map<String, Object> params = new HashMap<String, Object>(5);
			if (branch instanceof Location) {
				params.put("nazivogr", "odeljenje: "
						+ ((Location) branch).getName());
			} else {
				params.put("nazivogr", "");
			}

			params.put("start", Utils.toLocaleDate(start));
			params.put("end", Utils.toLocaleDate(end));
			params.put("subreport", subreport);
			params.put("dom1", dom1);

			JasperPrint jp = JasperFillManager
					.fillReport(
							MemberBook.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/memberbook.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
