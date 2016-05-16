package com.gint.app.bisis4web.formatters;

import java.util.HashMap;

import com.gint.app.bisis4.records.Record;

/**
 * Represents a record formatter.
 *
 * @author mbranko@uns.ns.ac.yu
 */
public interface RecordFormatter {
  
  public String toASCII(Record record, String locale);
  public String toHTML(Record record, String locale);

}
