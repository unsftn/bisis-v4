package com.gint.app.bisis4.utils;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MimeTypes {
  
  public static String guessMimeTypeFromFilename(String filename) {
    if (filename == null) {
      log.warn("Guessing MIME type: filename=null");
      return null;
    }
    int dotPos = filename.lastIndexOf('.');
    if (dotPos == -1 || dotPos == filename.length() - 1) {
      log.warn("Filename " + filename + " has no extension.");
      return null;
    }
    String ext = filename.substring(dotPos + 1);
    return guessMimeTypeFromExtension(ext);
  }

  public static String guessMimeTypeFromExtension(String ext) {
    String retVal = (String)types.get(ext.toLowerCase());
    if (retVal == null)
      log.warn("Extension " + ext + " has no associated MIME type.");
    return retVal;
  }
  
  private static Properties types = new Properties();
  private static Log log = LogFactory.getLog(MimeTypes.class);
  
  static {
    types.put("pdf", "application/pdf");
    types.put("zip", "application/zip");
    types.put("rar", "application/x-rar");
    types.put("doc", "application/msword");
    types.put("ppt", "application/vnd.ms-powerpoint");
    types.put("txt", "text/plain");
    types.put("html", "text/html");
    types.put("htm", "text/html");
  }
}
