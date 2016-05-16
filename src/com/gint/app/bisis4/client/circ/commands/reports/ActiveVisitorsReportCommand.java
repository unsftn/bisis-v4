package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;


public class ActiveVisitorsReportCommand extends HibernateCommand {
	
	/**
	 * aktivni korisnici su oni koji su:
	 * 1. u datom periodu su zaduzili knjigu i nisu je vratili ili su je vratili posle datuma zaduzenja(to moze
	 * biti i sutradan jer se aktivni korisnici racunaju na jedan dan)
	 * 
	 * 2. u datom periodu su produzili zaduzenje i nisu vratili knjigu ili su knjigu produzili posle 
	 * datuma zaduzenja (najranije sutradan)
	 * 
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public ActiveVisitorsReportCommand(Date start, Date end, Object location){
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
				String query1 = "select distinct(l.users.userId), l.users.userCategs.name,l.lendDate,l.returnDate,l.resumeDate from Lending l  where "+
					    " (l.location.name= :loc) and " +
						" (((l.lendDate between :start and :end)and((l.returnDate= null)or (l.returnDate> l.lendDate)))" +
						" or" +
						"((l.resumeDate between :start and :end) and ((l.returnDate= null)" +
						" or(l.resumeDate >l.lendDate)))) order by l.users.userCategs.name";		
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select distinct(l.users.userId), l.users.userCategs.name,l.lendDate,l.returnDate,l.resumeDate from Lending l  where "+
			   	" (((l.lendDate between :start and :end)and((l.returnDate= null)or (l.returnDate> l.lendDate)))" +
				" or" +
				"((l.resumeDate between :start and :end)and ((l.returnDate= null)" +
				" or(l.resumeDate >l.lendDate)))) order by l.users.userCategs.name";
					crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("ActiveVisitors report");
		}catch (Exception e){
			log.error("ActiveVisitors report failed");
			log.error(e);
		}
	}

}
