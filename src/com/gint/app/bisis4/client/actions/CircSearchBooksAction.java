package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.circ.Cirkulacija;

public class CircSearchBooksAction extends AbstractAction {

  public CircSearchBooksAction() {
    putValue(SHORT_DESCRIPTION, "Pretra\u017eivanje publikacija");
    putValue(NAME, "Publikacije");
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/circ/images/find_book16.png")));
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/circ/images/find_book24.png")));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().showPanel("searchBooksPanel");
    }
  }
}
