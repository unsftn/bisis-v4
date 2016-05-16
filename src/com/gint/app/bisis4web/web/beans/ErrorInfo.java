package com.gint.app.bisis4web.web.beans;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * Represents the current error information.
 * @author mbranko@uns.ns.ac.yu
 */
@SuppressWarnings("serial")
public class ErrorInfo implements Serializable{

  public ErrorInfo() {
    errorOccured = false;
    errorMessage = "";
  }
  
  /** 
   * Sets the exception stack trace as the error message. 
   * @param ex Exception to be presented as the error. 
   */
  public void setException(Exception ex) {
    errorOccured = true;
    StringWriter sw = new StringWriter();
    ex.printStackTrace(new PrintWriter(sw));
    errorMessage = sw.toString();
  }
  
  /**
   * @return Returns the errorMessage.
   */
  public String getErrorMessage() {
    return errorMessage;
  }
  /**
   * @param errorMessage The errorMessage to set.
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
  /**
   * @return Returns the errorOccured.
   */
  public boolean isErrorOccured() {
    return errorOccured;
  }
  /**
   * @param errorOccured The errorOccured to set.
   */
  public void setErrorOccured(boolean errorOccured) {
    this.errorOccured = errorOccured;
  }
  
  /** Flag to indicate that error has occured. */
  private boolean errorOccured;
  /** Textual description of the error. */
  private String errorMessage;
}
