package com.gint.app.bisis4.textsrv.fulltext;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Produces DocBuilder objects based on the given file extension.
 */
public class DocBuilderFactory {

  /**
   * Returns a single instance of a DocBuilder suitable for the given MIME type.
   * 
   * @param extension The file extension.
   * @return A suitable DocBuilder object
   */
  public static DocBuilder getDocBuilder(String mimeType) {
    DocBuilder docBuilder = (DocBuilder)map.get(mimeType);
    if (docBuilder == null)
      log.warn("No suitable DocBuilder for MIME type: " + mimeType);
    return docBuilder;
  }
  
  private static Properties map = new Properties();
  private static Log log = LogFactory.getLog(DocBuilderFactory.class);
  
  static {
    map.put("text/plain", new AsciiDocBuilder());
    map.put("application/pdf", new PdfDocBuilder());
//    map.put("application/msword", new WordDocBuilder());
//    map.put("application/vnd.ms-powerpoint", new PowerPointDocBuilder());
//    map.put("application/vns.ms-excel", new ExcelDocBuilder());
//    map.put("text/html", new HtmlDocBuilder());
//    map.put("application/zip", new ZipDocBuilder());
//    map.put("application/x-rar", new RarDocBuilder());
  }
}