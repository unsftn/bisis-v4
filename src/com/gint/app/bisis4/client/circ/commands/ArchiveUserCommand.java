package com.gint.app.bisis4.client.circ.commands;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ReplicationMode;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Users;
import com.gint.app.bisis4.commandservice.CommandType;


public class ArchiveUserCommand extends HibernateCommand {
	
	Users user = null;
	List children = null;
	Serializable staleID = null;
	String staleName = null;
	boolean success = false;
	String message = null;
	String userID;
	
	public ArchiveUserCommand(){
		this.type = CommandType.HIBERNATEARCHIVE;
	}
	
	public void setUser(Users user){
		this.user = user;
	}
	
	public Users getUser(){
		return user;
	}
	
	public void setChildren(List children){
		this.children = children;
	}
	
	public void setUserID(String userID){
		this.userID = userID;
	}
	
	public boolean isSaved(){
		return success;
	}
	
	public Serializable getStaleID(){
		return staleID;
	}
	
	public String getStaleName(){
		return staleName;
	}
	
	public String getMessage(){
		return message;
	}

	@Override
	public void execute() {
		Transaction tx = session.beginTransaction();
		try{			
			if (user != null){
				session.replicate(user, ReplicationMode.OVERWRITE);
			}
			
			if (children != null){
		      Iterator it2 = children.iterator();
		      while(it2.hasNext()){
		    	  session.replicate(it2.next(), ReplicationMode.OVERWRITE);
		      }
			}
			tx.commit();
			success = true;
			log.info("archiving user successfully: " + userID);
	   	}catch (StaleObjectStateException e){
	   		tx.rollback();
	   		log.error("archiving user failed: " + userID);
	   		log.error(e);
	   		staleID = e.getIdentifier();
	   		staleName = e.getEntityName();
	   		message = e.getMessage();
		} catch (HibernateException e) {
			tx.rollback();
			log.error("archiving user failed: " + userID);
			log.error(e);
			message = e.getMessage();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
