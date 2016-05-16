/*
 * Created on Sep 24, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4web.xmlmessaging.util;

/**
 * @author Miki
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.util.List;

import javax.lang.model.util.Elements;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.dom4j.dom.DOMNamespace;

public class MessageUtil {
	
   public static Document setHeaders(Document contentDoc,String from, String to, String convId){
	Element newHeader=new DOMElement("header");
    Element retFrom=new DOMElement("from");
    retFrom.setText(from);
    newHeader.add(retFrom);
    Element retTo=new DOMElement("to");
    retTo.setText(to);
    newHeader.add(retTo);
    Element retTimestamp=new DOMElement("datestamp");
    retTimestamp.setText(""+new java.util.Date().toGMTString());
    Element convid=new DOMElement("conversation_id");
    convid.setText(convId);
    newHeader.add(convid);
    DOMElement newroot=(DOMElement)contentDoc.getRootElement();
    List<Element> els=newroot.elements();
    els.add(0, newHeader);
    return contentDoc;
   }
     
   public static Document messageSetup(String rootElement,String namespace,String schemaURL, String actionName, Element content) {
   		Namespace nmsp=DOMNamespace.get("xsi",namespace);
   		Document newDoc=new DOMDocument();
   		Element resp=new DOMElement(rootElement);
		Element action=new DOMElement("action");
		action.addAttribute("name",actionName);
		action.add(content);
		
		resp.addAttribute("xsi:noNamespaceSchemaLocation",schemaURL);
		resp.add(nmsp);
        resp.add(action);
		newDoc.setRootElement(resp);
		
	    return newDoc;
   }
   
   public static String replaceCharsInUnicode(String input){
	String retVal=new String(input);
	if(retVal.indexOf('\007')!=-1){
		String[] splited=retVal.split("\007");
		for(int i=0;i<splited.length;i++){
			if(i==0)retVal=splited[0];
			else retVal+=splited[i];
		}
	}
	//retVal=retVal.replace('\007','@');
	retVal=retVal.replace('\037','$');
	retVal=retVal.replace('\036','#');
	retVal=retVal.replace('\035','_');
	return retVal;
}
}