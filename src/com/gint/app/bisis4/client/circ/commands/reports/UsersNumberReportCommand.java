package com.gint.app.bisis4.client.circ.commands.reports;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;


import com.gint.app.bisis4.client.circ.commands.HibernateCommand;
import com.gint.app.bisis4.client.circ.model.Users;

public class UsersNumberReportCommand extends HibernateCommand {
	
	/**
	*  ukupan broj korisnika koji su se uclanili od pocetka godine
	*  ukupan broj korisnika koji su se uclanili u tom periodu
	*/
	
	int num = 0;
	Date start;
	Date end;
	Object location;
	
	public UsersNumberReportCommand(Date start, Date end, Object location){
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
			if (!location.equals(" ")) {
				signcrt.add(Expression.eq("location", location));
			}
			if (crt != null) {
				num = crt.list().size();
			}
		  tx.commit();
		  log.info("UsersNumber report");
		}catch (Exception e){
			log.error("UsersNumber report failed");
			log.error(e);
		}
	}

}
