package com.gint.app.bisis4.client.circ.commands;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Users;


public class SaveUserCommand extends HibernateCommand {
	
	Users user = null;
	List books = null;
	List children = null;
	List removedchildren = null;
	Serializable staleID = null;
	String staleName = null;
	boolean success = false;
	String message = null;
	String userID;
	
	public void setUser(Users user){
		this.user = user;
	}
	
	public Users getUser(){
		return user;
	}
	
	public void setChildren(List children){
		this.children = children;
	}
	
	public void setRemovedChildren(List removedchildren){
		this.removedchildren = removedchildren;
	}
	
	public void setBooks(List books){
		this.books = books;
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
			if (books != null){
			  Iterator it1 = books.iterator();
		      while(it1.hasNext()){
		        session.update(it1.next());
		      }
			}
			
			if (user != null){
				session.saveOrUpdate(user);
			}
			
			if (children != null){
		      Iterator it2 = children.iterator();
		      while(it2.hasNext()){
		    	  session.saveOrUpdate(it2.next());
		      }
			}
			
			if (removedchildren != null){
		      Iterator it3 = removedchildren.iterator();
		      while(it3.hasNext()){
		    	  session.delete(it3.next());
		      }
			}
			
			tx.commit();
			success = true;
			log.info("saving user successfully: " + userID);
	   	}catch (StaleObjectStateException e){
	   		tx.rollback();
	   		log.error("saving user failed: " + userID);
	   		log.error(e);
	   		staleID = e.getIdentifier();
	   		staleName = e.getEntityName();
	   		message = e.getMessage();
		} catch (HibernateException e) {
			tx.rollback();
			log.error("saving user failed: " + userID);
			log.error(e);
			message = e.getMessage();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
