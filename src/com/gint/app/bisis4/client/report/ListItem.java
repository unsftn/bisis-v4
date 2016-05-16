package com.gint.app.bisis4.client.report;

import com.gint.app.bisis4.reports.Report;

public class ListItem implements Comparable{
  
  public ListItem(String fileName, String description, Report report) {
    this.fileName = fileName;
    this.description = description;
    this.report = report;
  }
  public ListItem() {
  }
  private String fileName;
  private String description;
  private Report report;
  public String getFileName() {
    return fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Report getReport() {
    return report;
  }
  public void setReport(Report report) {
    this.report = report;
  }
  public String toString() {
    return description;
  }
public int compareTo(Object o) {
	return this.fileName.compareTo(((ListItem)o).fileName);
}
}
