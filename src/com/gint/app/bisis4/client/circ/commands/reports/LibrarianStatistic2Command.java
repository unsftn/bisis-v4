package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.common.Utils;

public class LibrarianStatistic2Command extends HibernateCommand {
	
	/**
	 * broj zaduzenja po bibliotekarima
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public LibrarianStatistic2Command(Date start, Date end, Object location){
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
				String query1 = "select librarianLend, count(*) from Lending "
						+ " where (lendDate>= :start) and (lendDate<= :end) and"
						+ " (location.name= :loc) group by librarianLend";
				crt = session.createQuery(query1).setParameter("start", Utils.setMinDate(start),Hibernate.TIMESTAMP).setParameter("end", Utils.setMaxDate(end),Hibernate.TIMESTAMP).setString("loc", loc);

			} else {
				String query2 = "select librarianLend, count(*) from Lending "
						+ " where (lendDate>= :start) and (lendDate<= :end) group by librarianLend";
				crt = session.createQuery(query2).setParameter("start", Utils.setMinDate(start),Hibernate.TIMESTAMP).setParameter("end", Utils.setMaxDate(end),Hibernate.TIMESTAMP);

			}

			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("LibrarianStatistic2 report");
		}catch (Exception e){
			log.error("LibrarianStatistic2 report failed");
			log.error(e);
		}
	}

}
