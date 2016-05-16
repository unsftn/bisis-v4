package com.gint.app.bisis4web.web;

import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;
import java.util.Locale;

/**
 *
 * Provides language-dependent messages stored in .properties files
 *
 * @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
 * @author Milan Vidakovic, minja@uns.ns.ac.yu
 */
public class Messages {

  /**
   * Retrieves a language-dependent message from the properties file
   *
   * @param msgName Message name
   * @return language-dependent version of the message
   */
  public static String get(String msgName, String locale) {
    ResourceBundle rb;
    if (locale == null)
      locale = "sr";
    if (fileCache.get(locale) == null) {
      rb = PropertyResourceBundle.getBundle(
        Messages.class.getPackage().getName()+".Messages", new Locale(locale));
      fileCache.put(locale, rb);
    } else {
      rb = (ResourceBundle)fileCache.get(locale);
    }
    return rb.getString(msgName);
  }

  private static Hashtable<String, ResourceBundle> fileCache;

  static {
    fileCache = new Hashtable<String, ResourceBundle>();
  }

}
