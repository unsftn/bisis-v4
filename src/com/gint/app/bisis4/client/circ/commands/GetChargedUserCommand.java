package com.gint.app.bisis4.client.circ.commands;

import java.util.Iterator;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;


public class GetChargedUserCommand extends HibernateCommand {
	
	String ctlgno;
	Object[] user = null;
	
	
	public GetChargedUserCommand(String ctlgno){
		this.ctlgno = ctlgno;
	}
	
	public Object[] getUser(){
		return user;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			SQLQuery q = session.createSQLQuery("select sys_id from lending l where l.return_date is null and l.ctlg_no = :ctlgno");
			q.setString("ctlgno", ctlgno);
			Iterator it = q.list().iterator();
			if (it.hasNext()){
				Integer sysid = (Integer)it.next();
				q = session.createSQLQuery("select user_id, first_name, last_name from users u where u.sys_id = :sysid")
				.addScalar("user_id", Hibernate.STRING).addScalar("first_name", Hibernate.STRING).addScalar("last_name", Hibernate.STRING);
				q.setInteger("sysid", sysid);
				it = q.list().iterator();
				if (it.hasNext()){
					user = (Object[])it.next();
				}
			}
			tx.commit();
		}catch (Exception e){
			log.error("loading chargde user failed");
			log.error(e);
		}
	}

}
