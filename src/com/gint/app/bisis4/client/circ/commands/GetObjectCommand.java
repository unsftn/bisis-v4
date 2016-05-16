package com.gint.app.bisis4.client.circ.commands;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Users;


public class GetObjectCommand extends HibernateCommand {
	
	Serializable identifier;
	String entityName;
	Object object = null;
	
	
	public GetObjectCommand(Serializable identifier, String entityName){
		this.identifier = identifier;
		this.entityName = entityName;
	}
	
	public Object getObject(){
		return object;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			object = session.load(entityName, identifier);
			tx.commit();
			log.info("loading object: "+ entityName);
		}catch (Exception e){
			log.error("loading object failed: "+ entityName);
			log.error(e.getMessage());
		}
	}

}
