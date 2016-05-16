package com.gint.app.bisis4.client.circ.commands;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Location;

public class GetCtlgNosCommand extends HibernateCommand {
	
	Date startDateL;
	Date endDateL;
	Location location;
	Date startDateR;
	Date endDateR;
	List list = null;
	
	
	
	public GetCtlgNosCommand(Date startDateL, Date endDateL, Location location, 
      Date startDateR, Date endDateR){
		this.startDateL = startDateL;
		this.endDateL = endDateL;
		this.location = location;
		this.startDateR = startDateR;
		this.endDateR = endDateR;
	}
	
	public List getList(){
		return list;
	}

	@Override
	public void execute() {
		
		String q = "select l.ctlgNo from Lending l where ";
		int loc = 0;
		if (startDateL != null){
			q = q + "((l.lendDate between :startDateL and :endDateL) or (l.resumeDate between :startDateL and :endDateL)) ";
	    }
	    if (startDateR != null){
	      if (startDateL != null)
	        q = q + "and ";
	      q = q + "(l.returnDate between :startDateR and :endDateR) ";
	    }
	    if (location != null){
	    	loc = location.getId();
	    	q = q + "and (l.location.id= :loc)";
	    }
		
		try{
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(q);
		    if (startDateL != null){
		      query.setTimestamp("startDateL", startDateL);
		      query.setTimestamp("endDateL", endDateL);
		    }
		    if (location != null)
		      query.setInteger("loc", loc);
		    if (startDateR != null){
		      query.setTimestamp("startDateR", startDateR);
		      query.setTimestamp("endDateR", endDateR);
		    }
		    list = query.list();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
