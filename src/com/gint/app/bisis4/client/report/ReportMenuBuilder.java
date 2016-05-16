package com.gint.app.bisis4.client.report;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.reports.Report;

public class ReportMenuBuilder {
  public static void addReport(JMenu root, Report report) {
	 if(report.getReportSettings().getParam("type").equals("online"))
		 return;
    String[] path = report.getReportSettings().getParam("menuitem")
        .split("\\|");
    if (path.length == 0)
      return;
    JMenu current = root;
    for (int i = 0; i < path.length - 1; i++) {
      current = makeMenu(current, path[i]);
    }
    JMenuItem menuItem = new JMenuItem(path[path.length - 1]);
    current.add(menuItem);
    final Report r = report;
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
    	  String [] currentReport;
    	  if (BisisApp.isStandalone()){
    		   currentReport = ReportUtils.loadReport(r, r.getReportSettings().getParam("destination"));
    	  }else{
             currentReport = ReportUtils.loadReport(r);
    	  }
        
        if ((currentReport!=null)&&(currentReport[0] != null))
          ReportUtils.showReport(currentReport[0],currentReport[1], r);
      }
    });
  }

  public static void addReports(JMenu root, List<Report> reports) {
    for (Report r : reports)
      addReport(root, r);
  }

  public static void addReport(JMenu root, Report report, String dir) {
    String[] path = report.getReportSettings().getParam("menuitem")
        .split("\\|");
    if (path.length == 0)
      return;
    JMenu current = root;
    for (int i = 0; i < path.length - 1; i++) {
      current = makeMenu(current, path[i]);
    }
    JMenuItem menuItem = new JMenuItem(path[path.length - 1]);
    current.add(menuItem);
    final Report r = report;
    final String d = dir;
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        String []currentReport = ReportUtils.loadReport(r, d);
        if (currentReport[0] != null)
          ReportUtils.showReport(currentReport[0], currentReport[1],r);
      }
    });
  }

  public static void addReports(JMenu root, List<Report> reports, String dir) {
    for (Report r : reports)
      addReport(root, r, dir);
  }
  public static void addReport(JComboBox combo, List<Report> reports){
	  for (Report r : reports){     
	   String type=r.getReportSettings().getParam("type");
	   if(type.equalsIgnoreCase("online")){
		  combo.addItem(r.getReportSettings().getParam("menuitem"));
	   }
	 } 
  }
  private static JMenu makeMenu(JMenu parent, String name) {
    int n = parent.getMenuComponentCount();
    JMenu target = null;
    for (int i = 0; i < n; i++) {
      Component c = parent.getMenuComponent(i);
      if (c instanceof JMenu) {
        JMenu m = (JMenu) c;
        if (m.getText().equals(name))
          return m;
      }
    }
    if (target == null) {
      target = new JMenu(name);
      parent.add(target);
    }
    return target;
  }
}
