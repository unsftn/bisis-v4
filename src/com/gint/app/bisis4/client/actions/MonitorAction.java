package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.util.gui.MonitorFrame;

public class MonitorAction extends AbstractAction {

  public MonitorAction() {
    putValue(SHORT_DESCRIPTION, "Karakteristike sistema");
    putValue(NAME, "Monitor");
  }
  
  public void actionPerformed(ActionEvent ev) {
    MonitorFrame memoryMonitor = new MonitorFrame();
    memoryMonitor.setVisible(true);
  }
}
