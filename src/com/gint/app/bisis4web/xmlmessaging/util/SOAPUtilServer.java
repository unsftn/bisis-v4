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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import com.gint.app.bisis4web.web.servlets.ExchangeServlet;

public class SOAPUtilServer {
     public static final String NS_PREFIX_EB = "eb";
     public static final String NS_URI_EB =
        "http://www.ebxml.org/namespaces/messageHeader";
     public static final String NS_PREFIX_XLINK = "xlink";
     public static final String NS_URI_XLINK =
        "http://www.w3.org/1999/xlink";

     public static void ebSetup(SOAPEnvelope envelope,
               String frparty,
               String toparty,
               String cpaId,
               String conversId,
               String service,
               String action
               ) {
          try {
              SOAPHeader header = envelope.getHeader();

        SOAPHeaderElement ebHeader = header.addHeaderElement(
        envelope.createName("MessageHeader", NS_PREFIX_EB, NS_URI_EB));
        ebHeader.setMustUnderstand(true);
        ebHeader.addAttribute(envelope.createName("version"), "1.0");

         SOAPElement ebFrom = ebHeader.addChildElement(
          envelope.createName("From", NS_PREFIX_EB, NS_URI_EB));
        SOAPElement ebPartyFrom = ebFrom.addChildElement(
          envelope.createName("PartyId", NS_PREFIX_EB, NS_URI_EB));
        ebPartyFrom.addTextNode(frparty);

        SOAPElement ebTo = ebHeader.addChildElement(
          envelope.createName("To", NS_PREFIX_EB, NS_URI_EB));
        SOAPElement ebPartyTo = ebTo.addChildElement(
          envelope.createName("PartyId", NS_PREFIX_EB, NS_URI_EB));
        ebPartyTo.addTextNode(toparty);

        SOAPElement ebCPAId = ebHeader.addChildElement(
          envelope.createName("CPAId", NS_PREFIX_EB, NS_URI_EB));
        ebCPAId.addTextNode(cpaId);

        SOAPElement ebConv = ebHeader.addChildElement(
          envelope.createName("ConversationId"
             , NS_PREFIX_EB, NS_URI_EB));
        ebConv.addTextNode(conversId);

        SOAPElement ebService = ebHeader.addChildElement(
          envelope.createName("Service", NS_PREFIX_EB, NS_URI_EB));
        ebService.addTextNode(service);

        SOAPElement ebAction = ebHeader.addChildElement(
          envelope.createName("Action", NS_PREFIX_EB, NS_URI_EB));
        ebAction.addTextNode(action);

        SOAPElement ebMesData = ebHeader.addChildElement(
          envelope.createName("MessageData", NS_PREFIX_EB, NS_URI_EB));

        SOAPElement ebMesId = ebMesData.addChildElement(
          envelope.createName("MessageId", NS_PREFIX_EB, NS_URI_EB));
        ebMesId.addTextNode(System.currentTimeMillis() + "");

        SOAPElement ebMesTime = ebMesData.addChildElement(
          envelope.createName("Timestamp", NS_PREFIX_EB, NS_URI_EB));
        ebMesTime.addTextNode(new Date().toString());

      } catch(Exception e) {
        e.printStackTrace();
      }
   }


   public static SOAPElement getChild
      (SOAPEnvelope envelope, SOAPElement elem, String child)
      throws SOAPException {
      Iterator iter =
        elem.getChildElements(envelope.createName
           (child, NS_PREFIX_EB, NS_URI_EB));
      if (iter.hasNext()) {
        SOAPElement e = (SOAPElement) iter.next();
        return e;
      }
      else
        return null;
   }
   
   public static SOAPMessage prepareSOAPMessage(Document payloadDoc, String action, String from, String to, String convId, boolean compressData){
 	SOAPMessage msg=null;
 	try{
 	 ActionResponse ra=(ActionResponse)(MessagingEnvironment.getResponseMapping()).get(action);
 	 System.out.println("response: "+ra.getActionName()+" : "+ra.getResponseName());
 	 MessageFactory mf = MessageFactory.newInstance();
     msg = mf.createMessage();
     
     SOAPPart sp = msg.getSOAPPart();

     //create the header container
     SOAPEnvelope envelope = sp.getEnvelope();

     ebSetup(envelope, from, to, MessagingEnvironment.CPAID, convId, "urn:services:"+action, ra.getResponseName());
     SOAPBody body = envelope.getBody();
    
     SOAPElement manifest = body.addBodyElement(
    		envelope.createName("Manifest",
            NS_PREFIX_EB, NS_URI_EB));

     SOAPElement reference =
      manifest.addChildElement(
      		envelope.createName("Reference",
            NS_PREFIX_EB, NS_URI_EB));
     reference.addAttribute(
     		envelope.createName("href",
        NS_PREFIX_XLINK, NS_URI_XLINK), "requestpart1");
     reference.addAttribute(
    		envelope.createName("type",
        NS_PREFIX_XLINK, NS_URI_XLINK), "simple");

     SOAPElement description =
      reference.addChildElement(
      		envelope.createName("Description",
            NS_PREFIX_EB, NS_URI_EB));
     description.addTextNode(ra.getResponseDescription());
     if(MessagingEnvironment.DEBUG==1)
    	 System.out.println("RESPONSE\nfrom:"+from+"\nto:"+to+"\nurn:services:"+action+"\naction:"+ra.getResponseName());
     StringWriter sw = new StringWriter();
     try {
          XMLWriter outputter = new XMLWriter(sw);
          outputter.write(payloadDoc);
       } catch (java.io.IOException e) {
        e.printStackTrace();
     }
     
     AttachmentPart transferAttach;
     if(!compressData){
         transferAttach = msg.createAttachmentPart( sw.toString(), "text/plain; charset=UTF-8");
         transferAttach.setContentType("text/plain; charset=UTF-8");
         transferAttach.setContentId("requestpart1");
         msg.addAttachmentPart(transferAttach);
     }else{
     	//compressData
    	if(MessagingEnvironment.DEBUG==1)
    		System.out.println("Compressing output");
     	transferAttach=msg.createAttachmentPart();
     	byte[] zippedContent=GzipUtility.zipText(sw.toString());
     	//System.out.println("Unzipped:\n+"+GzipUtility.unzipText(zippedContent));
     	DataHandler dh=new DataHandler(new ByteArrayDataSource(zippedContent, "application/x-gzip"));
     	transferAttach.setDataHandler(dh);
     	transferAttach.setContentId("requestpart1");
        msg.addAttachmentPart(transferAttach);
     }
     
 	}catch(Exception e){
 		e.printStackTrace();
 	}
     return msg;
   }
   
   public static Document extractDocument(SOAPMessage message, ExchangeServlet caller)
   	throws SAXException,DocumentException, SOAPException, IOException {
 	Document doc=null;
 	if (message != null) {
 		    Iterator iter = message.getAttachments();
            AttachmentPart af = (AttachmentPart) iter.next();
            String attcontent=null;
            if(af.getContentType().equalsIgnoreCase("application/x-gzip")){
            	
            	java.io.InputStream zippedStream = (java.io.InputStream)af.getContent();
            	attcontent=GzipUtility.unzipFromStream(zippedStream);
            	attcontent.trim();
            	caller.setZipIt(true);
            	if(MessagingEnvironment.DEBUG==1)
            		System.out.println("Received zipped content "+attcontent);
            }else{
            	InputStream in=af.getRawContent();
    			BufferedReader br=new BufferedReader(new InputStreamReader(in,"UTF-8"));
            	String temp;
            	attcontent=new String();
            	while((temp=br.readLine())!=null)
            		attcontent+=temp;
            	br.close();
            	if(MessagingEnvironment.DEBUG==1)
            		System.out.println("received: "+attcontent);
            }
            SAXReader builder;
            builder=new SAXReader("org.apache.xerces.parsers.SAXParser");
            builder.setFeature("http://xml.org/sax/features/validation",  false);
            builder.setFeature("http://apache.org/xml/features/validation/schema",  false);
            builder.setFeature("http://apache.org/xml/features/validation/schema-full-checking", false);
            builder.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",MessagingEnvironment.getReqSchemaURL());
            doc = builder.read(new StringReader(attcontent));
     }
 	return doc;
 }
}