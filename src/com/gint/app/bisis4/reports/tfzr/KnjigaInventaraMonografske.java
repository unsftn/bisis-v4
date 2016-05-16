package com.gint.app.bisis4.reports.tfzr;

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

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.reports.ReportsUtils;
import com.gint.app.bisis4.utils.FormatBigDecimal;
import com.gint.app.bisis4.utils.Signature;
import com.gint.util.string.StringUtils;

public class KnjigaInventaraMonografske extends Report {

 	 public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
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

	  }
 @Override
 public void init() {
	  itemMap.clear();
	    pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
 }

 @Override
 public void finish() {
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
 }
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
   
    if (rec.getPubType()!=1)
      return;
    Date date=rec.getCreationDate();
    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    String autor = ReportsUtils.getAutor(rec); 
    if (autor == null)
      autor = "";
    autor.trim();
    String izdavac = rec.getSubfieldContent("210c");
    if (izdavac == null)
      izdavac = "";
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
      mesto = "";
    String god = rec.getSubfieldContent("210d");
    if (god == null)
      god = "";
    String brsveske = rec.getSubfieldContent("200v");
    if (brsveske == null)
      brsveske = "";
    String godIzd=rec.getSubfieldContent("100c");
    StringBuffer opis = new StringBuffer();
    opis.append(autor);
     if (!(opis.equals(" "))&&(opis.length() > 0))
      opis.append(". ");
    opis.append(naslov);
    opis.append(", ");
    opis.append(brsveske);
    if (brsveske.length() > 0)
      opis.append(", ");
    opis.append(mesto);
    if (mesto.length() > 0)
      opis.append(", ");
    opis.append(izdavac);
    if (izdavac.length() > 0)
      opis.append(", ");
   
    if (god.length() > 0){
    	 opis.append(god);
         opis.append(".");
    }
    else{
    	opis.append(godIzd);
        opis.append(".");
    }
      
   
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
    String sig = " ";
    for (Primerak p : rec.getPrimerci()) {
    	
        if(p.getInvBroj()==null)
      	  continue;
        Matcher matcher = pattern.matcher(p.getInvBroj());
        if (!matcher.matches())
          continue;
        sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(), 
            p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
            p.getSigUDK());
        if (sig.equals(""))
      	  sig=" ";
        Item i = new Item();
        i.invbr = p.getInvBroj();
        i.datum = p.getDatumInventarisanja();
        i.opis = opis.toString();
        String povez=HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.POVEZ_CODER, p.getPovez());
        if(povez!=null){
        int zagrada=povez.indexOf("(");
        if(zagrada !=-1){
      	  i.povez= povez.substring(0,zagrada); 
        }else{
        i.povez=povez;
        }
        }else{
        	i.povez="";
        }
        i.dim = dim;
        String dobavljac=p.getDobavljac();
        String vrnab = p.getNacinNabavke();
        i.nabavka = HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.NACINNABAVKE_CODER, vrnab);
        i.cena = p.getCena() == null ? " " : 
          FormatBigDecimal.format(p.getCena(), 2).toPlainString();
        i.sig = sig;
        i.napomena = p.getNapomene();
        String part=settings.getParam("part");
        String key;
        if(part==null){
          key = settings.getParam("file") + getFilenameSuffix(date);
        }else{ //ukoliko zelimo iventarnu knjigu od po npr 1000
        	   //parametar part odredjuje koliko je primeraka u jednom fajlu
          String invBroj=p.getInvBroj().substring(2);
          int partBr=Integer.parseInt(part);
          int ceo=Integer.parseInt(invBroj)/partBr;
          int odBr=ceo*partBr;
          int doBr=ceo*partBr + partBr;
          key = settings.getParam("file") +"-"+ReportsUtils.addZeroes(String.valueOf(odBr))+"_do_"+ReportsUtils.addZeroes(String.valueOf(doBr));
        }
        getList(key).add(i);
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
  
 

  public class Item implements Comparable {
	    public String invbr;
	    public Date datum;
	    public String opis;
	    public String povez;
	    public String dim;
	    public String nabavka;
	    public String cena;
	    public String sig;
	    public String napomena;
	    public String ogr;
	    
	    public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return invbr.substring(4).compareTo(b.invbr.substring(4));
	      }
	      return 0;
	    }

	    public String toString() {
		      StringBuffer buf = new StringBuffer();
		      buf.append("\n  <item>\n    <rbr>");
		      buf.append(invbr);
		      buf.append("</rbr>\n    <datum>");
		      buf.append(datum == null ? "" : sdf.format(datum));
		      buf.append("</datum>\n    <opis>");
		      buf.append(opis == null ? "" : StringUtils.adjustForHTML(opis));
		      buf.append("</opis>\n    <povez>");
		      buf.append(povez);
		      buf.append("</povez>\n    <dim>");
		      buf.append(dim == null ? "" : StringUtils.adjustForHTML(dim));
		      buf.append("</dim>\n    <nabavka>");
		      buf.append(nabavka == null ? "" : StringUtils.adjustForHTML(nabavka));
		      buf.append("</nabavka>\n    <cena>");
		      buf.append(cena == null ? "" : StringUtils.adjustForHTML(cena));
		      buf.append("</cena>\n    <signatura>");
		      buf.append(sig == null ? "" : StringUtils.adjustForHTML(sig));
		      buf.append("</signatura>\n    <napomena>");
		      buf.append(napomena == null ? "" : StringUtils.adjustForHTML(napomena));
		      buf.append("</napomena>\n");
		      buf.append ("<sortinv>");
		      buf.append(invbr.substring(4));
		      buf.append("</sortinv>\n    </item>");
		      return buf.toString();
		    }
}
 

  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(KnjigaInventaraMonografske.class);
@Override
public void finishOnline( StringBuffer buff ) { 

	    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);   
	      Collections.sort(list);
	      for (Item i : list){
	    	  buff.append(i.toString());
	    	   
	      }
	    }
	    itemMap.clear();
}
}
