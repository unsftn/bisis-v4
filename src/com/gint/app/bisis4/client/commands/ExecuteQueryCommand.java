package com.gint.app.bisis4.client.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExecuteQueryCommand extends JdbcCommand {
	
	String sqlstr;
	ArrayList<ArrayList<Object>> res = new ArrayList<ArrayList<Object>>();
	
	public void setSqlString(String str){
		sqlstr = str;
	}
	
	public ArrayList<ArrayList<Object>> getResults(){
		return res;
	}
	
	public void clear(){
		sqlstr = null;
		if (res!=null){
		   res.clear();
		}else{
			res=new ArrayList<ArrayList<Object>>();
		}
		
	}

	@Override
	public void execute() {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(sqlstr);
			int col = rset.getMetaData().getColumnCount();
			while (rset.next()) {
				ArrayList<Object> row = new ArrayList<Object>();
				for (int i = 0; i < col; i++){
					row.add(i, rset.getObject(i+1));
				}
				res.add(row);
			}
			connection.commit();
			log.info("execute query successfully: " + sqlstr);
		} catch (SQLException e) {
			try {
				connection.rollback();
				res = null;
				log.error("execute query failed: " + e.getMessage());
			} catch (SQLException e1) {}
		}

	}

}
