package com.gint.app.bisis4.client.editor.registries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.commands.ExecuteQueryCommand;
import com.gint.app.bisis4.client.commands.ExecuteUpdateCommand;
import com.gint.util.gui.SwingWorker;

public class RegistryTableModel extends AbstractTableModel {

  public RegistryTableModel(int registryType) {
    this.registryType = registryType;
    if (registryType == Registries.AUTORI || registryType == Registries.UDK)
      columnCount = 2;
    else
      columnCount = 1;

    switch (registryType) {
      case Registries.ODREDNICE:
        query = "SELECT pojam FROM registar_odr";
        count = "SELECT count(*) FROM registar_odr";
        insert = "INSERT INTO registar_odr (pojam) VALUES (?)";
        update = "UPDATE registar_odr SET pojam=? WHERE pojam=?";
        delete = "DELETE FROM registar_odr WHERE pojam=?";
        break;
      case Registries.PODODREDNICE:
        query = "SELECT pojam FROM registar_pododr";
        count = "SELECT count(*) FROM registar_pododr";
        insert = "INSERT INTO registar_pododr (pojam) VALUES (?)";
        update = "UPDATE registar_pododr SET pojam=? WHERE pojam=?";
        delete = "DELETE FROM registar_pododr WHERE pojam=?";
        break;
      case Registries.AUTORI:
        query = "SELECT autor, original FROM registar_autori";
        count = "SELECT count(*) FROM registar_autori";
        insert = "INSERT INTO registar_autori (autor,original) VALUES (?,?)";
        update = "UPDATE registar_autori SET autor=?,original=? WHERE autor=?";
        delete = "DELETE FROM registar_autori WHERE autor=?";
        break;
      case Registries.KOLEKTIVNI:
        query = "SELECT kolektivac FROM registar_kolektivni";
        count = "SELECT count(*) FROM registar_kolektivni";
        insert = "INSERT INTO registar_kolektivni (kolektivac) VALUES (?)";
        update = "UPDATE registar_kolektivni SET kolektivac=? WHERE kolektivac=?";
        delete = "DELETE FROM registar_kolektivni WHERE kolektivac=?";
        break;
      case Registries.ZBIRKE:
        query = "SELECT naziv FROM registar_zbirke";
        count = "SELECT count(*) FROM registar_zbirke";
        insert = "INSERT INTO registar_zbirke (naziv) VALUES (?)";
        update = "UPDATE registar_zbirke SET naziv=? WHERE naziv=?";
        delete = "DELETE FROM registar_zbirke WHERE naziv=?";
        break;
      case Registries.UDK:
        query = "SELECT grupa, opis FROM registar_udk";
        count = "SELECT count(*) FROM registar_udk";
        insert = "INSERT INTO registar_udk (grupa,opis) VALUES (?,?)";
        update = "UPDATE registar_udk SET grupa=?,opis=? WHERE grupa=?";
        delete = "DELETE FROM registar_udk WHERE grupa=?";
        break;
      default:
        break;
    }
  }
  
  public void init(RegistryDlg dlg) {
    final RegistryDlg dlg2 = dlg;
    dlg.getGlassPane().setVisible(true);
    final SwingWorker worker = new SwingWorker() {
      public Object construct() {
        return new InitTask(RegistryTableModel.this, dlg2);
      }
    };
    worker.start();
  }
  
  public int getRowCount() {
    return rows.size();
  }
  
  public Object getValueAt(int row, int col) {
    RegistryItem item = (RegistryItem)rows.get(row);
    if (item == null)
      return "";
    if (col == 0)
      return item.getText1();
    else
      return item.getText2();
  }
  
  public int getColumnCount() {
    return columnCount;
  }
  
  public boolean isCellEditable(int row, int col) {
    return false;
  }
  
  public void clear() {
    rows.clear();
  }
  
  public RegistryItem getRow(int index) {
    return (RegistryItem)rows.get(index);
  }
  
  public void setRow(int index, RegistryItem item) {
    rows.set(index, item);
  }
  
  public void addRow(RegistryItem item) {
    try {
//      PreparedStatement stmt = 
//        RegistryDlg.getConnection().prepareStatement(insert);
//      stmt.setString(1, item.getText1());
//      if (registryType == Registries.AUTORI || registryType == Registries.UDK)
//        stmt.setString(2, item.getText2());
//      stmt.executeUpdate();
//      //RegistryDlg.getConnection().commit();
//      stmt.close();
     
      int ind = insert.indexOf('?');
      String insert2 = insert.substring(0, ind);
      insert2 = insert2 + "'" + item.getText1() + "'";
      if (registryType == Registries.AUTORI || registryType == Registries.UDK){
    	  insert2 = insert2 + ",'"+ item.getText2() + "'";
      }
      insert2 = insert2 + ")";
    //  System.out.println(insert2);
	  ExecuteUpdateCommand command = new ExecuteUpdateCommand();
	  command.setSqlString(insert2);
	  command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);
	  if (command != null){
		  rows.add(item);
	      fireTableRowsInserted(rows.size() - 1, rows.size() - 1); 
	  }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void deleteRow(int index) {
    try {
//      RegistryItem item = getRow(index);
//      PreparedStatement stmt = 
//        RegistryDlg.getConnection().prepareStatement(delete);
//      stmt.setString(1, item.getText1());
//      stmt.executeUpdate();
//      //RegistryDlg.getConnection().commit();
      
    	RegistryItem item = getRow(index);
    	int ind = delete.indexOf('?');
        String delete2 = delete.substring(0, ind);
        delete2 = delete2 + "'" + item.getText1() + "'";
        
      //  System.out.println(delete2);
  	    ExecuteUpdateCommand command = new ExecuteUpdateCommand();
  	    command.setSqlString(delete2);
  	    command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);
	  	if (command != null){
	  		 rows.remove(index);
	  	     fireTableRowsDeleted(index, index);
	  	}
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void updateRow(int index, RegistryItem item) {
    int i = 1;
    try {
      RegistryItem old = getRow(index);
//      PreparedStatement stmt = 
//        RegistryDlg.getConnection().prepareStatement(update);
//      stmt.setString(i++, item.getText1());
//      if (registryType == Registries.AUTORI || registryType == Registries.UDK)
//        stmt.setString(i++, item.getText2());
//      stmt.setString(i, old.getText1());
//      stmt.executeUpdate();
//      //RegistryDlg.getConnection().commit();
      
      
      int ind = update.indexOf('?');
      int ind2;
      String update2 = update.substring(0, ind);
      update2 = update2 + "'" + item.getText1() + "'";
      if (registryType == Registries.AUTORI || registryType == Registries.UDK){
    	  ind2 = update.indexOf('?', ind+1);
    	  update2 = update2 + update.substring(ind+1, ind2) + "'"+ item.getText2() + "'";
    	  ind = ind2;
      }
      ind2 = update.indexOf('?', ind+1);
      update2 = update2 + update.substring(ind+1, ind2) + "'"+ old.getText1() + "'";
      
     // System.out.println(update2);
	  ExecuteUpdateCommand command = new ExecuteUpdateCommand();
	  command.setSqlString(update2);
	  command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);
	  if (command != null){ 
	      rows.set(index, item);
	      fireTableRowsUpdated(index, index);
	  }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public int searchRow(RegistryItem item) {
    for (int i = 0; i < rows.size(); i++) {
      RegistryItem ri = getRow(i);
      if (ri.isLike(item))
        return i;
    }
    return -1;
  }
  
  public void sort(Comparator comparator) {
    Collections.sort(rows, comparator);
    fireTableDataChanged();
  }
  
  public int getRegistryType() {
    return registryType;
  }
  
  public ArrayList<ArrayList<Object>> getResults() {
    return results;
  }
  
  public class InitTask {
    public InitTask(RegistryTableModel model, RegistryDlg dlg) {
      try {
    	ExecuteQueryCommand command = new ExecuteQueryCommand();
  	    command.setSqlString(count);
  	    command = (ExecuteQueryCommand)BisisApp.getJdbcService().executeCommand(command);
  	    int size = ((Long)command.getResults().get(0).get(0)).intValue();
  	    command.clear();
  	    
//        stmt = RegistryDlg.getConnection().createStatement();
//        rset = stmt.executeQuery(count);
//        rset.next();
//        int size = rset.getInt(1);
//        rset.close();
        dlg.progressBar.setMinimum(0);
        dlg.progressBar.setMaximum(size);
        dlg.progressBar.setString("u\u010ditavam podatke");
        dlg.progressBar.setVisible(true);
        
        command.setSqlString(query);
  	    command = (ExecuteQueryCommand)BisisApp.getJdbcService().executeCommand(command);
  	    results = command.getResults();
  	    for (int i = 0; i<results.size(); i++){
  	    	dlg.progressBar.setValue(i+1);
  	    	RegistryItem item = new RegistryItem();
  	    	item.setIndex(i);
  	    	item.setText1((String)results.get(i).get(0));
  	    	if (registryType == Registries.AUTORI || registryType == Registries.UDK)
            item.setText2((String)results.get(i).get(1));
  	    	rows.add(item);
  	    }
  	    command.clear();
//  	    rset = stmt.executeQuery(query);
//  	    int rowCount = 0;
//        while (rset.next()) {
//          dlg.progressBar.setValue(rowCount++);
//          RegistryItem item = new RegistryItem();
//          item.setIndex(rset.getRow());
//          item.setText1(rset.getString(1));
//          if (registryType == Registries.AUTORI || registryType == Registries.UDK)
//            item.setText2(rset.getString(2));
//          rows.add(item);
//        }
//        rset.close();
//        stmt.close();
        dlg.progressBar.setString("sortiram");
        sort(RegistryUtils.getLatComparator());
        dlg.progressBar.setVisible(false);
        dlg.getGlassPane().setVisible(false);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    
  }
  
  private int registryType;
  private int columnCount;
  private Vector rows = new Vector();
//  private Statement stmt;
//  private ResultSet rset;
  private ArrayList<ArrayList<Object>> results;
  
  private String query;
  private String count;
  private String insert;
  private String update;
  private String delete;
}
