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


public class GetUsersForArchiveCommand extends HibernateCommand {
	
	Date date;
	List<String> list = null;
	
	
	
	public GetUsersForArchiveCommand(Date date){
		this.date = date;
	}
	
	public List<String> getList(){
		return list;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();

			String query = "SELECT distinct(u.user_id) FROM users u,signing s where u.sys_id=s.sys_id and :date > all(select s1.until_date from signing s1 where u.sys_id=s1.sys_id) and not exists (select l.sys_id from lending l where l.sys_id = u.sys_id and return_date is null)";
			Query q = session.createSQLQuery(query);
			q.setParameter("date",date, Hibernate.DATE);
			list = q.list();
			query = "SELECT u.user_id FROM users u where not exists(select s.sys_id from signing s where u.sys_id=s.sys_id) and not exists (select l.sys_id from lending l where l.sys_id = u.sys_id and return_date is null)";
			q = session.createSQLQuery(query);
			list.addAll(q.list());
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
