package com.gint.app.bisis4.prepis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Statement;


import com.gint.app.bisis4.indexer.IndexerApp;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.utils.Signature;

public class JedinstvenostInventarnogBroja {
	
	private static File errorInvFile = new File("dupliInventarniErr.txt");
	private static FileOutputStream stream;
	private static OutputStreamWriter writer;		
	
	
	
	static{
		try {
			System.out.println("Creating additional table INVENTARNI_BROJEVI");
			Statement stmt = IndexerApp.conn.createStatement();
			stmt.execute("CREATE TABLE INVENTARNI_BROJEVI(" +
					"inv_br varchar(25) not null) ");			
					//"primary key (inv_br))");
			IndexerApp.conn.commit();		
			
			stmt = IndexerApp.conn.createStatement();
			stmt.execute("create unique index Unique_inv_broj on INVENTARNI_BROJEVI ("+
						"	inv_br);");
			IndexerApp.conn.commit();		
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
				try {
					stream = new FileOutputStream(errorInvFile);
					writer = new OutputStreamWriter(stream,"UTF-8");					
				} catch (FileNotFoundException e) {					
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {					
					e.printStackTrace();
				}			
	}
	
	public static void dodajInventarniBroj(Primerak p) throws IOException{		
		try {
			Statement stmt = IndexerApp.conn.createStatement();
			stmt.execute("INSERT INTO INVENTARNI_BROJEVI(inv_br) VALUES ("+p.getInvBroj()+");");
			IndexerApp.conn.commit();
		} catch (SQLException e) {			
			writer.append(p.getInvBroj()+" --> Signatura: "+Signature.format(p));
			writer.append("\n");
			writer.flush();
			//e.printStackTrace();
		}
	}
	
	public static void dodajInventarniBroj(Godina g) throws IOException{		
		try {
			Statement stmt = IndexerApp.conn.createStatement();
			stmt.execute("INSERT INTO INVENTARNI_BROJEVI(inv_br) VALUES ("+g.getInvBroj()+");");
			IndexerApp.conn.commit();
		} catch (SQLException e) {			
			writer.append(g.getInvBroj()+" --> Signatura: "+Signature.format(g));			
				writer.append("\n");
			writer.flush();
			//e.printStackTrace();
		}
	}
	
	
	public static void close(){
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
