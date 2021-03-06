package com.gint.app.bisis4.reports.gbki;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Period;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.utils.LatCyrUtils;

public class StatistikaNabavke extends Report {

	@Override
	  public void init() {
		  itemMap.clear();
		    pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
		    log.info("Report initialized.");
	  }
	  @Override
	  public void finishInv() {  
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
    Float cena;
    String code = rec.getSubfieldContent("101a");
    for (Primerak p : rec.getPrimerci()) {
    	 Date datInv = p.getDatumInventarisanja();
    	 String key = settings.getParam("file") + getFilenameSuffix(datInv);
    	 String invBr = p.getInvBroj();
     	if (invBr == null || invBr.equals("")|| invBr.length()<4){
     		continue;
     	}
    	String odeljenje=invBr.substring(0, 2);
    	String nacinNab = p.getNacinNabavke();
    	if (nacinNab == null || nacinNab.equals("")){
    		continue;
    	}
    	
      	
    	if (p.getCena() == null || p.getCena().equals("")) {
    		cena=Float.valueOf("0");	
    	}else{
    	 cena=p.getCena().floatValue();
    	}
        List<Item> list=getList(key);
        Item item=getItem(list,odeljenje);
        if (item == null ){
        	item=new Item(odeljenje);
        	getList(key).add(item);  	
        }
        
        
        // nacin nabavke i vrednost
        String nacin = nacinNab.trim();;
        if (nacin.equals("a") || nacin.equals("k")) {
        	item.add(0, 0, 0, 0, 1, 0, 0, cena, 0, 0);
          
        } else if (nacin.equals("c") || nacin.equals("p")|| nacin.equals("o")) {
              String finansijer=p.getFinansijer();
        	  if (finansijer!=null){
        		  finansijer = finansijer.trim(); 
        	      finansijer=LatCyrUtils.toLatin( finansijer);
        	      finansijer=finansijer.toLowerCase();
        	      if (finansijer.indexOf("min")!=-1) {
        	    	  item.add(0, 0, 0, 0, 0, 0, 1, 0, 0, cena);  
        	      }else{
        	    	  item.add(0, 0, 0, 0, 0, 1, 0, 0, cena, 0);
        	      }
        	  } else {
                  item.add(0, 0, 0, 0, 0, 1, 0, 0, cena, 0);
             }
         }

        if (code==null)
         code="";
        if (code.equals("scr")||code.equals("scc")) //srpski
          item.add(0, 1, 0, 0, 0, 0, 0, 0, 0, 0);	
          
        else if (code.equals("hun")) //madjarski
          item.add(0, 0, 1, 0, 0, 0, 0, 0, 0, 0); 	          
        else  //ostalo
          item.add(0, 0, 0, 1, 0, 0, 0, 0, 0, 0);	
        
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
  	  if (it.code.equals(code)){			
		  return it;
	  }
	}
	return null;
  }
 
  public class Item implements Comparable {
	  public String code;
	    public int quantity = 0;	    
	    public int srpski;
	    public int madjarski;
	    public int strani;
	    public int kupovina;
	    public int poklon;
	    public int poklonM;
	    public double vredKupovina;
	    public double vredPoklon;
	    public double vredPoklonM;
	    public boolean isTotal;

	    public Item(String code) {
			super();
			this.code = code;
			this.quantity = 0;
			
			this.srpski = 0;
			this.madjarski = 0;
			this.strani = 0;
			this.kupovina = 0;
			this.poklon = 0;
			this.poklonM = 0;
			this.vredKupovina = 0;
			this.vredPoklon = 0;
			this.vredPoklonM = 0;
			this.isTotal = false;
			
		}

		public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return code.compareTo(b.code);
	      }
	      return 0;
	    }

		public String toString() {
		      StringBuffer buf = new StringBuffer();
		      buf.append("\n  <item code=\"");
		      buf.append(code);
		      buf.append("\" total=\"");
		      buf.append(isTotal);
		      buf.append("\">\n    <srpski>");
		      buf.append(srpski);
		      buf.append("</srpski>\n    <madjarski>");
		      buf.append(madjarski);
		      buf.append("</madjarski>\n    <strani>");
		      buf.append(strani);
		      buf.append("</strani>\n    <jezukup>");
		      buf.append(srpski + madjarski + strani);
		      buf.append("</jezukup>\n    <kupovina>");
		      buf.append(kupovina);
		      buf.append("</kupovina>\n    <poklon>");
		      buf.append(poklon);
		      buf.append("</poklon>\n    <poklonM>");
		      buf.append(poklonM);
		      buf.append("</poklonM>\n    <vredKupovina>");
		      buf.append(vredKupovina);
		      buf.append("</vredKupovina>\n    <vredPoklon>");
		      buf.append(vredPoklon);
		      buf.append("</vredPoklon>\n    <vredPoklonM>");
		      buf.append(vredPoklonM);
		      buf.append("</vredPoklonM>\n    <vredUkup>");
		      buf.append(vredKupovina + vredPoklon + vredPoklonM);
		      buf.append("</vredUkup>\n  </item>");
		      return buf.toString();
		    }
	    
	    	    
	    
	    public void add(int quantity, int srpski, int madjarski, int strani, 
	    		int kupovina, int poklon, int poklonM, double vredKupovina, double vredPoklon,
	            double vredPoklonM) {
	    	this.quantity += quantity;
	    	this.srpski += srpski;
	    	this.madjarski += madjarski;
	    	this.strani += strani;
	    	this.kupovina += kupovina;
	    	this.poklon += poklon;
	    	this.poklonM += poklonM;
	    	this.vredKupovina += vredKupovina;
	    	this.vredPoklon += vredPoklon;
	    	this.vredPoklonM += vredPoklonM;
	    	this.isTotal=true;
	    }
	    public void addItem(Item i) {
	    	quantity += i.quantity;
	    	quantity += i.quantity;
	    	srpski += i.srpski;
	    	madjarski += i.madjarski;
	    	strani += i.strani;
	    	kupovina += i.kupovina;
	    	poklon += i.poklon;
	    	poklonM += i.poklonM;
	    	vredKupovina += i.vredKupovina;
	    	vredPoklon += i.vredPoklon;
	    	vredPoklonM += i.vredPoklonM;
	      
	    }
	  }
  public class ItemProblems{
	  int noCode;
	  int noPrice;
	  int noInvDate;
	  int noBranch;
	  int noInvNum;
	  
	  String problems;
	  public ItemProblems() {
			super();
			this.noCode = 0;
			this.noPrice = 0;
			this.noInvDate = 0;
			this.problems = "";
			this.noBranch = 0;
			this.noInvNum = 0;
		}
	  
	  
	  public void add(int noCode, int noPrice, int noInvDate, int noBranch, int noInvNum, String problems) {
			this.noCode += noCode;
	        this.noPrice += noPrice;
	        this.noInvDate += noInvDate;
	        this.noBranch += noBranch;
	        this.noInvNum += noInvNum;
	        this.problems += problems;
	        
	      }
	    public String toString() {
	        StringBuffer buf = new StringBuffer();
	        
	        buf.append("\n  <item>\n    <noSignature>");
	        buf.append(noCode);
	        buf.append("</noSignature>\n    <noPrice>");
	        buf.append(noPrice);
	        buf.append("</noPrice>\n    <noInvDate>");
	        buf.append(noInvDate);
	        buf.append("</noInvDate>\n  <noBranch>");
	        buf.append(noBranch);
	        buf.append("</noBranch>\n   <noInvNum>");
	        buf.append(noInvNum);
	        buf.append("</noInvNum>\n   <problems>");
	        buf.append(problems);
	        buf.append("</problems>\n   </item>");
	        return buf.toString();
	      }
		
  }
  public char getFirstDigit(String s) {
	    if (s.length() == 0)
	      return ' ';
	    int pos = 0;
	    if (s.charAt(0) == '(') {
	      pos = s.indexOf(')') + 1;
	      if (pos == 0 || pos == s.length())
	        pos = 1;
	    }
	    while (!Character.isDigit(s.charAt(pos)) && pos < s.length())
	      pos++;
	    if (pos == s.length())
	      return ' ';
	    return s.charAt(pos);
	  }
 
  public void addTotals(List<Item> itemList) {
	    
	    Item total = new Item("UK");
	    for (Item i:itemList){
	    	total.addItem(i);
	    }
	    	    
	    items.add(total);
	    
	  }
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Period period;
  private Pattern pattern;
  private List<Item> items = new ArrayList<Item>();
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  ItemProblems ip;
  private static Log log = LogFactory.getLog(StatistikaNabavke.class);
  NumberFormat nf;
@Override
public void finishOnline(StringBuffer buff) {
	// TODO Auto-generated method stub
	
}

}
