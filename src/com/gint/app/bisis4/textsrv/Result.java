package com.gint.app.bisis4.textsrv;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
  private int[] records;
  private List<String> invs;
  
  public Result(){
  }
  
  
  public List<String> getInvs() {
    return invs;
  }
  public void setInvs(List<String> invs) {
    this.invs = invs;
  }
  public int[] getRecords() {
    return records;
  }
  public void setRecords(int[] records) {
    this.records = records;
  }
  
  public int getResultCount(){
  	return records.length;
  }

}
