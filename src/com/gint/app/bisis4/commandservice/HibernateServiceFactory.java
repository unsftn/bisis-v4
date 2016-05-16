package com.gint.app.bisis4.commandservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class HibernateServiceFactory implements ServiceFactory{
	
	private static Log log = LogFactory.getLog(HibernateServiceFactory.class.getName());

	public Service createService(int type, String param){
		
	    Service service = null;
	    
	    if (type == ServiceType.LOCAL){
	    	service = new HibernateServiceLocal();
	    } else if (type == ServiceType.REMOTE){
	    	service = new ServiceBroker(param);
	    }
	    return service;
	}
	
}
