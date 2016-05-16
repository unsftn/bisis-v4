package com.gint.app.bisis4.commandservice;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JdbcServiceRemote implements Service{
	
	private static Log log = LogFactory.getLog(JdbcServiceRemote.class.getName());
	private static DataSource dataSource;
	
	static {
	    try {
	    	//TODO
	    	Context ctx = new InitialContext();
	        dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/CommandConn");
	    } catch (Exception ex) {
	    	log.error(ex);
	      ex.printStackTrace();
	    }
	  }
	
	
	public Command executeCommand(Command command){
		try {
		  Connection conn = dataSource.getConnection();
		  command.setContext(conn);
		  command.execute();
		  command.setContext(null);
		  conn.close();
		  return command;
		} catch (SQLException ex) {
	      log.fatal(ex);
	      ex.printStackTrace();
	      return null;
	    }
	}

}
