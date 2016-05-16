package com.gint.app.bisis4.remoting;

import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.textsrv.DocFile;
import com.gint.app.bisis4.textsrv.LockException;
import com.gint.app.bisis4.textsrv.Result;

public interface RecordManager {
  
  // finding records
  public int[] select1(String query, String sortPrefix)throws ParseException;
  public int[] select2(Query query, String sortPrefix);
  public int[] select2x(byte[] serializedQuery, String sortPrefix);
  public int[] select3(Query query, Filter filter, String sortPrefix);
  public Result selectAll1(String query, String sortPrefix) throws ParseException;
  public Result selectAll2(Query query, String sortPrefix);
  public Result selectAll2x(byte[] serializedQuery, String sortPrefix);
  public Result selectAll3(Query query, Filter filter, String sortPrefix);
  public Result selectAll3x(byte[] serializedQuery, byte[] serializedFilter, String sortPrefix);
  public List<String> selectExp(String query, String prefix,String text);

  // retrieving records
  public Record getRecord(int recID);
  public List<DocFile> getDocFiles(int rn);
  public Record[] getRecords(int[] recIDs);
  public Record getAndLock(int recID, String user) throws LockException;
  public String lock(int recID, String user);
  public void unlock(int recID);
  
  // storing records
  public int getNewID(String counterName);
  public boolean add(Record rec);
  public Record update(Record rec);
  public boolean delete(int recID);
  public boolean reindex(int recID);
  
}

