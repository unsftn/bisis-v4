package com.gint.app.bisis4.client.circ.commands;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;





public class SaveObjectsCommand extends HibernateCommand {
	
	List list = null;
	boolean success = false;
	String message = null;
	
	public SaveObjectsCommand(){
	}
	
	public SaveObjectsCommand(List list){
		this.list = list;
	}
	
	public void setList(List list){
		this.list = list;
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
			for (Object item : list){
				session.saveOrUpdate(item);
			}
			tx.commit();
			success = true;
			log.info("saving objects successfully: " + list.get(0).getClass());
		} catch (HibernateException e) {
			tx.rollback();
			log.error("saving objects failed: " + list.get(0).getClass());
			log.error(e);
			message = e.getMessage();
		}
	}

}
