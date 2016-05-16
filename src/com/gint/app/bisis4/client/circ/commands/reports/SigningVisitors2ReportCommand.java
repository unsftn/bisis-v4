package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class SigningVisitors2ReportCommand extends HibernateCommand {
	
	/**
	 * Broj korisnika po vrsti uclanjenja:
	 * 1. koji su u tom periodu zaduzili knjigu ili produzili ili vratili knjigu 
	 * 
	 * 2. koji su se u tom periodu uclanili ali nisu nista na taj dan zaduzili, produzili
	 * ili razduzili
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public SigningVisitors2ReportCommand(Date start, Date end, Object location){
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
				String query1 = "select distinct(s.users.userId), s.users.mmbrTypes.name, s.signDate   from Signing s  where "
						+ " (s.location.name= :loc) and  " +
						"(s.signDate between :start and :end) and " +
						"s.users.userId not in (select distinct(l2.users.userId) from Lending l2 where "+ 
						"(l2.location.name= :loc) and  " +
						"(l2.lendDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)) or " +
	                    "(l2.resumeDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)) or "+
	                    "(l2.returnDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)))) "+		
	                    "order by s.users.mmbrTypes.name ";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select distinct(s.users.userId), s.users.mmbrTypes.name, s.signDate   from Signing s  where " +
					"(s.signDate between :start and :end) and " +
					"s.users.userId not in (select distinct(l2.users.userId) from Lending l2 where "+ 
					"(l2.lendDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)) or " +
	                "(l2.resumeDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)) or "+
	                "(l2.returnDate between date(s.signDate) and timestampadd(DAY,1,s.signDate)))) "+		
	                "order by s.users.mmbrTypes.name ";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("SigningVisitors2 report");
		}catch (Exception e){
			log.error("SigningVisitors2 report failed");
			log.error(e);
		}
	}

}
