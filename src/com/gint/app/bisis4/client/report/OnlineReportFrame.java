package com.gint.app.bisis4.client.report;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JasperPrint;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.circ.view.ReportResults;
import com.gint.app.bisis4.reports.ReportCollection;
import com.gint.app.bisis4.reports.ReportRunner;
import com.gint.app.bisis4.textsrv.Result;

public class OnlineReportFrame extends JInternalFrame{
	
	public  OnlineReportFrame() {
		super("Online generisanje izve\u0161taja",true, true, false, true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		panel.setLayout(layout);
		btnOK.setText("Prika\u017ei");
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bOK_actionPerformed(e);
			}
		});
		btnCancel.setText("Odustani");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bCancel_actionPerformed(e);
			}
	    });
	   String path ="/com/gint/app/bisis4/reports/"+BisisApp.getINIFile().getString("library", "name")+"/reports.ini";
	   if (OnlineReportFrame.class.getResource(path) == null) {
	      path="/com/gint/app/bisis4/reports/general/reports.ini";
	   } 
	   ReportCollection repCol = new ReportCollection(path);
       ReportMenuBuilder.addReport(reportsCmb, repCol.getReports());
		
	    getContentPane().add(panel);
		panel.add(chooseReport,"split 2");
		panel.add(reportsCmb,"wrap");
		panel.add(insertInvBr,"split 4");
		panel.add(odBr);
		panel.add(new JLabel("do"));
		panel.add(doBr,"wrap");
		panel.add(btnOK,"gapleft 80,split 2");
		panel.add(btnCancel,"wrap");
		panel.add(jasperPanel,"wrap,span 2,height 500,width 1020");
		setSize(1020, 670);
		getRootPane().setDefaultButton(btnOK);
		
	}

    void bOK_actionPerformed(ActionEvent e) {
      try{
    	if(odBr.getText().length()!=11){
    		 JOptionPane.showMessageDialog(this, 
   		          "Inventarni broj mora biti du\u017eine 11!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
           
    	}else if(doBr.getText().length()!=11){
    		 JOptionPane.showMessageDialog(this, 
      		          "Inventarni broj mora biti du\u017eine 11!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
    		
    	}else if(Integer.parseInt(doBr.getText().substring(4))-Integer.parseInt(odBr.getText().substring(4))>MAXRECORDS){
    		JOptionPane.showMessageDialog(this, "Prevelik opseg inventarnih brojeva","Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
         
    	}else{
		   String path ="/com/gint/app/bisis4/reports/"+BisisApp.getINIFile().getString("library", "name")+"/reports.ini";
		   if (OnlineReportFrame.class.getResource(path) == null) {
		      path="/com/gint/app/bisis4/reports/general/reports.ini";
		   } 
		   ReportCollection repCol = new ReportCollection(path);			     
           ReportRunner runner = new ReportRunner(repCol);
           JasperPrint jp=runner.run(odBr.getText(),doBr.getText(),(String)reportsCmb.getSelectedItem());
           jasperPanel.setJasper(jp);
           validate();
           jasperPanel.setVisible(true);
    	}
      }catch(NumberFormatException e1){
    		JOptionPane.showMessageDialog(this, "Inventarni broj se mora sastojati samo od cifara","Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
      }
    }
    
    void bCancel_actionPerformed(ActionEvent e) {

    	
    	setVisible(false);
	}
    void taQuery_keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == KeyEvent.VK_ENTER)
    		btnOK.doClick();
    	else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    		btnCancel.doClick();
    }
    

  JPanel panel = new JPanel();
  JLabel lSelect = new JLabel();
  MigLayout layout = new MigLayout("","[][]","[]10[] 30[]10[push]");
  JTextField odBr=new JTextField(7);
  JTextField doBr=new JTextField(7);
  JComboBox reportsCmb=new JComboBox();
  JLabel chooseReport=new JLabel("Izaberite izve\u0161taj:");
  JLabel insertInvBr=new JLabel("Unesite inventarne brojeve:");
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  ReportResults jasperPanel = new ReportResults();
  int []hits;
  Result queryResult;
  String query = "";
  int MAXRECORDS=100;
}
