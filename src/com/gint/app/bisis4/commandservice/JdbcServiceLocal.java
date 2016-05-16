package com.gint.app.bisis4.commandservice;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.utils.INIFile;

public class JdbcServiceLocal implements Service{
	
	private static Log log = LogFactory.getLog(JdbcServiceLocal.class.getName());
	private static Connection connection;
	
	public JdbcServiceLocal() throws Exception{
		  INIFile iniFile = new INIFile(JdbcServiceLocal.class.getResource("/client-config.ini"));
	      connection = DriverManager.getConnection(
	          iniFile.getString("database", "url"), 
	          iniFile.getString("database", "username"), 
	          iniFile.getString("database", "password"));     
	      connection.setAutoCommit(false);
	}
	
	public Command executeCommand(Command command){
	  command.setContext(connection);
	  command.execute();
	  command.setContext(null);
	  return command;
	}

}
