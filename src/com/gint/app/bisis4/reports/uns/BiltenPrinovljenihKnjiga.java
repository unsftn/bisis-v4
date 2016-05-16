package com.gint.app.bisis4.reports.uns;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.reports.ReportsUtils;
import com.gint.app.bisis4.utils.LatCyrUtils;
import com.gint.app.bisis4.utils.Signature;
import com.gint.util.string.StringUtils;

public class BiltenPrinovljenihKnjiga extends Report {
	public class Item implements Comparable {
		public String opis;

		public String sig;

		public Item(String opis) {
			super();
			this.opis = opis;
			sig = "";
		}

		public void add(String sig) {
			this.sig = this.sig + " " + sig;

		}

		public String toString() {
			StringBuffer buf = new StringBuffer();
			buf.append("\n  <item>\n    <opis>");
			buf.append(opis == null ? "" : StringUtils.adjustForHTML(LatCyrUtils.toLatin(opis)));
			buf.append("</opis>\n    <signatura>");
			buf.append(sig == null ? "" : StringUtils.adjustForHTML(sig));
			buf.append("</signatura>\n   </item>");
			return buf.toString();
		}

		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	public Item getItem(List<Item> itemsL, String opis) {

		for (Item it : itemsL) {
			if (it.opis.equals(opis)) {
				return it;
			}
		}

		return null;
	}

	public void finishInv() { //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
		log.info("Finishing report...");
		for (List<Item> list : itemMap.values())
			Collections.sort(list);

		for (String key : itemMap.keySet()) {
			List<Item> list = itemMap.get(key);
			PrintWriter out = getWriter(key);
			for (Item i : list) {
				out.print(i.toString());

			}
			out.flush();
			itemMap.get(key).clear();
		}

		log.info("Report finished.");
	}

	@Override
	public void init() {
		itemMap.clear();
		pattern = Pattern
				.compile(getReportSettings().getParam("invnumpattern"));
		log.info("Report initialized.");
	}

	@Override
	public void finish() {
		log.info("Finishing report...");
		for (List<Item> list : itemMap.values())
			Collections.sort(list);

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
		Item item;
		if (rec == null)
			return;
		if (rec.getPubType() != 1)
			return;
		//200adeh
		String naslov = rec.getSubfieldContent("200a");
		if (naslov == null)
			naslov = "";
		String f200d = rec.getSubfieldContent("200d");
		if (f200d == null)
			f200d = "";
		String f200e = rec.getSubfieldContent("200e");
		if (f200e == null)
			f200e = "";
		String f200h = rec.getSubfieldContent("200h");
		if (f200h == null)
			f200h = "";
		//210acd  
		String f210a = rec.getSubfieldContent("210a");
		if (f210a == null)
			f210a = "";
		String f210c = rec.getSubfieldContent("210c");
		if (f210c == null)
			f210c = "";
		String f210d = rec.getSubfieldContent("210d");
		if (f210d == null)
			f210d = "";

		//odrednica
		String heading = ReportsUtils.parseHeading(rec);
		String opis="";
		opis=opis+heading.toUpperCase();
		if (opis.length() > 0)
			opis=opis+"\n";
		opis=opis+naslov.toUpperCase();
		if (opis.length() > 0)
			opis=opis+" ";
		    opis=opis+f200d.toUpperCase();
		    opis=opis.trim();
		if (opis.length() > 0)
			opis=opis+" ";
		    opis=opis+f200e.toUpperCase();
		    opis=opis.trim();
		if (opis.length() > 0)
			opis=opis+" ";
		    opis=opis+f200h.toUpperCase();
		    opis=opis.trim();
		char end = opis.charAt(opis.length() - 1);
		if (opis.length() > 0 && (end != ".".charAt(0)) && (end != ")".charAt(0))) {
			opis=opis+".- ";
		}
		 opis=opis+f210a.toUpperCase();
		if (opis.length() > 0)
			opis=opis+" ";
		    opis=opis+f210c.toUpperCase();
		    opis=opis.trim();
		if (opis.length() > 0)
			opis=opis+" ";
		 	opis=opis+f210d.toUpperCase();

		String sig = "";

		for (Primerak p : rec.getPrimerci()) {

			if (p.getInvBroj() == null)
				continue;
			Matcher matcher = pattern.matcher(p.getInvBroj());
			if (!matcher.matches())
				continue;

			sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(), p
					.getSigIntOznaka(), p.getSigFormat(), p
					.getSigNumerusCurens(), p.getSigUDK());

			String key = settings.getParam("file")
					+ getFilenameSuffix(p.getDatumInventarisanja());

			if (getList(key).size() == 0) { // lista je prazna
				item = new Item(opis.toString());
				item.add(sig);
				getList(key).add(item);
			} else {
				item = getItem(getList(key), opis.toString());
				if (item == null) {
					item = new Item(opis.toString());
					item.add(sig);
					getList(key).add(item);
				} else {
					item.add(sig);
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

	@Override
	public void finishOnline(StringBuffer buff) {
		log.info("Finishing report...");
		for (String key : itemMap.keySet()) {
			List<Item> list = itemMap.get(key);
			Collections.sort(list);
			for (Item i : list) {
				buff.append(i.toString());
			}
		}
		itemMap.clear();
		log.info("Report finished.");
	}

	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	private Pattern pattern;
	private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	private static Log log = LogFactory.getLog(BiltenPrinovljenihKnjiga.class);
}
