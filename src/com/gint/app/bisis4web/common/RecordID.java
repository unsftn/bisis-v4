package com.gint.app.bisis4web.common;

import java.io.Serializable;

/**
 * Represents a record identifier consisting of a library ID and a 
 * local record ID.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class RecordID implements Serializable {

  /**
   * Constructs a default record identifier.
   */
  public RecordID() {
    libraryID = 0;
    localID = 0;
    libraryAware = true;
  }
  /**
   * Constructs the identifier with the given values.
   * @param libraryID
   * @param localID
   */
  public RecordID(int libraryID, int localID) {
    this.libraryID = libraryID;
    this.localID = localID;
    libraryAware = true;
  }
  
  /**
   * @return Returns the libraryID.
   */
  public int getLibraryID() {
    return libraryID;
  }
  /**
   * @param libraryID The libraryID to set.
   */
  public void setLibraryID(int libraryID) {
    this.libraryID = libraryID;
  }
  /**
   * @return Returns the localID.
   */
  public int getLocalID() {
    return localID;
  }
  /**
   * @param localID The localID to set.
   */
  public void setLocalID(int localRecordID) {
    this.localID = localRecordID;
  }
  /**
   * @return Returns the libraryAware.
   */
  public boolean isLibraryAware() {
    return libraryAware;
  }
  /**
   * @param libraryAware The libraryAware to set.
   */
  public void setLibraryAware(boolean libraryAware) {
    this.libraryAware = libraryAware;
  }
  
  /**
   * Returns <code>true</code> if objects are equal. Equality may be
   * library-aware (default) or not.
   */
  public boolean equals(Object o) {
    if (o instanceof RecordID) {
      RecordID rid = (RecordID)o;
      if (libraryAware) {
        if (localID == rid.localID && libraryID == rid.libraryID)
          return true;
        else
          return false;
      } else {
        if (localID == rid.localID)
          return true;
        else
          return false;
      }
    } else
      return false;
  }
  
  /**
   * Returns the hash code for this object computed as 
   * <code>libraryID + localID</code>. 
   */
  public int hashCode() {
    return libraryID + localID;
  }
  
  /**
   * Returns the string representation of this object.
   */
  public String toString() {
    return "|" + libraryID + "," + localID + "|";
  }

  /** Library identifier. */
  private int libraryID;
  /** Local record identifier. */
  private int localID;
  /** Checks whether identifier comparison is library-aware. */
  private boolean libraryAware;
}
