package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class BookHistoryReportCommand extends HibernateCommand {
	
	/**
	 * istorija knjige
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	String ctlgNo;
	
	public BookHistoryReportCommand(Date start, Date end, Object location, String ctlgNo){
		this.start = start;
		this.end = end;
		this.location = location;
		this.ctlgNo = ctlgNo;
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
				String query1 = "select l.users.userId, l.lendDate,l.returnDate from Lending l "
						+ " where (l.ctlgNo= :ctlgNo) and (l.lendDate between :start and :end) "+
								"and"
						+ " (l.location.name= :loc)";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc).setString("ctlgNo", ctlgNo);
			} else {
				String query2 = "select l.users.userId, l.lendDate, l.returnDate from Lending l "
					+ " where (l.ctlgNo= :ctlgNo) and (l.lendDate between :start and :end) ";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("ctlgNo", ctlgNo);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("BookHistory report");
		}catch (Exception e){
			log.error("BookHistory report failed");
			log.error(e);
		}
	}

}
