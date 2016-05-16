package com.gint.app.bisis4.client.backup.dbmodel;

import java.sql.Types;
import java.util.ArrayList;

public class Column {

  private String name;
  private int type;
  private int size;
  private int decimals;
  private int nullable;
  private int position;
  private String autoIncrement;
  
  public Column() {
	}
  public Column(String name, int type, int size, int decimals, int nullable, 
      int position, String autoIncrement) {
    this.name = name;
    this.type = type;
    this.size = size;
    this.decimals = decimals;
    this.nullable = nullable;
    this.position = position;
    this.autoIncrement=autoIncrement;
  }

  public int getDecimals() {
    return decimals;
  }

  public String getAutoIncrement() {
	return autoIncrement;
}
public void setAutoIncrement(String autoIncrement) {
	this.autoIncrement = autoIncrement;
}
public void setDecimals(int decimals) {
    this.decimals = decimals;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNullable() {
    return nullable;
  }

  public void setNullable(int nullable) {
    this.nullable = nullable;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String toString() {
	if(autoIncrement.equalsIgnoreCase("YES")){
		 return String.format("  %1$-20s %2$-15s  %3$10s %4$10s", name, typeDesc(), nullDesc(),"AUTO_INCREMENT");
	}else{
	
		return String.format("  %1$-20s %2$-15s  %3$10s", name, typeDesc(), nullDesc());
	}
    
  }
  
  public String typeDesc() {
    switch (type) {
      case Types.INTEGER:
        return "INTEGER";
      case Types.CHAR:
        return "CHAR("+size+")";
      case Types.VARCHAR:
        return "VARCHAR("+size+")";
      case Types.DECIMAL:
        return "DECIMAL("+size+","+decimals+")";
      case Types.FLOAT:
        return "FLOAT";
      case Types.DOUBLE:
        return "DOUBLE";
      case Types.DATE:
        return "DATETIME";
      case Types.TIME: 
        return "TIME";
      case Types.TIMESTAMP:
        return "DATETIME";
      case Types.BOOLEAN:
        return "BOOLEAN";
      case Types.VARBINARY:
        return "VARBINARY";
      case Types.LONGNVARCHAR:
        return "LONGVARTEXT";
      case Types.LONGVARBINARY:
        return "LONGVARBINARY";
      case Types.OTHER:
        return "OTHER";
      default:
        return "TEXT";
    }
  }
  
  public String nullDesc() {
    return (nullable == 0) ? "NOT NULL" : "NULL";
  }
}
