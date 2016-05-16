package com.gint.app.bisis4.reports.gbpa;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Report;


public class StatistikaBazeSerijske extends Report {

  @Override
  public void init() {
      log.info("Report initialized.");
      pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
  }

  
  @Override
  public void finish() {
	  log.info("Finishing report...");	 
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
	  boolean zapis=true;
      if (rec == null)
        return;
      Item i=null;
      String key;
      for (Godina p : rec.getGodine()) {
          String ogr ;
          if (p.getOdeljenje() == null) {
             String pom = p.getInvBroj();
             if (pom == null || pom.length() < 2)
                 continue;
             ogr = pom.substring(0, 2);

           }else if (p.getOdeljenje().length() != 2){
              continue;
           }else{
              ogr = p.getOdeljenje();
           }
           key = settings.getParam("file") + getFilenameSuffix(p.getDatumInventarisanja());
           List<Item> list=getList(key);
           i=getItem(list,ogr);
           if (i == null ){
      	      i=new Item(ogr);
      	      getList(key).add(i);  	
           }
      String nabavka = p.getNacinNabavke();
      if ("a".equals(nabavka) || "k".equals(nabavka)){
        i.kupljenih++;
        i.primeraka++;
      }
      else if ("c".equals(nabavka) || "p".equals(nabavka)){
        i.poklonjenih++;
        i.primeraka++;
        
      } 
      else if ("o".equals(nabavka)){
        i.izotkupa++;
        i.primeraka++;
      }
      else{
    	  i.ostalo++;  
    	  i.primeraka++;
      }
      if(zapis){
          i.zapisa++;
          zapis=false;
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
  
  public Item getItem(List <Item>itemsL, String code) {
	for (Item it : itemsL){
  	  if (it.odeljenje.equalsIgnoreCase(code)){			
		  return it;
	  }
	}
	return null;
  }
  public class Item implements Comparable {
	    public String odeljenje;
	    public int rekatalogizovanih;
	    public int otpisanih;
	    public int poklonjenih;
	    public int kupljenih;
	    public int izotkupa;
	    public int primeraka;
	    public int ostalo;
	    public int zapisa;
	    
	    public Item (String odeljenje){
	    	this.odeljenje=odeljenje;
	    	this.rekatalogizovanih=0;
	    	this.otpisanih=0;
	    	this.poklonjenih=0;
	    	this.kupljenih=0;
	    	this.izotkupa=0;
	    	this.primeraka=0;
	    	this.zapisa=0;
	    	this.ostalo=0;
	    }
	    public int compareTo(Object o) {
	      Item i = (Item)o;      
	      return odeljenje.compareToIgnoreCase(i.odeljenje);

	    }
	    

	    
	    public String toString() {
	      StringBuffer retVal = new StringBuffer();
	      retVal.append("<item><odeljenje>");
	      retVal.append(HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.ODELJENJE_CODER, odeljenje));
	      retVal.append("</odeljenje><otpisanih>");
	      retVal.append(otpisanih);
	      retVal.append("</otpisanih><poklonjenih>");
	      retVal.append(poklonjenih);
	      retVal.append("</poklonjenih><kupljenih>");
	      retVal.append(kupljenih);
	      retVal.append("</kupljenih><izotkupa>");
	      retVal.append(izotkupa);
	      retVal.append("</izotkupa><primeraka>");
	      retVal.append(primeraka);
	      retVal.append("</primeraka><ostalo>");
	      retVal.append(ostalo);
	      retVal.append("</ostalo><zapisa>");
	      retVal.append(zapisa);
	      retVal.append("</zapisa></item>");
	      return retVal.toString();
	    }
	    

	  }
  @Override
  public void finishInv() {
  	// TODO Auto-generated method stub
  	
  }

  @Override
  public void finishOnline(StringBuffer buff) {
  	// TODO Auto-generated method stub
  	
  } 
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(StatistikaBaze.class);
  private Pattern pattern;

}
