package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Users;

public class MemberByGroupReportCommand extends HibernateCommand {
	
	/**
	 * podavi o individualnim clanovima koji pripadaju kolektivnim clanovima
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	Object group;
	
	public MemberByGroupReportCommand(Date start, Date end, Object location, Object group){
		this.start = start;
		this.end = end;
		this.location = location;
		this.group = group;
	}
	
	public List getList(){
		return list;
	}
	

	@Override
	public void execute() {
		try{
		  Transaction tx = session.beginTransaction();
		  Criteria crt = session.createCriteria(Users.class);
			Criteria crt1 = crt.createCriteria("signings");
			crt1.add(Expression.between("signDate", start,end));
			crt.add(Expression.eq("groups", group));
			if (!location.equals(" ")) {
				crt1.add(Expression.eq("location", location));
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("MemberByGroup report");
		}catch (Exception e){
			log.error("MemberByGroup report failed");
			log.error(e);
		}
	}

}
