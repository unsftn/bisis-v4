package com.gint.app.bisis4web.web;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.gint.app.bisis4.format.PubTypes;
import com.gint.app.bisis4.format.UFormat;
import com.gint.app.bisis4.prefixes.PrefixConfigFactory;
import com.gint.app.bisis4.prefixes.PrefixMap;
import com.gint.app.bisis4.remoting.RecordManager;
import com.gint.app.bisis4.remoting.RecordManagerImpl;

@SuppressWarnings("unused")
public class Settings {
  
  public static Settings getSettings() {
    return instance;
  }
  
  private Settings() {
  }
  
//  public Connection getConn() {
//    return conn;
//  }
  public String getLocale() {
    return locale;
  }
  public String getStaticFilter() {
    return staticFilter;
  }
  public String getShowRashod() {
    return showRashod;
  }
  public RecordManager getRecMgr() {
    return recMgr;
  }
  public UFormat getFormat(){
  	return format;
  }
  public PrefixMap getPrefixMap() {
    return prefixMap;
  }
  
  private String locale;
  private String staticFilter;
  private String showRashod;
  private RecordManager recMgr = null;
  private UFormat format;
  private PrefixMap prefixMap;
  
  private static Settings instance = new Settings();
  
  static {
    ResourceBundle rb = PropertyResourceBundle.getBundle(
        "com.gint.app.bisis4web.web.Settings");
    String driver     = rb.getString("driver");
    String URL        = rb.getString("url");
    String username   = rb.getString("username");
    String password   = rb.getString("password");
    instance.locale   = rb.getString("locale");
    instance.showRashod = rb.getString("showRashod");
    try {
      instance.staticFilter = rb.getString("staticFilter");
    } catch (Exception ex) {
      instance.staticFilter = null;
    }
    try {
      Class.forName(driver);
      GenericObjectPool connectionPool = new GenericObjectPool(null);
      DriverManagerConnectionFactory connectionFactory = 
        new DriverManagerConnectionFactory(URL, username, password);
      PoolableConnectionFactory poolableConnectionFactory =
        new PoolableConnectionFactory(connectionFactory, connectionPool, null, 
            null, false, false);
      PoolingDataSource poolingDataSource = new PoolingDataSource(connectionPool);
      RecordManagerImpl recMgr1 = new RecordManagerImpl();
      recMgr1.setIndexPath(rb.getString("index"));
      recMgr1.setDataSource(poolingDataSource);
      recMgr1.setStandalone(true);
      
      instance.format = PubTypes.getFormat();
      instance.prefixMap = PrefixConfigFactory.getPrefixConfig().getPrefixMap();
      
      instance.recMgr = recMgr1;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
