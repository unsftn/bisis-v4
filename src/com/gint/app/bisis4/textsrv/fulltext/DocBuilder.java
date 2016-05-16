package com.gint.app.bisis4.textsrv.fulltext;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;

/**
 * Interface for creating Lucene documents based on various file formats.
 */
public interface DocBuilder {

  /**
   * Builds a Lucene document based on the given file.
   * 
   * @param file The file to be processed
   * @param fileId Internal file ID
   * @return The built Lucene document
   * @throws IOException
   */
  public Document getDoc(File file, int fileId) throws IOException;
}
