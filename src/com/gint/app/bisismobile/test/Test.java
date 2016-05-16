package com.gint.app.bisismobile.test;

import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.serializers.LooseXMLSerializer;
import com.gint.app.bisis4.records.serializers.PrimerakSerializer;



public class Test {

	/**
	 * @param args
	 */
	static String url= "http://nastava.is.pmf.uns.ac.rs:8080/bisismobile/MobileQuery";
	
	static String record = "<record recordID=\"26852\" pubType=\"1\">  <field name=\"996\" ind1=\"0\" ind2=\" \">" +
			"<subfield name=\"d\"><subsubfield name=\"l\">Mt</subsubfield><subsubfield name=\"n\">235</subsubfield></subfield>" +
			"<subfield name=\"f\">00150000235</subfield>" +
			 "<subfield name=\"o\">20050817</subfield>" +
			   " <subfield name=\"r\">Tempus</subfield>" +
			    "<subfield name=\"s\">b</subfield>" +
			"<subfield name=\"v\">c</subfield>" +
			"<subfield name=\"w\">00</subfield>" +
			"<subfiled name=\"q\">+</subfieled>" +
			"<subfield name=\"9\">1</subfield>" +
			
			"</field></record>";
	
	
	public static void main(String[] args) {
		
		//login("test", "test");
		query("00150000235");
		//query2("00150000235");
		//save(record);
		//coder();
		
	}


	public static void login(String user, String pass) {
	  	HttpClient client = new HttpClient();
	  	PostMethod post = new PostMethod(url); 
	  	post.addParameter("action", "login");
	  	post.addParameter("user", user);
	  	post.addParameter("pass", pass);
	  	try{
		  	int status = client.executeMethod(post);
		  	if (status == HttpStatus.SC_OK){	  	
		  		String response = post.getResponseBodyAsString();
		  		System.out.println(response);
		  	}else{
		  		System.out.println(status);
		  	}
	  	}catch(Exception ex){
	  		ex.printStackTrace();
	  	}
	  }
	
	
	public static void query(String bookid) {
	  	HttpClient client = new HttpClient();
	  	PostMethod post = new PostMethod(url); 
	  	post.addParameter("action", "query");
	  	post.addParameter("bookid", bookid);
	  	try{
		  	int status = client.executeMethod(post);
		  	if (status == HttpStatus.SC_OK){	  	
		  		String response = post.getResponseBodyAsString();
		  		System.out.println(response);
		  	}else{
		  		System.out.println(status);
		  	}
	  	}catch(Exception ex){
	  		ex.printStackTrace();
	  	}
	  }
	
	
	public static void save(String record) {
		HttpClient client = new HttpClient();
	  	PostMethod post = new PostMethod(url); 
	  	post.addParameter("action", "save");
	  	post.addParameter("record", record);
	  	try{
		  	int status = client.executeMethod(post);
		  	if (status == HttpStatus.SC_OK){	  	
		  		String response = post.getResponseBodyAsString();
		  		System.out.println(response);
		  	}else{
		  		System.out.println(status);
		  	}
	  	}catch(Exception ex){
	  		ex.printStackTrace();
	  	}
	  }
	
	public static void coder() {
	  	HttpClient client = new HttpClient();
	  	PostMethod post = new PostMethod(url); 
	  	post.addParameter("action", "coder");
	  	try{
		  	int status = client.executeMethod(post);
		  	if (status == HttpStatus.SC_OK){	  	
		  		String response = post.getResponseBodyAsString();
		  		System.out.println(response);
		  	}else{
		  		System.out.println(status);
		  	}
	  	}catch(Exception ex){
	  		ex.printStackTrace();
	  	}
	  }
	
	public static void query2(String bookid) {
	  	HttpClient client = new HttpClient();
	  	PostMethod post = new PostMethod(url); 
	  	post.addParameter("action", "query");
	  	post.addParameter("bookid", bookid);
	  	try{
		  	int status = client.executeMethod(post);
		  	if (status == HttpStatus.SC_OK){	  	
		  		String response = post.getResponseBodyAsString();
		  		System.out.println(response);
		  		
		  		Record recordsent = LooseXMLSerializer.fromLooseXML(response);
		  		System.out.println(recordsent.getPrimerci().size());
		    	Field f = recordsent.getField("996");
		    	Primerak p = PrimerakSerializer.getPrimerak(f);
		    	p.setStatus("r");
		    	p.setDatumStatusa(new Date());
		    	System.out.println(p);
		    	Field f1 = PrimerakSerializer.getField(p);
		    	recordsent.add(f1);
		    	String xmlrecord = LooseXMLSerializer.toLooseXML(recordsent);
		    	System.out.println("new");
		    	System.out.println(xmlrecord);
		    	
		  	}else{
		  		System.out.println(status);
		  	}
	  	}catch(Exception ex){
	  		ex.printStackTrace();
	  	}
	  }

}
