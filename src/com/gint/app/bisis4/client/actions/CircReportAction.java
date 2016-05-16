package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.circ.Cirkulacija;

public class CircReportAction extends AbstractAction {

  public CircReportAction() {
    putValue(SHORT_DESCRIPTION, "Izve\u0161taji");
    putValue(NAME, "Izve\u0161taji");
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/circ/images/files_text24.png")));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().showPanel("reportPanel");
    }
  }
}
