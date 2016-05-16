package com.gint.app.bisis4.prepis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.records.Subsubfield;

public class PrepisVladimirovci {
	
	public static void main(String[] args) throws IOException{
	 String recordPath = "/com/gint/app/bisis4/prepis/recordsXMLErr.xml";
	//	String recordPath = "/com/gint/app/bisis4/prepis/ftn-winisis.xml";
	 File input = new File(recordPath);
	 InputStream is = PrepisVladimirovci.class.getResourceAsStream(recordPath);
	 InputStreamReader reader = new InputStreamReader(is,"UTF-8");
	 BufferedReader in = new BufferedReader(reader);
	 
	 File errorRecFile = new File("recordsXMLErr.xml");
		FileOutputStream stream = new FileOutputStream(errorRecFile);			
		OutputStreamWriter writerErr = new OutputStreamWriter(stream,"UTF-8");	
	 
	 
	 File unimarcFile = new File("zapisi-vladimirovciDodatak.dat");
	 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
   	new FileOutputStream(unimarcFile), "ISO-8859-1"));
	 
	 //BufferedReader in = new BufferedReader(
   // new FileReader(recordPath));
		 
	/*	FileOutputStream outStream;
	 OutputStreamWriter writer;
	 outStream = new FileOutputStream(unimarcFile);
  writer = new OutputStreamWriter(outStream);*/
  
  int totalCount = 0;
  int badRecordsCount = 0;
  String line="";
  StringBuffer recordXML = new StringBuffer(); 
  while((line=in.readLine())!=null){
  	line = line.trim();
  	if ("".equals(line))
				continue; 
  //	System.out.println("line="+line);
  	
  	if(line.equals("<record>")){  		
  		recordXML = new StringBuffer();
  		recordXML.append(line+"\n");  		
  	}else if(line.equals("</record>")){
  		totalCount++;
  		if (totalCount % 1000 == 0)
 				System.out.print(" " + totalCount);
  		recordXML.append(line); 
  		try{  			
  		Record rec = RecordFactory.fromLooseXML1(recordXML.toString());
  		rec = srediSignaturu(rec);
  		rec = srediPovez(rec);
  		String str = RecordFactory.toUNIMARC1(0, rec);
  		out.write(str, 0, str.length());
  		out.append("\n");
    
  	/*	writer.append(RecordFactory.toBISIS35(0, rec));
 			writer.append("\n");
 			writer.flush();*/
  		}catch(Exception e){
  			badRecordsCount++;
  			writerErr.append(recordXML);  			
  			System.out.println(recordXML.toString());
  		}
  
  	}else{
  		recordXML.append(line+"\n");
  		
  	}		
			
  }	
		System.out.println("Converted records number: "+totalCount);
		System.out.println("Bad records number: "+badRecordsCount);
		out.close();
		writerErr.close();
	}
	
	public static Record prepisiZapis(Record rec){
		rec.setCreationDate(new Date());
		rec.setCreator(new Author("unknown","unknown"));
		rec.setPubType(1);
		for(Primerak p:rec.getPrimerci()){
			if(p.getNacinNabavke()!=null)
				if(p.getNacinNabavke().equals("\u043A"))
						p.setNacinNabavke("k");
			if(p.getInvBroj()!=null){
				p.setInvBroj("00000000000".substring(0,11-p.getInvBroj().length())+p.getInvBroj());
			}
		}
		return rec;
	}
	
	private static Record srediPovez(Record rec){
		for(Field f996:rec.getFields("996")){
			if(f996.getSubfield('6')!=null){
				Subfield sf = new Subfield('s');
				String povez = f996.getSubfieldContent('6');
				if(povez!=null){
					if(povez.equals("tp") || povez.equals("t."));
						povez = "t";
					if(povez.equals("mp"))
						povez = "m";
					if(povez.equals("\u0442"))
						povez = "t";
					if(povez.equals("\u043C"))
						povez = "m";
				
				sf.setContent(povez);
				f996.add(sf);
				}
			}				
		}
		return rec;
	}
	
	public static Record srediSignaturu(Record rec){
		//System.out.println(RecordFactory.toFullFormat(1, rec));
		for(Field f996:rec.getFields("996")){
			//System.out.println("Ima polja 996");
			if(f996.getSubfield('d')!=null){				
				Subfield sfd = f996.getSubfield('d');
				String sig = sfd.getContent();
   // System.out.println("Signatura: "+sig);
				f996.remove(sfd);
				
				Subsubfield ssfu = new Subsubfield('u');
				ssfu.setContent(sig);				
				Subfield sf = new Subfield('d');
				sf.add(ssfu);
				f996.add(sf);
			}	
		}
	//	System.out.println(RecordFactory.toFullFormat(1, rec));
		return rec;
		
	}
	

}
