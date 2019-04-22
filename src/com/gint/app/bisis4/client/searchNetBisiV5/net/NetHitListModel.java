package com.gint.app.bisis4.client.searchNetBisiV5.net;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;

public class NetHitListModel extends AbstractListModel {

  public NetHitListModel() {
  }
  
  public Object getElementAt(int index) {
    try {
      return records[index];
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  
  public int getSize() {
    if (records == null)
      return 0;
    return records.length;
  }
  
  public void setHits(Object []hits ) {
    try {
      records = hits;
      //izbacuje prazna polja i potpolja
      fireContentsChanged(this, 0, records.length - 1);
    } catch (Exception ex) {
      log.fatal(ex);
    }
  }
  
  public void refresh() {
    fireContentsChanged(this, 0, records.length - 1);
  }
  
  private Object records[];
  
  private static Log log = LogFactory.getLog(NetHitListModel.class);
}
