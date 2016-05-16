package com.gint.app.bisis4.reports;

import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;

public class XMLTransformer {
	

public static Node transform(Node sourceNode, InputStream in) {
	
   DOMResult domResult = new DOMResult();
   try {
	   TransformerFactory tFactory=TransformerFactory.newInstance();
	   
	   Transformer transformer = tFactory.newTransformer(new StreamSource(in));
	   DOMSource domSource = new DOMSource(sourceNode);
	   transformer.transform(domSource, domResult);
  
   } catch (NullPointerException e){e.printStackTrace();}
     catch (TransformerConfigurationException e) {} catch (TransformerException e) {
		e.printStackTrace();
	}
     return domResult.getNode().getFirstChild();
     
  
 }

}
