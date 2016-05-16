package com.gint.app.bisis4.librarian;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.gint.util.xml.XMLUtils;

class LibrarianContextBuilder extends DefaultHandler {

  public static LibrarianContext getLibrarianContext(String xml) {
    LibrarianContextBuilder builder = new LibrarianContextBuilder();
    try {
      SAXParser parser = factory.newSAXParser();
      parser.parse(XMLUtils.getInputSourceFromString(xml), builder);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return builder.lc;
  }
  
  public void startElement(String uri, String localName, String qName, Attributes attrs) {
    if (qName.equals("librarian-context")) {
      lc = new LibrarianContext();
    } else if (qName.equals("process-type")) {
      lc.getProcessTypes().add(ProcessTypeCatalog.get(attrs.getValue("name")));
    } else if (qName.equals("default-process-type")) {
      lc.setDefaultProcessType(ProcessTypeCatalog.get(attrs.getValue("name")));
    } else if(qName.equals("prefixes")){
    	lc.setPref1(attrs.getValue("pref1"));
    	lc.setPref2(attrs.getValue("pref2"));
    	lc.setPref3(attrs.getValue("pref3"));
    	lc.setPref4(attrs.getValue("pref4"));
    	lc.setPref5(attrs.getValue("pref5"));
    }
  }
  
  
  
  
  private LibrarianContext lc;
  private static SAXParserFactory factory = SAXParserFactory.newInstance();
}
