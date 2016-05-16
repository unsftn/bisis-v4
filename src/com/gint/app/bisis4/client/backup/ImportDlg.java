package com.gint.app.bisis4.client.backup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.utils.INIFile;
import com.gint.util.gui.WindowUtils;

public class ImportDlg extends JDialog {
  
  public ImportDlg() {
    super();
    INIFile iniFile = new INIFile(ImportTask.class.getResource("/client-config.ini"));
    this.setTitle("BISIS import");
    progressBar.setMinimum(0);
    progressBar.setStringPainted(true);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
        setVisible(false);
      }
    });
     btnChooseFile.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){
	    		fc = new JFileChooser();
	    		fc.setFileFilter(new ZipFileFilter());
			    fc.showOpenDialog(new JFrame());
			    if(fc.getSelectedFile()!=null)
			      
			      tfFileName.setText(fc.getSelectedFile().getAbsolutePath());
	    	}
	      });
    btnChooseFile.setFocusable(false);

    MigLayout mig = new MigLayout();
    setLayout(mig);
    add(new JLabel("Adresa hosta:"),"gap unrelated");
    host.setText(iniFile.getString("database", "url") );
    add(host,"span 3, wrap");
    add(new JLabel("Username:"),"gap unrelated");
    username.setText(iniFile.getString("database", "username") );
    add(username,"split 3");
    add(new JLabel("Password:"));
    password.setText(iniFile.getString("database", "password") );
    add(password,"wrap");
    add(new JLabel("U\u010ditaj bekap:"),"gap unrelated");
    add(tfFileName,"span 2");
    add(btnChooseFile,"wrap");
    add(newdatabase,"wrap");
    add(status,"span 4,growx,height 20:20:20,wrap");
    add(progressBar, "span 4,growx, wrap");
    add(btnCancel,"span 2");
    add(btnOK,"span 2");
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
    newdatabase.setEnabled(false);
    boolean create=false;
    if(newdatabase.isSelected()){
    	create=true;
    }
    ImportTask task = new ImportTask(fileName, this,create);
    task.execute();
  }
  
  JFileChooser fc;
  JButton btnOK = new JButton("Start");
  JButton btnCancel = new JButton("Odustani");
  JTextField tfFileName = new JTextField(20);
  JButton btnChooseFile = new JButton("...");
  JProgressBar progressBar = new JProgressBar();
  JLabel status = new JLabel("");
  JTextField host=new JTextField(25);
  JTextField username=new JTextField(5);
  JTextField password=new JTextField(5);
  JCheckBox newdatabase=new JCheckBox("Kreiraj bazu");
  
  
  public static void main(String[]arg){
	  ImportDlg importD=new ImportDlg();
	  importD.setVisible(true);
  }
}
