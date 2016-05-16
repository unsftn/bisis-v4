package com.gint.app.bisis4.commandservice;

public interface Server {
	
	public byte[] receiveCommand(byte[] serializedCommand);

}
