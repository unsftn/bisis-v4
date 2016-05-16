package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;


public class DeleteObjectCommand extends HibernateCommand {
	
	Object object = null;
	boolean success = false;
	String message = null;
	
	public DeleteObjectCommand(Object object){
		this.object = object;
	}
	
	public void setObject(Object object){
		this.object = object;
	}
	
	public boolean isSaved(){
		return success;
	}
	
	public String getMessage(){
		return message;
	}

	@Override
	public void execute() {
		
		Transaction tx = session.beginTransaction();
		try{
			session.delete(object);
			tx.commit();
			success = true;
			log.info("deleting object successfully: " + object.getClass());
		} catch (HibernateException e) {
			tx.rollback();
			log.error("deleting object failed: " + object.getClass());
			log.error(e);
			message = e.getMessage();
		}
	}

}
