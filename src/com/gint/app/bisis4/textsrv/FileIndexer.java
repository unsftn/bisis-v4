package com.gint.app.bisis4.textsrv;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.gint.app.bisis4.textsrv.fulltext.DocBuilder;
import com.gint.app.bisis4.textsrv.fulltext.DocBuilderFactory;

public class FileIndexer {
  
  public FileIndexer() {
  }
  
  public FileIndexer(String indexPath) {
    this.indexPath = indexPath;
  }

  public String getIndexPath() {
    return indexPath;
  }

  public void setIndexPath(String indexPath) {
    this.indexPath = indexPath;
  }
  
  public boolean add(DocFile file) {
    try {
      IndexWriter iw = getIndexWriter(); 
      iw.addDocument(getDocument(file));
      iw.close();
    } catch (IOException ex) {
      log.fatal(ex);
      return false;
    }
    return true;
  }
  
  public boolean update(DocFile file) {
    if (!delete(file))
      return false;
    return add(file);
  }
  
  public boolean delete(DocFile file) {
    try {
      IndexReader indexReader = IndexReader.open(indexPath);
      Term term = new Term("FILEID", Integer.toString(file.getId()));
      indexReader.deleteDocuments(term);
      indexReader.close();
      return true;
    } catch (IOException ex) {
      log.fatal(ex);
      return false;
    }
  }

  public boolean optimize() {
    try {
      IndexWriter indexWriter = getIndexWriter();
      indexWriter.optimize();
      return true;
    } catch (IOException ex) {
      log.fatal(ex);
      return false;
    }
  }

  protected IndexWriter getIndexWriter() {
    try {
      boolean createIndex = true;
      File testIndexPath = new File(indexPath);
      if (!testIndexPath.exists())
        testIndexPath.mkdirs();
      if (testIndexPath.isDirectory()) {
        if (testIndexPath.list().length > 0)
          createIndex = false;
        return new IndexWriter(indexPath, new BisisAnalyzer(), createIndex);
      }
    } catch (Exception ex) {
      log.fatal(ex);
    }
    return null;
  }
  
  protected Document getDocument(DocFile docFile) {
    DocBuilder docBuilder = DocBuilderFactory.getDocBuilder(
        docFile.getContentType());
    if (docBuilder == null)
      return null;
    File file = new File(FileStorage.getFullPath(docFile));
    if (!file.isFile()) {
      log.fatal("File " + file.getAbsolutePath() + " does not exist.");
      return null;
    }
    Document doc = null;
    try {
      doc = docBuilder.getDoc(file, docFile.getId());
    } catch (IOException ex) {
      log.fatal(ex);
    }
    return doc;
  }

  protected String indexPath;
  private static Log log = LogFactory.getLog(FileIndexer.class);
}
