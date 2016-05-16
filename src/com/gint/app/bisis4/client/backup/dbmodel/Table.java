package com.gint.app.bisis4.client.backup.dbmodel;

import java.util.ArrayList;
import java.util.List;

public class Table {
  
  private String name;
  private List<Column> columns;
  private PrimaryKey primaryKey;
  private List<ForeignKey> foreignKeys;

  public Table() {
    columns = new ArrayList<Column>();
    primaryKey = new PrimaryKey();
    foreignKeys = new ArrayList<ForeignKey>();
  }
  
  public Table(String name) {
    this();
    this.name = name;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }
  public void addColumn(Column column ) {
	  this.columns.add(column);
}
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PrimaryKey getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(PrimaryKey primaryKey) {
    this.primaryKey = primaryKey;
  }
  
  public List<ForeignKey> getForeignKeys() {
    return foreignKeys;
  }

  public void setForeignKeys(List<ForeignKey> foreignKeys) {
    this.foreignKeys = foreignKeys;
  }
  public void addForeignKey(ForeignKey foreignKey) {
	    this.foreignKeys.add(foreignKey);
	  }
  public Column getColumn(String name) {
    for (Column c : columns) {
      if (c.getName().equals(name))
        return c;
    }
    return null;
  }

  public ForeignKey getForeignKey(String name) {
    for (ForeignKey k : foreignKeys) {
      if (k.getName().equals(name))
        return k;
    }
    return null;
  }
  
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("TABLE: ");
    retVal.append(name);
    retVal.append("\n");
    for (Column c : columns) {
      retVal.append(c.toString());
      if (primaryKey.getColumns().contains(c))
        retVal.append(" <pk>");
      for (ForeignKey fk : foreignKeys) {
        if (fk.getFkColumns().contains(c))
          retVal.append(" <fk>");
      }
      retVal.append("\n");
    }
    return retVal.toString();
  }
}
