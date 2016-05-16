package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Groups;


public class GetGroupCommand extends HibernateCommand {
	
	String userID;
	Groups group = null;
	
	public GetGroupCommand(String userID){
		this.userID = userID;
	}
	
	public Groups getUser(){
		return group;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("from Groups u where u.userId = :userid");
			q.setString("userid", userID);
			group = (Groups)q.uniqueResult();
			tx.commit();
			log.info("loading group: "+ userID);
		}catch (Exception e){
			log.error("loading group failed: "+ userID);
			log.error(e.getMessage());
		}
	}

}
