package com.gint.app.bisis4.cards;

import java.util.*;
import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Record;
import com.gint.util.file.FileUtils;
import com.gint.app.bisis4.utils.INIFile;
import freemarker.template.*;
import java.io.*;




// FIZICKI FORMAT LISTICA: (15cm x 7,5cm) odnosno (5,91'' x 2,95'')
// OBAVEZNO !!!!!!!!!!!
// Potrebno je na sistemu defilisati format BibListic koji je 13,50cm x 7,70cm i TopMargin je 0,50 cm

/** Report -- pocetna klasa za generisanje listica.
    @author Tatjana Zubic tanja@uns.ns.ac.yu
    @version 1.0
    
    @author Bojana Dimic bdimic@uns.ns.ac.yu
    @verson 4.0
    
  */
public class Report {

	  public static int brRed;
	  public static String currentType="";
	  public  static String locale;
	  public  static String nextPage;
	  public  static int brRedova;
	 
	  public static String[] reportTypeDesc;
	  public static String[] reportTypes;
	  public static int translateX;
	  public static int translateY;
	  public static String fontSize;
	  static String izlaz1;  
	  static int brmax; 
	  static int bkmax;
    
	  private static INIFile iniFile;    
	  private static Log log = LogFactory.getLog(Report.class.getName());
	  private static Record record=null;
    
    
    static{
    	ucitajParam();
    	loadReportTypes();
    }
    
  /** Formira izvestaj za vise zapisa.
      @param docIDs niz ID zapisa
      @param rezances niz odgovarajucih zapisa
      @param typeCode tip listica
      @return HTML izvestaj
    */
  public static String makeMulti(int[] docIDs, Record[] recs, String typeCode) {
    String retVal = "";
    for (int i = 0; i < docIDs.length; i++) {
       retVal += makeOne(docIDs[i], recs[i], typeCode);
    }
    return retVal;
  }
  
  public static String makeMulti(Record[] recs, boolean saInventarnim){
  	String retVal = "";
  	for(Record rec:recs){
  		retVal += makeOne(rec, saInventarnim);
  		retVal += "<BR><BR>";
  	}
  	return retVal;
  }
  
  /** Formira izvestaj za jedan zapis.
      @param docID ID zapisa
      @param rezance Zapis
      @param typeCode tip listica
      @return HTML izvestaj
    */
  
  public static String makeOne(int docID, Record  rec, String typeCode){
	  
    String izlaz;   
    boolean formatRed=true;
    boolean formatStrana=true;
    int brSignatura;
    String type; 
    record=rec;
    
    Configuration  cfg = new Configuration();
    iniFile = BisisApp.getINIFile();
    String locale = iniFile.getString("bookcards", "locale");
    cfg.setClassForTemplateLoading(Report.class,"templejti/"+locale+"/");
    
    
    Base Base=new Base(docID, rec, typeCode);   
    
  	Template temp=null;
  	Template temp1=null;
  	try{
  		 temp = cfg.getTemplate(typeCode+"_"+locale+".ftl");
        
  	}catch(Exception e1){
  		e1.printStackTrace();
  		try{
  			 temp = cfg.getTemplate(typeCode+".ftl");
  			
  		}catch(Exception e2){
  			e2.printStackTrace();
  			return izlaz="Nepostojeci tip listica";
  		}        
  	}  	
  	try{
			 temp1 = cfg.getTemplate("_novaStr_"+locale+".ftl");
		}catch(Exception e3){
			try{
				 temp1 = cfg.getTemplate("_novaStr.ftl");
			}catch(Exception e4){
				return izlaz="Neodgovarajuci tip listica";//com.gint.app.bisis.editor.Messages.get("BISISAPP_CMD_ELIST_UNKNOWNTYPE");		  		
			}
			
		}		
		
		Map<String,Object> root=Base.struktura();
		
		RecordUtilities recUtilities = new RecordUtilities(rec);
		root.put("recUtil", recUtilities);		
		root.put("docID",new Integer(Base.getID()));
		root.put("saInventarnim", true);
		root.put("nextPage",nextPage);
		
		try{
			ResourceBundle rb = PropertyResourceBundle.getBundle(
			        Report.class.getPackage().getName()+".templejti."+locale+"."+typeCode,new Locale(locale));
			
			
		    //format=rb.getString("format").equals("true");
		  formatRed=rb.getString("formatRed").equals("true");
			formatStrana=rb.getString("formatStrana").equals("true");
			
			brmax=Base.toInt(rb.getString("brmax"));
			bkmax=Base.toInt(rb.getString("bkmax"));
			
			type=rb.getString("type");
			brSignatura=Base.toInt(rb.getString("brSignatura"));
			root.put("brSignatura",new Integer(brSignatura));
			root.put("brk",new Integer (bkmax-1));
		}catch(Exception ex1){			
				return izlaz="Greska u odredjivanju osobina";
		}
	
	if (!Base.checkPubType(type))
		return izlaz = "<BR><BR>Pogresan tip publikacije"; //+ com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_WRONGPUBTYPE") + "<BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR>";

    Writer out=new StringWriter();
    Writer out1=new StringWriter();
    try {    	
      temp.process(root,out);      
      temp1.process(root,out1);
	} catch (Exception ex) {
    log.fatal("Ne valja templejt1");
  }
	
	try {
		out.flush();
		out1.flush();
	} catch (IOException e) {		
		e.printStackTrace();
	}
	
	
    izlaz1=out1.toString();
    izlaz1=Base.format(izlaz1);
    
    izlaz=out.toString();
   // if (format)
       izlaz=Base.format(izlaz);
       
    if(formatRed && !formatStrana) {
        izlaz=Base.formatRed1(izlaz);        
    }
    else if (formatRed){
    	izlaz=Base.formatRed(izlaz);    	
    }
    if(formatStrana)
        if(!izlaz.equals("")){
            izlaz=Base.formatStrana(izlaz);            
        }   
    return izlaz;
  }
  
  
  /**
   * @author bojana dimic
   * 
   * kreira listic za prosledjeni zapis
   * pri cemu se programski odredjuje koji listic 
   * ce se prikazati u zavisnosti od tipa publikacije
   * 
   * monografska publikacija (tip 1) - monogarfski_locale.ftl
   * serijska publikacija (tip 2) - serijski_locale.ftl
   * analitika (tip 3) - analiticki_locale.ftl
   * 
   * 
   */
  
  public static String makeOne(Record  rec, boolean saInventarnim){
  	String izlaz;   
    boolean formatRed=true;
    boolean formatStrana=true;
    int brSignatura;
    String type; 
    
    Configuration  cfg = new Configuration();
    iniFile = BisisApp.getINIFile();
    String locale = iniFile.getString("bookcards", "locale");
    cfg.setClassForTemplateLoading(Report.class,"templejti/"+locale+"/");    
    Base Base=new Base(rec);    
  	Template temp=null;
  	Template temp1=null;
  	String typeCode = "";
  	
  	switch(rec.getPubType()){
	  	case 1:typeCode = "monografski"; break;
	  	case 2:typeCode = "serijski"; break;
	  	case 3:typeCode = "analiticki"; break;
	  	case 4:typeCode = "neknjizna"; break;
	  	case 5:typeCode = "elektronskiIzvori"; break;
  	} 	
  	
  	if(typeCode.equals(""))
  		return "Ne postoji tip listica za izabrani zapis";
  	
  	
  	try{
  		 temp = cfg.getTemplate(typeCode+"_"+locale+".ftl");
        
  	}catch(Exception e1){
  		e1.printStackTrace();
  		
  		try{
  			 temp = cfg.getTemplate(typeCode+".ftl");  			
  		}catch(Exception e2){
  			e2.printStackTrace();
  			return izlaz="Nepostojeci tip listica";
  		}        
  	}  	
  	try{
			 temp1 = cfg.getTemplate("_novaStr_"+locale+".ftl");
		}catch(Exception e3){
			try{
				 temp1 = cfg.getTemplate("_novaStr.ftl");
			}catch(Exception e4){
				return izlaz="Neodgovarajuci tip listica";		  		
			}			
		}	
		
		Map<String,Object> root=Base.struktura();
		
		RecordUtilities recUtilities = new RecordUtilities(rec);
		root.put("saInventarnim", saInventarnim);
		root.put("recUtil", recUtilities);		
		root.put("docID",rec.getRecordID());
		root.put("nextPage",nextPage);
		
		try{
			ResourceBundle rb = PropertyResourceBundle.getBundle(
			Report.class.getPackage().getName()+".templejti."+locale+"."+typeCode,new Locale(locale));
			
			
		    //format=rb.getString("format").equals("true");
		  formatRed=rb.getString("formatRed").equals("true");
			formatStrana=rb.getString("formatStrana").equals("true");
			
			brmax=Base.toInt(rb.getString("brmax"));
			bkmax=Base.toInt(rb.getString("bkmax"));
			
			type=rb.getString("type");
			brSignatura=Base.toInt(rb.getString("brSignatura"));
			root.put("brSignatura",new Integer(brSignatura));
			root.put("brk",new Integer (bkmax-1));
		}catch(Exception ex1){			
				return izlaz="Greska u odredjivanju osobina";
		}
	
	if (!Base.checkPubType(type))
		return izlaz = "<BR><BR>Pogresan tip publikacije"; 

    Writer out=new StringWriter();
    Writer out1=new StringWriter();
    try {    	
      temp.process(root,out);      
      temp1.process(root,out1);
	} catch (Exception ex) {
    log.fatal("Ne valja templejt1");
  }
	
	try {
		out.flush();
		out1.flush();
	} catch (IOException e) {		
		e.printStackTrace();
	}
	
	
    izlaz1=out1.toString();
    izlaz1=Base.format(izlaz1);
    
    izlaz=out.toString();
   // if (format)
       izlaz=Base.format(izlaz);
       
    if(formatRed && !formatStrana) {
        izlaz=Base.formatRed1(izlaz);        
    }
    else if (formatRed){
    	izlaz=Base.formatRed(izlaz);    	
    }
    if(formatStrana)
        if(!izlaz.equals("")){
            izlaz=Base.formatStrana(izlaz);            
        }  
    
    String dodatak="";
    if(locale.equals("gbsa"))
    	dodatak=RecordUtilities.getGodineSabac(rec);   
     
    return izlaz+dodatak;
  	
  }  
  

  public static void ucitajParam(){
  	
  	try{
  		
	  INIFile iniFile = BisisApp.getINIFile();
	 
	    translateX=Integer.parseInt(iniFile.getString("bookcards", "translateX"));
      translateY=Integer.parseInt(iniFile.getString("bookcards", "translateY"));
      fontSize=iniFile.getString("bookcards", "fontSize"); 
      brRedova=Integer.parseInt(iniFile.getString("bookcards", "brRedova"));
      locale = iniFile.getString("bookcards", "locale");
      nextPage= iniFile.getString("bookcards", "nextPage");
      currentType=iniFile.getString("bookcards", "currentType");      
  	}catch(Exception e) {
  		
		e.printStackTrace();
	}
	
      
  }

  public final static void loadReportTypes()  {
  	  String str=""; 
      try {
        String dirName = "/com/gint/app/bisis4/cards/templejti/"+locale;
        String[] files = FileUtils.listFiles(Report.class, dirName);        
        int brojFajlova=0;
        for (int i = 0; i < files.length; i++) {        	 
          if (files[i].endsWith(".ftl")){
            str=files[i].replaceAll("_"+locale+".ftl","");
  		    str=str.substring(str.lastIndexOf(locale)+locale.length()+1);
  		    if (!str.startsWith("_")) {  		
          	  brojFajlova++;       
  		    }
          }
        }
        reportTypes = new String[brojFajlova];
        boolean existCurrent=false;
        int j=0;
        for (int i = 0; i < files.length; i++) {
        	if (files[i].endsWith(".ftl")) {
        		str=files[i].replaceAll("_"+locale+".ftl","");
        		str=str.substring(str.lastIndexOf(locale)+locale.length()+1);   
        		
        		if( !str.startsWith("_")) {
        			 reportTypes[j]=str; 
            	    if (str.equals(currentType))
            	    	existCurrent=true;
            	    j++;
        	    }
            }
          }
        if(!existCurrent ){        	
        	JOptionPane.showMessageDialog(null, "Ne postoji tekuci tip");            
        }        	
      } catch (Exception ex) {
      	log.fatal("Ne moze da nadje direktorijum sa tipovima listica");
      }
  }
  
}
