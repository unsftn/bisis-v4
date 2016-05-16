package com.gint.app.bisis4.client.backup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.SwingWorker.StateValue;

import net.miginfocom.swing.MigLayout;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.util.gui.WindowUtils;

public class BackupDlg extends JDialog {
  
  public BackupDlg() {
    super(BisisApp.getMainFrame(), "BISIS Bekap", false);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    progressBar.setMinimum(0);
    progressBar.setStringPainted(true);
//    String year=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
//    String month=String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
//    String day=String.valueOf(Calendar.getInstance().get(Calendar.DATE));
//    final String fileName="backup-"+day+"."+month+"."+year+".zip";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String date = sdf.format(new Date());
		final String fileName="backup-"+date+".zip";
    tfFileName.setText(fileName);
    btnOK.setFocusable(false);
    getRootPane().setDefaultButton(btnOK);
    btnOK.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/ok.gif")));
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          	  handleOK();
      }
    });
    btnCancel.setFocusable(false);
    btnCancel.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/remove.gif")));
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	tfFileName.setText("");
        setVisible(false);
      }
    });
    btnChooseFile.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){
	    		fc = new JFileChooser();		
	    		fc.setFileFilter(new ZipFileFilter());
	    		fc.setSelectedFile(new File(fileName));
	    		fc.showSaveDialog(BisisApp.getMainFrame());

			    if(fc.getSelectedFile()!=null)
			      tfFileName.setText(fc.getSelectedFile().getAbsolutePath());
	    	}
	      });
    btnChooseFile.setFocusable(false);

    MigLayout mig = new MigLayout(
        "insets dialog, wrap",
        "[]rel[]",
        "[]rel[]para[]para[]");
    setLayout(mig);
    add(new JLabel("Odredi\u0161te za bekap:"), "span 2, wrap");
    add(tfFileName, "");
    add(btnChooseFile, "wrap");
    add(progressBar, "span 2, center, growx, wrap");
    add(btnCancel, "span 2, split 2, tag cancel");
    add(btnOK, "wrap, tag ok");
    setModalityType(ModalityType.MODELESS);
    pack();
    WindowUtils.centerOnScreen(this);
  }

  private void handleOK() {
    String fileName = tfFileName.getText().trim();
    if ("".equals(fileName))
      return;
    btnOK.setEnabled(false);
    btnCancel.setEnabled(false);
    btnChooseFile.setEnabled(false);
    tfFileName.setEnabled(false);
    task = new BackupTask(fileName, this, BisisApp.getConnection());
    task.execute();

  }
  
  JFileChooser fc;
  JButton btnOK = new JButton("Start");
  JButton btnCancel = new JButton("Odustani");
  JTextField tfFileName = new JTextField(20);
  JButton btnChooseFile = new JButton("...");
  JProgressBar progressBar = new JProgressBar();
  BackupTask task;
}
