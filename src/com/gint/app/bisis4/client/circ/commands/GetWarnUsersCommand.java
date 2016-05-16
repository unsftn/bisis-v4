package com.gint.app.bisis4.client.circ.commands;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.Users;


public class GetWarnUsersCommand extends HibernateCommand {
	
	Date startDate;
	Date endDate;
	Location loc;
	List<Users> list = null;
	
	
	
	public GetWarnUsersCommand(Date startDate, Date endDate, Location loc){
		this.startDate = startDate;
		this.endDate = endDate;
		this.loc = loc;
	}
	
	public List<Users> getList(){
		return list;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
//			Criterion lending = Expression.between("deadline", startDate, endDate);
//	    if (loc != null){
//	      Criterion tmp = Expression.eq("location", loc);
//	      lending = Expression.and(lending, tmp);
//	    }
//	    Criteria crt = session.createCriteria(Users.class);
//	    crt.add(Restrictions.eq("warningInd", 1)).createCriteria("lendings").add(lending).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//	    list = crt.list();
			String query = "select distinct u from Users u inner join fetch u.lendings l where u.warningInd = :warningInd and l.deadline between :startDate and :endDate";
			if (loc != null){
				query = query + " and l.location = :loc";
			}
			Query q = session.createQuery(query);
			q.setInteger("warningInd", 1);
			q.setParameter("startDate",startDate, Hibernate.TIMESTAMP);
			q.setParameter("endDate", endDate, Hibernate.TIMESTAMP);
			if (loc != null){
				q.setParameter("loc", loc);
			}
			list = q.list();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
