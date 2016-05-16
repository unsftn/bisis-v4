package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.circ.Cirkulacija;

public class CircPicturebooksAction extends AbstractAction {

  public CircPicturebooksAction() {
    putValue(SHORT_DESCRIPTION, "Slikovnice");
    putValue(NAME, "Slikovnice");
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(4);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true);
    } else if (Cirkulacija.getApp().getMainFrame().getUserPanel().isVisible()){
    	Cirkulacija.getApp().getMainFrame().getUserPanel().showPicturebooks();
    }
  }
}
