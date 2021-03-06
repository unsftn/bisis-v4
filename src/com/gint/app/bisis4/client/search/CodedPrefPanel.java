/**
 * 
 */
package com.gint.app.bisis4.client.search;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.editorutils.CodeChoiceDialog;
import com.gint.app.bisis4.format.UItem;

/**
 * @author dimicb
 *
 */
public class CodedPrefPanel extends JPanel {
	
	private JTextField txtFld = new JTextField();
	private JButton button = new JButton();
	private String pref = "";

	public CodedPrefPanel() {
		button.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/coder.gif")));
		button.setPreferredSize(new Dimension(20,20));
		//button.setPreferredSize(new Dimension(1,2));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0,0,0,5);		
		c.fill = GridBagConstraints.BOTH;
		add(txtFld, c);
		c.insets = new Insets(0,0,0,0);		
		c.gridx = 1;
		c.weightx = 0.001;	
		c.weighty = 0;	
		add(button, c);
		
		//actions		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				openCoder();
			}			
		});
	}
	
	public void setPref(String pref){
		this.pref = pref;
	}	
	
	public void requestFocus() {
		txtFld.requestFocus();		
	}
	
	public String getText(){
		return txtFld.getText();
	}
	
	public JTextField getTextField(){
		return txtFld;
	}
	
	private void openCoder(){		
		List<UItem> codesList = CodedPrefUtils.getCodesForPrefix(pref);		
		if(codesList!=null){
			CodeChoiceDialog ccd = new CodeChoiceDialog(BisisApp.getMainFrame(),
				"\u0160ifarnik", codesList,"\u0160ifarnik za prefiks "+pref,"");
			ccd.setVisible(true);		
			if(ccd.getSelectedCode()!=null)
				txtFld.setText(ccd.getSelectedCode());
			//else codedCellEditor.setText("");
			ccd.setVisible(false);	
		}
	}
	
	
	
	
	

	
}
