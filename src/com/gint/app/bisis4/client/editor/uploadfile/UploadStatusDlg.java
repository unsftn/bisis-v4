package com.gint.app.bisis4.client.editor.uploadfile;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.util.gui.WindowUtils;

public class UploadStatusDlg extends JDialog {
	
	private String fileName = "";
	private JLabel  label = new JLabel(); 
	
	public UploadStatusDlg(){
		super(BisisApp.getMainFrame(), "Info", true);
  setSize(300,150);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		progressBar.setMinimum(0);
		progressBar.setIndeterminate(true);
		progressBar.setMinimumSize(new Dimension(200,5));		
		
		MigLayout mig = new MigLayout(
		   "",
		   "[]", "[]para[]");
		setLayout(mig);		
		label.setText("<html><b>U toku je slanje dokumenta na server..." +
					"<br><b>Naziv fajla: </b><i>"+fileName+"</i></html>");
		add(label, "center, wrap");
		add(progressBar, "span 2, center, growx, wrap");		
		pack();
		WindowUtils.centerOnScreen(this);
		
		}
	
	 public void setFileName(String fileName){
	 	this.fileName = fileName;
	 	label.setText("<html><b>U toku je slanje dokumenta na server...</b>" +
					"<br><b>Naziv fajla: </b><i>"+fileName+"</i></html>");
	 }
		
		private JProgressBar progressBar = new JProgressBar();
		

	}


