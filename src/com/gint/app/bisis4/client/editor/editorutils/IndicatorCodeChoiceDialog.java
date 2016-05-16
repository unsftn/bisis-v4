package com.gint.app.bisis4.client.editor.editorutils;

import java.awt.Frame;


import com.gint.app.bisis4.format.*;

public class IndicatorCodeChoiceDialog extends CodeChoiceDialog {

	public IndicatorCodeChoiceDialog() {
		super();		
	}
	
	public IndicatorCodeChoiceDialog(UIndicator ind,Frame owner) {
		super(owner, "Obrada indikatora", 
				ind.getValues(), 
				"Polje: "+ind.getOwner().getName()+"-"+ind.getOwner().getDescription(),
				"ind"+ind.getIndex()+"-"+ind.getDescription()); 
		
	}

}
