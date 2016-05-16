package com.gint.app.bisis4web.xmlmessaging.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Vector;

import org.apache.lucene.search.Query;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMCDATA;
import org.dom4j.dom.DOMElement;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.utils.QueryUtils;
import com.gint.app.bisis4web.formatters.BriefFormatter;
import com.gint.app.bisis4web.formatters.RecordFormatterFactory;
import com.gint.app.bisis4web.web.Settings;
import com.gint.app.bisis4web.xmlmessaging.util.MessageUtil;
import com.gint.app.bisis4web.xmlmessaging.util.MessagingEnvironment;
import com.gint.util.string.UnimarcConverter;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PerformSearchAction extends AbstractAction {

	private final int MAXHITS=1000;
	
	
	public Document invokeAction(Element actionData) {
		Document retVal=null;
		Element params=actionData.element("params");
		//list of XML elements
		List<Element> prefList=null;
		List<Element> opList=null;
		prefList=params.elements("prefix");
		opList=params.elements("operator");
		
		Vector<Integer> badIndexes=new Vector<Integer>();
		if(prefList==null || (prefList.size()>1 && (opList==null || opList.size()!=prefList.size()-1)) ){
			System.out.println("\nNema korektnih prefiksa!\n");
			return errorHandler.getErrorElement("Search Error","PerformSearchResponse");
		}else{
			for(int i=0;i<prefList.size();i++){
				Element currElem=prefList.get(i);
				if(currElem.attribute("name")==null || (currElem.attributeValue("name")).equals("") || currElem.getText()==null || currElem.getText().equals(""))
					badIndexes.add(i);
				if(i>0){
					Element currOperator=(Element)opList.get(i-1);
					if(currOperator.attribute("type")==null || currOperator.attributeValue("type").equals("")){
						badIndexes.add(i);
					}
				}
			}
			if(badIndexes.size()>0){
				for(int i=0;i<badIndexes.size();i++){
					int toRemove=badIndexes.get(i);
					prefList.remove(toRemove);
					toRemove--;
					if(toRemove>=0)
						opList.remove(toRemove);
				}
			}
		}

		int maxValidFields=0;
		
		//check prefixes
		if(prefList==null || prefList.size()==0) {
			//postaviti ErrorHandler
		}else{
			
			String []prefixes=new String[5];
			String []values=new String[5];
			String []operators=new String[4];
			
			maxValidFields=Math.min(prefList.size(), opList.size()+1);
			
			for(int i=0;i<5;i++){
				if(i<maxValidFields){
					Element currEl=prefList.get(i);
					prefixes[i]=currEl.attributeValue("name");
					if (MessagingEnvironment.DEBUG == 1)
					  System.out.println("search prefix: " + prefixes[i]);
					try{
						values[i]=URLDecoder.decode((String)currEl.getText(), "UTF-8");
	          if (MessagingEnvironment.DEBUG == 1)
	            System.out.println("search value: " + values[i]);
					}catch(UnsupportedEncodingException uee){
						return errorHandler.getErrorElement("Search Error","PerformSearchResponse");
					}
					if(i>0){
						operators[i-1]=(opList.get(i-1)).attributeValue("type");
	          if (MessagingEnvironment.DEBUG == 1)
	            System.out.println("search operator: " + operators[i-1]);
					}
					
					if(values[i]!=null)
						values[i].trim();
				}else{
					prefixes[i]="";
					values[i]="";
					if(i>0){
						operators[i-1]="";
					}	
				}
			}
			
			//System.out.println("posle for maxValidFields="+maxValidFields);
			
			
			Vector resultsDocIds=null;
			Query query=QueryUtils.makeLuceneAPIQuery( 
					prefixes[0], operators[0], values[0], 
					prefixes[1], operators[1], values[1], 
					prefixes[2], operators[2], values[2], 
					prefixes[3], operators[3], values[3], 
					prefixes[4], values[4]);
			
      if (MessagingEnvironment.DEBUG == 1)
        System.out.println("Lucene Query: " + query);

			
			if (query == null) {
				return errorHandler.getErrorElement("Search Error","PerformSearchResponse");
		    }
		    
		    int[] hits;
		    if (Settings.getSettings().getStaticFilter() != null) {
		    	if(MessagingEnvironment.DEBUG==1)
		    		System.out.println("\n calling select3 type of query\n");
		    	hits = Settings.getSettings().getRecMgr().select3(query, QueryUtils.getQueryFilter(Settings.getSettings().getStaticFilter()), null);
		    }else{
		    	if(MessagingEnvironment.DEBUG==1)
		    		System.out.println("\n calling select2 type of query\n");
		    	hits = Settings.getSettings().getRecMgr().select2(query, null);
		    }
		    
		    if (hits != null){
		    	if(hits.length>MAXHITS){
		    		retVal=errorHandler.getErrorElement("Too many results error","PerformSearchResponse","\n\tMAX ALLOWED: "+MAXHITS);
				}else{
					if(MessagingEnvironment.DEBUG==1)
			    		System.out.println("\n calling getRecords\n");
			    	
					Record[] records = Settings.getSettings().getRecMgr().getRecords(hits);
					retVal=prepareResult(records);
				}
		    }
		}
	    return retVal;
    }
	
	/**
	 * Generates XML document containing brief representation of records matching the query
	 * @param hits - Array of records matching the query 
	 * @return Returns XML (payload) document to be inserted into SOAP message 
	 */
	private Document prepareResult(Record []hits){
		Element content=new DOMElement("content");
		BriefFormatter rf=(BriefFormatter)RecordFormatterFactory.getFormatter(RecordFormatterFactory.FORMAT_BRIEF);
		content.addAttribute("count",""+0);
		int counter=0;
		UnimarcConverter conv=new UnimarcConverter();
		if(hits!=null){
			for (int i=0;i<hits.length;i++){
				Record oneHit = hits[i];
				if(oneHit!=null){
					Element recBrief=new DOMElement("record_brief");
					recBrief.addAttribute("recId",""+oneHit.getRecordID());
					
					//add author element
					Element author = new DOMElement("author");
					author.add(new DOMCDATA(getElementContent(rf.getAuthor(oneHit, "sr").toString(),conv)));
					recBrief.add(author);
				
					//add title element
					Element title = new DOMElement("title");
					title.add(new DOMCDATA(getElementContent(rf.getTitle(oneHit),conv)));
					recBrief.add(title);
				
					//add publisher element
					Element publisher = new DOMElement("publisher");
					publisher.add(new DOMCDATA(getElementContent(rf.getTitle(oneHit),conv)));
					recBrief.add(publisher);
					
					//add publication year element
					Element pubYear = new DOMElement("publication_year");
					pubYear.add(new DOMCDATA(rf.getYear(oneHit)));
					recBrief.add(pubYear);
					
					//add language year element
					Element language = new DOMElement("language");
					language.add(new DOMCDATA(rf.getLanguageOfOriginal(oneHit)));
					recBrief.add(language);
					
					//add language year element
					Element country = new DOMElement("country");
					country.add(new DOMCDATA(rf.getCountry(oneHit)));
					recBrief.add(country);
					content.add(recBrief);
					counter++;
				}else{
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("Null objekat za record");
				}
			}
			content.addAttribute("count",""+counter);
		}
		Document newDoc = MessageUtil.messageSetup("bisis_response","http://www.w3.org/2001/XMLSchema-instance",respSchemaURL,"PerformSearchResponse",content);
		return newDoc;
	}
	
	/**
	 * 
	 * @param in
	 * @param conv
	 * @return String cleared of all non-XML-valid characters 
	 */
	private String getElementContent(String in, UnimarcConverter conv){
		String elementValue="";
		elementValue=MessageUtil.replaceCharsInUnicode(conv.Unimarc2Unicode(in));
		return elementValue;
	}
}