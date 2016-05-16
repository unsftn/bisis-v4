/*
 * Created on 2004.10.24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4web.xmlmessaging.util;

import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;


/**
 * @author mikiz
 *
 * This class is intended to provide one central point of Error Handling for complete package of XML Messaging
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class ErrorHandler {
	
		private static LinkedHashMap errors;
		private  String reqSchemaURL;
		private  String respSchemaURL;
		
		static{
			errors=new LinkedHashMap();
			try{
				ResourceBundle rb = ResourceReader.getRB();
				if(rb!=null){
					int i=0;
					String action;
					while((action=rb.getString("error"+i))!=null){
						String []errorDef=action.split("/");
						if(errorDef.length==4){
							int level=0;
							try{
								level=Integer.parseInt(errorDef[3]);
							}catch(NumberFormatException nfe){
								//do nothing - report to log
							}
							Error ne=new Error(errorDef[0],errorDef[1],errorDef[2],level);
							errors.put(errorDef[1],ne);
						}
						i++;
					}
					
				}
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		
	/**
	 * @param reqSchemaURL
	 * @param respSchemaURL
	 */
	public ErrorHandler() {
		super();
		this.reqSchemaURL = MessagingEnvironment.getReqSchemaURL();
		this.respSchemaURL = MessagingEnvironment.getRespSchemaURL();
	}
	
	/**
	 * @param reqSchemaURL
	 * @param respSchemaURL
	 */
	public ErrorHandler(String reqSchemaURL, String respSchemaURL) {
		super();
		this.reqSchemaURL = reqSchemaURL;
		this.respSchemaURL = respSchemaURL;
	}
		
	public Document getErrorElement(String errorCode, String action){
			Document retVal=null;
			Element content=new DOMElement("error");
			Error thisError=null;
			if((thisError=(Error)errors.get(errorCode))==null){
				thisError=(Error)errors.get("Unspecified server error");
			}
			if(thisError==null){
				thisError=new Error("Unspecified server error","An unknown error","Server is unable to perform action due to unknown internal error",0); 
			}
			content.addAttribute("code",thisError.getCode());
			content.addAttribute("level",""+thisError.getLevel());
			content.addAttribute("severity",thisError.getLevelDesc());
			content.setText(thisError.getDescription());
			retVal=MessageUtil.messageSetup("bisis_response","http://www.w3.org/2001/XMLSchema-instance",respSchemaURL,action,content);
			return retVal;
		}
	
	public Document getErrorElement(String errorCode, String action, String descriptionAdd){
		Document retVal=null;
		Element content=new DOMElement("error");
		Error thisError=null;
		if((thisError=(Error)errors.get(errorCode))==null){
			thisError=(Error)errors.get("Unspecified server error");
		}
		if(thisError==null){
			thisError=new Error("Unspecified server error","An unknown error","Server is unable to perform action due to unknown internal error",0); 
		}
		content.addAttribute("code",thisError.getCode());
		content.addAttribute("level",""+thisError.getLevel());
		content.addAttribute("severity",thisError.getLevelDesc());
		content.setText(thisError.getDescription()+descriptionAdd);
		retVal=MessageUtil.messageSetup("bisis_response","http://www.w3.org/2001/XMLSchema-instance",respSchemaURL,action,content);
		return retVal;
	}
}
