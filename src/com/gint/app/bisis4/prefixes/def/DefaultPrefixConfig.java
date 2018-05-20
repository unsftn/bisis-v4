package com.gint.app.bisis4.prefixes.def;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import com.gint.app.bisis4.prefixes.PrefixConfig;
import com.gint.app.bisis4.prefixes.PrefixHandler;
import com.gint.app.bisis4.prefixes.PrefixMap;
import com.gint.app.bisis4.prefixes.PrefixValue;

/**
 * Implements a default prefix configuration.
 * 
 * @author mbranko@uns.ac.rs
 */
public class DefaultPrefixConfig implements PrefixConfig {

  public PrefixHandler getPrefixHandler() {
    return prefixHandler;
  }

  public PrefixMap getPrefixMap() {
    return prefixMap;
  }

  public Set<String> getAllPrefixes() {
    return allPrefixes;
  }

  public Set<String> getSortPrefixes() {
    return sortPrefixes;
  }

  public String getWordDelimiters() {
    return ", ;:\"()[]{}-+/'\t\r\n\f";
  }

  public String getSentenceDelimiters() {
    return ".?!";
  }
  
  public String getAllDelimiters() {
    return ", ;:\"()[]{}-+/'.?!\t\r\n\f";
  }
  
  public List<PrefixValue> getPrefixNames() {
    return prefixNameLists.get(null);
  }
  
  public List<PrefixValue> getPrefixNames(Locale locale) {
    List<PrefixValue> retVal = prefixNameLists.get(locale);
    if (retVal == null) {
      retVal = initPrefixNameList(locale);
      prefixNameLists.put(locale, retVal);
    }
    return retVal;
  }
  
  public DefaultPrefixConfig() {
    prefixHandler = new DefaultPrefixHandler();
    prefixMap = new DefaultPrefixMap();
    allPrefixes = new HashSet<String>();
    sortPrefixes = new HashSet<String>();
    allPrefixes.add("AB");
    allPrefixes.add("AM");
    allPrefixes.add("AN");
    allPrefixes.add("AP");
    allPrefixes.add("AT");
    allPrefixes.add("AU");
    allPrefixes.add("BI");
    allPrefixes.add("BN");
    allPrefixes.add("CB");
    allPrefixes.add("CC");
    allPrefixes.add("CD");
    allPrefixes.add("CH");
    allPrefixes.add("CL");
    allPrefixes.add("CN");
    allPrefixes.add("CO");
    allPrefixes.add("CP");
    allPrefixes.add("CR");
    allPrefixes.add("CS");
    allPrefixes.add("DA");
    allPrefixes.add("DC");
    allPrefixes.add("DE");
    allPrefixes.add("DR");
    allPrefixes.add("DS");
    allPrefixes.add("DT");
    allPrefixes.add("ES");
    allPrefixes.add("FC");
    allPrefixes.add("FD");
    allPrefixes.add("FI");
    allPrefixes.add("FN");
    allPrefixes.add("FQ");
    allPrefixes.add("FS");
    allPrefixes.add("GE");
    allPrefixes.add("GM");
    allPrefixes.add("GN");
    allPrefixes.add("GS");
    allPrefixes.add("IC");
    allPrefixes.add("ID");
    allPrefixes.add("II");
    allPrefixes.add("IN");
    allPrefixes.add("IR");
    allPrefixes.add("KT");
    allPrefixes.add("KW");
    allPrefixes.add("LA");
    allPrefixes.add("LC");
    allPrefixes.add("LI");
    allPrefixes.add("LO");
    allPrefixes.add("MR");
    allPrefixes.add("ND");
    allPrefixes.add("NM");
    allPrefixes.add("NT");
    allPrefixes.add("P2");
    allPrefixes.add("PA");
    allPrefixes.add("PM");
    allPrefixes.add("PN");
    allPrefixes.add("PP");
    allPrefixes.add("PR");
    allPrefixes.add("PU");
    allPrefixes.add("PY");
    allPrefixes.add("RE");
    allPrefixes.add("RJ");
    allPrefixes.add("RN");
    allPrefixes.add("RS");
    allPrefixes.add("RT");
    allPrefixes.add("SC");
    allPrefixes.add("SG");
    allPrefixes.add("SI");
    allPrefixes.add("SK");
    allPrefixes.add("SN");
    allPrefixes.add("SP");
    allPrefixes.add("SR");
    allPrefixes.add("SS");
    allPrefixes.add("ST");
    allPrefixes.add("SU");
    allPrefixes.add("SF");
    allPrefixes.add("SL");
    allPrefixes.add("SQ");
    allPrefixes.add("SW");
    allPrefixes.add("SX");
    allPrefixes.add("SY");
    allPrefixes.add("SZ");
    allPrefixes.add("TA");
    allPrefixes.add("TI");
    allPrefixes.add("TN");
    allPrefixes.add("TP");
    allPrefixes.add("TS");
    allPrefixes.add("TY");
    allPrefixes.add("UG");
    allPrefixes.add("US");
    allPrefixes.add("UT");
    allPrefixes.add("Y1");
    allPrefixes.add("Y2");
    sortPrefixes.add("RN");
    sortPrefixes.add("AU");
    sortPrefixes.add("TI");
    sortPrefixes.add("PY");
    prefixNameLists = new HashMap<>();
    prefixNameLists.put(null, initPrefixNameList(null));
  }
  
  private List<PrefixValue> initPrefixNameList(Locale locale) {
    List<PrefixValue> retVal = new ArrayList<>();
    ResourceBundle rb = null;
    if (locale == null)
      rb = PropertyResourceBundle.getBundle(
          "com.gint.app.bisis4.prefixes.def.PrefixNames");
    else
      rb = PropertyResourceBundle.getBundle(
          "com.gint.app.bisis4.prefixes.def.PrefixNames", locale);
    if (rb == null)
      return retVal;
    Enumeration<String> keys = rb.getKeys();
    while (keys.hasMoreElements()) {
      String key = keys.nextElement();
      String value = rb.getString(key);
      retVal.add(new PrefixValue(key.toUpperCase(), value));
    }
    Collections.sort(retVal);

    List<PrefixValue> temp = new ArrayList<>();
    if (locale == null)
      rb = PropertyResourceBundle.getBundle(
          "com.gint.app.bisis4.prefixes.def.SubfieldNames");
    else
      rb = PropertyResourceBundle.getBundle(
          "com.gint.app.bisis4.prefixes.def.SubfieldNames", locale);
    if (rb == null)
      return retVal;
    keys = rb.getKeys();
    while (keys.hasMoreElements()) {
      String key = (String)keys.nextElement();
      String value = rb.getString(key);
      temp.add(new PrefixValue(key.toLowerCase(), value));
    }
    Collections.sort(temp);
    
    retVal.addAll(temp);
    return retVal;
  }
  
  public String getPrefixName(String prefix) {
    return getPrefixName(prefix, null);
  }
  
  public String getPrefixName(String prefix, Locale locale) {
    for (PrefixValue pv : getPrefixNames(locale)) {
      if (pv.prefName.equals(prefix))
        return pv.value;
    }
    return "";
  }
  
  private PrefixHandler prefixHandler;
  private PrefixMap prefixMap;
  private Set<String> allPrefixes;
  private Set<String> sortPrefixes;
  private HashMap<Locale, List<PrefixValue>> prefixNameLists;
}
