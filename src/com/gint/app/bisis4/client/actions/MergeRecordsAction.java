package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.editor.Obrada;

public class MergeRecordsAction extends AbstractAction {

	public MergeRecordsAction() {
    putValue(SHORT_DESCRIPTION, "spajanje zapisa");
    putValue(NAME, "Merge");
    /*putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/new_record.gif")));*/
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
    //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F1));
  }
	
	public void actionPerformed(ActionEvent arg0) {
		Obrada.openMergeRecordsFrame();
	}

}
