package com.gint.app.bisis4.client.commands;

import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteUpdateCommand extends JdbcCommand {
	
	String sqlstr;
	int rows;
	Exception ex;
	
	public void setSqlString(String str){
		sqlstr = str;
	}
	
	public int getRowCount(){
		return rows;
	}
	
	public Exception getException(){
		return ex;
	}

	@Override
	public void execute() {
		try {
			Statement stmt = connection.createStatement();
			rows = stmt.executeUpdate(sqlstr);
			connection.commit();
			log.info("execute update successfully: " + sqlstr);
		} catch (SQLException e) {
			try {
				connection.rollback();
				ex = e;
				log.error("execute update failed: " + sqlstr + " ; " + e.getMessage());
			} catch (SQLException e1) {}
		}

	}

}
