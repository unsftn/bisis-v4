package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.Session;

import com.gint.app.bisis4.commandservice.Command;
import com.gint.app.bisis4.commandservice.CommandType;


public abstract class HibernateCommand extends Command {
	
	protected Session session;
	
	public HibernateCommand(){
		this.type = CommandType.HIBERNATE;
	}
	
	public void setContext(Object context){
		session = (Session)context;
	}
	
}
