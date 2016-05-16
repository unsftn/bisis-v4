package com.gint.app.bisis4.client.editor.editorutils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.gint.app.bisis4.format.UField;
import com.gint.app.bisis4.format.USubfield;

public class UElementListCellRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);		
	    
	    if(value instanceof UField) 
			setText(((UField)value).getName()+"-"+((UField)value).getDescription());
	    else if(value instanceof USubfield){
	    	setText(((USubfield)value).getName()+"-"+((USubfield)value).getDescription());	    	
	    }
	

	return this;
    }
		

}
