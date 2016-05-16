package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Location;

public class CategoriaReportCommand extends HibernateCommand {
	
	/**broj korisnika koji pripadaju odredjenoj kategoriji i uclanili su se u datom periodu
	 * 
	 * @param start
	 * @param end
	 * @param location
	 * @return
	 */
	
	List list = null;
	Date start;
	Date end;
	Object location;
	
	public CategoriaReportCommand(Date start, Date end, Object location){
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
				String query1 = "select uc.name, count(u.userId) from Users u "
						+ "inner join u.userCategs as uc inner join u.signings as s "
						+ " where (s.signDate>= :start) and (s.signDate<= :end) and"
						+ " (s.location.name= :loc) group by uc.name";
				crt = session.createQuery(query1).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP).setString("loc", loc);
			} else {
				String query2 = "select uc.name, count(u.userId) from Users u "
						+ "inner join u.userCategs as uc inner join u.signings as s "
						+ " where (s.signDate>= :start) and (s.signDate<= :end) group by uc.name";
				crt = session.createQuery(query2).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);
			}
			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("Categoria report");
		}catch (Exception e){
			log.error("Categoria report failed");
			log.error(e);
		}
	}

}
