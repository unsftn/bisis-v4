package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class Statistic1ReportCommand extends HibernateCommand {
	
	/**
	 * broj upisanih korisnika u datom periodu
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public Statistic1ReportCommand(Date start, Date end, Object location){
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
				String query1 = "select count(*)  from Users u inner join u. signings as s where "
						+ " (s.location.name= :loc) and  (s.signDate between :start and :end)";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select count(*)  from Users u inner join u. signings as s where "
						+ " (s.signDate between :start and :end)";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("Statistic1 report");
		}catch (Exception e){
			log.error("Statistic1 report failed");
			log.error(e);
		}
	}

}
