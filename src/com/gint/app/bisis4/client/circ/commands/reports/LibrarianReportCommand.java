package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.common.Utils;

public class LibrarianReportCommand extends HibernateCommand {
	
	/**
	 * broj korisnika koji su se tog dana uclanili i grupisani su po bibliotekarima
	 */
	
	List list = null;
	Date date;
	Object location;
	
	public LibrarianReportCommand(Date date, Object location){
		this.date = date;
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

				String query1 = "select u.userId, u.firstName, u.lastName, u.address,u.zip, u.city, u.docNo,u.docCity,u.jmbg, s.librarian,s.receiptId,s.cost,mt.name from Users u "
						+ "inner join u.mmbrTypes as mt inner join u.signings as s "
						+ " where (s.signDate>= :start) and (s.signDate<= :end) and"
						+ " (s.location.name= :loc) order by s.librarian, u.userId";
				crt = session.createQuery(query1).setParameter("start", Utils.setMinDate(date),Hibernate.TIMESTAMP).setParameter("end", Utils.setMaxDate(date),Hibernate.TIMESTAMP).setString("loc", loc);

			} else {
				String query2 = "select u.userId, u.firstName, u.lastName, u.address,u.zip, u.city, u.docNo,u.docCity,u.jmbg, s.librarian,s.receiptId,s.cost,mt.name from Users u "
						+ "inner join u.mmbrTypes as mt inner join u.signings as s "
						+ " where (s.signDate>= :start) and (s.signDate<= :end) order by s.librarian, u.userId";
				crt = session.createQuery(query2).setParameter("start", Utils.setMinDate(date),Hibernate.TIMESTAMP).setParameter("end", Utils.setMaxDate(date),Hibernate.TIMESTAMP);

			}

			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("Librarian report");
		}catch (Exception e){
			log.error("Librarian report failed");
			log.error(e);
		}
	}

}
