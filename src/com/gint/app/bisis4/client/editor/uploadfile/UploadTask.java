package com.gint.app.bisis4.client.editor.uploadfile;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.recordtree.CurrRecord;
import com.gint.app.bisis4.textsrv.DocFile;
import com.gint.app.bisis4.utils.MimeTypes;

public class UploadTask extends SwingWorker<Boolean, Integer>{
	
	private int rn;
	private File file;
	private String uploader;
	private UploadedFilesTableModel model;
	private boolean successful;
	private UploadStatusDlg dlg;
	private boolean overwrite;
	
	
	public UploadTask(int rn, File f, String uploader, 
			UploadedFilesTableModel model, UploadStatusDlg dlg, boolean overwrite){
		this.rn = rn;
		this.file = f;
		this.uploader = uploader;
		this.model = model;
		this.dlg = dlg;
		this.overwrite = overwrite;
	}

	@Override
	protected Boolean doInBackground() throws Exception {				
		successful = FileManagerClient.upload(BisisApp.getFileManagerURL(), rn, 
				file, uploader);
	 return successful;
	}
	
	@Override
 protected void process(List<Integer> tableCount) {
		
 }
	
 protected void done() { 	
 	if(successful){
			String contentType = MimeTypes.guessMimeTypeFromFilename(file.getName());
			if(!overwrite){
			DocFile df = new DocFile(0,rn,file.getName()
					,contentType,BisisApp.getLibrarian().getIme()+"@"+BisisApp.getINIFile().getString("library", "name"));
			model.addDocFile(df);
			}else{
				DocFile df = model.getDocFile(file.getName());
				df.setUploader(uploader);
				model.fireTableDataChanged();
			}
		}else{
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Gre\u0161ka prilikim ka\u010denja dokumenta.\n Operacija nije izvr\u0161ena.","Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
		}			
 	dlg.setVisible(false);
  dlg.dispose();
 	
 	
 	
 	
 }

}
