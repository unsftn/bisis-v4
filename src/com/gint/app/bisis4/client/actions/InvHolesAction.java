package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.editor.Obrada;

public class InvHolesAction extends AbstractAction {

	public InvHolesAction() {
    putValue(SHORT_DESCRIPTION, "pronalazenja praznina u inventarnim brojevima");
    putValue(NAME, "Praznine u inventaru...");
    /*putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/new_record.gif")));*/
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
    //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F1));
  }
	
	public void actionPerformed(ActionEvent arg0) {
		Obrada.openInvHolesFrame();
	}

}
