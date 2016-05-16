package com.gint.app.bisis4.client;

import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.caucho.burlap.client.BurlapProxyFactory;
//import com.caucho.hessian.client.HessianProxyFactory;
import com.gint.app.bisis4.client.login.LoginFrame;
import com.gint.app.bisis4.commandservice.CommandType;
import com.gint.app.bisis4.commandservice.HibernateArchiveServiceFactory;
import com.gint.app.bisis4.commandservice.HibernateServiceFactory;
import com.gint.app.bisis4.commandservice.JdbcServiceFactory;
import com.gint.app.bisis4.commandservice.Service;
import com.gint.app.bisis4.commandservice.ServiceFactory;
import com.gint.app.bisis4.commandservice.ServiceType;
import com.gint.app.bisis4.format.HoldingsDataCoders;
import com.gint.app.bisis4.format.PubTypes;
import com.gint.app.bisis4.format.UFormat;
import com.gint.app.bisis4.librarian.Librarian;
import com.gint.app.bisis4.librarian.ProcessTypeCatalog;
import com.gint.app.bisis4.remoting.RecordManager;
import com.gint.app.bisis4.remoting.RecordManagerImpl;
import com.gint.app.bisis4.utils.INIFile;
import com.gint.app.bisis4.utils.NetUtils;
import com.gint.app.bisis4.utils.SplashScreen;

public class BisisApp {

  public static final String VERSION = "4.0";
  
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    JDialog.setDefaultLookAndFeelDecorated(true);
    splash = new SplashScreen();
    splash.setImage("/com/gint/app/bisis4/client/images/book-big.png");
    splash.setVisible(true);
    splash.getMessage().setText("Pokre\u0107em menad\u017eer zapisa");
    
    if (args.length != 0){
    	URL url = null;
		try {
			url = new URL(args[0]);
			url.openStream();
		} catch (Exception e) {
			log.error(e);
			splash.setVisible(false);
		    JOptionPane.showMessageDialog(null, "Nije pronadjena konfiguracija!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
		    System.exit(0);
		}
    	iniFile = new INIFile(url);
    } else {
    	iniFile = new INIFile(BisisApp.class.getResource("/client-config.ini"));
    }
    
    standalone = iniFile.getBoolean("textsrv", "standalone");
    if (standalone) {
      try {
        Class.forName(iniFile.getString("database", "driver"));
        GenericObjectPool connectionPool = new GenericObjectPool(null);
        DriverManagerConnectionFactory connectionFactory = 
          new DriverManagerConnectionFactory(
              iniFile.getString("database", "url"), 
              iniFile.getString("database", "username"), 
              iniFile.getString("database", "password"));
        PoolableConnectionFactory poolableConnectionFactory =
          new PoolableConnectionFactory(connectionFactory, connectionPool, null, 
              null, false, false);
        PoolingDataSource poolingDataSource = new PoolingDataSource(connectionPool);
        RecordManagerImpl recMgr1 = new RecordManagerImpl();
        String osname = System.getProperty("os.name");
        if (osname.startsWith("Windows"))
          recMgr1.setIndexPath("C:/lucene-index"); 
        else
          recMgr1.setIndexPath("/opt/lucene-index");
        recMgr1.setDataSource(poolingDataSource);
        recMgr1.setStandalone(true);
        recMgr = recMgr1;
      } catch (Exception ex) {
        log.error(ex);
      }
    } else {
      try {
        //Class.forName(iniFile.getString("database", "driver"));
        //HessianProxyFactory proxyFactory = new HessianProxyFactory();
        BurlapProxyFactory proxyFactory = new BurlapProxyFactory();
        recMgr = (RecordManager)proxyFactory.create(RecordManager.class, 
            iniFile.getString("textsrv", "recmgr"));
      } catch (Exception ex) {
        log.error(ex);
      }
    }
    fileMgrEnabled = getINIFile().getBoolean("filestorage", "enabled");    
    fileMgrURL = getINIFile().getString("filestorage", "filemgr");
    
    splash.getMessage().setText("Otvaram vezu sa bazom");
       
//    try {
//      connection = DriverManager.getConnection(
//          iniFile.getString("database", "url"), 
//          iniFile.getString("database", "username"), 
//          iniFile.getString("database", "password"));     
//      connection.setAutoCommit(false);
//    } catch (SQLException e) {
//      log.fatal(e);
//      splash.setVisible(false);
//      e.printStackTrace();
//      JOptionPane.showMessageDialog(null, "Gre\u0161ka pri konekciji sa bazom", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
//      System.exit(0);
//    }  
    
    ServiceFactory factory = getFactory(CommandType.JDBC);
    String mac = NetUtils.getMACAddress();
    log.info("Procitana MAC adresa: " + mac);
    String category = "commandsrv";
    if (BisisApp.getINIFile().getCategories().contains(mac)){
    	category = mac;
    	log.info("Pronadjena konfiguracija za MAC adresu: " + mac);
    }else{
    	log.info("Nije pronadjena konfiguracija za MAC adresu: " + mac +". Koristi se default konfiguracija [commandsrv]");
    }
    if (BisisApp.getINIFile().getBoolean(category, "remote")){
    	jdbcservice = factory.createService(ServiceType.REMOTE, BisisApp.getINIFile().getString(category, "service"));
    	log.info("Kreirana instanca za udaljen pristup serveru.");
    } else {
    	jdbcservice = factory.createService(ServiceType.LOCAL, null);
    	log.info("Kreirana instanca za lokalni pristup serveru.");
    }
    
    if (jdbcservice == null){
      splash.setVisible(false);
      JOptionPane.showMessageDialog(null, "Gre\u0161ka pri konekciji sa bazom", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
    
    splash.getMessage().setText("U\u010ditavam UNIMARC");
    format = PubTypes.getFormat();
    try{
    	HoldingsDataCoders.loadData(jdbcservice);
    } catch (Exception e){
    	splash.setVisible(false);
    	JOptionPane.showMessageDialog(null, e.getMessage(), "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
    splash.getMessage().setText("U\u010ditavam parametre");
    ProcessTypeCatalog.init();    
    splash.getMessage().setText("U\u010ditavam prozore");
    mf = new MainFrame();
    mf.setResizable(true);
    mf.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    if (Toolkit.getDefaultToolkit().isFrameStateSupported(JFrame.MAXIMIZED_BOTH))
      mf.setExtendedState(mf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    splash.setVisible(false);
    splash.dispose();
    
    LoginFrame login = new LoginFrame();
    boolean correct = false;
    while (!correct){
	    if (login.isConfirmed()) {
	      librarian = new Librarian(login.getUsername(), login.getPassword());
	      if (librarian.login()) {	    
	      	correct = true;
	        login.disp();
	        mf.setVisible(true);
	      } else {
	        JOptionPane.showMessageDialog(null, "Pogre\u0161no ime/lozinka",
	            "Greska", JOptionPane.ERROR_MESSAGE);
	        login.setVis(true);
	        //System.exit(0);
	      }
	    } else {
	      System.exit(0);
	    }
    
    }
    
    mf.setJMenuBar(new MenuBuilder(librarian));
    mf.initialize(librarian);
  }

  public static MainFrame getMainFrame() {
    return mf;
  }
  
  public static RecordManager getRecordManager() {
    return recMgr;
  }
  
//  public static Connection getConnection() {
//    return connection;
//  }
  
  
  // postoji zbog bekapa
  //TODO napraviti bekap za udaljeni pristup bazi
  public static Connection getConnection(){
      Connection conn = null;
      try {
	    conn = DriverManager.getConnection(
	        iniFile.getString("database", "url"), 
	        iniFile.getString("database", "username"), 
	        iniFile.getString("database", "password"));     
	    conn.setAutoCommit(false);
	  } catch (SQLException e) {
	    log.fatal(e);
	    splash.setVisible(false);
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(null, "Gre\u0161ka pri konekciji sa bazom", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
	    System.exit(0);
	  }  
	  return conn;
  }
  
  public static Librarian getLibrarian() {
    return librarian;
  }
  
//  public static void setConnection(Connection c) {
//    connection = c;
//  }
  
  public static SplashScreen getSplash() {
    return splash;
  }
  
  public static UFormat getFormat() {
    return format;
  }
  
  public static INIFile getINIFile() {
    return iniFile;
  }
  
  public static boolean isStandalone(){
  	return standalone;
  }
  
  
  public static ServiceFactory getFactory(int type){
	  if(type == CommandType.HIBERNATE){
		  return new HibernateServiceFactory();
	  } else if (type == CommandType.HIBERNATEARCHIVE){
		  return new HibernateArchiveServiceFactory();
	  } else if (type == CommandType.JDBC){
		  return new JdbcServiceFactory();
	  } else if (type == CommandType.NONE){
		  ;
	  }
	  return null;
  }
  
  public static Service getJdbcService(){
	  return jdbcservice; 
  }
  
  public static String getFileManagerURL(){
  	return fileMgrURL;
  }
  
  public static boolean isFileMgrEnabled(){
  	return fileMgrEnabled;
  }
  
  private static MainFrame mf;
  private static RecordManager recMgr;
  //private static Connection connection;
  private static Service jdbcservice;
  private static Librarian librarian;
  private static SplashScreen splash;
  private static UFormat format;
  private static INIFile iniFile;
  private static boolean standalone;
  private static Log log = LogFactory.getLog(BisisApp.class.getName());
  
  private static String fileMgrURL = "";
  private static boolean fileMgrEnabled = false;
}
