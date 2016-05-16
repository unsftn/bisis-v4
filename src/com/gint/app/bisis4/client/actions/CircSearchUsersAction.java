package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.circ.Cirkulacija;

public class CircSearchUsersAction extends AbstractAction {

  public CircSearchUsersAction() {
    putValue(SHORT_DESCRIPTION, "Pretra\u017eivanje korisnika");
    putValue(NAME, "Korisnici");
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/circ/images/find_user16.png")));
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/circ/images/find_user24.png")));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().showPanel("searchUsersPanel");
    }
  }
}
