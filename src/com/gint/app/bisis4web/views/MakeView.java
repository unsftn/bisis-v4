package com.gint.app.bisis4web.views;

import java.io.StringWriter;
import java.io.Writer;
//import java.net.MalformedURLException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

//import com.caucho.burlap.client.BurlapProxyFactory;
//import com.gint.app.bisis4.format.HoldingsDataCoders;
import com.gint.app.bisis4.records.Record;
//import com.gint.app.bisis4.remoting.RecordManager;
import com.gint.app.bisis4web.web.Messages;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MakeView {
	 public static String locale = null;

	 public static void init(){
	  	try{
	  		ResourceBundle rb = PropertyResourceBundle.getBundle("com.gint.app.bisis4web.web.Settings");
	      locale = rb.getString("localeB");
	    }catch(Exception e) {		
			e.printStackTrace();
		}
	 }
	
public static String make(Record rec, String typeCode, String language){
  	
		if (locale == null){
			init();
		}
	
    Template temp;
    
    Configuration  cfg = new Configuration();
    cfg.setClassForTemplateLoading(MakeView.class,"templates/"+locale+"/");
  	
  	try{
  		 temp = cfg.getTemplate(typeCode+"_"+locale+".ftl");    
  	}catch(Exception e1){
  		try{
  			 temp = cfg.getTemplate(typeCode+".ftl");
  		}catch(Exception e2){
  			return "Ne postojeci tip prikaza!";
  		}
  	}
  	
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("labels", loadLabels(language));
		model.put("data", rec);
		model.put("utils", new Utils(rec));
		
    Writer out = new StringWriter();
    try {    	
			temp.process(model,out);
			out.flush();
			return out.toString();
    } catch (Exception ex) {
    	ex.printStackTrace();
    	return "Greska";
    }
  }
	
	private static Map<String,String> loadLabels(String language){
		Map<String,String> root=new HashMap<String,String>();	
		root.put("ogranci",Messages.get("ogranci", language));
		root.put("slobodnih",Messages.get("slobodno", language));
		root.put("autorR", Messages.get("AU", language));
		root.put("naslovR",Messages.get("TI",language));
		root.put("naslovDrugogR",Messages.get("AU_SE",language));
		root.put("deoR",Messages.get("PART",language));
		root.put("naslovDelaR",Messages.get("TI_SEC",language));
		root.put("mestoR",Messages.get("PUB_PLACE",language));
		root.put("izdavacR",Messages.get("PUBLISHER",language));
		root.put("godIzdR",Messages.get("PUB_DATE",language));
		root.put("brStrR",Messages.get("NUM_PAGE",language));
		root.put("dimenzijeR",Messages.get("DIM",language));
		root.put("zbirkaR",Messages.get("COLLEC",language));
		root.put("napomeneR",Messages.get("NOTES",language));
		root.put("udkR",Messages.get("UDK",language));
		root.put("signaturaPozR",Messages.get("SIGN_LOAN",language));
		root.put("signat",Messages.get("SIGN",language));
		root.put("signaturaDrugiR",Messages.get("SIGN_OTHER",language));
		root.put("slobodniR",Messages.get("FREE_COPY",language));
		root.put("invbrojR",Messages.get("INV_NUM",language));
    	root.put("dostupnost","Dostupnost primeraka");
    	root.put("url","URL");
    return root;
	}
	
//	public static void main(String[] args) {
//		try {
//			init();
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection conn = null;
//			try {
//				conn = DriverManager.getConnection("jdbc:mysql://www.biblioteka.ftn.uns.ac.rs/bisisftn?characterEncoding=UTF-8", "bisisftn", "bisisftn");
//				conn.setAutoCommit(false);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			HoldingsDataCoders.loadData(conn);
//			//System.out.println(HoldingsDataCoders.getZaduzivostStatusa("p"));
//			BurlapProxyFactory proxyFactory = new BurlapProxyFactory();
//			RecordManager recMgr = (RecordManager)proxyFactory.create(RecordManager.class, 
//					"http://www.biblioteka.ftn.uns.ac.rs/bisis/RecMgr");
//			Record rec = recMgr.getRecord(7183);
//			Utils utils = new Utils(rec);
//			System.out.println(make(rec,"detail","sr"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
