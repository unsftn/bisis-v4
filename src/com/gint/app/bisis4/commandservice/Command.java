package com.gint.app.bisis4.commandservice;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class Command implements Serializable{
	
	protected Object context;
	protected int type;
	protected static Log log = LogFactory.getLog(Command.class.getName());

  public void setContext(Object context){
  	this.context = context;
  }
  
  public int getType(){
	return type;
  }
  
  public void setType(int type){
	this.type = type;
  }
  
  public abstract void execute();
	
}
