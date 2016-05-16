package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class PassiveVisitors2ReportCommand extends HibernateCommand {
	
	/** 
	 * pasivni korisnici su oni koji su:
	 * 1. vratili knjigu u datom periodu, ali taj dan kada su je vratili nisu zaduzili knjigu ili produzili
	 * i nemaju nevracenih knjiga ili su knjige vratili pre tog datuma
	 * 
	 * 2. se uclanili u datom periodu, ali tog dana nisu nista zaduzili,produzili ili vratili
	 * 
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public PassiveVisitors2ReportCommand(Date start, Date end, Object location){
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
				String query1 = "select distinct( s.users.userId), s.users.userCategs.name,s.signDate  from Signing s  inner join s.users.lendings as l where "
						+ " (s.location.name= :loc) and  " +
						"(s.signDate between :start and :end) and " +
						"s.users.userId not in (select distinct(l2.users.userId) from Lending l2 where "+ 
						"(l2.location.name= :loc) and  " +
	                    "(l2.lendDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)) or " +
	                    "(l2.resumeDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)) or "+
	                    "(l2.returnDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)))) "+		
						 "order by s.users.userCategs.name";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select distinct(s.users.userId), s.users.userCategs.name,s.signDate  from Signing s  inner join s.users.lendings as l where "+
					"(s.signDate between :start and :end) and " +
					"s.users.userId not in (select distinct(l2.users.userId) from Lending l2 where "+ 
					 "(l2.lendDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)) or " +
	                 "(l2.resumeDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)) or "+
	                 "(l2.returnDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)))) "+		
						 "order by s.users.userCategs.name";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("PasiveVisitors2 report");
		}catch (Exception e){
			log.error("PasiveVisitors2 report failed");
			log.error(e);
		}
	}

}
