package com.gint.app.bisis4.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.gint.app.bisis4.client.editor.Obrada;

public class NewRecordAction extends AbstractAction {

  public NewRecordAction() {
    putValue(SHORT_DESCRIPTION, "Otvaranje novog zapisa");
    putValue(NAME, "Novi zapis...");
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/new_record.gif")));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
    //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F1));
  }
  
  public void actionPerformed(ActionEvent ev) {
	  Obrada.newRecord(null);
  }

}
