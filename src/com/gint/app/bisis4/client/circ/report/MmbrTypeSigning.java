package com.gint.app.bisis4.client.circ.report;

import java.util.Iterator;
import java.util.List;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;

public class MmbrTypeSigning {
	public static Document setXML(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			String mmbr = (String) obj[0];
			long br = (Long) obj[1];
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(mmbr);
			row.setColumn15(br);
		}

		return report.getDomNode().getOwnerDocument();

	}

}
