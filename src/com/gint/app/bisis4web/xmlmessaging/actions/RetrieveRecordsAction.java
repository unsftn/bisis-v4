package com.gint.app.bisis4web.xmlmessaging.actions;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMNamespace;
import org.dom4j.io.SAXReader;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.serializers.LooseXMLSerializer;
import com.gint.app.bisis4web.web.Settings;
import com.gint.app.bisis4web.xmlmessaging.util.MessageUtil;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RetrieveRecordsAction extends AbstractAction {

	/* (non-Javadoc)
	 * @see com.gint.app.bisis4web.xmlmessaging.actions.AbstractAction#invokeAction()
	 */
	
	private static final String unimarcSchemaURL="http://zare.tmd.uns.ac.rs:8080/bisisXMLM/unimarc.xsd";
	
	public Document invokeAction(Element actionData) {
		Document retVal=null;
		Element params=actionData.element("params");
		//lista XML elements
		List<Element> recIdList =params.elements("recordId");
		Vector<Integer> hitsVector=new Vector<Integer>();
		
		if(recIdList==null){
			//pozvati Errorhandler - nije primljen korektan zahtev 
		}else{
			//pocistiti sve sto u zahtevu nema id
			for(int i=0;i<recIdList.size();i++){
				Element currElem=recIdList.get(i);
				if(currElem.attribute("id")!=null || (!(currElem.attributeValue("id")).equals(""))){
					try{
						int recId=Integer.parseInt(currElem.attributeValue("id"));
						hitsVector.add(recId);
					}catch(NumberFormatException nfe){
						nfe.printStackTrace();
					}
				}
			}
		}
		if(hitsVector.size()==0){
//			pozvati Errorhandler - nije primljen korektan zahtev
		}else{
			try{
				//pokupiti rezultate
				int []hits=new int[hitsVector.size()];
				for(int i=0; i<hits.length; i++)
					hits[i]=hitsVector.get(i);
				Record[] records = Settings.getSettings().getRecMgr().getRecords(hits);
				retVal=prepareResult(records);
			}catch(Exception se){
				se.printStackTrace();
				//throw new DatabaseErrorException("Error while retreiving data from the database\nMessage:\n"+se.getMessage());
			}finally{
				
			}
		}
	    return retVal;
    }
	
	/**
	 * 
	 * @param results
	 * @return
	 */
	private Document prepareResult(Record[] results){
		Document newDoc=null;
		StringWriter sw=new StringWriter();
		sw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
		sw.write("<unimarc>");
		int i=0;
		if(results!=null){
			for (i=0; i<results.length; i++){
				sw.write(LooseXMLSerializer.toLooseXML(results[i]));
			}
		
		}
		sw.write("</unimarc>");
		
		String unimarcXML=(sw.getBuffer()).toString();
		SAXReader builder;
		try{
	        builder=new SAXReader("org.apache.xerces.parsers.SAXParser");
        	Document unimarc=builder.read(new StringReader(unimarcXML));
        	Element content=unimarc.getRootElement();
        	Namespace nmsp=DOMNamespace.get("xsi","http://www.w3.org/2001/XMLSchema-instance");
        	content.add(nmsp);
       		content.addAttribute("noNamespaceSchemaLocation",unimarcSchemaURL);
    		newDoc=MessageUtil.messageSetup("bisis_response","http://www.w3.org/2001/XMLSchema-instance",respSchemaURL,"RetrieveRecordsResponse",content);
        }catch(Exception e){
        	e.printStackTrace();
        }
        
		return newDoc;
	}
}