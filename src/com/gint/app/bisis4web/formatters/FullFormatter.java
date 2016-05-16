package com.gint.app.bisis4web.formatters;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;

/**
 * Formats records in the full format.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class FullFormatter implements RecordFormatter {

  public String toASCII(Record record, String locale) {
    return RecordFactory.toFullFormat(0, record, false);
  }

  public String toHTML(Record record, String locale) {
    return "<code>" + RecordFactory.toFullFormat(0, record, true) + "</code>";
  }

}
