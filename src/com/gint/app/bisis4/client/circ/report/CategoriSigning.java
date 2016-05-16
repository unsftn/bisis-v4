package com.gint.app.bisis4.client.circ.report;



import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Document;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

public class CategoriSigning {

	public static Document setXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			String uctg = (String) obj[0];
			long br = (Long) obj[1];
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(uctg);
			row.setColumn15(br);
		}

		return report.getDomNode().getOwnerDocument();

	}

}
