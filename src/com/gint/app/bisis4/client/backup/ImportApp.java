package com.gint.app.bisis4.client.backup;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gint.app.bisis4.client.backup.BackupTask;

public class ImportApp {
	public static void main(String[] args) throws Exception{
		
		if (args.length==0){
			  ImportDlg importD=new ImportDlg();
			  importD.setVisible(true);
		}else if(args.length != 5 && args.length != 6){
			System.out.println("import: <address> <database> <user> <password> <path> [<create>] ");
		  return;
		}else{	
		   String address=args[0];
	       String database=args[1];
	       String user=args[2];
	       String password=args[3];
	       String fileName =args[4];
	       boolean create=false;
           if (args.length == 6)
			  create=true;
	
           Class.forName("com.mysql.jdbc.Driver");
           Connection conn = DriverManager.getConnection(
           "jdbc:mysql://"+address+"/"+database, user, password);
           conn.setAutoCommit(false);
           ImportTask task = new ImportTask(fileName, null,create);
           task.execute();
	    }
	 }
  }
