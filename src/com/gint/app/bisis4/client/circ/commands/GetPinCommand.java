package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.SQLQuery;
import org.hibernate.Transaction;




public class GetPinCommand extends HibernateCommand {
	
	Integer sysid;
	String pin;
	
	public GetPinCommand(String pin){
		this.pin = pin;
	}
	
	public void setUserID(String pin){
		this.pin = pin;
	}
	
	public Integer getSysID(){
		return sysid;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			SQLQuery q = session.createSQLQuery("select sys_id from users u where u.pass = :pin");
			q.setString("pin", pin);
			sysid = (Integer)q.uniqueResult();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
