package com.gint.app.bisis4.utils;

import java.text.Normalizer;

/**
 * Latin-Cyrillic script conversion - for Serbian language only.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class LatCyrUtils {
  
  /**
   * Converts a string with cyrillic text to unaccented latin.
   * 
   * @param s String to be converted
   * @return The converted string
   */
  public static String toLatinUnaccented(String s) {
    return removeAccents(toLatin(s));
  }
  
  /**
   * Removes accents from all letters according to Unicode spec.
   * @param s String to be unaccented.
   * @return Unaccented string.
   */
  public static String removeAccents(String s) {
    // Convert accented chars to equivalent unaccented char + dead char accent 
    // pair. See http://www.unicode.org/unicode/reports/tr15/tr15-23.html to 
    // understand the NFD transform.
    final String normalized = Normalizer.normalize(s, Normalizer.Form.NFD);
    // Remove the dead char accents, leaving just the unaccented chars.
    // Stripped string should have the same length as the original accented one.
    StringBuilder sb = new StringBuilder(s.length());
  
    for (int i = 0; i < normalized.length(); i++) {
      char c = normalized.charAt(i);
      if(c=='\u0111' || c=='\u0110' ){ //dodato jer normalizer nije pretvarao 
    	  sb.append("dj");
      }else if (Character.getType(c) != Character.NON_SPACING_MARK){
        sb.append( c );
      }
    }
    return sb.toString();
  }
  
  /**
   * Converts a string with cyrillic text to latin.
   * 
   * @param s String to be converted
   * @return The converted string
   */
  public static String toLatin(String s) {
    String t = s;
    for (int i = 0; i < cyr.length; i++)
      t = t.replace(cyr[i], lat[i]);
    t = replace(t, "\u0409", "LJ");
    t = replace(t, "\u040A", "NJ");
    t = replace(t, "\u040F", "D\u017D");
    t = replace(t, "\u0459", "lj");
    t = replace(t, "\u045A", "nj");
    t = replace(t, "\u045F", "d\u017E");
    return t;
  }
  
  /**
   * Converts a string with latin text to cyrillic.
   * 
   * @param s String to be converted
   * @return The converted string
   */
  public static String toCyrillic(String s) {
    String t = s;
    t = replace(t, "LJ", "\u0409");
    t = replace(t, "Lj", "\u0409");
    t = replace(t, "NJ", "\u040A");
    t = replace(t, "Nj", "\u040A");
    t = replace(t, "D\u017D", "\u040F");
    t = replace(t, "D\u017E", "\u040F");
    t = replace(t, "lj", "\u0459");
    t = replace(t, "nj", "\u045A");
    t = replace(t, "d\u017e", "\u045F");
    for (int i = 0; i < cyr.length; i++)
      t = t.replace(lat[i], cyr[i]);
    return t;
  }

  /** Replaces one substring with another in a given string.
   *
   *  @param s Container string
   *  @param src Substring to be replaced
   *  @param dest Replacement string
   *  @return Converted string
   */
  private static String replace(String s, String src, String dest) {
    StringBuffer retVal = new StringBuffer();
    int finishedPos = 0;
    int startPos = 0;
    int srcLen = src.length();
    while ((startPos = s.indexOf(src, startPos)) != -1) {
      if (startPos != finishedPos)
        retVal.append(s.substring(finishedPos, startPos));
      startPos += srcLen;
      retVal.append(dest);
      finishedPos = startPos;
    }
    if (finishedPos < s.length())
      retVal.append(s.substring(finishedPos));
    return retVal.toString();
  }

  
  private static char[] cyr = { '\u0410', '\u0411', '\u0412', '\u0413', 
      '\u0414', '\u0402', '\u0415', '\u0416', '\u0417', '\u0418', '\u0408',
      '\u041A', '\u041B', '\u041C', '\u041D', '\u041E', '\u041F', '\u0420',
      '\u0421', '\u0422', '\u040B', '\u0423', '\u0424', '\u0425', '\u0426',
      '\u0427', '\u0428', '\u0430', '\u0431', '\u0432', '\u0433', '\u0434',
      '\u0452', '\u0435', '\u0436', '\u0437', '\u0438', '\u0458', '\u043A',
      '\u043B', '\u043C', '\u043D', '\u043E', '\u043F', '\u0440', '\u0441',
      '\u0442', '\u045B', '\u0443', '\u0444', '\u0445', '\u0446', '\u0447',
      '\u0448'};
  
  private static char[] lat = { 'A', 'B', 'V', 'G', 'D', '\u0110', 'E', 
      '\u017D', 'Z', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T',
      '\u0106', 'U', 'F', 'H', 'C', '\u010C', '\u0160', 'a', 'b', 'v', 'g', 'd',
      '\u0111', 'e', '\u017e', 'z', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r',
      's', 't', '\u0107', 'u', 'f', 'h', 'c', '\u010d', '\u0161'};
  
 
  
}
