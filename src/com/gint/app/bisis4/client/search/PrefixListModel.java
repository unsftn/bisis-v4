package com.gint.app.bisis4.client.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractListModel;

import com.gint.app.bisis4.prefixes.PrefixConfigFactory;
import com.gint.app.bisis4.prefixes.PrefixValue;

public class PrefixListModel extends AbstractListModel {

  public PrefixListModel() {
    this((Locale)null);
  }
  
  public PrefixListModel(String locale) {
    this(new Locale(locale));
  }

  public PrefixListModel(Locale locale) {
    displayed = new ArrayList();
    initList(locale);
  }
  
  public int getSize() {
    return displayed.size();
  }

  public Object getElementAt(int index) {
    PrefixValue pv = (PrefixValue)displayed.get(index);
    if (pv == null)
      return null;
    return pv.prefName + " - " + pv.value;
  }
  
  public String getPrefixName(int index) {
    return ((PrefixValue)displayed.get(index)).prefName;
  }
  
  public int getSelection(char c) {
    int n = displayed.size();
    for (int i = 0; i < n; i++) {
      if (Character.toUpperCase(
            ((PrefixValue)displayed.get(i)).prefName.charAt(0)) == 
          Character.toUpperCase(c))
        return i;
    }
    return -1;
  }

  public int getSelection(String s) {
    int n = displayed.size();
    for (int i = 0; i < n; i++) {
      PrefixValue disp = (PrefixValue)displayed.get(i);
      if (disp.prefName.startsWith(s))
        return i;
    }
    return -1;
  }
  
  private void initList(Locale locale) {
    displayed.clear();
    displayed.addAll(
        PrefixConfigFactory.getPrefixConfig().getPrefixNames(locale));
  }
  
  List displayed;
}
