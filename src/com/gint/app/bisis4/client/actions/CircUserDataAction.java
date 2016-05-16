package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.circ.Cirkulacija;

public class CircUserDataAction extends AbstractAction {

  public CircUserDataAction() {
    putValue(SHORT_DESCRIPTION, "Postoje\u0107i korisnik");
    putValue(NAME, "Podaci");
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/circ/images/user24.png")));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(1);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true);
    }
  }
}
