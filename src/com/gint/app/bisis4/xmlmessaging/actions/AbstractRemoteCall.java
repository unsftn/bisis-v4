/*
 *  Created on 2005.4.4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4.xmlmessaging.actions;

import java.util.LinkedHashMap;
import java.util.Vector;

import org.dom4j.Document;

import com.gint.app.bisis4.xmlmessaging.MessagingError;
import com.gint.app.bisis4.xmlmessaging.communication.LibraryServerDesc;
import com.gint.app.bisis4.xmlmessaging.communication.XMLMessagingProcessor;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractRemoteCall {
	
	public abstract void initiateAction(XMLMessagingProcessor callerForm,LinkedHashMap requestParams);
	public abstract Vector processResponse(Document response, LibraryServerDesc lib, MessagingError me);
	
	public String getConvId() {
		return convId;
	}
	
	protected void setConvId(String newConv){
		convId=newConv;
	}
	
	private String convId="";
}
