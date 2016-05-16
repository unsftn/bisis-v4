/*
 * Created on 2004.9.26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4web.web.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.xml.messaging.JAXMServlet;
import javax.xml.messaging.ReqRespListener;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gint.app.bisis4web.xmlmessaging.actions.AbstractAction;
import com.gint.app.bisis4web.xmlmessaging.util.ActionResponse;
import com.gint.app.bisis4web.xmlmessaging.util.ErrorHandler;
import com.gint.app.bisis4web.xmlmessaging.util.MessageUtil;
import com.gint.app.bisis4web.xmlmessaging.util.MessagingEnvironment;
import com.gint.app.bisis4web.xmlmessaging.util.SOAPUtilServer;

/**
 * @author mikiz
 *
 * Servlet Class
 *
 * @web.servlet              name="ExchangeServlet"
 *                           display-name="ExchangeServlet"
 *                           description="Servlet for data exchange"
 * @web.servlet-mapping      url-pattern="/ExchangeServlet"
 * 
*/

@SuppressWarnings("serial")
public class ExchangeServlet extends JAXMServlet implements ReqRespListener {
	private boolean zipIt=false;
	
	/* (non-Javadoc)
	 * @see javax.xml.messaging.ReqRespListener#onMessage(javax.xml.soap.SOAPMessage)
	 */
	
	public SOAPMessage onMessage(SOAPMessage message) {
	   zipIt=false;
       SOAPMessage retMsg = null;
       Document doc=null;
       Document returned=null;
       ErrorHandler errorHandler=new ErrorHandler(MessagingEnvironment.getReqSchemaURL(),MessagingEnvironment.getRespSchemaURL());
       AbstractAction ca=null;
       String action;
       LinkedHashMap actionMapping=MessagingEnvironment.getActionMapping();
       LinkedHashMap responseMapping=MessagingEnvironment.getResponseMapping();
       
       try {
    	   if(MessagingEnvironment.DEBUG==1)
        	   System.out.println("\nStarting SOAP Parsing\n");
           
           SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
           SOAPHeader header = envelope.getHeader();

           SOAPElement ebHeader = SOAPUtilServer.getChild(envelope, header, "MessageHeader");

           SOAPElement ebHeaderTo = SOAPUtilServer.getChild(envelope, ebHeader, "To");
           SOAPElement ebHeaderToParty = SOAPUtilServer.getChild(envelope, ebHeaderTo, "PartyId");
           String to = ebHeaderToParty.getValue();
           SOAPElement ebHeaderFrom = SOAPUtilServer.getChild(envelope, ebHeader, "From");
           SOAPElement ebHeaderFromParty = SOAPUtilServer.getChild(envelope, ebHeaderFrom, "PartyId");
           String from = ebHeaderFromParty.getValue();
           SOAPElement ebHeaderCPAId = SOAPUtilServer.getChild(envelope, ebHeader, "CPAId");
           String cpaid = ebHeaderCPAId.getValue();

           SOAPElement ebHeaderConversationId =SOAPUtilServer.getChild(envelope, ebHeader, "ConversationId");
           String convid = ebHeaderConversationId.getValue();
           
           SOAPElement actionRequested =SOAPUtilServer.getChild(envelope, ebHeader, "Action");
           action = actionRequested.getValue();
           
          //create object for handling specific action
           if(MessagingEnvironment.DEBUG==1)
        	   System.out.println("\n"+action+"\n");
		   try{
           		Class currAction=Class.forName((String)actionMapping.get(action));
           		ca = (AbstractAction)currAction.newInstance();
           		ca.setErrorHandler(errorHandler);
           		ca.setReqSchemaURL(MessagingEnvironment.getReqSchemaURL());
           		ca.setRespSchemaURL(MessagingEnvironment.getRespSchemaURL());
           		ca.setResponseAction(((ActionResponse)responseMapping.get(action)).getResponseName());
		   }catch(Exception ce){
		   		returned=errorHandler.getErrorElement("Unknown Action Error","UnknownRequestResponse");
		   }
           if(ca!=null){
           		try{
           			doc=SOAPUtilServer.extractDocument(message,this);
           			Element root = doc.getRootElement();
                	Element calledAction = root.element("action");
                	returned=ca.invokeAction(calledAction);
           		}catch(SOAPException se){
           			returned=errorHandler.getErrorElement("Document Parsing Error",ca.getResponseAction());
           		}catch(DocumentException je){
           			returned=errorHandler.getErrorElement("Document Parsing Error",ca.getResponseAction());
           		}catch(IOException ie){
           			returned=errorHandler.getErrorElement("Internal Server Error",ca.getResponseAction());
           		}catch(Exception e){
           			e.printStackTrace();
           		}
           }

           returned=MessageUtil.setHeaders(returned,to,from,convid);
           retMsg=SOAPUtilServer.prepareSOAPMessage(returned,action,to,from,convid,zipIt);
           
       }catch(Exception e){
       		//do nothing for now
       }
       return retMsg;
	}
	/**
	 * @return Returns the zipIt.
	 */
	public boolean isZipIt() {
		return zipIt;
	}
	/**
	 * @param zipIt The zipIt to set.
	 */
	public void setZipIt(boolean zipIt) {
		this.zipIt = zipIt;
	}
}
