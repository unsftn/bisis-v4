package com.gint.app.bisis4.reports.gbns;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.reports.Report;
import com.gint.util.string.LatCyrUtils;

public class NabavkaPoJezicimaPoRacunu extends Report {

	@Override
	public void init() {
		itemMap.clear();
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
			code = "xxx"; //nema jezika
		   // log.info("Nema oznake za jezik. Recodrd ID: "+rec.getRecordID());
		}
		for (Primerak p : rec.getPrimerci()) {
			Date  invDate;
	      /*  if (p.getStatus()!=null) {
	        	if(p.getStatus().equals("5"))  {//za presiglirane gleda datum statusa a ne datum inventarisanja
	        		invDate = p.getDatumStatusa();
	             }else{   //za sve ostale slucajeve uzimam datum inventarisanja
	            	 invDate=p.getDatumInventarisanja();
	           }
	        }else{//ukoliko nema statusa opet uzimam datum inventarisanja */
	        	invDate=p.getDatumStatusa();
	       // }
			
			String key = settings.getParam("file") + getFilenameSuffix(invDate);
			String invBr = p.getInvBroj();
			sigla=p.getOdeljenje();
			if (sigla == null || sigla.equals("")) {
			  if (invBr == null || invBr.equals("")) {
				continue;
			  }
			 sigla = invBr.substring(0, 2);
			}
	        if (p.getStatus()!=null) {
	        	if(p.getStatus().equals("9")) //ne broji rashodovane
	        		continue; 
	        } 
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

		public int odeljenje03;

		public int odeljenje04;

		public int odeljenje05;

		public int odeljenje06;

		public int odeljenje07;

		public int odeljenje08;

		public int odeljenje09;

		public int odeljenje10;

		public int odeljenje11;

		public int odeljenje13;

		public int odeljenje14;

		public int odeljenje16;

		public int odeljenje17;

		public int odeljenje18;

		public int odeljenje19;

		public int odeljenje20;

		public int odeljenje21;

		public int odeljenje22;

		public int odeljenje23;

		public int odeljenje25;

		public int odeljenje26;

		public int odeljenje27;

		public int odeljenje28;

		public int odeljenje31;
		public int odeljenje40;
		public int odeljenje43;

		//public int odeljenje44;
		public int odeljenjeX;

		public Item(String code) {
			super();
			this.code = code;
			odeljenje01 = 0;
			odeljenje02 = 0;
			odeljenje03 = 0;
			odeljenje04 = 0;
			odeljenje05 = 0;
			odeljenje06 = 0;
			odeljenje07 = 0;
			odeljenje08 = 0;
			odeljenje09 = 0;
			odeljenje10 = 0;
			odeljenje11 = 0;
			odeljenje13 = 0;
			odeljenje14 = 0;
			odeljenje16 = 0;
			odeljenje17 = 0;
			odeljenje18 = 0;
			odeljenje19 = 0;
			odeljenje20 = 0;
			odeljenje21 = 0;
			odeljenje22 = 0;
			odeljenje23 = 0;
			odeljenje25 = 0;
			odeljenje26 = 0;
			odeljenje27 = 0;
			odeljenje28 = 0;
			odeljenje31 = 0;
			odeljenje40 = 0;
			odeljenje43 = 0;
			//odeljenje44 = 0;
			odeljenjeX = 0;

		}

		public int hashCode() {
			return code.hashCode();
		}
		
		public int total(){
			return odeljenje01 + odeljenje02 + odeljenje03 + odeljenje04 +
			odeljenje05 + odeljenje06 + odeljenje07 + odeljenje08 + odeljenje09 +
			odeljenje10 + odeljenje11 + odeljenje13 + odeljenje14 + odeljenje16 +
			odeljenje17 + odeljenje18 + odeljenje19 + odeljenje20 + odeljenje21 +
			odeljenje22 + odeljenje23 + odeljenje25 + odeljenje26 + odeljenje27 +
			odeljenje28 + odeljenje31+odeljenje40+odeljenje43/*+odeljenje44*/;
		}

		public int compareTo(Object o) {
			Item obj = (Item) o;
			return code.compareTo(obj.code);
		}

		public void add(String sigla) {
			switch (Integer.parseInt(sigla)) {
			case 1:
				odeljenje01++;
				break;
			case 2:
				odeljenje02++;
				break;
			case 3:
				odeljenje03++;
				break;
			case 4:
				odeljenje04++;
				break;
			case 5:
				odeljenje05++;
				break;
			case 6:
				odeljenje06++;
				break;
			case 7:
				odeljenje07++;
				break;
			case 8:
				odeljenje08++;
				break;
			case 9:
				odeljenje09++;
				break;
			case 10:
				odeljenje10++;
				break;
			case 11:
				odeljenje11++;
				break;
			case 13:
				odeljenje13++;
				break;
			case 14:
				odeljenje14++;
				break;
			case 16:
				odeljenje16++;
				break;
			case 17:
				odeljenje17++;
				break;
			case 18:
				odeljenje18++;
				break;
			case 19:
				odeljenje19++;
				break;
			case 20:
				odeljenje20++;
				break;
			case 21:
				odeljenje21++;
				break;
			case 22:
				odeljenje22++;
				break;
			case 23:
				odeljenje23++;
				break;
			case 25:
				odeljenje25++;
				break;
			case 26:
				odeljenje26++;
				break;
			case 27:
				odeljenje27++;
				break;
			case 28:
				odeljenje28++;
				break;
			case 31:
				odeljenje31++;
				break;
			case 40:
				odeljenje40++;
				break;
			case 43:
				odeljenje43++;
				break;
			/*case 44:
				odeljenje44++;
				break;*/
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
			buff.append("<odeljenje03>");
			buff.append(odeljenje03);
			buff.append("</odeljenje03>\n");
			buff.append("<odeljenje04>");
			buff.append(odeljenje04);
			buff.append("</odeljenje04>\n");
			buff.append("<odeljenje05>");
			buff.append(odeljenje05);
			buff.append("</odeljenje05>\n");
			buff.append("<odeljenje06>");
			buff.append(odeljenje06);
			buff.append("</odeljenje06>\n");
			buff.append("<odeljenje07>");
			buff.append(odeljenje07);
			buff.append("</odeljenje07>\n");
			buff.append("<odeljenje08>");
			buff.append(odeljenje08);
			buff.append("</odeljenje08>\n");
			buff.append("<odeljenje09>");
			buff.append(odeljenje09);
			buff.append("</odeljenje09>\n");
			buff.append("<odeljenje10>");
			buff.append(odeljenje10);
			buff.append("</odeljenje10>\n");
			buff.append("<odeljenje11>");
			buff.append(odeljenje11);
			buff.append("</odeljenje11>\n");
			buff.append("<odeljenje13>");
			buff.append(odeljenje13);
			buff.append("</odeljenje13>\n");
			buff.append("<odeljenje14>");
			buff.append(odeljenje14);
			buff.append("</odeljenje14>\n");
			buff.append("<odeljenje16>");
			buff.append(odeljenje16);
			buff.append("</odeljenje16>\n");
			buff.append("<odeljenje17>");
			buff.append(odeljenje17);
			buff.append("</odeljenje17>\n");
			buff.append("<odeljenje18>");
			buff.append(odeljenje18);
			buff.append("</odeljenje18>\n");
			buff.append("<odeljenje19>");
			buff.append(odeljenje19);
			buff.append("</odeljenje19>\n");
			buff.append("<odeljenje20>");
			buff.append(odeljenje20);
			buff.append("</odeljenje20>\n");
			buff.append("<odeljenje21>");
			buff.append(odeljenje21);
			buff.append("</odeljenje21>\n");
			buff.append("<odeljenje22>");
			buff.append(odeljenje22);
			buff.append("</odeljenje22>\n");
			buff.append("<odeljenje23>");
			buff.append(odeljenje23);
			buff.append("</odeljenje23>\n");
			buff.append("<odeljenje25>");
			buff.append(odeljenje25);
			buff.append("</odeljenje25>\n");
			buff.append("<odeljenje26>");
			buff.append(odeljenje26);
			buff.append("</odeljenje26>\n");
			buff.append("<odeljenje27>");
			buff.append(odeljenje27);
			buff.append("</odeljenje27>\n");
			buff.append("<odeljenje28>");
			buff.append(odeljenje28);
			buff.append("</odeljenje28>\n");
			buff.append("<odeljenje31>");
			buff.append(odeljenje31);
			buff.append("</odeljenje31>\n");
			buff.append("<odeljenje40>");
			buff.append(odeljenje40);
			buff.append("</odeljenje40>\n");
			buff.append("<odeljenje43>");
			buff.append(odeljenje43);
			buff.append("</odeljenje43>\n");
			/*buff.append("<odeljenje44>");
			buff.append(odeljenje44);
			buff.append("</odeljenje44>\n");*/
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

	private static Log log = LogFactory.getLog(NabavkaPoJezicimaPoRacunu.class);

	private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();

	NumberFormat nf;

	@Override
	public void finishOnline(StringBuffer buff) {
		// TODO Auto-generated method stub
		
	}
}
