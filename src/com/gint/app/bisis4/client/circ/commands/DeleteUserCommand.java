package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Users;


public class DeleteUserCommand extends HibernateCommand {
	
	int sys_id;
	boolean success = false;
	String message = null;
	
	public DeleteUserCommand(int sys_id){
		this.sys_id = sys_id;
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
			Object user = session.load(Users.class, sys_id);
			session.delete(user);
			tx.commit();
			success = true;
			log.info("deleting user successfully: sys_id " + sys_id);
		} catch (HibernateException e) {
			tx.rollback();
			log.error("deleting user failed: sys_id " + sys_id);
			log.error(e);
			message = e.getMessage();
		}
	}

}
