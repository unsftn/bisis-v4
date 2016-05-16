package com.gint.app.bisis4.reports.vlad;

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
import java.util.regex.Pattern;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Report;

public class NabavkaPoOgrancima extends Report {
	 @Override
	  public void init() {
		
		  nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();

	  }
	  public void finishInv() {
		  
	  }
	  @Override
	  public void finish() {

			for (List<Item> list : itemMap.values())
				Collections.sort(list);

			for (String key : itemMap.keySet()) {
				List<Item> list = itemMap.get(key);
				PrintWriter out = getWriter(key);
				for (Item i : list) {
					out.print(i.toString());
				}
			}
			closeFiles();
			itemMap.clear();
	  }

	  @Override
	  public void handleRecord(Record rec) {
		  if (rec == null)
		      return;
		  String greske=" ";
		      	    
		  for (Primerak p : rec.getPrimerci()) {
		      String invbr = p.getInvBroj();		      
		      if (invbr == null || invbr.equals("")) {	        
		        if (!greske.contains(String.valueOf(rec.getRecordID()))){
		        	greske=greske+ " "+rec.getRecordID();
		      }
		        continue;
		      }

		    		
		      String nacinNab = p.getNacinNabavke();
		      if (nacinNab == null || nacinNab.equals("")) {
		    	  nacinNab="x"; 
		    	  
	    	  if (!greske.contains(String.valueOf(rec.getRecordID()))){
	    		  greske=greske+ " "+rec.getRecordID();
	    		 
		       }
		    	
		      }
		      String sigla = p.getOdeljenje();
		      if (sigla == null || sigla.equals(" ")) {
		       sigla=p.getInvBroj().substring(0,2);
		      }

		        Date  invDate;
		        if (p.getStatus()!=null) {
		        	if(p.getStatus().equals("5"))  {//za presiglirane gleda datum statusa a ne datum inventarisanja
		        		invDate = p.getDatumStatusa();
		             }else{   //za sve ostale slucajeve uzimam datum inventarisanja
		            	 invDate=p.getDatumInventarisanja();
		           }
		        }else{//ukoliko nema statusa opet uzimam datum inventarisanja 
		        	invDate=p.getDatumInventarisanja();
		        }
		      String key = settings.getParam("file") + getFilenameSuffix(invDate);
		      Item item=getItem(getList(key),sigla);    
		      char t = nacinNab.charAt(0);     
		      
		      if (item == null ){
		         	item=new Item(sigla);
		         	getList(key).add(item);
		         	
		         }
		     
		      
		      switch (t) {
		        case 'k':
                  item.add(1, 0, 0, 0, 0, 0, 0,0," ");	           
		          break;
		        case 'd':   //obavezni primerak
			           item.add(0, 1, 0, 0, 0, 0, 0,0," ");
				          break;
		        case 'p': //poklon  
		           item.add(0, 0, 1, 0, 0, 0, 0,0," ");
		           break;		       
		        case 'm': // poklon ministarstva
		           item.add(0, 0, 0, 1, 0, 0, 0,0," ");
		           break;
		        case 'b': // razmena
		           item.add(0, 0, 0, 0, 1, 0, 0,0," ");
		           break;
		        case 'f': // samostalno izdanje
		           item.add(0, 0, 0, 0, 0, 1,0,0," ");
		           break;
		        case 'e': //zateceni fond
			        item.add(0, 0, 0, 0, 0, 0,1,0," ");
			        break;
		        case 'x': //zateceni fond
			        item.add(0, 0, 0, 0, 0, 0,0,1,greske);
			        break;
		        default: //nema informacije o nabavci ili je oznaka r,ili u
			        item.add(0, 0, 0, 0, 0, 0,0,1,greske);
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
		    public int donacija;
		    public int poklon;
		    public int otkup;
		    public int razmena;
		    public int samizdat;
		    public int zateceno;
		    public int nemainform;
		    public String greske;

		    

		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.kupovina = 0;
				this.donacija = 0;
				this.poklon = 0;
				this.otkup = 0;
				this.razmena = 0;
				this.samizdat = 0;
				this.zateceno = 0;
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
		    	int zarez=odeljenje.indexOf(",");
		    	if(zarez!=-1){
		    		odeljenje=odeljenje.substring(0, zarez);
		    	}
		    	StringBuffer buf = new StringBuffer();
		        buf.append("\n  <item id=\"");
		        buf.append(sigla);
		        buf.append("\">\n  <ogranak>");
		        buf.append(odeljenje);
		        buf.append(	"</ogranak>\n	<kupovina>");
		        buf.append(kupovina);
		        buf.append("</kupovina>\n    <poklon>");
		        buf.append(poklon);
		        buf.append("</poklon>\n    <donacija>");
		        buf.append(donacija);
		        buf.append("</donacija>\n    <otkup>");
		        buf.append(otkup);
		        buf.append("</otkup>\n    <razmena>");
		        buf.append(razmena);
		        buf.append("</razmena>\n    <samizdat>");
		        buf.append(samizdat);
		        buf.append("</samizdat>\n    <zateceno>");
		        buf.append(zateceno);
		        buf.append("</zateceno>\n    <nemainform>");
		        buf.append(nemainform);
		        buf.append("</nemainform>\n		<greske>");
		        buf.append(greske);
		        buf.append(" ");
		        buf.append("</greske>\n		</item>");
		        return buf.toString();
		    }
		    
		    
		    
		    
		    public void add(int kupovina, int donacija, int poklon, int otkup, int razmena, int samizdat, int zateceno,int nemainform,String greske) {
		    	this.kupovina += kupovina;
		    	this.donacija += donacija;
		    	this.poklon += poklon;
		    	this.otkup += otkup;
		    	this.razmena += razmena;
		    	this.samizdat += samizdat;
		    	this.zateceno += zateceno;   
		    	this.nemainform+=nemainform;
		    	this.greske+=greske;
		    	
		    }
		    public void addItem(Item i) {
		      	this.kupovina += i.kupovina;
		    	this.donacija += i.donacija;
		    	this.poklon += i.poklon;
		    	this.otkup += i.otkup;
		    	this.razmena += i.razmena;
		    	this.samizdat += i.samizdat;
		    	this.zateceno += i.zateceno;
		    	this.nemainform+=i.nemainform;
		    	this.greske+=i.greske;
			 }
		     
		  }
	
	 
	  
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	  NumberFormat nf;
	@Override
	public void finishOnline(StringBuffer buff) {
		// TODO Auto-generated method stub
		
	}
}
