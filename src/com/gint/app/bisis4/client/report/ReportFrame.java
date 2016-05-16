package com.gint.app.bisis4.client.report;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

public class ReportFrame extends JInternalFrame {
  public ReportFrame(String title, JasperPrint jp) {
    super(title, true, true, true, true);
    setLayout(new BorderLayout());
    add(new JRViewer(jp), BorderLayout.CENTER);
   
    setSize(getToolkit().getScreenSize().width,getToolkit().getScreenSize().height-100);
  }
}
