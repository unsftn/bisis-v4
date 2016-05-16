package com.gint.app.bisis4.client.backup.dbmodel;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.DecapitalizeNameMapper;
import org.apache.commons.betwixt.strategy.HyphenatedNameMapper;

import com.mysql.jdbc.ResultSetMetaData;

public class ModelFactory {
	
	  public static Model createModel(InputStream in) throws Exception {
		   
		   BeanReader reader = new BeanReader();
		   reader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(
		        true);

		    reader.getXMLIntrospector().getConfiguration().setAttributeNameMapper(
		        new HyphenatedNameMapper());
		    reader.getXMLIntrospector().getConfiguration().setElementNameMapper(
		        new DecapitalizeNameMapper());
		    reader.getBindingConfiguration().setMapIDs(true);
		    reader.registerBeanClass(Model.class);
		    Model m = (Model) reader.parse(in);
		    return m;

	  }
	
  public static Model createModel(Connection conn) throws SQLException {
    Model model = new Model();
    DatabaseMetaData dbmd = conn.getMetaData();
    
    Map<String, Table> tableMap = new HashMap<String, Table>();
    ResultSet rset = dbmd.getTables(null, null, null, null);
    while (rset.next()) {
      String tableName = rset.getString("TABLE_NAME");
      String tableType = rset.getString("TABLE_TYPE");
      if (tableType.indexOf("VIEW") != -1)
        continue;
      if (tableType.indexOf("SYSTEM") != -1)
        continue;
      tableMap.put(tableName, new Table(tableName));
    }
    rset.close();
    rset = dbmd.getColumns(null, null, null, null);
    while (rset.next()) {
      String tableName = rset.getString("TABLE_NAME");
      String columnName = rset.getString("COLUMN_NAME");
      int dataType = rset.getInt("DATA_TYPE");
      int columnSize = rset.getInt("COLUMN_SIZE");
      int decimalDigits = rset.getInt("DECIMAL_DIGITS");
      int nullable = rset.getInt("NULLABLE");
      int position = rset.getInt("ORDINAL_POSITION");
   
      String autoincrement=rset.getString("IS_AUTOINCREMENT" );
      Table table = tableMap.get(tableName);
      if (table != null) {
        Column column = new Column(columnName, dataType, columnSize, 
            decimalDigits, nullable, position, autoincrement);
   
        table.getColumns().add(column);
      }
    }
    rset.close();
    
    for (Table t : tableMap.values()) {
      Collections.sort(t.getColumns(), new ColumnOrderComparator());
    }
    
    for (Table t : tableMap.values()) {
      rset = dbmd.getPrimaryKeys(null, null, t.getName());
     
      while (rset.next()) {
        String columnName = rset.getString("COLUMN_NAME");
        String pkName = rset.getString("PK_NAME");
        t.getPrimaryKey().setName(pkName);
        Column c = t.getColumn(columnName);
        if (c != null)
          t.getPrimaryKey().getColumns().add(c);
      }
      rset.close();
      Collections.sort(t.getPrimaryKey().getColumns(), new PrimaryKeyOrderComparator());
    }
  //postavljanje stranih kljuceva  
    for (Table t : tableMap.values()) {
      rset = dbmd.getExportedKeys(null, null, t.getName());
      while (rset.next()) {
        String pkTableName = rset.getString("PKTABLE_NAME");
        String fkTableName = rset.getString("FKTABLE_NAME"); //ime tabele u koju je presao primarni kljuc
        String pkColumnName = rset.getString("PKCOLUMN_NAME");
        String fkColumnName = rset.getString("FKCOLUMN_NAME");
        String fkName = rset.getString("FK_NAME");
        String updateRule=rset.getString("UPDATE_RULE");
        String deleteRule=rset.getString("DELETE_RULE");
        Table pkTable = tableMap.get(pkTableName);
        Table fkTable = tableMap.get(fkTableName);//tabela u koju je presao primarni kljuc
        ForeignKey fk = fkTable.getForeignKey(fkName);
        if (fk == null) {
          fk = new ForeignKey(fkName);
          fk.setUpdateRule(getRestriction(updateRule));
          fk.setDeleteRule(getRestriction(deleteRule));
          fkTable.getForeignKeys().add(fk);
        }

        if (pkTable != null && fkTable != null) {
          fk.setFkTable(fkTable);
          fk.setPkTable(pkTable);
          Column fkColumn = fkTable.getColumn(fkColumnName);
          Column pkColumn = pkTable.getColumn(pkColumnName);
          if (fkColumn != null && pkColumn != null) {
            fk.getFkColumns().add(fkColumn);
            fk.getPkColumns().add(pkColumn);
          }
        }
      }
      rset.close();
    }
    
    // create a hierarchical list of tables
    List<Table> temp = new ArrayList<Table>();
    temp.addAll(tableMap.values());
    do {
      for (Iterator<Table> i = temp.iterator(); i.hasNext(); ) {
        Table t = i.next(); 
       
        boolean hasParents = false;
        for (ForeignKey fk : t.getForeignKeys()) {
          if (temp.contains(fk.getPkTable()))
            hasParents = true;
        }
        if (!hasParents) {
          model.getHierarchicalTables().add(t); //
          i.remove();
        }
        
      }
    } while (temp.size() > 0);
    
    model.getAlphabeticalTables().addAll(model.getHierarchicalTables());
    Collections.sort( model.getAlphabeticalTables(), new TableAlphabeticalComparator());
    return model;
  }
  private static String getRestriction(String rest){
	  switch(Integer.parseInt(rest)){
	  case 0:
		   return"CASCADE";
		  
	  case 1:
		  return"NO ACTION";
		  
	  case 2:
		  return"SET NULL";
		  
	  case 3:
		  return"RESTRICT";
		  
	  case 4:
		  return"NO ACTION";
	  default:
	     return"NO ACTION";
		  
	  }
  }
  public static class ColumnOrderComparator implements Comparator<Column>{
    public int compare(Column c1, Column c2) {
      return c1.getPosition() - c2.getPosition();
    }
    public boolean equals(Object o) {
      return (o instanceof ColumnOrderComparator);
    }
  }
  public static class PrimaryKeyOrderComparator implements Comparator<Column>{
    public int compare(Column c1, Column c2) {
      return c1.getPosition() - c2.getPosition();
    }
    public boolean equals(Object o) {
      return (o instanceof PrimaryKeyOrderComparator);
    }
  }
  public static class TableAlphabeticalComparator implements Comparator<Table>{
    public int compare(Table t1, Table t2) {
      return t1.getName().compareTo(t2.getName());
    }
    public boolean equals(Object o) {
      return (o instanceof TableAlphabeticalComparator);
    }
  }
  


  public static void saveModel(Model model, OutputStream out) throws Exception {
    BeanWriter writer = new BeanWriter(out);
    writer.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(
        true);
    writer.enablePrettyPrint();
    writer.getBindingConfiguration().setMapIDs(true);
    writer.getXMLIntrospector().getConfiguration().setAttributeNameMapper(
        new HyphenatedNameMapper());
    writer.getXMLIntrospector().getConfiguration().setElementNameMapper(
        new DecapitalizeNameMapper());
    
    // write out the bean
    writer.write(model);
    System.out.println("");
  }

}
