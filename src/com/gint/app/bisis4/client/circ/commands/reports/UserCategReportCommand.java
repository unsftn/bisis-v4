package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.common.Utils;

public class UserCategReportCommand extends HibernateCommand {
	
	/**
	 * podaci o korisniku koji su se uclanili datog dana po kategorijama 
	 */
	
	List list = null;
	Date date;
	Object location;
	
	public UserCategReportCommand(Date date, Object location){
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
				String query1 = "select u.userId, u.firstName, u.lastName, u.address,u.zip, u.city, u.docNo,u.docCity,u.jmbg, s.librarian,s.receiptId,s.cost,uc.name from Users u "
						+ "inner join u.signings as s inner join u.userCategs as uc "
						+ " where (s.signDate>= :start) and (s.signDate<= :end) and"
						+ " (s.location.name= :loc) order by uc.name";
				crt = session.createQuery(query1).setParameter("start", Utils.setMinDate(date),Hibernate.TIMESTAMP).setParameter("end", Utils.setMaxDate(date),Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select u.userId, u.firstName, u.lastName, u.address,u.zip, u.city, u.docNo,u.docCity,u.jmbg, s.librarian,s.receiptId,s.cost,uc.name from Users u "
						+ " inner join u.signings as s inner join u.userCategs as uc "
						+ " where (s.signDate>= :start) and (s.signDate<= :end) order by uc.name";
				crt = session.createQuery(query2).setParameter("start", Utils.setMinDate(date),Hibernate.TIMESTAMP).setParameter("end", Utils.setMaxDate(date),Hibernate.TIMESTAMP);
			}

			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("UserCateg report");
		}catch (Exception e){
			log.error("UserCateg report failed");
			log.error(e);
		}
	}

}
