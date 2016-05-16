package com.gint.app.bisis4.xmlmessaging.communication;

import org.dom4j.Document;

import com.gint.app.bisis4.xmlmessaging.actions.AbstractRemoteCall;

/* */
public interface XMLMessagingProcessor {
	public void setErrors(String text);
	public void processResponse(Document resp,AbstractRemoteCall caller, LibraryServerDesc lib);
}
