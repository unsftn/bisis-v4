package com.gint.app.bisis4.reports.ftn;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.format.HoldingsDataCoders;
import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.utils.LatCyrUtils;


public class StatistikaObradjivaca extends Report {
  @Override
  public void init() {
    itemMap.clear();
    log.info("Report initialized");
    pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
  }

  @Override
  public void finish() {
    log.info("Finishing report...");
    for (String key : itemMap.keySet()) {
      Map<String, Item> map = itemMap.get(key);
      PrintWriter out = getWriter(key);
      for (Item i : map.values()){
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

	String obr;
    if (rec == null)
      return;
    if (rec.getSubfieldContent("001c")!=null && rec.getSubfieldContent("001c").compareToIgnoreCase("m")==0){
 
    for (Field f : rec.getFields("992")) {
      String type="";
      String sdate=null;
      Subfield sf6 = f.getSubfield('6');//broj primeraka
      Subfield sfb = f.getSubfield('f'); //koje obradio
      if (sfb == null || sfb.getContent() == null)
        continue;
      obr=f.getSubfield('f').getContent();
      int brPrimeraka = 1;
      
		if (f.getSubfield('b')!=null){
		    type = f.getSubfield('b').getContent();
		}
		if (f.getSubfield('c')!=null){
		    sdate = f.getSubfield('c').getContent();
		}
      if (sf6 != null && sf6.getContent() != null && sf6.getContent().trim().compareToIgnoreCase("")!=0 && type.compareToIgnoreCase("cr")!=0) {
  
        try {
          brPrimeraka = Integer.parseInt(sf6.getContent().trim());
        } catch (Exception ex) {
          log.warn("Invalid value in f9926: " + sf6.getContent() + ", ID: "
              + rec.getRecordID());
        }
      }

      Date date = null;
      try {
        date = intern.parse(sdate);
        String key = settings.getParam("file") + getFilenameSuffix(date);
        Item item = getItem(key, obr);
        if ("cr".equals(type))
          item.add(1, 0, 0, 0,0,0);
        else if ("dop".equals(type)){
          item.add(0, 1, 0, 0,0,0);
        }else if ("isp".equals(type)){
          item.add(0, 0, 1, 0,0,0);
        }else if ("red".equals(type))
          item.add(0, 0, 0, brPrimeraka,0,0);
        else if ("nn".equals(type))
            item.add(0, 0, 0, 0, brPrimeraka,0);
        else if ("evid".equals(type))
            item.add(0, 0, 0, 0, 0,brPrimeraka);
      } catch (Exception ex) {
          log.warn("problem sa datumom "+sdate +": "+rec.getRecordID());
      }
    
      }
    }
  }

  public class Item implements Comparable<Item> {
    public String obr;
    public int cr;
    public int dop;
    public int isp;
    public int red;
    public int nn;
    public int evid;
    public int compareTo(Item o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return obr.compareTo(b.obr);
      }
      return 0;
    }

    public Item(String obr) {
      cr = 0;
      dop = 0;
      isp = 0;
      red = 0;
      nn=0;
      evid=0;
      this.obr = HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.LIBRARIAN_CODER, obr);
      if(this.obr!=null){
    	  this.obr=LatCyrUtils.toCyrillic(this.obr);
     }else{
    	 this.obr=obr;
     }
    	 
    }

    public String toString() {
        return "<item><obr>"+obr+"</obr><cr>"+cr+"</cr><dp>"+dop+"</dp><co>"+isp+"</co><re>"+red+"</re><nov>"+nn+"</nov><evid>"+evid+"</evid></item>\n";
      }

    public int hashCode() {
      return obr.hashCode();
    }

    public boolean equals(Object o) {
      Item i = (Item) o;
      return obr.equals(i.obr);
    }

    public void add(int cr, int dp, int co, int re,int nn,int ev) {
      this.cr += cr;
      this.dop += dp;
      this.isp += co;
      this.red += re;
      this.nn+=nn;
      this.evid+=ev;
    }

    public void setObr(String obr) {
      this.obr = obr;
    }
 

  }

  public Item getItem(String key, String obr) {
    Map<String, Item> map = getItemMap(key);
    Item i = getItem(map, obr);
    return i;
  }
  
  public Item getItem(Map<String, Item> map, String obr) {
    Item i = map.get(obr);
    if (i == null) {
      i = new Item(obr);
      map.put(obr, i);
    }
    return i;
  }
  
  public Map<String, Item> getItemMap(String key) {
    Map<String, Item> map = itemMap.get(key);
    if (map == null) {
      map = new HashMap<String, Item>();
      itemMap.put(key, map);
    }
    return map;
  }
  private Pattern pattern;

  private Map<String, Map<String, Item>> itemMap = new HashMap<String, Map<String, Item>>();
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  private static Log log = LogFactory.getLog(StatistikaObradjivaca.class);
@Override
public void finishInv() {
	// TODO Auto-generated method stub
	
}


@Override
public void finishOnline(StringBuffer buff) {
	// TODO Auto-generated method stub
	
}
}
