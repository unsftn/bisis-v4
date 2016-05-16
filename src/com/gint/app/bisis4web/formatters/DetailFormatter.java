package com.gint.app.bisis4web.formatters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.prefixes.PrefixValue;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4web.views.MakeView;

/**
 * Formats records in the detailed format.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class DetailFormatter implements RecordFormatter {
  
  public DetailFormatter() {
    try {
      ResourceBundle rb = PropertyResourceBundle.getBundle(
          DetailFormatter.class.getName());
      String sPrefList = rb.getString("DISPLAYED_PREFIXES");
      StringTokenizer st = new StringTokenizer(sPrefList, ", \t");
      prefList = new ArrayList();
      while (st.hasMoreTokens()) {
        prefList.add(st.nextToken());
      }
    } catch (Exception ex) {
      log.fatal(ex);
    }
  }

  public String toASCII(Record record, String locale) {
    return MakeView.make(record,"detail",locale);    
  }

  public String toHTML(Record record, String locale) {
    return MakeView.make(record,"detail",locale);    
  }
  
  public String getPrefixDesc(String prefName, List prefixNames) {
    Iterator it = prefixNames.iterator();
    while (it.hasNext()) {
      PrefixValue pv = (PrefixValue)it.next();
      if (pv.prefName.equals(prefName))
        return pv.value;
    }
    return null;
  }

  private List prefList;
  
  private static Log log = LogFactory.getLog(
      "com.gint.app.bisis4web.web.formatters.DetailFormatter");
}
