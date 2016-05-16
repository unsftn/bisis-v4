package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.gint.app.bisis4.client.circ.model.Users;

public class GetAllUserDataCommand extends HibernateCommand {
	
	String userID;
	Users user = null;
	
	public GetAllUserDataCommand(String userID){
		this.userID = userID;
	}
	
	public void setUserID(String userID){
		this.userID = userID;
	}
	
	public Users getUser(){
		return user;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			Criteria crt = session.createCriteria(Users.class).setFetchMode("signings", FetchMode.JOIN).setFetchMode("alllendings", FetchMode.JOIN)
						.setFetchMode("duplicates", FetchMode.JOIN).setFetchMode("picturebooks", FetchMode.JOIN).setFetchMode("alllendings.warningses", FetchMode.JOIN);
			crt.add( Restrictions.eq("userId", userID));
			user = (Users)crt.uniqueResult();
			tx.commit();
			log.info("loading all user data: "+ userID);
	   	}catch (Exception e){
	   		log.error("loading all user data failed: " + userID);
	   		log.error(e.getMessage());
		}
	}

}
