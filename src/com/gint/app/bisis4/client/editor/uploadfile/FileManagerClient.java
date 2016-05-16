package com.gint.app.bisis4.client.editor.uploadfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.apache.activemq.broker.Connector;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.textsrv.DocFile;
import com.sun.net.httpserver.HttpsConfigurator;

public class FileManagerClient {	
  
  public static boolean upload(String url, int rn, File file, String uploader) {
    if (!file.isFile()) {
      log.warn("File " + file.getAbsolutePath() + " does not exist.");
      return false;
    }
    
    HttpClient client = new HttpClient();
    PostMethod post = new PostMethod(url);
    FilePart filePart = null;
    try {
      filePart = new FilePart("file", file);
    } catch (FileNotFoundException ex) {
      log.fatal(ex);
      return false;
    }
    Part[] parts = {
      new StringPart("operation", "upload"),
      new StringPart("rn", Integer.toString(rn)),
      new StringPart("filename", file.getName()),
      new StringPart("uploader", uploader),
      filePart
    };
    post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
    try {
      int status = client.executeMethod(post);
      System.out.println("status = "+status);
      if (status == HttpStatus.SC_OK)
        log.info("File " + file.getAbsolutePath() + " successfully uploaded.");
      else
        log.fatal("File upload for " + file.getAbsolutePath() + " failed: " + 
            HttpStatus.getStatusText(status));      
    } catch (Exception ex) {
      log.fatal(ex);
      return false;
    }
    post.releaseConnection();
    return true;
  }
  
  public static File download(String url,DocFile docFile) {
  	
  	String urlWithParameters = url+"?operation=download&rn="+docFile.getRn()+
  	"&filename="+docFile.getFilename().replace(" ", "+")+"&file_id="+docFile.getId()+
			"&content_type="+docFile.getContentType()+"&uploader="+docFile.getUploader();  	
  	HttpClient client = new HttpClient();
  	GetMethod get = new GetMethod(urlWithParameters);  	
  	try {
  		int status = client.executeMethod(get);
  		File folder = new File("bisis-downloads");
  		folder.mkdir();
  		if(!folder.exists())
  			folder.createNewFile();  		
				File file = new File(folder,docFile.getFilename());				
				FileOutputStream outputStream = new FileOutputStream(file);
  	 InputStream stream = get.getResponseBodyAsStream();
  	 int length = 0;
  	 byte[] buf = new byte[100];
  		while ((stream!= null) && ((length = stream.read(buf)) != -1))
	   {
  			outputStream.write(buf,0,length);
	   }
  		stream.close();
  		outputStream.close();
  		return (File)file;				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
  	/*
  	
  	conf.setHost(url);  	
  	try {
  	HttpConnection connection = new HttpConnection(conf);  	
  	RandomAccessFile file = new RandomAccessFile("RacuniBrzoStanje.pdf","rw");  	
				connection.open();
				String line = "";
				while(!(line=connection.readLine("")).equals(""))
					file.writeChars(line);
				file.close();
			} catch (Exception e) {				
				e.printStackTrace();
			}
 
  	
  	
  	
  	
  	/*
  	HttpClient client = new HttpClient();
  	PostMethod post = new PostMethod(url);
  	post.addParameter("operation", "download");
  	post.addParameter("rn", String.valueOf(docFile.getRn()));
  	post.addParameter("filename",docFile.getFilename());
  	post.addParameter("file_id", String.valueOf(docFile.getId()));
  	post.addParameter("content_type", docFile.getContentType());
  	post.addParameter("uploader",docFile.getUploader());  	
  	try {
    int status = client.executeMethod(post);    
    System.out.println("status = "+status);
    if (status == HttpStatus.SC_OK)
      log.info("File " + docFile.getFilename() + " successfully downloaded.");
    else
      log.fatal("File download for " + docFile.getFilename() + " failed: " + 
          HttpStatus.getStatusText(status));      
  } catch (Exception ex) {
    log.fatal(ex);
    return false;
  }
   post.releaseConnection();  	
   */
  	//return true;
  }
  
  public static boolean delete(String url, DocFile docFile) {
    return delete(url, docFile.getId());
  }
  
  public static boolean deleteAllForRecord(String url, int rn) {
  	HttpClient client = new HttpClient();
  	PostMethod post = new PostMethod(url); 
  	post.addParameter("operation", "deleteAllForRecord");
  	post.addParameter("rn", String.valueOf(rn));
  	try{
	  	int status = client.executeMethod(post);
	  	if (status == HttpStatus.SC_OK){	  	
	    log.info("Files for record rn=" + rn + " successfully deleted.");
	    return true;
	  	}else{
	    log.fatal("Delete files for record rn=" + rn + " failed: " + 
	        HttpStatus.getStatusText(status));
	    return false;
	  	}
  	}catch(Exception ex){
  		log.fatal(ex);
  		return false;
  	}	
   
  }
  
 
  
  public static boolean delete(String url, int id) {
  	HttpClient client = new HttpClient();
  	PostMethod post = new PostMethod(url); 
  	post.addParameter("operation", "delete");
  	post.addParameter("id", String.valueOf(id));
  	try{
	  	int status = client.executeMethod(post);
	  	if (status == HttpStatus.SC_OK){	  	
	    log.info("File with id=" + id + " successfully deleted.");
	    return true;
	  	}else{
	    log.fatal("Delete file id=" + id + " failed: " + 
	        HttpStatus.getStatusText(status));
	    return false;
	  	}
  	}catch(Exception ex){
  		log.fatal(ex);
  		return false;
  	}	
   
  }
  
  private static Log log = LogFactory.getLog(FileManagerClient.class);
  
  public static void main(String[] args) {
    upload("http://localhost:8080/bisis/FileMgr", 2, 
    	 new File("E:/BISIS/uputstvo-editor.pdf"), "bojana@pmf");
      //  new File(System.getProperty("user.home") + "/.bashrc"), "branko@ftn");
  }

}
