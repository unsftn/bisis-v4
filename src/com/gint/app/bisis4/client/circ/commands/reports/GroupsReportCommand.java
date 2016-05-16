package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Groups;
import com.gint.app.bisis4.client.circ.model.Location;


public class GroupsReportCommand extends HibernateCommand {
	
	/**
	 * spisak kolektivnih clanova
	 */
	
	List list = null;
	String location;
	
	public GroupsReportCommand(String location){
		this.location = location;
	}
	
	public List getList(){
		return list;
	}
	

	@Override
	public void execute() {
		try{
		  Transaction tx = session.beginTransaction();
		  Criteria crt = session.createCriteria(Groups.class);
//			if (!location.equals(" ")) {
//				Criteria crt1 = crt.createCriteria("userses");
//				Criteria crt2 = crt1.createCriteria("signings");
//				crt2.add(Expression.eq("location", location));
//			}
		  if (!location.equals("")) {  
			  crt.add(Expression.like("userId", location+"%"));
		  }
			if (crt != null) {	
				list = crt.list();
			}
		  tx.commit();
		  log.info("Groups report");
		}catch (Exception e){
			log.error("Groups report failed");
			log.error(e);
		}
	}

}
