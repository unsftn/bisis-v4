package com.gint.app.bisis4.client.backup.dbmodel;

import java.util.ArrayList;
import java.util.List;

public class PrimaryKey {

  private String name;
  private List<Column> columns;

  
  public PrimaryKey() {
    columns = new ArrayList<Column>();
  }

  public PrimaryKey(String name) {
    this.name = name;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }
  public void addColumn(Column column) {
	    this.columns.add(column);
	  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  


}
