package com.gint.app.bisis4.client.backup.dbmodel;

import java.util.ArrayList;
import java.util.List;

public class ForeignKey {

  private String name;
  private String updateRule;
  private String deleteRule;
  private Table pkTable;
  private Table fkTable;
  private List<Column> fkColumns;
  private List<Column> pkColumns;

  public ForeignKey() {
    fkColumns = new ArrayList<Column>();
    pkColumns = new ArrayList<Column>();
  }
  public ForeignKey(String name) {
    this();
    this.name = name;
  }
  public List<Column> getFkColumns() {
    return fkColumns;
  }
  public void setFkColumns(List<Column> fkColumns) {
    this.fkColumns = fkColumns;
  }
  public void addFkColumn(Column fkColumn) {
	    this.fkColumns.add(fkColumn);
  }
  public Table getFkTable() {
    return fkTable;
  }
  public void setFkTable(Table fkTable) {
    this.fkTable = fkTable;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public List<Column> getPkColumns() {
    return pkColumns;
  }
  public void setPkColumns(List<Column> pkColumns) {
    this.pkColumns = pkColumns;
  }
  public void addPkColumn(Column pkColumn) {
	    this.pkColumns.add(pkColumn);
}
  public Table getPkTable() {
    return pkTable;
  }
  public void setPkTable(Table pkTable) {
    this.pkTable = pkTable;
  }
  public String getUpdateRule() {
	    return updateRule;
	  }

public void setUpdateRule(String updateRule) {
	 this.updateRule = updateRule;
}

public String getDeleteRule() {
	    return deleteRule;
	  }

public void setDeleteRule(String deleteRule) {
	 this.deleteRule = deleteRule;
}
}
