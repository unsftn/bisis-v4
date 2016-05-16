package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.circ.Cirkulacija;

public class CircUserMembershipAction extends AbstractAction {

  public CircUserMembershipAction() {
    putValue(SHORT_DESCRIPTION, "\u010clanarina korisnika");
    putValue(NAME, "\u010clanarina");
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(2);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true); 
    }
  }
}
