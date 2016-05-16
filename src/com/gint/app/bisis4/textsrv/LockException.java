package com.gint.app.bisis4.textsrv;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class LockException extends Exception {

  public LockException(String inUseBy) {
    this.inUseBy = inUseBy;
  }
  
  public String getInUseBy() {
    return inUseBy;
  }
  
  public String getMessage() {
    return "The record is locked by " + inUseBy;
  }
  
  private String inUseBy;
}
