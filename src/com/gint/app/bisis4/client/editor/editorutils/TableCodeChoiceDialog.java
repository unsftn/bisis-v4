package com.gint.app.bisis4.client.editor.editorutils;

import java.awt.Frame;

import com.gint.app.bisis4.client.editor.Messages;
import com.gint.app.bisis4.format.HoldingsDataCoders;
import com.gint.app.bisis4.format.USubfield;

public class TableCodeChoiceDialog extends CodeChoiceDialog {

	

		public TableCodeChoiceDialog(USubfield us,int holdingCoder,Frame owner) {
			super(owner, 
				Messages.getString("EDITOR_CODEDSUBFIELD"),  //$NON-NLS-1$
				HoldingsDataCoders.getCoder(holdingCoder), 
				Messages.getString("EDITOR_LABELFIELD")+us.getOwner().getName()+"-"+us.getOwner().getDescription(),  //$NON-NLS-1$ //$NON-NLS-2$
				Messages.getString("EDITOR_LABELSUBFIELD")+us.getName()+"-"+us.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
			
		}


}
