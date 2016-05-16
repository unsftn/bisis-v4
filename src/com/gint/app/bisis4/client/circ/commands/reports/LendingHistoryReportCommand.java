package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class LendingHistoryReportCommand extends HibernateCommand {
	
	/**
	 * istorija clana
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	String userNo;
	
	public LendingHistoryReportCommand(Date start, Date end, Object location, String userNo){
		this.start = start;
		this.end = end;
		this.location = location;
		this.userNo = userNo;
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
				String query1 = "select l.ctlgNo,l.lendDate,l.returnDate from Lending l "
						+ " where (l.users.userId= :userNo) and (l.lendDate between :start and :end) "+
								"and"
						+ " (l.location.name= :loc) order by l.lendDate";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc).setString("userNo", userNo);
			} else {
				String query2 = "select l.ctlgNo,l.lendDate,l.returnDate from Lending l "
					+ " where (l.users.userId= :userNo) and (l.lendDate between :start and :end) "+
					"order by l.lendDate";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("userNo", userNo);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("LendingHistory report");
		}catch (Exception e){
			log.error("LendingHistory report failed");
			log.error(e);
		}
	}

}
