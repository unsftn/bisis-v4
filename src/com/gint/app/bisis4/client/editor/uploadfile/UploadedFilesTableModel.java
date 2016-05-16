package com.gint.app.bisis4.client.editor.uploadfile;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.gint.app.bisis4.textsrv.DocFile;

public class UploadedFilesTableModel extends AbstractTableModel {
	
	private List<DocFile> docFiles = new ArrayList<DocFile>();
	private String[] columns;
	
	public UploadedFilesTableModel(){
		docFiles = new ArrayList<DocFile>();
		columns = new String[4];
		columns[0] = "Id dokumenta";
		columns[1] = "Naziv dokumenta";
		columns[2] = "Oka\u010dio";
		columns[3] = "Tip dokumenta";		
	}
	
	public UploadedFilesTableModel(List<DocFile> docFiles){
		this.docFiles = docFiles;		
		columns = new String[4];
		columns[0] = "Id dokumenta";
		columns[1] = "Naziv dokumenta";
		columns[2] = "Oka\u010dio";
		columns[3] = "Tip dokumenta";
	}
	
	
	public void addDocFile(DocFile docFile){		
		docFiles.add(docFile);
		fireTableDataChanged();
	}
	

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.length;
	}
	
	public String getColumnName(int column){
		return columns[column];
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return docFiles.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {		
		DocFile docFile = docFiles.get(rowIndex);
		switch(columnIndex){
			case 0: return docFile.getId();
			case 1: return docFile.getFilename(); 
			case 2: return docFile.getUploader();
			case 3: return docFile.getContentType().substring(docFile.getContentType().indexOf("/")+1);
		}
		return null;
	}
	
	public DocFile getFileForRow(int row){
		return docFiles.get(row);
	}
	
	public void deleteFile(DocFile docFile){
		docFiles.remove(docFile);
		fireTableDataChanged();		
	}
	
	public void setDocFiles(List<DocFile> docFiles){
		this.docFiles = docFiles;
		fireTableDataChanged();
	}
	
	public boolean isFileExists(String name){
		for(DocFile df:docFiles){
			if(df.getFilename().equals(name))
				return true;
		}
		return false;
	}
	
	public DocFile getDocFile(String name){
		for(DocFile df:docFiles){
			if(df.getFilename().equals(name))
				return df;
		}
		return null;
	}
	
	

}
