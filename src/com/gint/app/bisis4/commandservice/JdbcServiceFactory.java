package com.gint.app.bisis4.commandservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JdbcServiceFactory implements ServiceFactory{
	
	private static Log log = LogFactory.getLog(JdbcServiceFactory.class.getName());

	public Service createService(int type, String param){
		
	    Service service = null;
	    
	    if (type == ServiceType.LOCAL){
	    	try{
	    		service = new JdbcServiceLocal();
		    }catch (Exception e){
				log.fatal(e.getMessage());
			}
	    } else if (type == ServiceType.REMOTE){
	    	service = new ServiceBroker(param);
	    }
	    return service;
	}
	
}
