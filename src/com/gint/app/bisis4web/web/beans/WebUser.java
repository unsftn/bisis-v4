package com.gint.app.bisis4web.web.beans;

import java.io.Serializable;

import com.gint.app.bisis4web.common.RecordID;
import com.gint.app.bisis4web.formatters.RecordFormatterFactory;

/**
 * Represents a users web session.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
@SuppressWarnings("serial")
public class WebUser implements Serializable{
  
  public static final int INIT_PAGE_SIZE = 5;
  
  public WebUser() {
    locale = "sr";
    currentQuery = "";
    recordIDs = new RecordID[0];
    displayMode = RecordFormatterFactory.FORMAT_BRIEF;
    startIndex = 0;
    pageSize = INIT_PAGE_SIZE;
  }
  
  public int getHitCount() {
    return recordIDs.length;
  }
  
  public RecordID getRecordID(int index) {
    if (index < 0 || index >= recordIDs.length)
      return null;
    return recordIDs[index];
  }
  
  /**
   * @return Returns the locale.
   */
  public String getLocale() {
    return locale;
  }
  /**
   * @param locale The locale to set.
   */
  public void setLocale(String locale) {
    this.locale = locale;
  }
  /**
   * @return Returns the recordIDs.
   */
  public RecordID[] getRecordIDs() {
    return recordIDs;
  }
  /**
   * @param recordIDs The recordIDs to set.
   */
  public void setRecordIDs(RecordID[] recordIDs) {
    this.recordIDs = recordIDs;
  }
  /**
   * @return Returns the currentQuery.
   */
  public String getCurrentQuery() {
    return currentQuery;
  }
  /**
   * @param currentQuery The currentQuery to set.
   */
  public void setCurrentQuery(String currentQuery) {
    this.currentQuery = currentQuery;
  }
  /**
   * @return Returns the displayMode.
   */
  public int getDisplayMode() {
    return displayMode;
  }
  /**
   * @param displayMode The displayMode to set.
   */
  public void setDisplayMode(int displayMode) {
    this.displayMode = displayMode;
  }
  /**
   * @return Returns the startIndex.
   */
  public int getStartIndex() {
    return startIndex;
  }
  /**
   * @param startIndex The startIndex to set.
   */
  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }
  /**
   * @return Returns the pageSize.
   */
  public int getPageSize() {
    return pageSize;
  }
  /**
   * @param pageSize The pageSize to set.
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  public int getEndIndex() {
    int retVal = startIndex + pageSize;
    if (retVal > getHitCount())
      retVal = getHitCount();
    return retVal;
  }

  /** The user's chosen locale */
  private String locale;
  
  /** The list of currently retrieved record IDs. */
  private RecordID[] recordIDs;
  
  /** The current query. */
  private String currentQuery;
  
  /** The current display mode. */
  private int displayMode;
  
  /** The index of the starting record in the view. */
  private int startIndex;
  
  /** Page size for record viewing. */
  private int pageSize;
  
}
