package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class Statistic5ReportCommand extends HibernateCommand {
	
	/**
	 * pasivni korisnici
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public Statistic5ReportCommand(Date start, Date end, Object location){
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
				String query1 = "select distinct(l.users.userId),l.returnDate, count(distinct l.users.userId)  from Lending l  where "
						+ " (l.location.name= :loc) and  " +
						"(l.returnDate between :start and :end) " +
						" and " +
						"l.users.userId not in (select distinct(l2.users.userId) from Lending l2 where "+
						"(l2.location.name= :loc) and  " +
	                    "((l2.lendDate between date(l.returnDate) and timestampadd(DAY,1,l.returnDate)) or " +
	                    "(l2.resumeDate between date(l.returnDate) and timestampadd(DAY,1,l.returnDate))) and "+
					    " ((l2.returnDate=null)or l2.returnDate > timestampadd(DAY,1,l.returnDate))) " +
						"group by date(l.returnDate)";				
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select distinct(l.users.userId),l.returnDate, count(distinct l.users.userId)  from Lending l  where "+
					"(l.returnDate between :start and :end) " +
					" and " +
					"l.users.userId not in (select distinct(l2.users.userId) from Lending l2 where "+
					  "((l2.lendDate between date(l.returnDate) and timestampadd(DAY,1,l.returnDate)) or " +
	                  "(l2.resumeDate between date(l.returnDate) and timestampadd(DAY,1,l.returnDate))) and "+
					    " ((l2.returnDate=null)or l2.returnDate > timestampadd(DAY,1,l.returnDate))) " +
						"group by date(l.returnDate)";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("Statistic5 report");
		}catch (Exception e){
			log.error("Statistic5 report failed");
			log.error(e);
		}
	}

}
