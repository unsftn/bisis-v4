package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.circ.Cirkulacija;

public class CircNewUserAction extends AbstractAction {

  public CircNewUserAction() {
    putValue(SHORT_DESCRIPTION, "Novi individualni korisnik");
    putValue(NAME, "Individualni");
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/circ/images/add_user_bold16.png")));
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/circ/images/add_user_bold24.png")));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
    //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_I));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().getUserPanel().loadDefault();
      Cirkulacija.getApp().getMainFrame().showPanel("userPanel");
    }
  }
}
