/*
 * Created on 2004.11.10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4web.xmlmessaging.util;

import java.util.LinkedHashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MessagingEnvironment {
	private static String reqSchemaURL="http://bisis.uns.ac.rs/schemas/bisisXMLM/bisis_req.xsd";
	private static String respSchemaURL="http://bisis.uns.ac.rs/schemas/bisisXMLM/bisis_resp.xsd";
	private static String unimarcSchema="http://bisis.uns.ac.rs/schemas/bisisXMLM/unimarc.xsd";
	private static String thisLibNode="localhost";
	private static LinkedHashMap<String, String> actionMapping=null;
	private static LinkedHashMap<String, ActionResponse> responseMapping=null;
	public static final String CPAID="http://bisis.uns.ac.rs/schemas";
	public static final int SEARCHREQUEST=1;
	public static final int RETRIEVEREQUEST=2;
	public static final int GETPREFIXESREQUEST=3;
	public static final int LISTSERVERSREQUEST=4;
	public static final int DEBUG=0; 
	
	static{
		ResourceBundle rb=ResourceReader.getRB();
		
		//load data about available servers
		//load data about htis node
		try{
			thisLibNode=rb.getString("thisLibNode");
		}catch(MissingResourceException mre){
			//do nothing
		}
		
		//load data about schemas
		try{
			reqSchemaURL=rb.getString("requestSchema");
			respSchemaURL=rb.getString("responseSchema");
		}catch(MissingResourceException mre){
			//mre.printStackTrace();
		}
		
	}
	
	static{
		actionMapping=new LinkedHashMap<String, String>();
		responseMapping=new LinkedHashMap<String, ActionResponse>();
		try{
			ResourceBundle rb = ResourceReader.getRB();
			if(rb!=null){
				int i=1;
				String action;
				while((action=rb.getString("action"+i))!=null){
					String []actionDef=action.split("/");
					actionMapping.put(actionDef[0],actionDef[1]);
					ActionResponse resp=new ActionResponse(actionDef[2],actionDef[1],actionDef[3]);
					responseMapping.put(actionDef[0],resp);
					i++;
				}
				
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * @return Returns the actionMapping.
	 */
	public static LinkedHashMap getActionMapping() {
		return actionMapping;
	}
	/**
	 * @return Returns the responseMapping.
	 */
	public static LinkedHashMap getResponseMapping() {
		return responseMapping;
	}
	/**
	 * @param reqSchemaURL The reqSchemaURL to set.
	 */
	public static void setReqSchemaURL(String reqSchemaURL) {
		MessagingEnvironment.reqSchemaURL = reqSchemaURL;
	}
	/**
	 * @param respSchemaURL The respSchemaURL to set.
	 */
	public static void setRespSchemaURL(String respSchemaURL) {
		MessagingEnvironment.respSchemaURL = respSchemaURL;
	}

	
	
	/**
	 * @return Returns the reqSchemaURL.
	 */
	public static String getReqSchemaURL() {
		return reqSchemaURL;
	}
	/**
	 * @return Returns the respSchemaURL.
	 */
	public static String getRespSchemaURL() {
		return respSchemaURL;
	}
	/**
	 * @return Returns the thisLibNode.
	 */
	public static String getThisLibNode() {
		return thisLibNode;
	}
	public static String getUnimarcSchema() {
		return unimarcSchema;
	}
}
