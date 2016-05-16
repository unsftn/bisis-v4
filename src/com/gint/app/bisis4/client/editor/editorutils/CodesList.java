package com.gint.app.bisis4.client.editor.editorutils;



import java.util.List;
import java.util.Iterator;

import javax.swing.JList;
import javax.swing.DefaultListModel;

import com.gint.app.bisis4.format.UItem;

public class CodesList extends JList {
	
	DefaultListModel lmodel = new DefaultListModel();
	
	public CodesList(List<UItem> l) {
		super();
		this.setModel(lmodel);	
		
		
		Iterator i = l.iterator();
		
		while (i.hasNext()){
			UItem item = (UItem)i.next();			
			lmodel.addElement(item.getCode()+"-"+item.getValue());
		}
		
	}

	public void setList(List<UItem> list){
		lmodel.clear();
		Iterator i = list.iterator();		
		while (i.hasNext()){
			UItem item = (UItem)i.next();			
			lmodel.addElement(item.getCode()+"-"+item.getValue());
		}
		
		
	}
}
