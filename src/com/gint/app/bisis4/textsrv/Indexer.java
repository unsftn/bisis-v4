package com.gint.app.bisis4.textsrv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;

import com.gint.app.bisis4.prefixes.PrefixConfigFactory;
import com.gint.app.bisis4.prefixes.PrefixConverter;
import com.gint.app.bisis4.prefixes.PrefixValue;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.utils.LatCyrUtils;
import com.gint.util.string.StringUtils;

public class Indexer {

  public Indexer() {
  }
  
  public Indexer(String indexPath) {
    this.indexPath = indexPath;
  }
  
  public String getIndexPath() {
    return indexPath;
  }

  public void setIndexPath(String indexPath) {
    this.indexPath = indexPath;
  }
  
  /**
   * Adds a new record to the index.
   * @param rec Record to be added
   * @return true if successful
   */
  public boolean add(Record rec, String stRashod) {
    try {
      IndexWriter iw = getIndexWriter(); 
      iw.addDocument(getDocument(rec, stRashod));
      iw.close();
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
		   iw.close();
		} catch (IOException ex) {
		   log.fatal(ex);
		   return false;
		 }
		 return true;
  }
  
  public void deleteField(String recID, String prefix, String value){
	   try {
		   Searcher searcher = new IndexSearcher(indexPath);
		   Query q=new TermQuery(new Term("ID",recID));
		   Hits hits=searcher.search(q);
		   if ((hits==null)||(hits.length()!=1)){
			   log.fatal("greska pri brisanju polja. Zapis: "+recID);
			   return ;   
		   }   
		   
		   Document doc=hits.doc(0);
		   Field [] fields=doc.getFields(prefix);
		   IndexWriter iw = getIndexWriter(); 
		   doc.removeFields(prefix);
		   for(int i=0;i<fields.length;i++){
			   if(!fields[i].stringValue().equals(value)){
				   doc.add(fields[i]);
			   } 
		   }
		   iw.updateDocument(new Term("ID", recID), doc);
		   iw.close();
		} catch (IOException ex) {
		   log.fatal(ex);
		 }
		 
 }
  /**
   * Updates a record in the index
   * @param rec Record to update
   * @return true if successful
   */
  public boolean update(Record rec, String stRashod) {
    if (!delete(rec))
      return false;
    return add(rec, stRashod);
  }
  
  /**
   * Deletes a record from the index
   * @param recID Record to delete
   * @return true if successful
   */
  public boolean delete(Record rec) {
    return delete(rec.getRecordID());
  }
  
  /**
   * Deletes a record from the index
   * @param recID Record ID
   * @return true if successful
   */
  public boolean delete(int recordID) {
    try {
      IndexReader indexReader = IndexReader.open(indexPath);
      Term term = new Term("ID", Integer.toString(recordID));
      indexReader.deleteDocuments(term);
      indexReader.close();
      return true;
    } catch (IOException ex) {
      log.fatal(ex);
      return false;
    }
  }
  
  /**
   * Optimizes the Lucene index.
   * @return true if successful
   */
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
  
  /**
   * Constructs a Lucene document containing prefixes from the given record.
   * @param rec Source record
   * @return A new Lucene document
   */
  protected Document getDocument(Record rec, String stRashod) {
    Document doc = new Document();
    Field id=new Field("ID",
    		    Integer.toString(rec.getRecordID()), 
    	        Field.Store.YES, 
    	        Field.Index.UN_TOKENIZED, 
    	        Field.TermVector.NO);
    
    doc.add(id);
    
    Set<String> sortPrefixes = PrefixConfigFactory.getPrefixConfig().getSortPrefixes();
    Iterator prefixes = PrefixConverter.toPrefixes(rec, stRashod).iterator();
    while (prefixes.hasNext()) {
      PrefixValue pref = (PrefixValue)prefixes.next();
      String value = LatCyrUtils.toLatin(pref.value);
      String valueWithOutAccent=LatCyrUtils.removeAccents(value);
      Field f = null;
      if(nontokenized.contains(pref.prefName)){
        f=new Field(pref.prefName, valueWithOutAccent.toLowerCase(),
            Field.Store.YES, 
            Field.Index.UN_TOKENIZED, 
            Field.TermVector.NO);      
      } else if(isbnList.contains(pref.prefName)){ //zbog ISBN
    	  valueWithOutAccent=StringUtils.clearDelimiters(valueWithOutAccent, delims);
    	  valueWithOutAccent=valueWithOutAccent.replace(" ", "");
    	  f=new Field(pref.prefName, "0start0 " + valueWithOutAccent.toLowerCase() + " 0end0", 
    	            Field.Store.YES, 
    	            Field.Index.TOKENIZED, 
    	            Field.TermVector.WITH_POSITIONS_OFFSETS); 
    	  
      }else {
    	  valueWithOutAccent=StringUtils.clearDelimiters(valueWithOutAccent, delims);// da bi izbacio sve znakove interpukcije osim za UDK,ISBN, ISSN
          f=new Field(pref.prefName, "0start0 " + valueWithOutAccent.toLowerCase() + " 0end0", 
          Field.Store.YES, 
          Field.Index.TOKENIZED, 
          Field.TermVector.WITH_POSITIONS_OFFSETS);
          
    	  value=StringUtils.clearDelimiters(value, delims);// cuvamo i orginalnu verziju terma ali ne indeksiramo
    	  Field  f1=new Field(pref.prefName, "0start0 " + value.toLowerCase() + " 0end0", 
          Field.Store.YES, 
          Field.Index.NO, 
          Field.TermVector.NO);
          doc.add(f1);
      }
      doc.add(f);
      
      if (sortPrefixes.contains(pref.prefName)) {
        if (doc.getField(pref.prefName+"_sort") == null)
          doc.add(new Field(pref.prefName+"_sort", value.toLowerCase(), 
              Field.Store.YES, 
              Field.Index.UN_TOKENIZED, 
              Field.TermVector.NO));
      }
    }
    return doc;
  }
  
  
  /**
   * Returns a new Lucene index writer. Creates the index if necessary.  
   * @return
   */
  protected IndexWriter getIndexWriter() {
    try {
      boolean createIndex = true;
      File testIndexPath = new File(indexPath);
      if (!testIndexPath.exists())
        testIndexPath.mkdirs();
      if (testIndexPath.isDirectory()) {
        if (testIndexPath.list().length > 0)
          createIndex = false;
        return new IndexWriter(indexPath, new WhitespaceAnalyzer(), createIndex);
      }
    } catch (Exception ex) {
      log.fatal(ex);
    }
    return null;
  }
  
  protected String indexPath;
  private static List<String> nontokenized=new ArrayList<String>();
  private static List<String> isbnList=new ArrayList<String>();
  static{
	  nontokenized.add("DC");
	 // nontokenized.add("SN"); //za isbn i issn izbacujemo crtice
	 // nontokenized.add("SP");
	//  nontokenized.add("SC");
	//  nontokenized.add("BN");
	  nontokenized.add("675a");
	//  nontokenized.add("010a");
	//  nontokenized.add("011a");
	  nontokenized.add("IN");
	
  }
  static{

	  isbnList.add("SN"); //za isbn i issn izbacujemo crtice
	  isbnList.add("SP");
	  isbnList.add("SC");
	  isbnList.add("BN");;
	  isbnList.add("010a");
	  isbnList.add("011a");	
  }
  
  private static String delims = "*?,;:\"()[]{}+/.!-" ;
  private static Log log = LogFactory.getLog(Indexer.class);
}
