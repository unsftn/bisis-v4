package com.gint.app.bisis4.textsrv.fulltext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * Builds Lucene documents from ASCII text files. Uses system default character 
 * encoding.
 */
public class AsciiDocBuilder implements DocBuilder {

  /**
   * Builds a Lucene document based on the given file.
   * 
   * @param file The file to be processed
   * @return The built Lucene document
   * @throws IOException
   */
  public Document getDoc(File file, int fileId) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    Document doc = new Document();
    doc.add(new Field("FT", reader));
    doc.add(new Field("FILEID", Integer.toString(fileId), 
        Field.Store.YES, Field.Index.UN_TOKENIZED));
    return doc;
  }
}
