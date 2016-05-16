package com.gint.app.bisis4.reports;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
/**
 * @author Bojana Dimic  
 */

public class InvNumberHolesReport {
	
	/**
	 * pronalazi rupe u inventarnim brojevima
	 * parametri su duzine 2 ili *
	 * ako je * na tom mestu se moze nalaziti bilo sta 
	 * na primer bilo koje odeljenje 
	 */
	
	public static String getHoles(String odeljenje, String invKnjiga, int odInt, int doInt){		
		// pogresna vrednost parametara
		if(odeljenje.equals("*") && invKnjiga.equals("*")) return null;
		if(!odeljenje.equals("*") && odeljenje.length()!=2) return null;
		if(!invKnjiga.equals("*") && invKnjiga.length()!=2) return null;
		
		// parametri su ok
		StringBuffer retVal = new StringBuffer();
 		//konekcija na bazu		
    ReportCollection reportCollection = new ReportCollection("/reports.ini");
    Connection conn = null;
    try {
  		Class.forName(reportCollection.getDriver());
  		conn = DriverManager.getConnection(reportCollection.getJdbcUrl(),
  		          reportCollection.getUsername(), reportCollection.getPassword());
  		
  		PreparedStatement stmt;			
			if(!odeljenje.equals("*") && invKnjiga.equals("*")){				
				for (int brojac = odInt;brojac<=doInt;brojac++){
					String brojStr = String.valueOf(brojac);
					brojStr = "00000000000".substring(0, 7-brojStr.length())+brojStr;				
					String invBroj = odeljenje+"__"+brojStr;			
					stmt = conn.prepareStatement(SELECT_INVBROJ_PRIMERKA);
					stmt.setString(1, invBroj);
					ResultSet rs = stmt.executeQuery();
					if(!rs.next()) retVal.append(brojStr+"\n");
					stmt.close();
				}
			}
			if(!invKnjiga.equals("*") && odeljenje.equals("*")){
				for (int brojac = odInt;brojac<=doInt;brojac++){
					String brojStr = String.valueOf(brojac);
					brojStr = "00000000000".substring(0, 7-brojStr.length())+brojStr;				
					String invBroj = "__"+invKnjiga+brojStr;			
					stmt = conn.prepareStatement(SELECT_INVBROJ_PRIMERKA);
					stmt.setString(1, invBroj);
					ResultSet rs = stmt.executeQuery();
					if(!rs.next()) retVal.append(brojStr+"\n");
					stmt.close();
				}
			}
			if(!invKnjiga.equals("*") && !odeljenje.equals("*")){
				for (int brojac = odInt;brojac<=doInt;brojac++){
					String brojStr = String.valueOf(brojac);
					brojStr = "00000000000".substring(0, 7-brojStr.length())+brojStr;				
					String invBroj = odeljenje+invKnjiga+brojStr;			
					stmt = conn.prepareStatement(SELECT_INVBROJ_PRIMERKA);
					stmt.setString(1, invBroj);
					ResultSet rs = stmt.executeQuery();
					if(!rs.next()) retVal.append(brojStr+"\n");
					stmt.close();
				}
			}
			} catch (SQLException e) {				 
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}		
		
		
		return retVal.toString();
		
	}
	
	
	private static String SELECT_INVBROJ_PRIMERKA = "SELECT inv_broj FROM Primerci " +
			"WHERE inv_broj like ?";
	
/*	public static void main (String[] args){
		List<String> holes = getHoles("00", "00", 1, 1000);
		System.out.println("Rupe u inventarnim brojevima: ");
		for(String inv:holes){
			System.out.println(inv);
			
			
		}
	}*/

}
