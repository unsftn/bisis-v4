package com.gint.app.bisis4.client.circ.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.DeleteObjectCommand;
import com.gint.app.bisis4.client.circ.commands.GetAllCommand;
import com.gint.app.bisis4.client.circ.commands.GetAllMembershipCommand;
import com.gint.app.bisis4.client.circ.commands.SaveObjectCommand;
import com.gint.app.bisis4.client.circ.model.MmbrTypes;
import com.gint.app.bisis4.client.circ.model.UserCategs;
import com.gint.app.bisis4.client.circ.model.Membership;


public class MmbrshipCoderTableModel extends AbstractTableModel implements Serializable {


    protected List<Membership>  data;
    protected List<String>  columnIdentifiers;

    public MmbrshipCoderTableModel() {
    	columnIdentifiers = new ArrayList<String>();
        columnIdentifiers.add("Vrsta \u010dlanstva");
    	columnIdentifiers.add("Kategorija korisnika");   	
        columnIdentifiers.add("Cena");
        GetAllMembershipCommand getAll = new GetAllMembershipCommand();
  		getAll = (GetAllMembershipCommand)Cirkulacija.getApp().getService().executeCommand(getAll);
        data = getAll.getList();
    }
    
//		 Manipulating rows
    
	    public void addRow(MmbrTypes mmbrTypes, UserCategs userCateg, Double cost) {
	    	int row = getRowCount();
	    	Membership rowData = null;
        
	        for (Membership m : data){
	          if (m.getMmbrTypes().getId() == mmbrTypes.getId() && m.getUserCategs().getId() == userCateg.getId()){
	            rowData = m;
	          }
	        }
	        if (rowData == null){
	          rowData = new Membership();
	          rowData.setUserCategs(userCateg);
	          rowData.setMmbrTypes(mmbrTypes);
	          rowData.setCost(cost);
	          SaveObjectCommand save = new SaveObjectCommand(rowData);
	          save = (SaveObjectCommand)Cirkulacija.getApp().getService().executeCommand(save);
	          if (save.isSaved()){
	            data.add(rowData);
	            fireTableRowsInserted(row, row);
	          }
	        }else{
	          rowData.setCost(cost);
	          SaveObjectCommand save = new SaveObjectCommand(rowData);
	      		save = (SaveObjectCommand)Cirkulacija.getApp().getService().executeCommand(save);
	          if (save.isSaved())
	            fireTableRowsUpdated(0, row);
	        }
	    	
	    }


	    public void removeRows(int[] rows) {
        for (int i = rows.length-1; i >= 0; i--){
          Membership m = data.get(rows[i]);
          DeleteObjectCommand delete = new DeleteObjectCommand(m);
          delete = (DeleteObjectCommand)Cirkulacija.getApp().getService().executeCommand(delete);
          if (delete.isSaved()){
            data.remove(rows[i]);
            fireTableRowsDeleted(rows[i], rows[i]);
          }
        }
	    }


		//
//		 Implementing the TableModel interface
		//

	    public int getRowCount() {
	        return data.size();
	    }

	    public int getColumnCount() {
	        return columnIdentifiers.size();
	    }

	    public String getColumnName(int column) {
	        Object id = null; 
		// This test is to cover the case when 
		// getColumnCount has been subclassed by mistake ... 
		if (column < columnIdentifiers.size()) {  
		    id = columnIdentifiers.get(column); 
		}
	        return (id == null) ? super.getColumnName(column) 
	                            : id.toString();
	    }
      
      public void setColumnName(int column, String text) {
        if (column < columnIdentifiers.size()) {  
          columnIdentifiers.set(column, text); 
          fireTableStructureChanged();
        }
      }

	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }

	    public Object getValueAt(int row, int column) {
	        Membership rowData = (Membership)data.get(row);
	        switch (column){
            	case 0: return rowData.getMmbrTypes().getName();
	        	case 1: return rowData.getUserCategs().getName();
	        	case 2: return rowData.getCost();
	        	default: return null;
			}
	    }

	    public Class getColumnClass(int col) {
			switch (col){
			 case 0: return String.class;
			 case 1: return String.class;
       case 2: return Double.class;
			 default: return String.class;
			}
	    }
}
