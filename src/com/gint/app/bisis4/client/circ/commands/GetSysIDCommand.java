package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.SQLQuery;
import org.hibernate.Transaction;




public class GetSysIDCommand extends HibernateCommand {
	
	Integer sysid;
	String userID;
	
	public GetSysIDCommand(String userID){
		this.userID = userID;
	}
	
	public void setUserID(String userID){
		this.userID = userID;
	}
	
	public Integer getSysID(){
		return sysid;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			SQLQuery q = session.createSQLQuery("select sys_id from users u where u.user_id = :user_id");
			q.setString("user_id", userID);
			sysid = (Integer)q.uniqueResult();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
