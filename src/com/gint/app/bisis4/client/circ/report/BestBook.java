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
import com.gint.app.bisis4.client.circ.commands.reports.BestBookReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.view.RecordBean;
import com.gint.app.bisis4.client.circ.common.Utils;
import com.gint.app.bisis4.records.Record;
import com.gint.util.xml.XMLUtils;


public class BestBook {

	public static Document setXML(List recs, List cit) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Record rec;
		try {
			Iterator it = recs.iterator();//lista Records
			while (it.hasNext()) {
			    rec = (Record) it.next();
				if (rec != null) {
					int indx = recs.indexOf(rec);
					Row row = report.addNewRow();
					row.addNewColumn1().setStringValue(cit.get(indx).toString());
					RecordBean bean = new RecordBean(rec);
					row.addNewColumn2().setStringValue(bean.getNaslov());
					row.addNewColumn3().setStringValue(bean.getAutor());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(Date start,Date end,Object location)throws IOException {
		
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
		
		BestBookReportCommand bestBook = new BestBookReportCommand(start, end, location);
		bestBook = (BestBookReportCommand)Cirkulacija.getApp().getService().executeCommand(bestBook);
		List l= bestBook.getList();
		List <Record> records=new ArrayList<Record>();;
		Iterator it =l.iterator();
		int recId;
        List<String> ls = new ArrayList<String>();
        List<Integer> doc = new ArrayList<Integer>(); // recordId za odgovarajuci inventarni broj u ls
        List<Long> cit = new ArrayList<Long>(); // broj iznajmljivanja za odgovarajuci inventarni broj u ls
        Object [] obj=null;
        							
         while (it.hasNext()) {  
          obj=(Object [])it.next();	
          String ctlgNo=(String)obj[0];
          ls.add(ctlgNo); // (ctlgNo)
          cit.add((Long)obj[1]); // count(ctlgNo)
          recId= Cirkulacija.getApp().getRecordsManager().getRecordId(ctlgNo);			
			if (recId!=0) {
				doc.add(recId); // ovo je lista recordId
			} else {
				doc.add(null);
			}
         }
         

        // iz liste izbacuje duple zapise i sabira broj iznajmljivanja za
        // inventarne brojeve koji predstavljaju jedan naslov
        int i = 0;
        Integer docc;
        while (i < ls.size()) {// idem po listi inventarnih brojeva
          docc = (Integer) doc.get(i); //uzem odgovarajuci recordId
          if (docc != null) {
            int docid = docc.intValue();
            int j = i + 1;
            Integer tmp;
            while (j < doc.size()) {// prodjem kroz listu docID i saberem
									// citanost
              tmp = (Integer) doc.get(j);
              if (tmp != null) {
                if (tmp.intValue() == docid) {
                  cit.set(i, cit.get(i) +  cit.get(j));
                  cit.remove(j);
                  doc.remove(j);
                  ls.remove(j);
                } else {
                  j++;
                }
              } else {
                doc.remove(j);
                cit.remove(j);
                ls.remove(j);
              }
            }
            i++;
          } else {
            cit.remove(i);
            doc.remove(i);
            ls.remove(i);
          }
        }
        
        // sortira listu zapisa po citanosti
        int kraj = 1;
        Long tmp1;
        String tmp2;
        while (kraj < cit.size()) {
          if (cit.get(kraj).compareTo( cit.get(kraj - 1)) <= 0) {
            kraj++;
          } else {
            int mesto = 0;
            boolean nasao = false;
            while (mesto <= kraj - 1 && !nasao) {
              if ( cit.get(kraj).compareTo( cit.get(mesto)) > 0) {
                nasao = true;
              } else {
                mesto++;
              }
            }
            tmp1 =  cit.remove(kraj);
            cit.add(mesto, tmp1);
            tmp2 = (String) ls.remove(kraj);
            ls.add(mesto, tmp2);
          }
        }    

// dobija zapise za listu inventarnih brojeva
		int id=0;
		int max=20;
		if(ls.size()<=max){
			max=ls.size();
		}
		Record rec;
		while (id<max) {
			rec = Cirkulacija.getApp().getRecordsManager().getRecord(ls.get(id));
			records.add(rec);
			id++;
		}
        	
		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate",Utils.toLocaleDate(end));
		if (location instanceof Location) {
			params.put("nazivogr", "odeljenje: "+((Location) location).getName());
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {

		 Document dom = setXML (records, cit);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(BestBook.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/najcitanije.jasper").openStream(), 
											params, ds);
			
			
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}
}
