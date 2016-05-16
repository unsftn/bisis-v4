package com.gint.app.bisis4.commandservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class ServiceRemoteFactory {
	
	private static Log log = LogFactory.getLog(ServiceRemoteFactory.class.getName());
	private static Service hibernateservice;
	private static Service hibernatearchiveservice;
	private static Service jdbcservice;

	public static Service createService(int type){
		
	    if (type == CommandType.HIBERNATE){
	    	return getHibernateService();
	    } else if (type == CommandType.JDBC){
	    	return getJdbcService();
	    } else if (type == CommandType.HIBERNATEARCHIVE){
	    	return getHibernateArchiveService();
	    } else if (type == CommandType.NONE){
	    	return getService();
	    }
	    return null;
	}
	
	private static Service getHibernateService(){
		if (hibernateservice == null){
			hibernateservice = new HibernateServiceRemote();
		}
		return hibernateservice;
	}
	
	private static Service getHibernateArchiveService(){
		if (hibernatearchiveservice == null){
			hibernatearchiveservice = new HibernateArchiveServiceRemote();
		}
		return hibernatearchiveservice;
	}
	
	private static Service getJdbcService(){
		if (jdbcservice == null){
			jdbcservice = new JdbcServiceRemote();
		}
		return jdbcservice;
	}
	
	private static Service getService(){
		return null;
	}
	
}
