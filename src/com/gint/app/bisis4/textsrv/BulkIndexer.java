package com.gint.app.bisis4.textsrv;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;

import com.gint.app.bisis4.records.Record;

public class BulkIndexer extends Indexer {

  public BulkIndexer() {
    super();
  }
  
  public BulkIndexer(String indexPath) {
    super(indexPath);
  }

  public boolean add(Record rec, String stRashod) {
    try {
      getIndexWriter().addDocument(getDocument(rec, stRashod));
    } catch (IOException ex) {
      log.fatal(ex);
      return false;
    }
    return true;
  }
  
  public boolean addField(String recID, String prefix, String value){
	   try {
		   Searcher searcher = new IndexSearcher(indexPath);
		   Query q=new TermQuery(new Term("ID",recID));
		   Hits hits=searcher.search(q);
		   if ((hits==null)||(hits.length()!=1)){
			   return false;
		   }   
		   Document doc=hits.doc(0);
		   IndexWriter iw = getIndexWriter(); 
		   Field f=new Field(prefix,value, Field.Store.YES, Field.Index.UN_TOKENIZED, Field.TermVector.NO);
		   doc.add(f);
		   iw.updateDocument(new Term("ID", recID), doc);
		} catch (IOException ex) {
		   log.fatal(ex);
		   return false;
		 }
		 return true;
 }
  
  public void close() {
    try {
      writer.close();
    } catch (Exception ex) {
      log.fatal(ex);
    }
  }
  
  @Override
  protected IndexWriter getIndexWriter() {
    if (++useCount % 1000 == 0) {
      close();
      writer = null;
    }
    if (writer == null) {
      try {
        boolean createIndex = true;
        File testIndexPath = new File(indexPath);
        if (!testIndexPath.exists())
          testIndexPath.mkdirs();
        if (testIndexPath.isDirectory()) {
          if (testIndexPath.list().length > 0)
            createIndex = false;
          writer = new IndexWriter(indexPath, new WhitespaceAnalyzer(), createIndex);
        }
      } catch (Exception ex) {
        log.fatal(ex);
      }
    }
    return writer;
  }
  
  private int useCount = 0;
  private IndexWriter writer = null;
  private static Log log = LogFactory.getLog(BulkIndexer.class.getName());
}
