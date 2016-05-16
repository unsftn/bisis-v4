package com.gint.app.bisis4.commandservice;

import java.net.MalformedURLException;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caucho.burlap.client.BurlapProxyFactory;

public class ServiceBroker implements Service {
	
	private static Log log = LogFactory.getLog(ServiceBroker.class.getName());
	private Server server = null;
	
	
	public ServiceBroker(String param){
		try {
	    //HessianProxyFactory proxyFactory = new HessianProxyFactory();
	    BurlapProxyFactory proxyFactory = new BurlapProxyFactory();
	    server = (Server)proxyFactory.create(Server.class, param);
		} catch (MalformedURLException e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	@Override
	public Command executeCommand(Command command){
		try {
			return (Command)SerializationUtils.deserialize(server.receiveCommand(SerializationUtils.serialize(command)));
		} catch (Exception e){
			return null;
		}
	}	
	
}