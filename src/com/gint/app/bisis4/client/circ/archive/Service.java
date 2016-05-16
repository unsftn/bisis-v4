package com.gint.app.bisis4.client.circ.archive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.gint.app.bisis4.commandservice.Command;

public class Service {
	
	private SessionFactory sessionFactory = null;
	private static Log log = LogFactory.getLog(Service.class.getName());
	

	public Service(String address, String database, String user, String password){
		try {

	      Configuration cfg = new Configuration().configure(Service.class.getResource(
	        "/com/gint/app/bisis4/client/circ/model/hibernate.cfg.xml"));
	    	  cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		      cfg.setProperty("hibernate.connection.url", "jdbc:mysql://"+address+"/"+database);
		      cfg.setProperty("hibernate.connection.username", user);
		      cfg.setProperty("hibernate.connection.password", password);
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
	  command.execute();
	  command.setContext(null);
	  session.close();
	  return command;
	}

}
