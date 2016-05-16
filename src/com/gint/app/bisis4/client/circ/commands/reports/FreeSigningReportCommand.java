package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Users;

public class FreeSigningReportCommand extends HibernateCommand {
	
	/**broj korisnika koji su se uclanili u datom periodu ali ne placaju clanarinu
	 * 
	 */
	
	int num = 0;
	Date start;
	Date end;
	Object location;
	
	public FreeSigningReportCommand(Date start, Date end, Object location){
		this.start = start;
		this.end = end;
		this.location = location;
	}
	
	public int getNum(){
		return num;
	}
	

	@Override
	public void execute() {
		try{
		  Transaction tx = session.beginTransaction();
		  Criteria crt = session.createCriteria(Users.class);
			Criteria signcrt = crt.createCriteria("signings");
			signcrt.add(Expression.between("signDate", start, end));
			signcrt.add(Expression.or(Expression.isNull("cost"),Expression.eq("cost", 0.0)));
			if (!location.equals(" ")) {
				signcrt.add(Expression.eq("location", location));
			}
			if (crt != null) {
				num = crt.list().size();
			}
		  tx.commit();
		  log.info("FreeSigning report");
		}catch (Exception e){
			log.error("FreeSigning report failed");
			log.error(e);
		}
	}

}
