package com.gint.app.bisis4.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.gint.app.bisis4.reports.XMLTransformer;
import com.gint.app.bisis4.reports.ReportCollection;
import com.gint.app.bisis4.reports.ReportRunner;


public class ReportsGenerator {


public static void deleteReports(String path){
	  File dir = new File(path);
	  if(!dir.isDirectory())
		  return;
	  File[] files = dir.listFiles ( );
      for (int i=0; i<files.length;i++){
    	  files[i].delete();
    	  
      }
	  
  }
  public static void init(List<Report> reports){
	  for(Report r : reports){
		  if(r.getReportSettings().getParam("sort")!=null) { //samo ako je definisano da se sortira
		  sortXSL.put(r.getReportSettings().getParam("file"), r.getReportSettings().getParam("sort"));
		  }
		  
	  }
	  
  }
  public static void sortReports(String path){
	  try {
	  File dir = new File(path);
      if(dir.isDirectory()){
    	  DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    	  Document doc;
  	    File[] files = dir.listFiles ( );
        for (int i=0; i<files.length;i++){
        	doc=null;
        	 String sort=null;
        	int crta=files[i].getName().indexOf("-");
        	int zagrada=files[i].getName().indexOf("(");
        	int tacka=files[i].getName().indexOf(".");
        	if (zagrada!=-1){
        	  sort=sortXSL.get(files[i].getName().substring(0,zagrada)); //ime fajla ali bez datuma, samo do (
        	}else if(crta!=-1){
              sort=sortXSL.get(files[i].getName().substring(0,crta)); //ime fajla ali bez datuma, samo do -
        	}else if(tacka!=-1){
        	  sort=sortXSL.get(files[i].getName().substring(0,tacka));
        	}
        	  if(sort==null)
        		  continue;
        	  if(files[i].getName().endsWith("x.xml")) //ne sortira one za koji nije utvrđen period
        		  continue;
      
      		InputStream sortStream =  ReportsGenerator.class.getResource(sort).openStream();    		    		
      	    doc = builder.parse(files[i]);
      	    Node xmlsort = XMLTransformer.transform(doc, sortStream);  	    
     	    XMLSerializer serializer = new XMLSerializer();
       	    serializer.setOutputFormat(new OutputFormat(xmlsort.getOwnerDocument(),"UTF-8",true));
      	    serializer.setOutputByteStream(new FileOutputStream(files[i]));
      	    serializer.serialize(xmlsort.getOwnerDocument());
      	    sortStream.close();
        }
      }
	  } catch (Exception e) {
		   log.error(e);
		}
  }
  public static void main(String[] args) {
    ReportCollection collection = new ReportCollection("/reports.ini");
    deleteReports(collection.getDestination()); //obrise postojece izvestaje od prethodnog dana
    init(collection.getReports());
    ReportRunner runner = new ReportRunner(collection);
    runner.run();  
    sortReports(collection.getDestination()); //sortira izvestaje 

   }
  
  private static Log log = LogFactory.getLog(ReportsGenerator.class);
  private static HashMap<String, String> sortXSL=new HashMap();
}
