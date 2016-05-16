package com.gint.app.bisis4.client.circ.commands;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import com.gint.app.bisis4.client.circ.model.WarningTypes;
import com.gint.app.bisis4.client.circ.model.Warnings;


public class GetWarnHistoryCommand extends HibernateCommand {
	
	Date startDate;
	Date endDate;
	WarningTypes wtype;
	List<Object[]> list = null;
	
	
	
	public GetWarnHistoryCommand(Date startDate, Date endDate, WarningTypes wtype){
		this.startDate = startDate;
		this.endDate = endDate;
		this.wtype = wtype;
	}
	
	public List<Object[]> getList(){
		return list;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			Criterion tmp1 = Expression.between("deadline", startDate, endDate);
			Criterion tmp2 = Expression.eq("warningTypes", wtype);
			Criteria crt = session.createCriteria(Warnings.class).add(Expression.and(tmp1, tmp2));
			crt.createAlias("lending", "l").createAlias("l.users", "u").createAlias("warningTypes", "w");
			crt.setProjection(Projections.projectionList()
	    		 .add(Projections.property("warnNo"))
	    		 .add(Projections.property("w.name"))
		         .add(Projections.property("wdate"))
		         .add(Projections.property("deadline"))
		         .add(Projections.property("u.userId"))
		         .add(Projections.property("u.lastName"))
		         .add(Projections.property("u.firstName"))
		         .add(Projections.property("l.ctlgNo"))
		         .add(Projections.property("l.returnDate"))
		         .add(Projections.property("note")));
			list = crt.list();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
