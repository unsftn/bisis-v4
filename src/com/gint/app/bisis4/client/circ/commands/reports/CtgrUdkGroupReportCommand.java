package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class CtgrUdkGroupReportCommand extends HibernateCommand {
	
	/**
	 * podaci o broju knjiga sortirane po UDK grupam koje su zaduzili korisnici iz odredjenih kategorija
	 * u datom periodu
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public CtgrUdkGroupReportCommand(Date start, Date end, Object location){
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
				String query1 = "select count(l.ctlgNo), uc.name from Users u "
						+ "inner join u.userCategs as uc inner join u.alllendings as l "
						+ " where (l.lendDate between :start and :end)or(l.resumeDate between :start and :end) "+
						  "and"
						+ " (l.location.name= :loc)group by uc.name";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 =  "select count(l.ctlgNo), uc.name from Users u "
					+ "inner join u.userCategs as uc inner join u.alllendings as l "
					+ " where (l.lendDate between :start and :end)" +
					   "or(l.resumeDate between :start and :end) group by uc.name";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("CtgrUdkGroup report");
		}catch (Exception e){
			log.error("CtgrUdkGroup report failed");
			log.error(e);
		}
	}

}
