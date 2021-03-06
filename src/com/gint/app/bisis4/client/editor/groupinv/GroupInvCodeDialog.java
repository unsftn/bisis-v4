package com.gint.app.bisis4.client.editor.groupinv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.editorutils.CenteredDialog;
import com.gint.app.bisis4.client.editor.editorutils.CodesList;
import com.gint.app.bisis4.client.editor.inventar.InventarConstraints;
import com.gint.app.bisis4.format.UItem;
import com.gint.app.bisis4.format.validators.DateValidator;

public class GroupInvCodeDialog extends CenteredDialog {
	
	private List<UItem> items;
	private String name;
	private JPanel datePanel;
	private JTextField dateTxtFld = new JTextField(8);
	private boolean isStatus = false;
	private CodesList codesList;
	private JScrollPane scrollPane;
	private JButton okButton;  
	private JButton cancelButton;	
	private JPanel buttonsPanel;  
	private String selectedCode = null;
	private String datumStatusa = "";
	
	public GroupInvCodeDialog(String name, List<UItem> items){
		super(BisisApp.getMainFrame(),true);
		this.items = items;
		this.name = name;
		this.setSize(280, 300);		
		setTitle("Promena vrednosti za "+name);
		isStatus = name.equals("Status");
		initialize();
	}

	private void initialize() {
		if(isStatus){
			setLayout(new MigLayout("", "", ""));
			datePanel = new JPanel();
			datePanel.setLayout(new MigLayout("","[]10[]",""));
			datePanel.add(new JLabel("Datum statusa: "));			
			datePanel.add(dateTxtFld,"grow");
			Date today = new Date();
	    dateTxtFld.setText(InventarConstraints.sdf.format(today));
			add(datePanel,"wrap");
		}else{
			setLayout(new MigLayout("insets 10 10 10 10","","[]"));
		}
		codesList = new CodesList(items);
		codesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		scrollPane = new JScrollPane(codesList);
		add(scrollPane,"grow, wrap");
		
		
		okButton = new JButton();
		okButton.setSize(new java.awt.Dimension(88,26));
		okButton.setText("Potvrdi");
		okButton.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/ok.gif")));		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				handleOk();
			}			
		});
    okButton.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent e){  
        handleKeys(e);    
      }     
    });		
		cancelButton = new JButton("Odustani");
		cancelButton.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/remove.gif")));		
		buttonsPanel = new JPanel();
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		add(buttonsPanel,"grow");
		
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				handleCancel();
			}			
		});
    cancelButton.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent e){  
        handleKeys(e);    
      }     
    });	
    codesList.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent e){  
        handleKeys(e);    
      }     
    });	
	}
	
	private void handleOk() {
		if(isStatus){
			datumStatusa = dateTxtFld.getText();
			DateValidator dv = new DateValidator();
			if(!dv.isValid(datumStatusa).equals("")){
				String message = "Pogre\u0161an format datuma";
				JOptionPane.showMessageDialog(BisisApp.getMainFrame(),message,"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
			}else{
				try{
					String selectedString = codesList.getSelectedValue().toString();
					selectedCode = selectedString.substring(0,selectedString.indexOf("-"));
				}catch(NullPointerException e){
					selectedCode = null;
				}		
				setVisible(false);	
			}				
		}else{
			try{
				String selectedString = codesList.getSelectedValue().toString();
				selectedCode = selectedString.substring(0,selectedString.indexOf("-"));
			}catch(NullPointerException e){
				selectedCode = null;
			}		
			setVisible(false);	
		}
		
	}

	private void handleCancel() {
		dispose();		
	}

	private void handleKeys(KeyEvent e) {
		 if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
	      handleCancel(); 		
	}
	
	public String getSelectedCode(){
		return selectedCode;
	}
	
	public String getDatumStatusa(){
		return datumStatusa;
	}
	

}
