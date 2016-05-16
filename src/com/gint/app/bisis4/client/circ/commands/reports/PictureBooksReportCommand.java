package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.commands.HibernateCommand;



public class PictureBooksReportCommand extends HibernateCommand {
	
	/**
   * slikovnice
   */	
	
	List list = null;
	Date start;
	Date end;
	
	public PictureBooksReportCommand(Date start, Date end){
		this.start = start;
		this.end = end;
	}
	
	public List getList(){
		return list;
	}
	

	@Override
	public void execute() {
		try{
		  Transaction tx = session.beginTransaction();
		  Query crt;
		  String query = "select count(p.users), sum(p.lendNo), sum(p.returnNo) from Picturebooks p where p.sdate between :start and :end";
			crt = session.createQuery(query).setParameter("start", start,Hibernate.TIMESTAMP).setParameter("end", end,Hibernate.TIMESTAMP);

			if (crt != null) {
				list = crt.list();
			}
		  tx.commit();
		  log.info("PictureBooks report");
		}catch (Exception e){
			log.error("PictureBooks report failed");
			log.error(e);
		}
	}

}
