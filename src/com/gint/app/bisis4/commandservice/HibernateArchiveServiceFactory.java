package com.gint.app.bisis4.commandservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class HibernateArchiveServiceFactory implements ServiceFactory{
	
	private static Log log = LogFactory.getLog(HibernateArchiveServiceFactory.class.getName());

	public Service createService(int type, String param){
		
	    Service service = null;
	    
	    if (type == ServiceType.LOCAL){
	    	service = new HibernateArchiveServiceLocal();
	    } else if (type == ServiceType.REMOTE){
	    	service = new ServiceBroker(param);
	    }
	    return service;
	}
	
}
