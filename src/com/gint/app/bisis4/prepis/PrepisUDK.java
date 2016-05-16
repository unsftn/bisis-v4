package com.gint.app.bisis4.prepis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.textsrv.DBStorage;


public class PrepisUDK{
	
	private static boolean prepis675=true;
	public static Connection conn;
	public static Map<String,String> preslikavanje;

	/**
	*@paramargs
	*/
	public static void main(String[] args) throws Exception{
		
		
		
		
		if(args.length!=5 && args.length!=4){
			System.out
				.println("index: <address> <database> <user> <password> [no675]");
			return;
		}
		String address=args[0];
		String database=args[1];
		String user=args[2];
		String password=args[3];
		String port = "3308";
		if(args.length==5){
			if(args[4].startsWith("no"))
				prepis675=false;
		}		
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("|Connecting to database...");
		conn=DriverManager.getConnection("jdbc:mysql://"+address +":"+port
				+"/"+database+"?characterEncoding=UTF-8",user,password);

		conn.setAutoCommit(true);
		System.out.println("|Connection created.");
	 	DBStorage storage=new DBStorage();
	 	Statement stmt=conn.createStatement();
		ResultSet rset=stmt.executeQuery("SELECT record_id FROM Records");
		int totalSigReplaced=0;
		int totalRecordsChanged=0;
		int count = 0;
		
		
		while(rset.next()){
			
			int recid=rset.getInt(1);
			//System.out.println("Recordid="+recid);
			Record rec=storage.get(conn,recid);
			count++;	
			boolean recordChanged = false;
			if(rec!=null){
				//primerci
				/*
				List<Primerak> primerci = rec.getPrimerci();
				for(Primerak p:primerci){
					String sigUDK = p.getSigUDK();
					String nova = getNewSignatura(sigUDK);					
					if(nova!=null){
						recordChanged = true;
						p.setSigUDK(nova);						
						totalSigReplaced++;
					}
				}
				*/
				
				//675
				if(prepis675){
					List<Field> fields675=rec.getFields("675");
					for(Field f675:fields675){
						if(f675!=null){
							Subfield sfa=f675.getSubfield('a');
							if(sfa!=null){
								String signatura = sfa.getContent();
								String nova = getNewSignatura(signatura);
								if(nova!=null){
									recordChanged = true;
									sfa.setContent(nova);									
									totalSigReplaced++;
								}
								
							}							
							
						}
					}
					
				}
				storage.update(conn, rec);			
				if(recordChanged)
					totalRecordsChanged++;
			}
			
			if (count % 1000 == 0){
			        System.out.println("Total records processed: " + count+", total sig replaced: "+totalSigReplaced+", total records changed: "+totalRecordsChanged);
			        
			}
		}
		
		
		System.out.println("Total records processed: " + count+", total sig replaced: "+totalSigReplaced+", total records changed: "+totalRecordsChanged);
		 rset.close();
		 stmt.close();
		 conn.close();
			
	}
	
	private static String getNewSignatura(String signatura){
		
		if(signatura!=null){
			signatura = signatura.replace('(', '<');
			signatura = signatura.replace(')', '>');
			for(String stara:preslikavanje.keySet()){
				if(signatura.trim().startsWith(stara)){					
					String newSig = signatura.replaceFirst(stara, preslikavanje.get(stara)).replace('<', '(').replace('>', ')');	
					return newSig;
						
				}
					
			}
		}
		return null;
	}
	
	static{
		preslikavanje = new HashMap<String, String>();
		preslikavanje.put("820<73>","821.111<73>");
		preslikavanje.put("820<71>","821.111<71>");		
		preslikavanje.put("820<94>","821.111<94>");
		preslikavanje.put("820<680>","821.111<680>");
		preslikavanje.put("830","821.112.2");
		preslikavanje.put("830<436>","821.112.2<436>");
		preslikavanje.put("839.31","821.112.5");
		preslikavanje.put("839.6","821.113.5");
		preslikavanje.put("839.7","821.113.6");
		preslikavanje.put("871","821.124");
		preslikavanje.put("850","821.131.1");
		preslikavanje.put("840","821.133.1");
		preslikavanje.put("860","821.134.2");
		preslikavanje.put("859","821.135.1");
		preslikavanje.put("860<72>","821.134.2<72>");
		preslikavanje.put("860<82>","821.134.2<82>");
		preslikavanje.put("860<83>","821.134.2<83>");
		preslikavanje.put("860<84>","821.134.2<84>");
		preslikavanje.put("860<85>","821.134.2<85>");
		preslikavanje.put("860<86>","821.134.2<86>");
		preslikavanje.put("860<87>","821.134.2<87>");
		preslikavanje.put("869","821.134.3");
		preslikavanje.put("869<83>","821.134.3<81>");
		preslikavanje.put("882","821.161.1");
		preslikavanje.put("884","821.162.1");
		preslikavanje.put("885","821.162.3");
		preslikavanje.put("886.7","821.163.2");
		preslikavanje.put("886.1","821.163.41");
		preslikavanje.put("886.2","821.163.42");
		preslikavanje.put("886.3","821.163.61");
		preslikavanje.put("891.1","821.214.21");
		preslikavanje.put("892.4","821.411.16");
		preslikavanje.put("894.511","821.511.141");
		preslikavanje.put("894.35","821.512.161");
		preslikavanje.put("894.56","821.521");
		preslikavanje.put("894.57","821.531");
		preslikavanje.put("894.51","821.581");


	}

}
