package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Document;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.GetAllCommand;
import com.gint.app.bisis4.client.circ.commands.reports.LibrarianStatistic1Command;
import com.gint.app.bisis4.client.circ.commands.reports.LibrarianStatistic2Command;
import com.gint.app.bisis4.client.circ.commands.reports.LibrarianStatistic3Command;
import com.gint.app.bisis4.client.circ.commands.reports.LibrarianStatistic4Command;
import com.gint.app.bisis4.client.circ.common.Utils;
import com.gint.app.bisis4.client.circ.model.Bibliotekari;
import com.gint.app.bisis4.client.circ.model.Location;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

public class LibrarianStatistic {
   private static Map<String,Item> librarians;
   
	
    
	public static JasperPrint setPrint(Date start, Date end, Object location) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>(3);
		Item i;
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
			params.put("location", "odeljenje: "
					+ ((Location) location).getName());
		} else {
			params.put("location", "");
		}
		JRXmlDataSource ds;
		List l1, l2, l3, l4;
		try {
			librarians = new HashMap<String, Item>();
			GetAllCommand all = new GetAllCommand();
			all.setArg(Bibliotekari.class);
			all = (GetAllCommand)Cirkulacija.getApp().getService().executeCommand(all);
			List <Bibliotekari> l= all.getList();
			
			for(int k=0;k<l.size();k++){	
				if (l.get(k).getCirkulacija() == 0){
					continue;
				}
				if (l.get(k).getIme() != null && l.get(k).getPrezime() != null){
					i = new Item(l.get(k).getIme()+" "+l.get(k).getPrezime());
				} else {
					i = new Item(l.get(k).getUsername());
				}
				librarians.put(l.get(k).getUsername().toLowerCase(), i);  
			}
			LibrarianStatistic1Command uclanjene = new LibrarianStatistic1Command(start, end, location);
			uclanjene = (LibrarianStatistic1Command)Cirkulacija.getApp().getService().executeCommand(uclanjene);
			l1=uclanjene.getList();
			
			LibrarianStatistic2Command zaduzenje = new LibrarianStatistic2Command(start, end, location);
			zaduzenje = (LibrarianStatistic2Command)Cirkulacija.getApp().getService().executeCommand(zaduzenje);
			l2=zaduzenje.getList();
			
			LibrarianStatistic4Command produzenje = new LibrarianStatistic4Command(start, end, location);
			produzenje = (LibrarianStatistic4Command)Cirkulacija.getApp().getService().executeCommand(produzenje);
			l3=produzenje.getList();
			
			LibrarianStatistic3Command razduzenje = new LibrarianStatistic3Command(start, end, location);
			razduzenje = (LibrarianStatistic3Command)Cirkulacija.getApp().getService().executeCommand(razduzenje);
			l4=razduzenje.getList();
					
			Document dom = setXML(l1, l2, l3, l4, location);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Librarian.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/librarianStatistic.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Document setXML(List l1, List l2, List l3, List l4,Object loc) {
	    
		Object[] obj;
	    String bib;
	    Long brojUsluga;
	    Item i;
	    Iterator it=l1.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			bib=(String)obj[0];
			brojUsluga=(Long)obj[1];
			i=librarians.get(bib.toLowerCase());
			if (i==null){
				i=new Item(bib);
				i.addUclanio(brojUsluga);
				librarians.put(bib.toLowerCase(), i);
			}else{
				i.addUclanio(brojUsluga);
			}
			
		}
		it=l2.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			bib=(String)obj[0];
			brojUsluga=(Long)obj[1];
			i=librarians.get(bib.toLowerCase());
			if (i==null){
				i=new Item(bib);
				i.addZaduzio(brojUsluga);
				librarians.put(bib.toLowerCase(), i);
			}else{
				i.addZaduzio(brojUsluga);
			}
			
		}
		it=l3.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			bib=(String)obj[0];
			brojUsluga=(Long)obj[1];
			i=librarians.get(bib.toLowerCase());
			if (i==null){
				i=new Item(bib);
				i.addProduzio(brojUsluga);
				librarians.put(bib.toLowerCase(), i);
			}else{
				i.addProduzio(brojUsluga);
			}
			
		}
		it=l4.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			bib=(String)obj[0];
			brojUsluga=(Long)obj[1];
			i=librarians.get(bib.toLowerCase());
			if (i==null){
				i=new Item(bib);
				i.addRazduzio(brojUsluga);
				librarians.put(bib.toLowerCase(), i);
			}else{
				i.addRazduzio(brojUsluga);
			}		
		}
		
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Set <String> libSet=librarians.keySet();
		Row row;
		Item librarianItem;
	    it =libSet.iterator();
		while(it.hasNext()){
			librarianItem=librarians.get((String)it.next());
			row = report.addNewRow();
			row.addNewColumn1().setStringValue(librarianItem.ime);
			row.addNewColumn2().setStringValue(String.valueOf(librarianItem.uclanio));
			row.addNewColumn3().setStringValue(String.valueOf(librarianItem.zaduzio));
			row.addNewColumn4().setStringValue(String.valueOf(librarianItem.produzio));
			row.addNewColumn5().setStringValue(String.valueOf(librarianItem.razduzio));
		}	      
		return report.getDomNode().getOwnerDocument();

	}

}
