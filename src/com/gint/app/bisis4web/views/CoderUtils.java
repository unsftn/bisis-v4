package com.gint.app.bisis4web.views;

import java.util.ArrayList;
import java.util.List;

import com.gint.app.bisis4.format.UCoder;
import com.gint.app.bisis4.format.UItem;
import com.gint.app.bisis4.format.USubfield;
import com.gint.app.bisis4.prefixes.PrefixMap;
import com.gint.app.bisis4web.web.Settings;

/**
 * Retrieves a list of coded values for the given prefix or subfield.
 */
public class CoderUtils {

  public static List<UItem> getCodedValuesForPrefix(String prefix) {
    List<UItem> retVal = new ArrayList<UItem>();
    PrefixMap prefixMap = Settings.getSettings().getPrefixMap();
    List<String> subfields = prefixMap.getSubfields(prefix);
    for (String subfield: subfields) {
      USubfield usf = Settings.getSettings().getFormat().getSubfield(subfield);
      if (usf != null) {
        UCoder ucoder = usf.getCoder();
        if (ucoder != null) {
          retVal.addAll(ucoder.getItems());
        }
      }
    }
    return retVal;
  }
  
  public static List<UItem> getCodedValuesForSubfield(String subfield) {
    List<UItem> retVal = new ArrayList<UItem>();
    USubfield usf = Settings.getSettings().getFormat().getSubfield(subfield);
    UCoder ucoder = usf.getCoder();
    if (ucoder != null) {
      retVal.addAll(ucoder.getItems());
    }
    return retVal;
  }
  
  public static String getCodedValuesForPrefixJson(String prefix) {
    List<UItem> items = getCodedValuesForPrefix(prefix);
    return toJson(items);
  }
  
  public static String getCodedValuesForSubfieldJson(String subfield) {
    List<UItem> items = getCodedValuesForSubfield(subfield);
    return toJson(items);
  }
  
  private static String toJson(List<UItem> items) {
    StringBuilder sb = new StringBuilder();
    sb.append("[\n");
    for (int i = 0; i < items.size(); i++) {
      if (i > 0)
        sb.append(",\n");
      sb.append("  {\"code\": \"");
      sb.append(items.get(i).getCode());
      sb.append("\", \"value\": \"");
      sb.append(items.get(i).getValue());
      sb.append("\"}");
    }
    if (items.size() > 0)
      sb.append("\n");
    sb.append("]\n");
    return sb.toString();
  }
  
  public static void main(String[] args) {
    System.out.println(getCodedValuesForPrefixJson("RT"));
    System.out.println(getCodedValuesForPrefixJson("PT"));
    System.out.println(getCodedValuesForPrefixJson("TI"));
  }
}
