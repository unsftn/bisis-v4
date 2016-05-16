package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.LockMode;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Location;



public class GetLastUserIDCommand extends HibernateCommand {
	
	Integer id;
	int lastId;
	
	
	public GetLastUserIDCommand(Integer id){
		this.id = id;
	}
	
	public int getLastID(){
		return lastId;
	}
	
	public void setLocation(Integer id){
		this.id = id;
	}

	@Override
	public void execute() {
		Transaction tx = session.beginTransaction();
		try{
			Location l = (Location)session.get(Location.class, id, LockMode.UPGRADE);
		    lastId = l.getLastUserId();
		    lastId++;
		    l.setLastUserId(lastId);
			tx.commit();
		}catch (Exception e){
	   		tx.rollback();
	   		lastId = 0;
			log.error(e);
		}
	}

}
