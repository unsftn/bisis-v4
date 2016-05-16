package com.gint.app.bisis4.reports.ftn;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.utils.LatCyrUtils;

public class NabavkaPoOgrancimaSerijske extends Report {
	 @Override
	  public void init() {
		
		    nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
		    pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
			log.info("Report initialized.");
	  }
	  public void finishInv() {
		  
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
		  if (rec == null)
		      return;
		  boolean sig=false;
		  String greske=" ";
		      
		    
		  for (Godina p : rec.getGodine()) {
			  if(p.getInvBroj()==null)
				  continue;
			  Matcher matcher = pattern.matcher(p.getInvBroj());
		      if (!matcher.matches())
		        continue;
		      String invbr = p.getInvBroj();		      
		      if (invbr == null || invbr.equals("")) {	        
		        if (!greske.contains(String.valueOf(rec.getRecordID()))){
		        	greske=greske+ " "+rec.getRecordID();
		         }
		        continue;
		      } 		
		      String nacinNab = p.getNacinNabavke();
		      if (nacinNab == null || nacinNab.equals("")) {
		    	  nacinNab="e"; //ovo sam se dogovorila sa Mirom Miljkovic
	    	  if (!greske.contains(String.valueOf(rec.getRecordID()))){
	    		  greske=greske+ " "+rec.getRecordID();
	    		 
		       }
		//    	  log.info("Nema šifre za nabavku. Inventarni broj : "+p.getInvBroj());
		    	
		      }
		      String sigla=null;
		      if (p.getInvBroj().length()>2) {
		       sigla=p.getInvBroj().substring(0,2);
		      }else{
		    	   sigla=p.getOdeljenje();
		       }

		        Date  invDate;
	

	
                    invDate=p.getDatumInventarisanja();  
		        	String key = settings.getParam("file") + getFilenameSuffix(invDate);
		            Item item=getItem(getList(key),sigla);    
		            char t = nacinNab.charAt(0);     
		            if (item == null ){
		            	item=new Item(sigla);
		         	   	getList(key).add(item);
		            } 
		            switch (t) {
		            	case 'a': // kupovina
		            	case 'k':
		            		item.add(1, 0, 0, 0, 0, 0, 0,0,0," ");	           
		            		break;
		            	case 'o':   //obavezni primerak
		            		item.add(0, 1, 0, 0, 0, 0, 0,0,0," ");
		            		break;
		            	case 'c': 
		            	case 'p': //poklon  
		            		item.add(0, 0, 1, 0, 0, 0, 0,0,0," ");
		            		break;
		            	case 'm': // poklon ministarstva
		            		item.add(0, 0, 0, 1, 0, 0, 0,0,0," ");
		            		break;
		            	case 'r': // razmena
		            		item.add(0, 0, 0, 0, 1, 0, 0,0,0," ");
		            		break;
		            	case 'f': // samostalno izdanje
		            	case 's':
		            		item.add(0, 0, 0, 0, 0, 1,0,0,0," ");
		            		break;
		            	case 'e': //zateceni fond
		            		item.add(0, 0, 0, 0, 0, 0,1,0,0," ");
		            		break;
		            	default: //nema informacije o nabavci 
		            		item.add(0, 0, 0, 0, 0, 0,0,0,1,greske);
		            		break;
		            
		    	}
		   }
	  }
	        
	 
	  public Item getItem(List<Item> iteml, String sigla) {
			
			for (Item it : iteml){
				
				if (it.sigla.compareToIgnoreCase(sigla)==0){	
					return it;
				}
			}
		    return null;
		    
		    
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


	  public class Item  implements Comparable{
		    public String sigla;
		    public int kupovina;
		    public int obavezan;
		    public int poklon;
		    public int minist;
		    public int razmena;
		    public int samizdat;
		    public int zateceno;
		    public int presiglirano;
		    public int nemainform;
		    public String greske;

		    

		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.kupovina = 0;
				this.obavezan = 0;
				this.poklon = 0;
				this.minist = 0;
				this.razmena = 0;
				this.samizdat = 0;
				this.zateceno = 0;
			    this.presiglirano= 0;
				this.nemainform=0;
				this.greske="";
				
			}
		    

		    public int compareTo(Object o) {
		        if (o instanceof Item) {
		          Item b = (Item)o;
		          return sigla.compareTo(b.sigla);
		        }
		        return 0;
		      }
		    public String toString() {
		    	String odeljenje=HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.ODELJENJE_CODER, sigla);
		    	int zarez;
		    	if(odeljenje!=null){
		    	 zarez=odeljenje.indexOf(",");
		    	}else{
		    		zarez=-1;
		    		odeljenje=sigla;
		    	}
		    	if(zarez!=-1){
		    		odeljenje=odeljenje.substring(0, zarez);
		    	}
		    	StringBuffer buf = new StringBuffer();
		        buf.append("\n  <item id=\"");
		        buf.append(sigla);
		        buf.append("\">\n  <ogranak>");
		        buf.append(LatCyrUtils.toCyrillic(odeljenje));
		        buf.append(	"</ogranak>\n	<kupovina>");
		        buf.append(kupovina);
		        buf.append("</kupovina>\n    <poklon>");
		        buf.append(poklon);
		        buf.append("</poklon>\n    <obavezan>");
		        buf.append(obavezan);
		        buf.append("</obavezan>\n    <minist>");
		        buf.append(minist);
		        buf.append("</minist>\n    <razmena>");
		        buf.append(razmena);
		        buf.append("</razmena>\n    <samizdat>");
		        buf.append(samizdat);
		        buf.append("</samizdat>\n    <zateceno>");
		        buf.append(zateceno);
		        buf.append("</zateceno>\n    <presiglirano>");
		        buf.append(presiglirano);
		        buf.append("</presiglirano>\n		<nemainform>");
		        buf.append(nemainform);
		        buf.append("</nemainform>\n		<greske>");
		        buf.append(greske);
		        buf.append("</greske>\n		</item>");
		        
		        return buf.toString();
		    }
		    
		    
		    
		    
		    public void add(int kupovina, int obavezan, int poklon, int minist, int razmena, int samizdat, int zateceno,int presiglirano,int nemainform,String greske) {
		    	this.kupovina += kupovina;
		    	this.obavezan += obavezan;
		    	this.poklon += poklon;
		    	this.minist += minist;
		    	this.razmena += razmena;
		    	this.samizdat += samizdat;
		    	this.zateceno += zateceno;
		    	this.presiglirano += presiglirano;  
		    	this.nemainform+=nemainform;
		    	this.greske+=greske;
		    	
		    }
		    public void addItem(Item i) {
		      	this.kupovina += i.kupovina;
		    	this.obavezan += i.obavezan;
		    	this.poklon += i.poklon;
		    	this.minist += i.minist;
		    	this.razmena += i.razmena;
		    	this.samizdat += i.samizdat;
		    	this.zateceno += i.zateceno;
		    	this.presiglirano += i.presiglirano;
		    	this.nemainform+=i.nemainform;
		    	this.greske+=i.greske;
			 }
		     
		  }
	
	 
	  
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	  private static Log log = LogFactory.getLog(NabavkaPoOgrancima.class);
	  NumberFormat nf;
	@Override
	public void finishOnline(StringBuffer buff) {
		// TODO Auto-generated method stub
		
	}
}
