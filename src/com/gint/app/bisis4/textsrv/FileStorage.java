package com.gint.app.bisis4.textsrv;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileStorage {

 //Bojana
	public static void store(DocFile doc, FileItem fileItem) {
  String fullPath = getFullPath(doc);
  File file = new File(fullPath);
  if (file.isFile()) {
    log.warn("File " + fullPath + " exists: will be overwritten!");
  }
  File dir = file.getParentFile();
  if (!dir.exists()) {
    dir.mkdirs();
    log.info("Creating directory " + dir.getAbsolutePath());
  }
  try {
  	 fileItem.write(file);
  } catch (Exception ex) {
    log.fatal(ex);
  }
}
	
	
	/*
	 // Branko
	public static void store(DocFile doc, InputStream input) {
    String fullPath = getFullPath(doc);
    File file = new File(fullPath);
    if (file.isFile()) {
      log.warn("File " + fullPath + " exists: will be overwritten!");
    }
    File dir = file.getParentFile();
    if (!dir.exists()) {
      dir.mkdirs();
      log.info("Creating directory " + dir.getAbsolutePath());
    }
    try {
      BufferedOutputStream out = new BufferedOutputStream(
          new FileOutputStream(file)); 
      IOUtils.copy(input, out);
      out.close();
    } catch (IOException ex) {
      log.fatal(ex);
    }
  }
  */
  public static InputStream load(DocFile doc) {
    String fullPath = getFullPath(doc);
    File file = new File(fullPath);
    if (!file.isFile()) {
      log.warn("File " + fullPath + " does not exist!");
      return null;
    }
    try {
      BufferedInputStream in = new BufferedInputStream(
          new FileInputStream(file));
      return in;
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  
  public static boolean delete(DocFile doc){
  	String fullPath = getFullPath(doc);
   File file = new File(fullPath);
   if (!file.isFile()) {
    log.warn("File " + fullPath + " does not exist!");
    return false;
   }else{
   	file.delete();   	
   	return true;
  }
  }
   
  public static boolean deleteFolderForDoc(DocFile doc){
  	String dirPath = storageRoot + "/" + "set" + getFileset(doc) + "/bisis" +doc.getRn();
  	File f = new File(dirPath);
  	return f.delete();
  }
   
  	
 
  
  public static String getFullPath(DocFile doc) {
   return storageRoot + "/" + "set" + getFileset(doc) + "/bisis" + 
        doc.getRn() + "/" + doc.getFilename();
  	
  }
  
  public static int getFileset(DocFile doc) {
    return (doc.getId() - 1) / documentsPerSet + 1;
  }

  private static String storageRoot;
  private static String indexDir;
  private static int documentsPerSet;
  private static Log log = LogFactory.getLog(FileStorage.class);

  static {
    storageRoot = "/bisis-file-storage";
    indexDir = "/bisis-file-index";
    documentsPerSet = 1000;
    try {
     
    	ResourceBundle rb = PropertyResourceBundle.getBundle(FileStorage.class.getName());
      String test = rb.getString("STORAGE_ROOT");
      if (test != null)
        storageRoot = test;
      test = rb.getString("INDEX_DIR");
      if (test != null)
        indexDir = test;
      test = rb.getString("DOCUMENTS_PER_SET");
      if (test != null)
        documentsPerSet = Integer.parseInt(test);            	
      createDir(storageRoot);
      createDir(indexDir);
    } catch (Exception ex) {
      log.fatal(ex);
    }
  }
    
  private static void createDir(String dir) throws Exception {
    File d = new File(dir);
    if (!d.exists()) {
      d.mkdirs();
    } else {
      if (d.isFile())
        log.fatal(dir + " is a file, not a directory!");
    }
  }

}
