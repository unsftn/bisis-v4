package com.gint.app.bisis4.remoting;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.textsrv.Indexer;

public class RecordConsumer implements MessageListener {

  public RecordConsumer(String luceneIndex) {
    indexer = new Indexer(luceneIndex);
    log.info("RecordConsumer created");
  }
  
  public void onMessage(Message msg) {
    try {
      ObjectMessage om = (ObjectMessage)msg;
      String operation = om.getStringProperty("operation");
      Object obj = om.getObject();
      String rashodCode = om.getStringProperty("rashodCode");
      log.info("onMessage:" +operation+ "operation");
      if (obj instanceof Record) {
        Record rec = (Record)obj;
        if ("add".equals(operation)) {
          indexer.add(rec, rashodCode);
          log.info("Record added, ID: " + rec.getRecordID());
        } else if ("update".equals(operation)) {
          indexer.update(rec, rashodCode);
          log.info("Record updated, ID: " + rec.getRecordID());
        }
      }
      else if ((obj instanceof Integer) && "delete".equals(operation)) {
        Integer recID = (Integer)obj;
        indexer.delete(recID);
        log.info("Record deleted, ID: " + recID);
      }
    } catch (JMSException e) {
      log.fatal(e);
    }
  }

  private Indexer indexer;
  
  private static Log log = LogFactory.getLog(RecordConsumer.class);
}
