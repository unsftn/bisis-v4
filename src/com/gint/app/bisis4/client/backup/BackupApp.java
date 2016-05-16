package com.gint.app.bisis4.client.backup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.gint.app.bisis4.client.backup.dbmodel.Model;
import com.gint.app.bisis4.client.backup.dbmodel.ModelFactory;
import com.gint.app.bisis4.client.backup.dbmodel.Table;

public class BackupApp {

	
	public static void main(String[] args) throws Exception{
		String fileName = "";
		if(args.length != 4 && args.length != 5){
			System.out.println("backup: <address> <database> <user> <password> [<path>]");
		  return;
		}
		String address=args[0];
		String database=args[1];
		String user=args[2];
		String password=args[3];
    
		if (args.length == 5){
			fileName = args[4];
			File dir = new File(fileName);
	    if (!dir.isDirectory()){
	    	System.out.println(fileName + " is not directory");
			  return;
	    }
	    if (!fileName.endsWith("/")){
	    	fileName = fileName + "/";
	    }
		}
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
        "jdbc:mysql://"+address+"/"+database, user, password);
		conn.setAutoCommit(false);
    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String date = sdf.format(new Date());
		fileName = fileName + "backup-"+date+".zip";
		//BackupTask task = new BackupTask(fileName, null, conn);
		//task.execute();
		System.out.println("Backup in process...");
		try {
			Model model = ModelFactory.createModel(conn);
			ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(fileName)));
			zip.putNextEntry(new ZipEntry("backup-date"));
			zip.write(sdf.format(new Date()).getBytes("UTF8"));
			zip.closeEntry();
			zip.putNextEntry(new ZipEntry("database-model.xml"));
			ModelFactory.saveModel(model, zip);
			zip.closeEntry();
			int i = 3;
			BackupActions actions = new BackupActions();
			for (Table t : model.getHierarchicalTables()) {
				zip.putNextEntry(new ZipEntry(t.getName() + ".tbl"));
				actions.saveTableData(conn, t, zip);
				zip.closeEntry();
			}
			zip.close();
			System.out.println("Backup is done!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
