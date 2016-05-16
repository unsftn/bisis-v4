package com.gint.app.bisis4.client.editor.invholes;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.miginfocom.swing.MigLayout;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.actions.InvHolesAction;
import com.gint.app.bisis4.client.editor.inventar.CodedValuePanel;
import com.gint.app.bisis4.client.report.ReportUtils;
import com.gint.app.bisis4.format.HoldingsDataCoders;
import com.gint.app.bisis4.reports.InvNumberHolesReport;
import com.gint.app.bisis4.utils.INIFile;

public class InvNumberHolesFrame extends JInternalFrame {
	
	public InvNumberHolesFrame(){
		super("Praznine u inventarnim brojevima", false, true, false, true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setSize(new Dimension(340,550));
		initialize();
		
	}
	
	private void initialize(){		
		//add(mainPanel);
		odeljenjePanel = new CodedValuePanel(HoldingsDataCoders.ODELJENJE_CODER,mainPanel);
		invKnjPanel = new CodedValuePanel(HoldingsDataCoders.INVENTARNAKNJIGA_CODER,mainPanel);
		odeljenjePanel.addAllowedSymbol("*");
		invKnjPanel.addAllowedSymbol("*");		
		executeButton = new JButton(new ImageIcon(getClass().getResource(
        	"/com/gint/app/bisis4/client/images/Check16.png")));
		executeButton.setText("Prona\u0111i");
		viewTxtArea = new JTextArea(20,20);
		viewTxtArea.setEditable(false);
		scrollPane = new JScrollPane(viewTxtArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		setLayout(new MigLayout("insets 10 10 10 10","[][][][][]","[]5[]10[]5[]20[]20[]"));
		add(new JLabel("Odeljenje"),"span 5, wrap");
		add(odeljenjePanel,"wrap");		
		add(new JLabel("Inventarna knjiga"),"wrap");
		add(invKnjPanel,"wrap");
		add(new JLabel("<html><b>od</b></html>"),"split 5");
		add(odTxtFld,"");
		add(new JLabel("<html><b>do</b></html>"),"");
		add(doTxtFld,"");
		add(executeButton,"wrap");
		add(scrollPane,"wrap, grow");
		
		executeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				handleFindHoles();
			}			
		});
	}
	
	private void handleFindHoles(){
		viewTxtArea.setText("");
		int odInt = 1;
		int doInt = 1;
		try{
			odInt = Integer.parseInt(odTxtFld.getText());
		}catch(NumberFormatException e){
			odInt = 1;			
		}
		try{
			doInt = Integer.parseInt(doTxtFld.getText());
		}catch(NumberFormatException e){
			doInt = 1;			
		}
		if(doInt<=odInt){
			doInt = 1;
			odInt = 1;
		}
		String retVal = null;
		String odeljenje = odeljenjePanel.getCode();
		String invKnj = invKnjPanel.getCode();
		retVal = InvNumberHolesFinder.getInvHolesfromIndex(odeljenje, invKnj, odInt, doInt);
		
		
/*		if(BisisApp.isStandalone())
			retVal = InvNumberHolesReport.getHoles(odeljenje, 
					invKnj, odInt, doInt);
		else{
			// pokrecemo servlet
			
			String reportServletUrl;
			INIFile iniFile = BisisApp.getINIFile();
	    if (iniFile == null)
	      iniFile = new INIFile(ReportUtils.class.getResource("/client-config.ini"));
	    reportServletUrl = iniFile.getString("reports",
	        "reportServletURL");
	    if (reportServletUrl == null)
	      log.fatal("Missing parameter: [reports] reportServletURL");
			
			String proxyHost = System.getProperty("proxyHost");
	    String proxyPort = System.getProperty("proxyHost");
	    System.setProperty("proxyHost", "");
	    System.setProperty("proxyPort", "");
	    System.setProperty("proxySet", "false");	    
	    
			try {
				URL url = new URL(reportServletUrl + "?reportFile=invHoles&odeljenje="
						+odeljenje+"&invKnj="+invKnj+"&odStr="+odTxtFld.getText()+"&doStr="+doTxtFld.getText());				
				URLConnection conn = url.openConnection();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(conn
	          .getInputStream(), "UTF8"));
	      String line = "";
	      StringBuffer buff = new StringBuffer();
	      while ((line = in.readLine()) != null)
	        buff.append(line + "\n");
	      in.close();	    
	      System.setProperty("proxyHost", proxyHost == null ? "" : proxyHost);
	      System.setProperty("proxyPort", proxyPort == null ? "" : proxyPort);
	      System.setProperty("proxySet", proxyHost == null ? "false" : "true");
	      retVal = buff.toString();				
			} catch (MalformedURLException e) {				
				log.fatal(e);
			} catch (IOException e) {				
				log.fatal(e);
			}
      
		}		*/		
		if(retVal!=null && retVal.length()==0) viewTxtArea.setText("Nema praznina u inventarnim brojevima.");			
				viewTxtArea.setText(retVal);
	}
	
	private static Log log = LogFactory.getLog(InvNumberHolesFrame.class);
	
	private JPanel mainPanel = new JPanel();	
	private CodedValuePanel odeljenjePanel;
	private CodedValuePanel invKnjPanel;
	private JTextField odTxtFld = new JTextField(6);
	private JTextField doTxtFld = new JTextField(6);
	private JTextArea viewTxtArea;
	private JScrollPane scrollPane;
	private JButton executeButton;
	

}
