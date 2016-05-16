package com.gint.app.bisis4.commandservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateArchiveServiceRemote implements Service{
	
	private static SessionFactory sessionFactory = null;
	private static Log log = LogFactory.getLog(HibernateArchiveServiceRemote.class.getName());
	
	static {
	    try {
	      Configuration cfg = new Configuration().configure(HibernateArchiveServiceRemote.class.getResource(
	        "/com/gint/app/bisis4/client/circ/model/hibernate.cfg.xml"));
	      cfg.setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/CommandConnArchive");     
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
