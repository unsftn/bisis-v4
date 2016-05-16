package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.common.Utils;

public class VisitorsReportCommand extends HibernateCommand {
	
	/** podaci o korisnicima koji su tog dana zaduzili knjigu, produzili ili vratili knjigu
	 * 
	 */
	
	List list = null;
	Date start;
	Object location;
	
	public VisitorsReportCommand(Date start, Object location){
		this.start = start;
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
		 start = Utils.setMinDate(start);
		 Date end = Utils.setMaxDate(start);
		 if (!location.equals(" ")) {
				String loc = ((Location) location).getName();
				String query1 = "select distinct(u.userId),u.firstName, u.lastName,u.address,u.city, u.zip, u.docNo, u.docCity, u.jmbg, s.location, l.returnDate, l.lendDate, l.ctlgNo from Users u "
						+ " inner join u.alllendings as l inner join u.signings s"
						+ " where ((l.lendDate between :start and :end) or (l.returnDate between :start and :end)or (l.resumeDate between :start and :end)) and"
						+ " (l.location.name= :loc) order by l.lendDate, l.returnDate desc ";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select distinct(u.userId),u.firstName, u.lastName,u.address,u.city, u.zip, u.docNo, u.docCity, u.jmbg, s.location, l.returnDate, l.lendDate, l.ctlgNo from Users u "
						+ " inner join u.alllendings as l inner join u.signings s"
						+ " where ((l.lendDate between :start and :end) or (l.returnDate between :start and :end)or (l.resumeDate between :start and :end))"
						+ "  order by l.lendDate, l.returnDate desc ";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}

			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("Visitors report");
		}catch (Exception e){
			log.error("Visitors report failed");
			log.error(e);
		}
	}

}
