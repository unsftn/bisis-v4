/*
 * Created on 2004.9.26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4web.xmlmessaging.actions;

import org.dom4j.Document;
import org.dom4j.Element;

import com.gint.app.bisis4web.xmlmessaging.util.ErrorHandler;



/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractAction {
	
	protected String respSchemaURL="";
	protected String reqSchemaURL="";
	protected ErrorHandler errorHandler;
	protected String responseAction=null;
	protected boolean compression=false;
	
	
	/**
	 * 
	 * @param actionData - XML Element representing received data needed to perform specified action 
	 * @return XML Document representing server response
	 */
	public abstract Document invokeAction(Element actionData);
	

	/**
	 * @return Returns the errorHandler.
	 */
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}
	/**
	 * @param errorHandler The errorHandler to set.
	 */
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}
	/**
	 * @return Returns the reqSchemaURL.
	 */
	public String getReqSchemaURL() {
		return reqSchemaURL;
	}
	/**
	 * @param reqSchemaURL The reqSchemaURL to set.
	 */
	public void setReqSchemaURL(String reqSchemaURL) {
		this.reqSchemaURL = reqSchemaURL;
	}
	/**
	 * @return Returns the respSchemaURL.
	 */
	public String getRespSchemaURL() {
		return respSchemaURL;
	}
	/**
	 * @param respSchemaURL The respSchemaURL to set.
	 */
	public void setRespSchemaURL(String respSchemaURL) {
		this.respSchemaURL = respSchemaURL;
	}
	/**
	 * @return Returns the responseAction.
	 */
	public String getResponseAction() {
		return responseAction;
	}
	/**
	 * @param responseAction The responseAction to set.
	 */
	public void setResponseAction(String responseAction) {
		this.responseAction = responseAction;
	}
}
