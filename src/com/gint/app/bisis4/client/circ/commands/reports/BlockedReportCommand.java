package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Users;

public class BlockedReportCommand extends HibernateCommand {
	
	/** blokirane kartice
   * 
   * @param location
   * @return
   */
	
	List list = null;
	Object location;
	
	public BlockedReportCommand(Object location){
		this.location = location;
	}
	
	public List getList(){
		return list;
	}
	

	@Override
	public void execute() {
		try{
		  Transaction tx = session.beginTransaction();
		  Criteria crt = session.createCriteria(Users.class);
			crt.add(Expression.isNotNull("blockReasons"));
			crt.add(Expression.not(Expression.like("blockReasons","")));
			crt.addOrder(Order.desc("blockReasons"));
			if (!location.equals(" ")) {
				crt.createAlias("signings", "s");
				crt.add(Expression.eq("s.location", location));
				crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			}
			if (crt != null) {	
				list = crt.list();
			}
		  tx.commit();
		  log.info("Blocked report");
		}catch (Exception e){
			log.error("Blocked report failed");
			log.error(e);
		}
	}

}
