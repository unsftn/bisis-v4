package com.gint.app.bisis4.client.circ.commands;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.gint.app.bisis4.client.circ.model.WarnCounters;
import com.gint.app.bisis4.client.circ.model.WarningTypes;


public class GetWarnCountersCommand extends HibernateCommand {
	
	WarningTypes wtype;
	List list = null;
	
	public GetWarnCountersCommand(WarningTypes wtype){
		this.wtype = wtype;
	}
	
	public List getCounters(){
		return list;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			list = session.createCriteria(WarnCounters.class)
				.add(Expression.eq("warningTypes", wtype)).list();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
