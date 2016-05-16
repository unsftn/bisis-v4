package com.gint.app.bisis4.reports;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.gint.app.bisis4.format.UItem;
 
public class SifarniciReports {
	
 private static Log log = LogFactory.getLog(SifarniciReports.class.getName());
		
	static public List<UItem> sif_Odeljenje = new ArrayList<UItem>(); 
	static public List<UItem> sif_Format = new ArrayList<UItem>();
	static public List<UItem> sif_Status = new ArrayList<UItem>();
	static public List<UItem> sif_Povez = new ArrayList<UItem>();
	static public List<UItem> sif_Podlokacija = new ArrayList<UItem>();
	static public List<UItem> sif_NacinNabavke = new ArrayList<UItem>();
	static public List<UItem> sif_InternaOznaka = new ArrayList<UItem>();
	static public List<UItem> sif_InvKnj = new ArrayList<UItem>();
	static public List<UItem> sif_Dostupnost = new ArrayList<UItem>();
		
		static{		
			init();
		}
		
		public static void init(){
	    Connection conn = ReportRunner.conn;
	    try {
	      Statement stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery("SELECT * FROM Odeljenje;");
	      sif_Odeljenje = createList(rs);     
	      rs = stmt.executeQuery("SELECT * FROM SigFormat;");
	      sif_Format = createList(rs);
	      rs = stmt.executeQuery("SELECT * FROM Status_Primerka");
	      sif_Status = createList(rs);
	      rs = stmt.executeQuery("SELECT * FROM Povez");
	      sif_Povez = createList(rs);
	      rs = stmt.executeQuery("SELECT * FROM Podlokacija");
	      sif_Podlokacija = createList(rs);
	      rs = stmt.executeQuery("SELECT * FROM Nacin_nabavke");
	      sif_NacinNabavke = createList(rs);
	      rs = stmt.executeQuery("SELECT * FROM Interna_oznaka");
	      sif_InternaOznaka = createList(rs);
	      rs = stmt.executeQuery("SELECT * FROM Invknj");
	      sif_InvKnj = createList(rs);     
	      rs = stmt.executeQuery("SELECT * FROM Dostupnost");
	      sif_Dostupnost = createList(rs);     
	    } catch (SQLException ex) {      
	      log.fatal(ex);
	    }   
	  };
		
		private static List<UItem> createList(ResultSet rs){
			List<UItem> list = new ArrayList<UItem>();
			UItem ui;
			try {
				while(rs.next()){
					ui = new UItem(rs.getString(1),rs.getString(2));				
					list.add(ui);
				}
			} catch (SQLException ex) {			
				log.fatal(ex);
			}		
			return list;
		}
		
		private static boolean containsCode(String code,List<UItem> list){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getCode().equals(code)) return true;
			}
			return false;
		}
		
		public static boolean isValidOdeljenje(String code){
	    if(sif_Odeljenje.size()>0)
	      return containsCode(code,sif_Odeljenje);
	    else return true;
		}
		
		public static boolean isValidFormat(String code){
			return containsCode(code,sif_Format);
		}
		
		public static boolean isValidStatus(String code){
			return containsCode(code,sif_Status);
		}
		
		public static boolean isValidPovez(String code){
			return containsCode(code,sif_Povez);
		}
		
		public static boolean isValidPodlokacija(String code){
			return containsCode(code,sif_Podlokacija);
		}
		
		public static boolean isValidNacinNabavke(String code){
			return containsCode(code,sif_NacinNabavke);
		}
		
		public static boolean isValidInternaOznaka(String code){
			return containsCode(code,sif_InternaOznaka);
		}
	  
	  public static boolean isValidDostupnost(String code){
	    return containsCode(code,sif_Dostupnost);
	  }
		
		public static String getOdeljenjeNaziv(String code){
			if (code==null)
				return "";
			for(int i=0;i<sif_Odeljenje.size();i++){
				if(sif_Odeljenje.get(i).getCode().equals(code))
					return sif_Odeljenje.get(i).getValue();
			}
			return "";
		}
		
		public static String getPovezNaziv(String code){
			if (code==null)
				return "";
			for(int i=0;i<sif_Povez.size();i++){
				if(sif_Povez.get(i).getCode().equals(code))
					return sif_Povez.get(i).getValue();
			}
			return "";
		}
		public static String getNabavkaNaziv(String code){
			if (code==null)
				return "";
			for(int i=0;i<sif_NacinNabavke.size();i++){
				if(sif_NacinNabavke.get(i).getCode().equals(code))
					return sif_NacinNabavke.get(i).getValue();
			}
			return "";
		}
		
	}

