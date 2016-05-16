package com.gint.app.bisis4.prepis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;

public class PrepisReport {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static File errorRecFile = new File("recordsErr.txt");
	//private static File unimarcErr = new File("unimarcErr.txt");
	private static FileOutputStream stream;
	private static OutputStreamWriter writer;		
	
	private static FileOutputStream streamUnimarc;
	private static OutputStreamWriter writerUnimarc;
	
	private static List output = new ArrayList(); 
	
	private static String localeStr = "";
	
	static {
		
		
	}
	
	public static void setLocaleStr(String str){
		localeStr = str;
		try {
			errorRecFile = new File("recordsErr"+localeStr.toUpperCase()+sdf.format(new Date())+".txt");
			stream = new FileOutputStream(errorRecFile);			
			writer = new OutputStreamWriter(stream,"UTF-8");	
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	
	public static void unesiZapis(Record rec){	
			//output.add(rec);		
			try {
				writer.append(RecordFactory.toFullFormat(0,rec)+"\n");	
				writer.flush();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
	
	
	public static void zatvoriWriter(){
		try {
			writer.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void upisi(String str){
		try {
			writer.append(str+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void stampaj(){	
		zatvoriWriter();
	}

}
