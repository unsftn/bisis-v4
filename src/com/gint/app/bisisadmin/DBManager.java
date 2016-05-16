package com.gint.app.bisisadmin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


public class DBManager {
	
	private PoolingDataSource poolingDataSource;
  
  
  public DBManager() {
	  ResourceBundle rb = PropertyResourceBundle.getBundle(
      "com.gint.app.bisisadmin.Config");
	  String driver     = rb.getString("driver");
	  String URL        = rb.getString("url");
	  String username   = rb.getString("username");
	  String password   = rb.getString("password");
	
	  try {
	    Class.forName(driver);
	    GenericObjectPool connectionPool = new GenericObjectPool(null);
	    DriverManagerConnectionFactory connectionFactory = 
	      new DriverManagerConnectionFactory(URL, username, password);
	    PoolableConnectionFactory poolableConnectionFactory =
	      new PoolableConnectionFactory(connectionFactory, connectionPool, null, 
	          null, false, false);
	    poolingDataSource = new PoolingDataSource(connectionPool);
	  } catch (Exception ex) {
	    ex.printStackTrace();
	  }  
  }
  
  public boolean login(UserBean user){
	  try {
		  Connection conn = poolingDataSource.getConnection();
		  String sql = "select administracija from Bibliotekari where username = '"+ user.getUser() 
		  		+"' and password = '" + user.getPass() + "'";
		  Statement stmt = conn.createStatement();
		  ResultSet rset = stmt.executeQuery(sql);
		  boolean res;
		  if (rset.next()) {
			  if (rset.getInt(1) == 1){
				  res = true;
			  } else {
				  res = false;
				  user.setMsg("Don't have privilegies!");
			  }
			  
		  } else {
			  res = false;
			  user.setMsg("Unknown user!");
		  }
		  conn.close();
		  return res;
	} catch (SQLException e) {
		e.printStackTrace();
		user.setMsg("Database connection error!");
		return false;
		}
  }
  
  
}
