package com.gint.app.bisis4.commandservice;

public interface ServiceFactory {
	
	public Service createService(int type, String param);

}
