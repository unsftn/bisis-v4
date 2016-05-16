package com.gint.app.bisis4.client.hitlist.groupview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.util.xml.XMLUtils;

public class PogodciPoDatumuInventara {

	public static JasperPrint execute(int[] hits, String query) {
		itemMap = new HashMap<String, Item>();
		if(query.startsWith("DA:")){
			if(query.contains("*")){
				datefromQuery=query.substring(3).replace("*", "");
			}else if(query.contains("?")){
				datefromQuery=query.substring(3).replace("?", "");
			}else{
			datefromQuery=query.substring(3);
			}
			
		}
		for (int i = 0; i < hits.length; i++) {
			process(BisisApp.getRecordManager().getRecord(hits[i]));
		}

		StringBuffer buff = new StringBuffer();
		buff.append("<report>\n");
		List keys = new ArrayList();
		keys.addAll(itemMap.keySet());
		Collections.sort(keys);
		Iterator branchIDs = keys.iterator();
		while (branchIDs.hasNext()) {
			String key = (String) branchIDs.next();
			Item item = (Item) itemMap.get(key);
			buff.append(item.toString());
		}
		buff.append("</report>\n");
		try {
			JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils
					.getDocumentFromString(buff.toString()), "/report/item");
			Map param = new HashMap();
			param.put("query", query);
			param.put("datum", datefromQuery);
			if(query.startsWith("DA:")){
				param.put("query", datefromQuery);
			}
			JasperPrint jp = JasperFillManager
					.fillReport(
							PogodciPoDatumuInventara.class
									.getResource(
											"/com/gint/app/bisis4/client/hitlist/groupview/BranchesPoDatumu.jasper")
									.openStream(), param, dataSource);
			return jp;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private static void process(Record rec) {
		List<Primerak> primerci = rec.getPrimerci();
		for (Primerak p : primerci) {
			String invbr = p.getInvBroj();
			String status = p.getStatus();
			if (status == null)
				status = "A";
			if (!status.equals("") && !status.equals("A")
					&& !status.equals("5")) { // samo aktivne i preusmerene
				continue;
			}
			String branchID=p.getOdeljenje();
		      if((branchID==null)||(branchID.equals(""))){
		    	  branchID=invbr.substring(0,2);
		      }
			Item item = itemMap.get(branchID);
			if (item == null) {
				PogodciPoDatumuInventara pog=new PogodciPoDatumuInventara();
				item =  pog.new Item(branchID);
				itemMap.put(branchID, item);
			}
			item.add(1, 0);
			if(p.getDatumInventarisanja()!=null){		
              String datInv=sdf.format(p.getDatumInventarisanja());
            if(datInv.startsWith(datefromQuery)){
            	item.add(0, 1);
            }
			}
			
		}
	}

	public class Item implements Comparable {
		public String sigla;

		public int inventar;

		public int primerak;

		public Item(String sigla) {
			super();
			this.sigla = sigla;
			this.inventar = 0;
			this.primerak = 0;
		}

		public int compareTo(Object o) {
			if (o instanceof Item) {
				Item b = (Item) o;
				return sigla.compareTo(b.sigla);
			}
			return 0;
		}

		public String toString() {
			StringBuffer buf = new StringBuffer();
			buf.append("\n  <item id=\"");
			buf.append(sigla);
			buf.append("\">\n  <primerak>");
			buf.append(primerak);
			buf.append("</primerak>\n	<inventar>");
			buf.append(inventar);
			buf.append("</inventar>\n    </item>");
			return buf.toString();
		}

		public void add(int primerak, int inventar) {
			this.primerak += primerak;
			this.inventar += inventar;

		}

	}
	private static String datefromQuery;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static Map<String, Item> itemMap;

}
