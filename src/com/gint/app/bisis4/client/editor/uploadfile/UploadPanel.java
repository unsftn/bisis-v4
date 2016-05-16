package com.gint.app.bisis4.client.editor.uploadfile;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.recordtree.CurrRecord;
import com.gint.app.bisis4.client.hitlist.formatters.BriefFormatter;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.textsrv.DocFile;

public class UploadPanel extends JPanel {
	
	private UploadedFilesTableModel model = null;
	private JTable table;
	private JScrollPane tableScrollPane; 
	private ListSelectionModel listSelModel;
	private JPanel buttonsPanel = new JPanel();
	private JButton downloadButton;
	private JButton deleteButton;
	private JButton uploadNewFileButton;
	private int rn;
	private JPanel recordDataPanel;
	private JLabel recordRNLabel;
	private JLabel recLabel;
	private BriefFormatter brief = new BriefFormatter();
	
	
	public UploadPanel(){
		setName("Upload");
		model = new UploadedFilesTableModel();
		table = new JTable(model);	
		tableScrollPane = new JScrollPane(table);
		listSelModel = table.getSelectionModel();
		downloadButton = new JButton("Otvori");
		downloadButton.setIcon(new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis4/client/images/open-icon.png")));
		deleteButton = new JButton("Obri\u0161i");
		deleteButton.setIcon(new ImageIcon(getClass().getResource(
   "/com/gint/app/bisis4/client/images/cancel_x.gif")));
		uploadNewFileButton = new JButton("Dodaj novi");
		uploadNewFileButton.setIcon(new ImageIcon(getClass().getResource(
   "/com/gint/app/bisis4/client/images/plus16.png")));
		recordDataPanel = new JPanel();
	 recordRNLabel = new JLabel();
		
		recLabel = new JLabel();
		layoutPanel();
		downloadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				handleDownloadFile();				
			}			
		});
		
		uploadNewFileButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
				handleUploadFile();
			}
		});
			
			deleteButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					handleDeleteFile();				
				}		
		});		
			
		addComponentListener(new ComponentAdapter(){	

			public void componentShown(ComponentEvent e) {
				recordRNLabel.setText("<html><B>RN=</B>"+CurrRecord.getRecord().getRN()+"</html>");
				recLabel.setText("<html>"+brief.toHTMLWithoutInv(CurrRecord.getRecord(), "sr")+"</html>");
			}
			
		});
		
		table.addMouseListener(new MouseAdapter(){
   public void mouseClicked(MouseEvent e) {
    if(e.getClickCount()==2){
      downloadButton.doClick();
    }       
  }     
  });
		
	}
	
	public void initializeDocList(Record rec){
		if(rec==null)
			this.rn=0;
		else
			this.rn = rec.getRN();
		model = new UploadedFilesTableModel();
		model = new UploadedFilesTableModel(BisisApp.getRecordManager().getDocFiles(rn));		
		table.setModel(model);
		recordRNLabel.setText("<html><B>RN=</B>"+CurrRecord.getRecord().getRN()+"</html>");		
		recLabel.setText("<html>"+brief.toHTMLWithoutInv(CurrRecord.getRecord(), "sr")+"</html>");
		
	}
	
	private void layoutPanel(){		
		
		GridLayout gy1 = new GridLayout(2,1);
		gy1.setHgap(8);
		gy1.setVgap(1);
		recordDataPanel.setLayout(gy1);
		
		recordDataPanel.add(recordRNLabel);		
		recordDataPanel.add(recLabel);
		Border border = BorderFactory.createLineBorder(Color.BLACK);		
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border,"Podaci o zapisu");		
		recordDataPanel.setBorder(titledBorder);
		GridBagLayout layout = new GridBagLayout();
  GridBagConstraints c = new GridBagConstraints();
  setLayout(layout);
  c.gridx = 0;
  c.gridy = 0;
  c.insets = new Insets(10,10,10,10);
  c.fill = GridBagConstraints.BOTH;
  c.weightx = 1;
  c.weighty = 0.05;
  this.add(recordDataPanel,c);
  c.gridx = 0;
  c.gridy = 1;
  c.weighty = 0.85;
  //c.fill = GridBagConstraints.BOTH;
 
  this.add(tableScrollPane,c);
  GridLayout gy = new GridLayout(1,3);
  gy.setHgap(8);
  buttonsPanel.setLayout(gy);
  buttonsPanel.add(downloadButton);  
  buttonsPanel.add(deleteButton);
  buttonsPanel.add(uploadNewFileButton);
  c.gridx = 0;
  c.gridy = 2;
  c.weightx = 0;
  c.weighty = 0.1;
  c.fill = GridBagConstraints.NONE;
  this.add(buttonsPanel,c);		
	}
		
	private void handleDownloadFile(){
		DocFile docFile = model.getFileForRow(table.getSelectedRow());
		File f = FileManagerClient.download(BisisApp.getFileManagerURL(), docFile);
		try {
			Desktop.getDesktop().open(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Gre\u0161ka prilikim otvaranja dokumenta.\n","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			e.printStackTrace();
		}
	}
	
	private void handleUploadFile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showDialog(BisisApp.getMainFrame(), "Dodaj novi");		
		File f = fileChooser.getSelectedFile();	
		if(model.isFileExists(f.getName())){
			Object[] options = { "Odustani", "Snimi" };
			String message = "Dokument "+f.getName()+" ve\u0107 postoji.\n" +
			"Da li \u017elite da snimite novu verziju fajla?";  	
		 int ret = JOptionPane.showOptionDialog(null, message , "Postoje\u0107i fajl", 
			    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		    	null, options, options[0]);
		 if(ret==1){
		 	DocFile df = model.getDocFile(f.getName());
		 	UploadStatusDlg dlg = new UploadStatusDlg();
				UploadTask task = new UploadTask(df.getRn(), f, BisisApp.getLibrarian().getIme()+"@"
						+BisisApp.getINIFile().getString("library", "name"), model, dlg, true);
				task.execute();
				dlg.setFileName(f.getName());
			 dlg.setVisible(true);
		 }			
		}else{
			UploadStatusDlg dlg = new UploadStatusDlg();
			UploadTask task = new UploadTask(rn, f, BisisApp.getLibrarian().getIme()+"@"+BisisApp.getINIFile().getString("library", "name"), model, dlg, false);
			task.execute();
			dlg.setFileName(f.getName());
			dlg.setVisible(true);	
		}
	}
	
	private void handleDeleteFile(){
		DocFile docFile = model.getFileForRow(table.getSelectedRow());	
		Object[] options = { "Obri\u0161i", "Odustani" };
		String message = "Da li ste sigurni da \u017eelite da obri\u0161ete dokument, \n" +
		docFile.getFilename()+"?";  	
	 int ret = JOptionPane.showOptionDialog(null, message , "Brisanje", 
		    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
	    	null, options, options[0]);
  if(ret==0){			
			boolean successful = FileManagerClient.delete(BisisApp.getFileManagerURL(),docFile);
			if(successful){
				model.deleteFile(docFile);
			}else{
				JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Gre\u0161ka prilikim brisanja dokumenta.\n Operacija nije izvrsena.","Gre\u0161ka",JOptionPane.ERROR_MESSAGE);				
			}
  }		
	}
	
	public UploadedFilesTableModel getModel(){
		return model;
	}
	
	

}

