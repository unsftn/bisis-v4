package com.gint.app.bisis4.commandservice;

import org.apache.commons.lang.SerializationUtils;

import com.caucho.burlap.server.BurlapServlet;

public class ServerImpl extends BurlapServlet implements Server {

	public byte[] receiveCommand(byte[] serializedCommand){
		Command command = (Command)SerializationUtils.deserialize(serializedCommand);
		Service service = ServiceRemoteFactory.createService(command.getType());
		command = service.executeCommand(command);
		return SerializationUtils.serialize(command);
	}
}
