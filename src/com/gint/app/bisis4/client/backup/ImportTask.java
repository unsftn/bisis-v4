package com.gint.app.bisis4.client.backup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import com.gint.app.bisis4.client.backup.dbmodel.Column;
import com.gint.app.bisis4.client.backup.dbmodel.ForeignKey;
import com.gint.app.bisis4.client.backup.dbmodel.Model;
import com.gint.app.bisis4.client.backup.dbmodel.ModelFactory;
import com.gint.app.bisis4.client.backup.dbmodel.PrimaryKey;
import com.gint.app.bisis4.client.backup.dbmodel.Table;

public class ImportTask extends SwingWorker<Integer,Integer>{
	
	 public ImportTask(String fileName, ImportDlg importDlg, boolean create) {
		    this.fileName = fileName;
		    this.importDlg = importDlg;
		    this.create=create;
		  }

//metod raspakuje bekap fajl i pravi objektni model baze
	 private static Model makeModel() {
		Model model=null;
		try {
	      ZipFile zis = new ZipFile(fileName);     
          Enumeration entries = zis.entries();
		  while(entries.hasMoreElements()){		  
		    ZipEntry ze = (ZipEntry) entries.nextElement();
		    if(!ze.getName().equals("database-model.xml"))
		    	continue;
		    model =ModelFactory.createModel(zis.getInputStream(ze));		      
		  }		  
		  zis.close();
		  return model;	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;			
		} catch (IOException e) {
			e.printStackTrace();
			return null;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
		}
	}
	
	private static int countNumberOfCommands(ZipFile zis){
		try{
		 BufferedReader br;
		 Enumeration entries = zis.entries();
		 int number=0;		 
		 while(entries.hasMoreElements()){
			  ZipEntry ze = (ZipEntry) entries.nextElement();
			  br = new BufferedReader(new InputStreamReader(zis.getInputStream(ze),"UTF8"));
			     while ((br.readLine()) != null) {
			        number++;
			     }
		   }
		 return number;
		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}		
	}
	
	private  void createSQL(Table t, ZipEntry ze,ZipFile zis, Connection conn){
		BufferedReader br;
		try{
			br = new BufferedReader(new InputStreamReader(zis.getInputStream(ze),"UTF8"));
			String insertColumn=getColumnsName(t);
			String line;
		 	String insert="INSERT INTO "+t.getName()+"("+insertColumn+") VALUES (" +makeQuestion(t.getColumns().size())+")";
			PreparedStatement insertStatement = conn.prepareStatement(insert);
		    while ((line=br.readLine()) != null) {
		    	   	String []parts=line.split("<sep>",-1);  
		    	  
		        	for (int i=0;i<parts.length-1;i++){	    
		        		if(parts[i].equals("")){
		        			insertStatement.setObject(i+1, null);
		         		}else{		         			
		        			insertStatement.setObject(i+1, parts[i]);
		        		}		                
		        	}
		         try {
                    insertStatement.execute(); 
                    count++;
                    publish(allnum,count);
                    conn.commit();  
		         } catch (Exception e) {
		 			JOptionPane.showMessageDialog(null, e.getMessage(), "GRESKA",JOptionPane.ERROR_MESSAGE);
		 			e.printStackTrace();			
		 		}	
		     }
		     insertStatement.close();
		     br.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "GRESKA",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();			
		}	
			  		
	}
	
	private void dropTable(List<Table> tables){
		String dropTable="drop table if exists ";
		Statement stat=getStatement();
		try{
	      for(int i=tables.size()-1;i>=0;i--){
			Table t=tables.get(i);
		    stat.execute(dropTable+t.getName());
		    conn.commit();
		  }
	      stat.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void createTable(List<Table> tables){
		String createTable;
		String createIndex;
		String alterTable="alter table ";
		Column column;
		Statement stat=getStatement();
	   try{
	    for(int i=0;i<tables.size();i++){
	    	createTable="create table ";
	    	Table t=tables.get(i);
			List <ForeignKey> foreignkeys=t.getForeignKeys();
			PrimaryKey primarykey=t.getPrimaryKey();
			List <Column> columnsPrim=primarykey.getColumns();
		    createTable=createTable+t.getName()+"(";
		    List<Column> columns=t.getColumns();
		    for(int j=0;j<columns.size()-1;j++){
		    	 column=columns.get(j);
		    	 createTable=createTable+" "+column+", "; 
		    }
		    createTable=createTable+" "+columns.get(columns.size()-1)+" "; 
		    createTable=createTable+", primary key ( ";
		    for(int j=0;j<columnsPrim.size()-1;j++){
		    	createTable=createTable+columnsPrim.get(j).getName()+" ,";
	        }
            createTable=createTable+columnsPrim.get(columnsPrim.size()-1).getName()+" ) ";
            createTable=createTable+" )";
		    stat.execute(createTable);
		    conn.commit();
		    
		     //kreira alter naredbe i index
		    for(int j=0;j<foreignkeys.size();j++){
		    	createIndex="CREATE INDEX ";
			    String ondelete=foreignkeys.get(j).getDeleteRule();
			    String onupdate=foreignkeys.get(j).getUpdateRule();
		    	List<Column> fkcolumns=foreignkeys.get(j).getFkColumns();
		    	List<Column> pkcolumns=foreignkeys.get(j).getPkColumns();
		    	createIndex=createIndex+foreignkeys.get(j).getName()+" ON "+t.getName()+" ( ";
		    	for(int k=0;k<fkcolumns.size()-1;k++){
		    		createIndex=createIndex+fkcolumns.get(k).getName()+" ,";
		    	}
		    	createIndex=createIndex+fkcolumns.get(fkcolumns.size()-1).getName()+" )";
		    
		    	alterTable="alter table ";	    	
		    	alterTable=alterTable+t.getName()+
		    	" add constraint "+foreignkeys.get(j).getName()+" foreign key (";
		    	for(int k=0;k<fkcolumns.size()-1;k++){
		    		alterTable=alterTable+fkcolumns.get(k).getName()+" ,";
		    	}
		    	alterTable=alterTable+fkcolumns.get(fkcolumns.size()-1).getName()+" )";
		    	alterTable=alterTable+" references "+foreignkeys.get(j).getPkTable().getName()+ " (";
		    	for(int k=0;k<pkcolumns.size()-1;k++){
		    		alterTable=alterTable+pkcolumns.get(k).getName()+" ,";
		    	}
		    	alterTable=alterTable+pkcolumns.get(pkcolumns.size()-1).getName()+" )";
		    	if(ondelete!=null && onupdate!=null){
		    		alterTable=alterTable+" on delete "+ondelete+" on update "+onupdate;
		    	}else if(ondelete!=null && onupdate==null){
		    		alterTable=alterTable+" on delete "+ondelete;
		    	}else if(ondelete==null && onupdate!=null){
		    		alterTable=alterTable+" on update "+onupdate;
		    	}
		    	
		    	stat.execute(createIndex);
				conn.commit();
			    stat.execute(alterTable);
			    conn.commit();
		    }   
		}
	    stat.close();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	}
	
	private void deleteTable(List<Table> tables){
		Statement stat=getStatement();
		String deleteTable="DELETE FROM ";
		try{
		 for(int i=tables.size()-1;i>=0;i--){
			Table t=tables.get(i);
			stat.execute(deleteTable+t.getName());
			conn.commit();
		 }
		 stat.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String getColumnsName(Table t){
		List<Column> columns=t.getColumns();
		String insertColumn="";
		for(int i=0;i<columns.size()-1;i++){
			insertColumn=insertColumn+columns.get(i).getName()+",";
		}
		insertColumn=insertColumn+columns.get(columns.size()-1).getName();
		return insertColumn;
	}
	
	private static String makeQuestion(int number){
		String questions="";
		for(int i=0;i<number-1;i++){
			questions=questions+"?,";
		}
		questions=questions+"?";
		return questions;
	}
	
	private Statement getStatement(){
    	try {
		  return conn.createStatement();
		 } catch (SQLException e) {
		 return null;
		 }
    }
	
	@Override
	protected Integer doInBackground() throws Exception {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(
				             importDlg.host.getText(),    
				             importDlg.username.getText(),
				             importDlg.password.getText());
		conn.setAutoCommit(false);

		Model model=makeModel();
	    ZipFile zis = new ZipFile(fileName);
	    Enumeration entries = zis.entries();
	    List<Table>tables=model.getHierarchicalTables();
	    Statement stat=getStatement();
		if(create){
			if (importDlg!=null){
			importDlg.status.setText("Bri\u0161e \u0161emu baze podataka ...");
			}else{
				System.out.println("Brise semu baze podataka ...");
			}
			dropTable(tables);
			if (importDlg!=null){
			importDlg.status.setText("Kreira \u0161emu baze podataka ...");
			}else{
				System.out.println("Kreira semu baze podataka ...");
			}
            createTable(tables);
        }
	    if(!create){  //ako su vec kreirane nove tabele nema potrebe da brisemo semu baze vec samo podatke
	    	if (importDlg!=null){
	    	importDlg.status.setText("Bri\u0161e podatke iz baze ...");
	    	}else{
	    		System.out.println("Brise podatke iz baze ...");
	    	}
	        deleteTable(tables);           
	    }
	    //importuje podatke u tabele
	    if (importDlg!=null){
	    importDlg.status.setText("Procenjuje trajanje importa ...");
	    }else{
    		System.out.println("Procenjuje trajanje importa ...");
    	}
	    allnum=countNumberOfCommands(zis);    
	    for(int i=0;i<tables.size();i++){
	       publish(tables.size(),i);
	       Table t=tables.get(i);
	       if (importDlg!=null){
	       importDlg.status.setText("Importuje podatke u tabelu "+t.getName()+" ...");
	       }else{
	    	   System.out.println("Importuje podatke u tabelu "+t.getName()+" ...");
	       }
	       while(entries.hasMoreElements()){
			  ZipEntry ze = (ZipEntry) entries.nextElement();
			  String fileName=ze.getName().replace(".tbl", "");
			  if(!fileName.equalsIgnoreCase(t.getName()))
			    	continue;
			  createSQL(t,ze,zis,conn);
			  
		   }
		   entries = zis.entries();
		 }
	     stat.close();
	     conn.close();         
	     return tables.size();
		}catch(Exception e){
			e.printStackTrace();
		 return -1;
		}
    }
	
	@Override
	  protected void process(List<Integer> tableCount) {
		if (importDlg!=null){
		 importDlg.progressBar.setMaximum(tableCount.get(0));
		 importDlg.progressBar.setValue(tableCount.get(1));
		 if(getState()==StateValue.DONE){
			 importDlg.progressBar.setValue(0);
			 importDlg.dispose();
			 JOptionPane.showMessageDialog(null, "Uspesno ste importovali podatke!", "INFO",JOptionPane.INFORMATION_MESSAGE);
			 System.exit(0);
		 }
	   }else{
		   if(getState()==StateValue.DONE)
			 System.exit(0);
	   }
	}
	  
	  @Override
	  protected void done() {
		if (importDlg!=null){
	     importDlg.btnOK.setEnabled(true);
	     importDlg.btnCancel.setEnabled(true);
	     importDlg.btnChooseFile.setEnabled(true);
	     importDlg.tfFileName.setEnabled(true);
	     importDlg.tfFileName.setText("");
	     importDlg.dispose();
	   }
      }
	  private static ImportDlg importDlg;
	  private static boolean create;
	  private static String fileName;
	  private static int count=0;
	  private static int allnum;
	  Connection conn;
	
}
