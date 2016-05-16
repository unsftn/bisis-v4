package com.gint.app.bisis4.client.backup.dbmodel;

import java.util.ArrayList;
import java.util.List;

public class Model {

  private List<Table> alphabeticalTables;
  private List<Table> hierarchicalTables;


  public Model() {
	  alphabeticalTables = new ArrayList<Table>();
	  hierarchicalTables = new ArrayList<Table>();
  }

  public List<Table> getAlphabeticalTables() {
    return alphabeticalTables;
  }

  public void setAlphabeticalTables(List<Table> tables) {
    this.alphabeticalTables = tables;
  }
  
  public void addAlphabeticalTable(Table table) {
	    this.alphabeticalTables.add(table);
  }

  public List<Table> getHierarchicalTables() {
    return hierarchicalTables;
  }

  public void setHierarchicalTables(List<Table> tables) {
    this.hierarchicalTables = tables;
  }
  
  public void addHierarchicalTable(Table table) {
	    this.hierarchicalTables.add(table);
  }
  public Table getTable(String tableName){
	  for(Table t: alphabeticalTables){
		  if (t.getName().equals(tableName))
			 return t;
		  
	  }
	  return null;
  }
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("=== database model ===\n");
    for(Table t : hierarchicalTables)
      retVal.append(t.toString());
    return retVal.toString();
  }

}
