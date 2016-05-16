package com.gint.app.bisis4.prefixes;

import com.gint.app.bisis4.prefixes.def.DefaultPrefixConfig;

/**
 * Produces PrefixMap objects according to the prefix.map system property.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class PrefixConfigFactory {
  
  public static PrefixConfig getPrefixConfig() {
    return new DefaultPrefixConfig();
  }
}
