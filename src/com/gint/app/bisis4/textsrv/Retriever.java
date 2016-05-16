package com.gint.app.bisis4.textsrv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

/**
 * Implements a record retriever.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class Retriever {

  /**
   * Default constructor.
   */
  public Retriever(String indexPath) {
    this.indexPath = indexPath;
  }
  
  /**
   * Executes a select query.
   * @param query The Lucene query
   * @return An array of record IDs; an empty array if an error occured
 * @throws IOException 
   */
  public int[] select(String query, String sortPrefix)  throws ParseException{
    try {
      WhitespaceAnalyzer sa= new WhitespaceAnalyzer();
      BooleanQuery.setMaxClauseCount(20000);//zbog heap-a
      QueryParser p = new QueryParser("KW", sa);
      p.setDefaultOperator(QueryParser.Operator.AND); //default operator je AND a ne OR kao sto je inace inicijalno
      Query q = p.parse(query);
      return select(q, sortPrefix);
    } catch (Exception ex) {
    	if (ex instanceof ParseException )
    		throw (ParseException)ex;
      log.warn(ex);
      return new int[0];
    }
  }
  
  /**
   * Executes a select query.
   * @param query The Lucene query
   * @return An array of record IDs; an empty array if an error occured
 * @throws IOException 
   */
  public int[] select(Query query, String sortPrefix)  {
    try {
      BooleanQuery.setMaxClauseCount(20000);//zbog heap-a
      Searcher searcher = new IndexSearcher(indexPath);
      Hits hits;
      if (sortPrefix == null || "".equals(sortPrefix))
        hits = searcher.search(query);
      else {
        int sortType = SortField.STRING;
        if ("RN_sort".equals(sortPrefix))
          sortType = SortField.INT;
        hits = searcher.search(query, new Sort(
            new SortField(sortPrefix, sortType)));
      }
      
      int n = hits.length();
      int[] retVal = new int[n];
      for (int i = 0; i < n; i++) {
        String recordID = hits.doc(i).get("ID");
        retVal[i] = Integer.parseInt(recordID);
      }
      searcher.close();
      return retVal;
    }catch(FileNotFoundException e){
    	return new int[0];
    } catch(Exception e){
    	return null;
    }
  }
  
  public Result selectAll(Query query, String sortPrefix){
    try {
      BooleanQuery.setMaxClauseCount(20000);//zbog heap-a
      Searcher searcher = new IndexSearcher(indexPath);
      Hits hits;
      if (sortPrefix == null || "".equals(sortPrefix))
        hits = searcher.search(query);
      else {
        int sortType = SortField.STRING;
        if ("RN_sort".equals(sortPrefix))
          sortType = SortField.INT;
        hits = searcher.search(query, new Sort(
            new SortField(sortPrefix, sortType)));
      }
      
      int n = hits.length();
      int[] retVal = new int[n];
      List<String> invs = new ArrayList<String>();
      Field[] tmp = null;
      
      for (int i = 0; i < n; i++) {
        String recordID = hits.doc(i).get("ID");
        retVal[i] = Integer.parseInt(recordID);
        tmp = hits.doc(i).getFields("IN");
        if (tmp != null){
          for (int j = 0; j<tmp.length; j++){
            invs.add(tmp[j].stringValue());
          } 
        }
      }
      searcher.close();
      Result result = new Result();
      result.setRecords(retVal);
      result.setInvs(invs);
      return result;
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  public Result selectAll1(String query, String sortPrefix)throws ParseException{
	    try {
	    	WhitespaceAnalyzer sa= new WhitespaceAnalyzer();
	    	Searcher searcher = new IndexSearcher(indexPath);
	        BooleanQuery.setMaxClauseCount(20000);//zbog heap-a
	        QueryParser p = new QueryParser("KW", sa);
	        p.setDefaultOperator(QueryParser.Operator.AND); //default operator je AND a ne OR kao sto je inace inicijalno
	        Query q = p.parse(query);
	        Hits hits;
	      if (sortPrefix == null || "".equals(sortPrefix))
	        hits = searcher.search(q);
	      else {
	        int sortType = SortField.STRING;
	        if ("RN_sort".equals(sortPrefix))
	          sortType = SortField.INT;
	        hits = searcher.search(q, new Sort(
	            new SortField(sortPrefix, sortType)));
	      }
	      
	      int n = hits.length();
	      int[] retVal = new int[n];
	      List<String> invs = new ArrayList<String>();
	      Field[] tmp = null;
	      
	      for (int i = 0; i < n; i++) {
	        String recordID = hits.doc(i).get("ID");
	        retVal[i] = Integer.parseInt(recordID);
	        tmp = hits.doc(i).getFields("IN");
	        if (tmp != null){
	          for (int j = 0; j<tmp.length; j++){
	            invs.add(tmp[j].stringValue());
	          } 
	        }
	      }
	      searcher.close();
	      Result result = new Result();
	      result.setRecords(retVal);
	      result.setInvs(invs);
	      return result;
	    } catch (Exception ex) {
	    	if (ex instanceof ParseException )
	    		throw (ParseException)ex;
	      log.fatal(ex);
	      return null;
	    }
	  }
  public int[] select(Query query, Filter filter, String sortPrefix){
	    try {
	      BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
	      Searcher searcher = new IndexSearcher(indexPath);
	      Hits hits;
	      if (sortPrefix == null || "".equals(sortPrefix)){
		        hits = searcher.search(query,filter);
	      } else {
	        int sortType = SortField.STRING;
	        if ("RN_sort".equals(sortPrefix))
	          sortType = SortField.INT;
	        hits = searcher.search(query,filter, new Sort(
	            new SortField(sortPrefix, sortType)));
		  }
	      int n = hits.length();
	      int[] retVal = new int[n];
	    
	      for (int i = 0; i < n; i++) {
	        String recordID = hits.doc(i).get("ID");
	        retVal[i] = Integer.parseInt(recordID);
	      }
	      searcher.close();
	      return retVal;
	    } catch (Exception ex) {
	      log.fatal(ex);
	      return null;
	    }
	  }
  
  public Result selectAll(Query query, Filter filter, String sortPrefix) {
    try {
      BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
      Searcher searcher = new IndexSearcher(indexPath);
      Hits hits;
      if (sortPrefix == null || "".equals(sortPrefix)){
	        hits = searcher.search(query,filter);
      } else {
        int sortType = SortField.STRING;
        if ("RN_sort".equals(sortPrefix))
          sortType = SortField.INT;
        hits = searcher.search(query,filter, new Sort(
            new SortField(sortPrefix, sortType)));
	  }
      int n = hits.length();
      int[] retVal = new int[n];
      List<String> invs = new ArrayList<String>();
      Field[] tmp = null;
    
      for (int i = 0; i < n; i++) {
        String recordID = hits.doc(i).get("ID");
        retVal[i] = Integer.parseInt(recordID);
        tmp = hits.doc(i).getFields("IN");
        if (tmp != null){
          for (int j = 0; j<tmp.length; j++){
            invs.add(tmp[j].stringValue());
          } 
        }
      }
      searcher.close();
      Result result = new Result();
      result.setRecords(retVal);
      result.setInvs(invs);
      return result;
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  
  public List<String> selectExpand(String query, String prefix,String text){
	    try {
	     WhitespaceAnalyzer sa= new WhitespaceAnalyzer();
	     BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
	     QueryParser p = new QueryParser("contents", sa);
	     Query q = p.parse(query);
	     Searcher searcher = new IndexSearcher(indexPath);
	     StopWatch clock=new StopWatch();
	     clock.start();
	     Hits hits = searcher.search(q);
	     int n = hits.length();
	     List <String> expandList = new ArrayList<String>();
	     Field[] tmp = null;
	     String pom="";
	      for (int i = 0; i < n; i++) {
	        tmp = hits.doc(i).getFields(prefix);
	        if (tmp != null){
	          for (int j = 0; j<tmp.length; j++){
	        	  pom=tmp[j].stringValue().replace("0start0 ", "");
	        	  pom=pom.replace(" 0end0", "");
	        	  if(pom.startsWith(text)&&(!expandList.contains(pom))){
	        	     expandList.add(pom);
	        	  }
	          } 
	        }
	      }
	      clock.stop();
	      searcher.close();
	      return expandList;
	    } catch (Exception ex) {
	      log.fatal(ex);
	      return null;
	    }
	  }
  
  
  private String indexPath;
  
  private static Log log = LogFactory.getLog(Retriever.class);
}
