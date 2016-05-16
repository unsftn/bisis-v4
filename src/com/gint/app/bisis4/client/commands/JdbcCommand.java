package com.gint.app.bisis4.client.commands;

import java.sql.Connection;

import com.gint.app.bisis4.commandservice.Command;
import com.gint.app.bisis4.commandservice.CommandType;

public abstract class JdbcCommand extends Command {

	protected Connection connection;
	
	public JdbcCommand(){
		this.type = CommandType.JDBC;
	}
	
	public void setContext(Object context){
		connection = (Connection)context;
	}

}
