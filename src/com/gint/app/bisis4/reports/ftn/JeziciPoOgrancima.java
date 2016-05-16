package com.gint.app.bisis4.reports.ftn;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.reports.Report;

public class JeziciPoOgrancima extends Report {

	@Override
	public void init() {
		itemMap.clear();
	    pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
		log.info("Report initialized.");
	}

	@Override
	public void finishInv() { // zbog inventerni one se snimaju u fajl po
		// segmentima a ne sve od jednom

	}

	@Override
	public void finish() {
		log.info("Finishing report...");

		for (String key : itemMap.keySet()) {
			List<Item> list = itemMap.get(key);
			PrintWriter out = getWriter(key);
			for (Item i : list) {
				out.print(i.toString());

			}
			out.println("</report>");
			out.close();
		}

		itemMap.clear();
		log.info("Report finished.");
	}

	@Override
	public void handleRecord(Record rec) {

		Item item=null;
		if (rec == null)
			return;
		String sigla,temp;
		String code = rec.getSubfieldContent("101a");
		List<Subfield> listS= rec.getSubfields("101a");
		if(listS.size()>1){
		 Iterator <Subfield> it=listS.iterator();
		 boolean ok=false;
		 while(it.hasNext()&& !ok){
			Subfield s=it.next();
			temp=s.getContent();
			if(!temp.equalsIgnoreCase("scc")||!temp.equalsIgnoreCase("scr")){
				ok=true;
				code=temp;
			}
		 }
		}
		if ((code == null)||(code.equals(""))){
			code = "nepoznat"; //nema jezika
		   // log.info("Nema oznake za jezik. Recodrd ID: "+rec.getRecordID());
		}
		for (Primerak p : rec.getPrimerci()) {
			Date  invDate;
			String invbr = p.getInvBroj();
			 Matcher matcher = pattern.matcher(p.getInvBroj());
		      if (!matcher.matches())
		        continue;
			String status=p.getStatus();
	        if (status==null) 
	        	status="A";
	         if(status.compareToIgnoreCase("5")==0){
	        	 invDate = p.getDatumStatusa(); 
	        	 sigla=p.getOdeljenje();
	         }else{
	        	 invDate=p.getDatumInventarisanja();
	        	 sigla=invbr.substring(0,2);
	         } 
			
			String key = settings.getParam("file") + getFilenameSuffix(invDate);
			


			if (getList(key).size() == 0) { // lista je prazna
				item = new Item(code);
				item.add(sigla);
				getList(key).add(item);
			} else {
				item = getItem(getList(key), code);
				if (item == null) {
					item = new Item(code);
					item.add(sigla);
					getList(key).add(item);
				} else {
					item.add(sigla);
				}

			}

		}
		

	}

	public List<Item> getList(String key) {
		List<Item> list = itemMap.get(key);
		if (list == null) {
			list = new ArrayList<Item>();
			itemMap.put(key, list);
		}
		return list;
	}

	public String nvl(String s) {
		return s == null ? "" : s;
	}

	public Item getItem(List<Item> itemsL, String code) {

		for (Item it : itemsL) {
			if (it.code.equals(code)) {
				return it;
			}
		}

		return null;
	}

	public class Item implements Comparable {
		public String code;

		public int odeljenje01;

		public int odeljenje02;

		
		public int odeljenjeX;

		public Item(String code) {
			super();
			this.code = code;
			odeljenje01 = 0;
			odeljenje02 = 0;
			odeljenjeX = 0;

		}

		public int hashCode() {
			return code.hashCode();
		}
		
		public int total(){
			return odeljenje01 + odeljenje02 ;
		}

		public int compareTo(Object o) {
			Item obj = (Item) o;
			return code.compareTo(obj.code);
		}

		public void add(String sigla) {
			switch (Integer.parseInt(sigla)) {
			case 0:
				odeljenje01++;
				break;
			case 2:
				odeljenje02++;
				break;
			
			default:
				odeljenjeX++;
			}
		}

		public String toString() {
			StringBuffer buff = new StringBuffer();
			buff.append("  <item id=\"");
			buff.append(code);
			buff.append("\">\n");
			buff.append("<odeljenje01>");
			buff.append(odeljenje01);
			buff.append("</odeljenje01>\n");
			buff.append("<odeljenje02>");
			buff.append(odeljenje02);
			buff.append("</odeljenje02>\n");
			buff.append("<odeljenjeX>");
			buff.append(odeljenjeX);
			buff.append("</odeljenjeX>\n");
			buff.append("<ukupno>");
			buff.append(total());
			buff.append("</ukupno>\n");
			buff.append("  </item>\n");
		
			return buff.toString();
		}

	}

	private static Log log = LogFactory.getLog(JeziciPoOgrancima.class);
	  private Pattern pattern;
	private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();

	NumberFormat nf;

	@Override
	public void finishOnline(StringBuffer buff) {
		// TODO Auto-generated method stub
		
	}
}
