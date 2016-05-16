package com.gint.app.bisis4.utils;

import java.io.*;
import java.net.URL;
import java.util.*;

/**  Klasa INIFile implementira operacije za rad sa INI
  *  datotekama. INI datoteke, po uzoru iz Windows-a, su
  *  tekstualne datoteke koje se sastoje iz vise sekcija
  *  (section) u kojima su definisane vrednosti zeljenih
  *  parametara navodjenjem reda <parametar> = <vrednost>.
  *  Komentari u datoteci pocinju znakom tacka-zarez (;) i
  *  protezu se do kraja tekuceg reda. Navodjenje imena
  *  sekcije se vrsi u uglastim zagradama i u posebnom redu.
  *  Sledi primer jedne INI datoteke:<BR><PRE>
  *  [Sekcija1]
  *  param1 = vrednost1
  *  param2 = vrednost2  ; komentar
  *  [Sekcija2]
  *  param3 = vrednost3
  *  param2 = vrednost2</PRE>
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  */
public class INIFile {

  /** Konstruise objekat sa datim parametrima.
    * @param filename Naziv lokalnog INI fajla.
    */
  public INIFile(String filename) {
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(
          new FileInputStream(filename), "UTF8"));
      readINI(in);
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Konstruise objekat sa datim parametrima.
    * @param url URL INI fajla.
    */
  public INIFile(URL url) {
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF8"));
      readINI(in);
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void readINI(BufferedReader in) {
    String line, key, value;
    StringTokenizer st;
    int pos;
    String currentCategory = "default";
    HashMap<String, String> currentMap = new HashMap<String, String>();
    categories.put(currentCategory, currentMap);
    try {
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals("") || line.indexOf(';') == 0)
          continue;
        if (line.charAt(0) == '[') {
          currentCategory = line.substring(1, line.length()-1);
          currentMap = new HashMap<String, String>();
          categories.put(currentCategory, currentMap);
        } else {
          st = new StringTokenizer(line, "=");
          if (st.hasMoreTokens()) {
            key = st.nextToken().trim();
            boolean hastoks = false;
            value = "";
            while (st.hasMoreTokens()) {
              value += (hastoks ? "=" : "") + st.nextToken().trim();
              hastoks = true;
            }
            currentMap.put(key, value);
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**  Vraca vrednost datog parametra u obliku stringa.
    *  @param category Kategorija (sekcija) u kojoj se nalazi parametar
    *  @param key Naziv parametra
    *  @return String koji sadrzi vrednost parametra
    */
  public String getString(String category, String key) {
    HashMap<String, String> hm = categories.get(category);
    if (hm == null)
      return "";
    else
      return hm.get(key);
  }

  /**  Vraca vrednost datog parametra u obliku integera.
    *  @param category Kategorija (sekcija) u kojoj se nalazi parametar
    *  @param key Naziv parametra
    *  @return Integer vrednost parametra
    */
  public int getInt(String category, String key) {
    HashMap<String, String> hm = categories.get(category);
    if (hm == null)
      return 0;
    else
      return Integer.parseInt(hm.get(key));
  }
  
  /** Vraca vrednost datog parametra u obliku booleana. Smatra se da je 
   *  vrednost true ako je vrednost parametra "yes", "true" ili "1", 
   *  case-insensitive.
   *  @param category Kategorija (sekcija) u kojoj se nalazi parametar
   *  @param key Naziv parametra
   *  @return Integer vrednost parametra
   */
  public boolean getBoolean(String category, String key) {
    HashMap<String, String> hm = categories.get(category);
    if (hm == null)
      return false;
    String val = hm.get(key).toLowerCase();
    return "yes".equals(val) || "true".equals(val) || "1".equals(val);
  }
  
  /**
   * Vraca listu svih postojecih kategorija. Lista moze biti prazna.
   */
  public List<String> getCategories() {
    List<String> retVal = new ArrayList<String>();
    retVal.addAll(categories.keySet());
    return retVal;
  }
  
  /**
   * Vraca listu svih parametara za datu kategoriju. Lista moze biti prazna.
   * Ako data kategorija ne postoji, vraca null.
   */
  public List<String> getParams(String category) {
    HashMap<String, String> cat = categories.get(category);
    if (cat == null)
      return null;
    List<String> retVal = new ArrayList<String>();
    retVal.addAll(cat.keySet());
    return retVal;
  }

  /**  Hash mapa koja sadrzi kategorije (sekcije). Hash kljuc je naziv kategorije (string),
    *  a vrednost je hash mapa koja sadrzi parove (parametar, vrednost). */
  private HashMap<String, HashMap<String, String>> categories = new HashMap<String, HashMap<String, String>>();
}

