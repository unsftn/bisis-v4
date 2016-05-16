package com.gint.app.bisis4.client.backup;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.gint.app.bisis4.client.backup.dbmodel.Model;
import com.gint.app.bisis4.client.backup.dbmodel.ModelFactory;
import com.gint.app.bisis4.client.backup.dbmodel.Table;

public class BackupTask extends SwingWorker<Integer, Integer> {

	public BackupTask(String fileName, BackupDlg backupDlg, Connection conn) {
		this.fileName = fileName;
		this.backupDlg = backupDlg;
		this.conn = conn;
	}

	@Override
	public Integer doInBackground() {
		try {
			Model model = ModelFactory.createModel(conn);
			int fileCount = model.getHierarchicalTables().size() + 2;
			publish(fileCount, 0);
			zip = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(fileName)));
			zip.putNextEntry(new ZipEntry("backup-date"));
			zip.write(sdf.format(new Date()).getBytes("UTF8"));
			zip.closeEntry();
			publish(fileCount, 1);
			zip.putNextEntry(new ZipEntry("database-model.xml"));
			ModelFactory.saveModel(model, zip);
			zip.closeEntry();
			publish(fileCount, 2);
			int i = 3;
			BackupActions actions = new BackupActions();
			for (Table t : model.getHierarchicalTables()) {
				publish(fileCount, i++);
				zip.putNextEntry(new ZipEntry(t.getName() + ".tbl"));
				actions.saveTableData(conn, t, zip);
				zip.closeEntry();
			}
			zip.close();
			return model.getHierarchicalTables().size() + 2;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null, "Greska! " + e.getMessage(),
					"Greska", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			return -1;

		}
	}

	@Override
	protected void process(List<Integer> tableCount) {
		if (backupDlg != null) {
			backupDlg.progressBar.setMaximum(tableCount.get(0));
			backupDlg.progressBar.setValue(tableCount.get(1));
		/*	if (getState() == StateValue.DONE) {
				backupDlg.progressBar.setValue(0);
				JOptionPane.showMessageDialog(null,
						"Uspesno ste exportovali podatke!", "INFO",
						JOptionPane.INFORMATION_MESSAGE);
			}*/
		}
	}

	@Override
	protected void done() {
		if (backupDlg != null) {
			backupDlg.btnOK.setEnabled(true);
			backupDlg.btnCancel.setEnabled(true);
			backupDlg.progressBar.setValue(0);
			backupDlg.btnChooseFile.setEnabled(true);
			backupDlg.tfFileName.setEnabled(true);
			backupDlg.tfFileName.setText("");
			backupDlg.dispose();
			JOptionPane.showMessageDialog(null,
					"Uspesno ste exportovali podatke!", "INFO",
					JOptionPane.INFORMATION_MESSAGE);

		}
	}

	

	private BackupDlg backupDlg;
	private String fileName;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private ZipOutputStream zip;
	private Connection conn;
}
