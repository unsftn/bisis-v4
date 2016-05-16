package com.gint.app.bisis4.librarian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LibrarianContext implements Serializable {

  private List<ProcessType> processTypes;
  private ProcessType defaultProcessType;
  private String pref1;
  private String pref2;
  private String pref3;
  private String pref4;
  private String pref5;

  public static LibrarianContext getLibrarianContext(String xml) {
    return LibrarianContextBuilder.getLibrarianContext(xml);
  }
  
  public LibrarianContext() {
    processTypes = new ArrayList<ProcessType>();
  }
  
  public ProcessType getDefaultProcessType() {
    return defaultProcessType;
  }
  public void setDefaultProcessType(ProcessType defaultProcessType) {
    this.defaultProcessType = defaultProcessType;
  }
  public List<ProcessType> getProcessTypes() {
    return processTypes;
  }
  public void setProcessTypes(List<ProcessType> processTypes) {
    this.processTypes = processTypes;
  }
  
  public String getPref1() {
    return pref1;
  }

  public void setPref1(String pref1) {
    this.pref1 = pref1;
  }

  public String getPref2() {
    return pref2;
  }

  public void setPref2(String pref2) {
    this.pref2 = pref2;
  }

  public String getPref3() {
    return pref3;
  }

  public void setPref3(String pref3) {
    this.pref3 = pref3;
  }

  public String getPref4() {
    return pref4;
  }

  public void setPref4(String pref4) {
    this.pref4 = pref4;
  }

  public String getPref5() {
    return pref5;
  }

  public void setPref5(String pref5) {
    this.pref5 = pref5;
  }
  
  public String toXML() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("<?xml version=\"1.0\"?>\n");
    retVal.append("<librarian-context>\n");
    if(processTypes!=null){
	    for (ProcessType pt : processTypes) {
	      retVal.append("  <process-type name=\"");
	      retVal.append(pt.getName());
	      retVal.append("\"/>\n");
	    }
	    if(defaultProcessType!=null){
		    retVal.append("  <default-process-type name=\"");
		    retVal.append(defaultProcessType.getName());
		    retVal.append("\"/>\n");
	    }
    }
    retVal.append("   <prefixes pref1=\"");
    retVal.append(getPref1());
    retVal.append("\" pref2=\"");
    retVal.append(getPref2());
    retVal.append("\" pref3=\"");
    retVal.append(getPref3());
    retVal.append("\" pref4=\"");
    retVal.append(getPref4());
    retVal.append("\" pref5=\"");
    retVal.append(getPref5());
    retVal.append("\" />\n");
    retVal.append("</librarian-context>\n");
    return retVal.toString();
  }
  
  public String toString() {
    return toXML();
  }

}
