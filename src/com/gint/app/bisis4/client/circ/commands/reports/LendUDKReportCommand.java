package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class LendUDKReportCommand extends HibernateCommand {
	
	/**
   * izdate i vracene po UDK
   */	
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public LendUDKReportCommand(Date start, Date end, Object location){
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
				String query1 = "select l.ctlgNo from Lending l "
						+ " where ((l.lendDate between :start and :end) or" +
								"(l.resumeDate between :start and :end))"+
								"and"
						+ " (l.location.name= :loc)";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 =  "select l.ctlgNo from Lending l "
					+ " where ((l.lendDate between :start and :end) or" +
					"(l.resumeDate between :start and :end))";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("LendUDK report");
		}catch (Exception e){
			log.error("LendUDK report failed");
			log.error(e);
		}
	}

}
