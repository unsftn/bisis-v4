package com.gint.app.bisis4.reports.apv;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Period;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.reports.ReportsUtils;

import com.gint.app.bisis4.utils.LatCyrUtils;
import com.gint.util.string.StringUtils;

public class RepublickiMaterijal extends Report {
	public class Item implements Comparable {
	    public String opis;

	    
	    public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return opis.compareTo(b.opis);
	      }
	      return 0;
	    }

	    public String toString() {
		      StringBuffer buf = new StringBuffer();
		      buf.append("\n  <item>\n  ");
		      buf.append("  <opis>");
		      buf.append(opis == null ? "" : StringUtils.adjustForHTML(LatCyrUtils.toLatin(opis)));
		      buf.append("</opis>\n   ");
		      buf.append(" </item>");
		      return buf.toString();
		    }
		 }
	  public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
		  log.info("Finishing report...");
		    for (List<Item> list : itemMap.values())
		      Collections.sort(list);
		    
		    for (String key : itemMap.keySet()) {
		      List<Item> list = itemMap.get(key);
		      PrintWriter out = getWriter(key);
		      for (Item i : list){
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
	    pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
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
	      for (Item i : list){
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
    if (rec == null)
      return;
    if (rec.getPubType() != 1)
      return;

    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    String izdavac = rec.getSubfieldContent("200f");
    if (izdavac == null)
      izdavac = "";
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
      mesto = "";
    String god = rec.getSubfieldContent("210d");
    if (god == null)
      god = "";

    
    StringBuffer opis = new StringBuffer();
    opis.append(naslov);
    opis.append("/");
    opis.append(izdavac);
    opis.append(".- ");
    opis.append(mesto);
    opis.append(",");
    opis.append(god);
    opis.append(".");
 
    List<Primerak> l=rec.getPrimerci();
    for (int i=0; i<l.size();i++) { 	
    	Primerak p=l.get(i);
      if(p.getInvBroj()==null)
    	  continue;
      Matcher matcher = pattern.matcher(p.getInvBroj());
      if (!matcher.matches())
        continue;

      Item it = new Item();
      it.opis = opis.toString();
   
      String part=settings.getParam("part");
      String type=settings.getParam("type");
      String key;
      if(part==null){
    	if (type.equals("online")){
    		key = settings.getParam("file");
    	}else{
         key = settings.getParam("file") + getFilenameSuffix(p.getDatumInventarisanja());
    	}
      }else{ //ukoliko zelimo iventarnu knjigu od po npr 1000
      	   //parametar part odredjuje koliko je primeraka u jednom fajlu
        String invBroj=p.getInvBroj().substring(2);
        int partBr=Integer.parseInt(part);
        int ceo=Integer.parseInt(invBroj)/partBr;
        int odBr=ceo*partBr;
        int doBr=ceo*partBr + partBr;
        key = settings.getParam("file") +"-"+ReportsUtils.addZeroes(String.valueOf(odBr))+"_do_"+ReportsUtils.addZeroes(String.valueOf(doBr));
      }
      getList(key).add(it);
      
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
  
  

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Period period;
  private Pattern pattern;
  private List<Item> items = new ArrayList<Item>();
  private String name;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(InvKnjigaMonografske.class);
@Override
public void finishOnline( StringBuffer buff ) {
	 log.info("Finishing report...");
	 

	    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);   
	      Collections.sort(list);
	      for (Item i : list){
	    	  buff.append(i.toString());
	    	   
	      }
	    }
	    itemMap.clear();
	    log.info("Report finished.");
}
}
