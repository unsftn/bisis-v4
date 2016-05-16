package com.gint.app.bisis4.commandservice;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.utils.INIFile;

public class HibernateServiceLocal implements Service{
	
	private static SessionFactory sessionFactory = null;
	private static Log log = LogFactory.getLog(HibernateServiceLocal.class.getName());
	
	static {
    try {
      INIFile iniFile = BisisApp.getINIFile();
      Configuration cfg = new Configuration().configure(HibernateServiceLocal.class.getResource(
        "/com/gint/app/bisis4/client/circ/model/hibernate.cfg.xml"));
      	cfg.setProperty("hibernate.connection.driver_class", iniFile.getString("database", "driver"));
        cfg.setProperty("hibernate.connection.url", iniFile.getString("database", "url"));
        cfg.setProperty("hibernate.connection.username", iniFile.getString("database", "username"));
        cfg.setProperty("hibernate.connection.password", iniFile.getString("database", "password"));
        cfg.setProperty("hibernate.connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
        cfg.setProperty("hibernate.c3p0.min_size", "2");
	    cfg.setProperty("hibernate.c3p0.max_size", "20");
	    cfg.setProperty("hibernate.c3p0.timeout", "1800");
       sessionFactory = cfg.buildSessionFactory();
    } catch (Exception ex) {
    	log.error(ex);
      ex.printStackTrace();
    }
  }
	
	
	public Command executeCommand(Command command){
		Session session = sessionFactory.openSession();
	  command.setContext(session);
//System.out.println(command.getClass().getSimpleName() + " - size before: "+ SerializationUtils.serialize(command).length);
//StopWatch clock = new StopWatch();
//clock.start();
	  command.execute();
	  command.setContext(null);
	  session.close();
//System.out.println(command.getClass().getSimpleName() + " - size after: "+ SerializationUtils.serialize(command).length);
//clock.stop();
//System.out.println("clock:"+clock.getTime());
	  return command;
	}

}
