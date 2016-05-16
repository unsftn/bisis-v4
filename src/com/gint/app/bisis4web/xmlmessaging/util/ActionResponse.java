/*
 * Created on 2004.12.1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4web.xmlmessaging.util;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ActionResponse {
	
	private String responseName;
	private String actionName;
	private String responseDescription;
	
	/**
	 * @param responseName
	 * @param actionName
	 * @param responseDescription
	 */
	public ActionResponse(String responseName, String actionName,
			String responseDescription) {
		super();
		this.responseName = responseName;
		this.actionName = actionName;
		this.responseDescription = responseDescription;
	}
	/**
	 * 
	 */
	public ActionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the actionName.
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * @param actionName The actionName to set.
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * @return Returns the responseDescription.
	 */
	public String getResponseDescription() {
		return responseDescription;
	}
	/**
	 * @param responseDescription The responseDescription to set.
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
	/**
	 * @return Returns the responseName.
	 */
	public String getResponseName() {
		return responseName;
	}
	/**
	 * @param responseName The responseName to set.
	 */
	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}
}
