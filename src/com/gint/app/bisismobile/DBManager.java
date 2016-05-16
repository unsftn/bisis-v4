package com.gint.app.bisismobile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.format.UItem;
import com.gint.app.bisis4.remoting.RecordManager;
import com.gint.app.bisis4.remoting.RecordManagerImpl;



@SuppressWarnings("unused")
public class DBManager {
	
	private DBManager() {
	}
	
	private static PoolingDataSource poolingDataSource;
	private static DBManager instance = new DBManager();
	private RecordManager recMgr = null;
  
  
  static {
	  ResourceBundle rb = PropertyResourceBundle.getBundle(
      "com.gint.app.bisismobile.Config");
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
	    RecordManagerImpl recMgr1 = new RecordManagerImpl();
	      recMgr1.setIndexPath(rb.getString("index"));
	      recMgr1.setDataSource(poolingDataSource);
	      recMgr1.setStandalone(true);
	      instance.recMgr = recMgr1;
	      
	      Connection conn = poolingDataSource.getConnection();
	      HoldingsDataCodersJdbc.loadData(conn);
	      conn.close();
	      
	  } catch (Exception ex) {
	    ex.printStackTrace();
	  }  
  }
  
  public static boolean login(UserBean user){
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
  
  public static String getStatusCoder(){
	  StringBuffer retVal = new StringBuffer(2048);
	  retVal.append("<coder>\n");
	  retVal.append("<item>\n");
	  retVal.append("<code>" + "null" + "</code>\n");
	  retVal.append("<value>" + "--- " + "</value>\n");
	  retVal.append("</item>\n");
	  List <UItem> list = HoldingsDataCodersJdbc.getCoder(HoldingsDataCodersJdbc.STATUS_CODER);
	  for (UItem i : list){
		  retVal.append("<item>\n");
		  retVal.append("<code>" + i.getCode() + "</code>\n");
		  retVal.append("<value>" + i.getValue() + "</value>\n");
		  retVal.append("</item>\n");
	  }
	  retVal.append("</coder>\n");
	  return retVal.toString();
  }
  
  
  
  public static DBManager getDBManager() {
	    return instance;
  }
  
  public RecordManager getRecMgr() {
	    return recMgr;
  }
  
  
}
