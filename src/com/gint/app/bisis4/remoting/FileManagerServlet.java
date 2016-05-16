package com.gint.app.bisis4.remoting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;

import com.gint.app.bisis4.textsrv.DocFile;
import com.gint.app.bisis4.textsrv.FileStorage;
import com.gint.app.bisis4.textsrv.Indexer;
import com.gint.app.bisis4.utils.MimeTypes;


public class FileManagerServlet extends HttpServlet {
  
  public void init(ServletConfig cfg) {  	
    try {
      String sSizeThreshold = cfg.getInitParameter("sizeThreshold");
      if (sSizeThreshold != null)
        sizeThreshold = Integer.parseInt(sSizeThreshold);
      else
        log.warn("Init parameter sizeThreshold not defined, defaults to 10 MB");
    } catch (NumberFormatException ex) {
      log.warn("Init parameter sizeThreshold invalid, defaults to 10 MB");
    }
    try {
      String sMaxSize = cfg.getInitParameter("maxSize");
      if (sMaxSize != null)
        maxSize = Integer.parseInt(sMaxSize);
      else
        log.warn("Init parameter maxSize not defined, defaults to 200 MB");
    } catch (NumberFormatException ex) {
      log.warn("Init parameter maxSize invalid, defaults to 200 MB");
    }
    String sTempDir = cfg.getInitParameter("tempDir");
    if (sTempDir != null) {
      tempDir = new File(sTempDir);
      File def = getDefaultTempDir();
      if (!tempDir.isDirectory()) {
        log.fatal("Init parameter tempDir is not a directory: " + 
            tempDir.getAbsolutePath() + ", defaulting to " + 
            def.getAbsolutePath());
        tempDir = def;
      }
      if (!tempDir.canWrite()) {
        log.fatal("Cannot write to tempDir: " + tempDir.getAbsolutePath() + 
            ", changing to " + def.getAbsolutePath());
        tempDir = getDefaultTempDir();
      }
    } else {
      tempDir = getDefaultTempDir();
      log.warn("Init parameter tempDir not defined, defaults to " + 
          tempDir.getAbsolutePath());
    }
    
    try {
      String sDataSource = cfg.getInitParameter("dataSource");
      if (sDataSource == null) {
        sDataSource = "jdbc/Bisis";
        log.warn("Init parameter sDataSource missing, defaulting to " 
            + sDataSource);
      }
      Context ctx = new InitialContext();
      dataSource = (DataSource)ctx.lookup("java:comp/env/" + sDataSource);
    } catch (NamingException ex) {
      log.fatal(ex);
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res) {
  	if(req.getParameter("operation").equals("download"))
  		handleDownload(req,res); 
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse res) {
    boolean isMultipart = ServletFileUpload.isMultipartContent(req);    
    if (!isMultipart && req.getParameter("operation").equals("delete")){      
      handleDelete(req, res);
    }
    if (!isMultipart && req.getParameter("operation").equals("deleteAllForRecord")){      
     handleDeleteAllForRecord(req, res);
   }
    else{     
      try {
        handleUpload(req, res);
        writeOK(res);
      } catch (FileUploadException ex) {      	 
        log.fatal(ex);
        writeError(res, ex);
      }
    }
  }
  
  private void handleDownload(HttpServletRequest req, HttpServletResponse res) {  	
			int rn = Integer.parseInt(req.getParameter("rn"));
			String fileName = req.getParameter("filename");
			int file_id = Integer.parseInt(req.getParameter("file_id"));
			String contentType = req.getParameter("content_type");
			String uploader = req.getParameter("uploader");
			DocFile docFile = new DocFile(file_id,rn,fileName,contentType,uploader);			
			InputStream stream = FileStorage.load(docFile);
			res.setContentType(docFile.getContentType());
			ServletOutputStream out;
			try {
				out = res.getOutputStream();
			 byte[] bbuf = new byte[100];
			 int length = 0;
				while ((stream != null) && ((length = stream.read(bbuf)) != -1)){
					out.write(bbuf,0,length);
				}
				out.close();
			} catch (IOException e) {
				log.fatal(e);
				writeError(res,e);
			}			
		}			
	

		private void handleDelete(HttpServletRequest req, HttpServletResponse res) {			
			int id = Integer.parseInt(req.getParameter("id"));
			Connection conn;
			try {
				conn = dataSource.getConnection();
				DocFile docFile = DocFile.load(conn, id);
				if(docFile.delete(conn)){
					boolean successful = FileStorage.delete(docFile);
					conn.commit();
					if(successful){
						deleteFromIndex(docFile.getRn(),docFile.getContentType());
						writeOK(res);
					}
				}
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
		
		private void handleDeleteAllForRecord(HttpServletRequest req, HttpServletResponse res) {			
				int rn = Integer.parseInt(req.getParameter("rn"));
				Connection conn;
				boolean successful = true;
				try {
					conn = dataSource.getConnection();
					List<DocFile> docFiles = DocFile.find(conn, rn);
					if(docFiles!=null && docFiles.size()>1){
					for(DocFile df:docFiles){					
						if(df.delete(conn)){
							successful = FileStorage.delete(df) && successful;
							conn.commit();
							deleteFromIndex(df.getRn(),df.getContentType());
							if(successful){
								writeOK(res);
							}
						}
					}
					FileStorage.deleteFolderForDoc(docFiles.get(0));
					}
					
				} catch (SQLException e) {			
					e.printStackTrace();
				}
			
  }
  
  @SuppressWarnings("unchecked")
  private void handleUpload(HttpServletRequest req, HttpServletResponse res) 
      throws FileUploadException {  	
  	
  	 DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(sizeThreshold);
    factory.setRepository(tempDir);
    
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setSizeMax(maxSize);
    
    Iterator items = upload.parseRequest(req).iterator();
    DocFile docFile = new DocFile();
    FileItem fileItem = null;
    
    while (items.hasNext()) {    	
      FileItem item = (FileItem)items.next();     
      if (item.isFormField())
        processTextField(item, docFile);
      else
        fileItem = item;
    }
    if (docFile.getRn() == 0)
      throw new FileUploadException("RN not defined for: " + docFile);
    if (docFile.getFilename() == null)
      throw new FileUploadException("Filename not defined for: " + docFile);
    if (docFile.getUploader() == null)
      throw new FileUploadException("Uploader not defined for: " + docFile);
    if (docFile.getContentType() == null)
      docFile.setContentType(
          MimeTypes.guessMimeTypeFromFilename(docFile.getFilename()));
    if (docFile.getContentType() == null)
      throw new FileUploadException("Content type unknown for: " + docFile);    
    try {
    		
      Connection conn = dataSource.getConnection();
      List<DocFile> docFiles = DocFile.find(conn, docFile.getRn());
      boolean overwrite = false;
      if (docFiles.size() > 0) {
      	for (DocFile df:docFiles) {
      		if (df.getFilename().equalsIgnoreCase(docFile.getFilename())) {
      			// overwrite
      			df.setUploader(docFile.getUploader());
      			df.update(conn);
      			overwrite = true;
      			break;
      		}
      	}
      }
      if (!overwrite) { 
    	//dodati u indeks da zapis sa RN imam elektronski resurs odredjenog tipa:.doc, .pdf...
    	boolean ok=addToIndex(docFile.getRn(),docFile.getContentType());  
    	if (!ok){
    		log.fatal("Informacija da zapis "+docFile.getRn()+" ima elektronske resurse nije indeksirana");
    	    throw new FileUploadException("Upload error:Informacija da zapis "+docFile.getRn()+" ima elektronske resurse nije indeksirana");
    	}
      	docFile.store(conn); // sada ce i atribut id biti upisan u objekat
      }
      FileStorage.store(docFile, fileItem);
      conn.commit();
      conn.close();
    } catch (Exception ex) {    	 
      log.fatal(ex);
      throw new FileUploadException("Upload error", ex);
    }
  }
  
  private boolean addToIndex(int rn,String docType){
	  String luceneIndex = InitializerServlet.getIndexPath();
	  Indexer indexer = new Indexer(luceneIndex);
	 
	  return indexer.addField(Integer.toString(rn), "XY", docType.toLowerCase());
  }
  
  private void deleteFromIndex(int rn,String docType){
	  String luceneIndex = InitializerServlet.getIndexPath();
	  Indexer indexer = new Indexer(luceneIndex);
	  indexer.deleteField(Integer.toString(rn), "XY", docType.toLowerCase());
  }
  
  private void processTextField(FileItem item, DocFile docFile) {
    String name = item.getFieldName();
    String value = item.getString();    
    if ("rn".equals(name))
      docFile.setRn(Integer.parseInt(value));
    else if ("filename".equals(name))
      docFile.setFilename(value);
    else if ("uploader".equals(name))
      docFile.setUploader(value);
    else if ("contentType".equals(name))
      docFile.setContentType(value);
    
    
  }
  
  private File getDefaultTempDir() {
    String tempdir = System.getProperty("java.io.tmpdir");
    return new File(tempdir);
  }
  
  private void writeOK(HttpServletResponse res) {
    res.setContentType("text/plain");
    res.setStatus(200);
    try {
      PrintWriter out = res.getWriter();
      out.println("OK");
    } catch (IOException ex) {
      log.fatal(ex);
    }
  }
  
  private void writeError(HttpServletResponse res, Exception ex) {
    res.setContentType("text/plain");
    res.setStatus(500);
    try {
      PrintWriter out = res.getWriter();
      out.println("ERROR");
      out.println(ex);      
    } catch (IOException ex1) {
    	 ex1.printStackTrace();
      log.fatal(ex1);
    }
  }
  
  
  public void destroy() {
  }
  
  /** max velicina fajla pre nego sto se podaci direktno pisu na disk, default 10 MB */
  private int sizeThreshold = 10000000;
  /** temp direktorijum za upload fajlova, default /tmp */
  private File tempDir = null;
  /** max velicina uploadovanog fajla, default 200 MB */
  private int maxSize = 200000000;
  /** DataSource za pristup bazi podataka */
  private DataSource dataSource;
  
  private static Log log = LogFactory.getLog(FileManagerServlet.class);
}
