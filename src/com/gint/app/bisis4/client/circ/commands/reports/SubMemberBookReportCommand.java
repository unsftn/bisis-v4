package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.common.Utils;

public class SubMemberBookReportCommand extends HibernateCommand {
	
	/**
	 * podaci o uclanjenim korisnicima na dati datum i vrsti uclanjena
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public SubMemberBookReportCommand(Date start, Date end, Object location){
		this.start = start;
		this.end = end;
		this.location = location;
	}
	
	public List getList(){
		return list;
	}
	

	@Override
	public void execute() {
		try{
		 Transaction tx = session.beginTransaction();
		 Query crt;
		 if (!location.equals(" ")) {
				String loc = ((Location) location).getName();
				String query1 = "select u.userId, s.cost,mt.name from Users u "
						+ "inner join u.mmbrTypes as mt inner join u.signings as s "
						+ " where (s.signDate>= :start) and (s.signDate<= :end) and"
						+ " (s.location.name= :loc) order by mt.name";
				crt = session.createQuery(query1).setParameter("start", Utils.setMinDate(start),Hibernate.TIMESTAMP).setParameter("end", Utils.setMaxDate(end),Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select u.userId, s.cost,mt.name from Users u "
					+ "inner join u.mmbrTypes as mt inner join u.signings as s "
					+ " where (s.signDate>= :start) and (s.signDate<= :end)"
					+ " order by mt.name";
				crt = session.createQuery(query2).setParameter("start", Utils.setMinDate(start),Hibernate.TIMESTAMP).setParameter("end", Utils.setMaxDate(end),Hibernate.TIMESTAMP);
			}

			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("SubMemberBook report");
		}catch (Exception e){
			log.error("SubMemberBook report failed");
			log.error(e);
		}
	}

}
