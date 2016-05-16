package com.gint.app.bisis4web.formatters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4web.views.MakeView;

/**
 * Formats records in the brief format.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class BriefFormatter implements RecordFormatter {
  
  public BriefFormatter() {
    etal = new HashMap();
    etal.put("sr", " i dr");
    etal.put("en", " et al");
    etal.put("hu", " et al");
  }
  
  public String toASCII(Record rec, String locale) {
    StringBuffer retVal = new StringBuffer();
    retVal.append(getAuthor(rec, locale));
    if (retVal.length() > 0)
      retVal.append(". ");
    retVal.append(getTitle(rec));
    retVal.append(". ");
    String pub = getPublisher(rec);
    if (pub.length() > 0) {
      retVal.append(pub);
      retVal.append(", ");
    }
    String place = getPlace(rec);
    if (place.length() > 0) {
      retVal.append(place);
      retVal.append(", ");
    }
    String year = getYear(rec);
    if (year.length() > 0) {
      retVal.append(year);
      retVal.append(".");
    }
    String invNums = getInvNums(rec);
    if (invNums.length() > 0) {
      retVal.append("\n  inv: ");
      retVal.append(invNums);
    }
    return retVal.toString();
  }
  
  public String toHTML(Record rec, String locale) {
    return MakeView.make(rec,"brief",locale);
  }
  
  public StringBuffer getAuthor(Record rec, String locale) {
    StringBuffer retVal = new StringBuffer();
    List f700 = rec.getFields("700");
    if (f700.size() > 0) {
      Field f1 = (Field)f700.get(0);
      if (f1.getInd2() == '1') {
        Subfield sfa = f1.getSubfield('a');
        Subfield sfb = f1.getSubfield('b');
        if (sfb != null)
          retVal.append(sfb.getContent());
        if (sfa != null) {
          if (retVal.length() > 0)
            retVal.append(' ');
          retVal.append(sfa.getContent());
        }
      } else {
        Subfield sfa = f1.getSubfield('a');
        if (sfa != null)
          retVal.append(sfa.getContent());
      }
      if (f700.size() > 1) {
        appendEtAl(retVal, locale);
        return retVal;
      }
    }
    List f701 = rec.getFields("701");
    if (f701.size() > 0) {
      if (retVal.length() > 0) {
        appendEtAl(retVal, locale);
        return retVal;
      }
      Field f1 = (Field)f701.get(0);
      if (f1.getInd2() == '1') {
        Subfield sfa = f1.getSubfield('a');
        Subfield sfb = f1.getSubfield('b');
        if (sfb != null)
          retVal.append(sfb.getContent());
        if (sfa != null) {
          if (retVal.length() > 0)
            retVal.append(' ');
          retVal.append(sfa.getContent());
        }
      } else {
        Subfield sfa = f1.getSubfield('a');
        if (sfa != null)
          retVal.append(sfa.getContent());
      }
      if (f701.size() > 1) {
        appendEtAl(retVal, locale);
        return retVal;
      }
    }
    List f702 = rec.getFields("702");
    if (f702.size() > 0) {
      if (retVal.length() > 0) {
        appendEtAl(retVal, locale);
        return retVal;
      }
      Field f1 = (Field)f702.get(0);
      if (f1.getInd2() == '1') {
        Subfield sfa = f1.getSubfield('a');
        Subfield sfb = f1.getSubfield('b');
        if (sfb != null)
          retVal.append(sfb.getContent());
        if (sfa != null) {
          if (retVal.length() > 0)
            retVal.append(' ');
          retVal.append(sfa.getContent());
        }
      } else {
        Subfield sfa = f1.getSubfield('a');
        if (sfa != null)
          retVal.append(sfa.getContent());
      }
      if (f702.size() > 1) {
        appendEtAl(retVal, locale);
        return retVal;
      }
    }
    return retVal;
  }
  
  public String getTitle(Record rec) {
    Field f200 = rec.getField("200");
    if (f200 != null) {
      Subfield sfa = f200.getSubfield('a');
      if (sfa != null)
        return sfa.getContent();
    }
    return "";
  }
  
  public String getPublisher(Record rec) {
    Field f210 = rec.getField("210");
    if (f210 != null) {
      Subfield sfc = f210.getSubfield('c');
      if (sfc != null)
        return sfc.getContent();
    }
    return "";
  }
  
  public String getYear(Record rec) {
    Field f100 = rec.getField("100");
    if (f100 != null) {
      Subfield sfc = f100.getSubfield('c');
      if (sfc != null)
        return sfc.getContent();
    }
    return "";
  }

  public String getPlace(Record rec) {
    Field f210 = rec.getField("210");
    if (f210 != null) {
      Subfield sfa = f210.getSubfield('a');
      if (sfa != null)
        return sfa.getContent();
    }
    return "";
  }
  
  public String getCountry(Record rec) {
    Field f102 = rec.getField("102");
    if (f102 != null) {
      Subfield sfc = f102.getSubfield('a');
      if (sfc != null)
        return sfc.getContent();
    }
    return "";
  }
  
  public String getLanguageOfOriginal(Record rec) {
    Field f101 = rec.getField("101");
    if (f101 != null) {
      Subfield sfc = f101.getSubfield('a');
      if (sfc != null)
        return sfc.getContent();
    }
    return "";
  }
  
  private String getInvNums(Record rec) {
    StringBuffer sb = new StringBuffer();
    Iterator it = rec.getSubfields("996f").iterator();
    while (it.hasNext()) {
      Subfield sf = (Subfield)it.next();
      if (sf.getContent().equals(""))
        continue;
      if (sb.length() > 0)
        sb.append(", ");
      sb.append(sf.getContent());
    }
    it = rec.getSubfields("997f").iterator();
    while (it.hasNext()) {
      Subfield sf = (Subfield)it.next();
      if (sf.getContent().equals(""))
        continue;
      if (sb.length() > 0)
        sb.append(", ");
      sb.append(sf.getContent());
    }
    return sb.toString();
  }

  private void appendEtAl(StringBuffer sb, String locale) {
    String i_dr = (String)etal.get(locale);
    if (i_dr != null)
      sb.append(i_dr);
  }

  private HashMap etal;
}
