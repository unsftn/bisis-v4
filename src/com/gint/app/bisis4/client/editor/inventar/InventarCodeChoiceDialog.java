package com.gint.app.bisis4.client.editor.inventar;

import java.util.List;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.editorutils.CodeChoiceDialog;

public class InventarCodeChoiceDialog extends CodeChoiceDialog {

	public InventarCodeChoiceDialog(String title, List cList) {
		super(BisisApp.getMainFrame(), title, cList, "", "");		
	}

}
