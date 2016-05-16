package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Lending;
;

public class DischargeBookCommand extends HibernateCommand {
	
	Lending lending;
	Object primerak;
	boolean success = false;
	String message = null;
	
	public DischargeBookCommand(Lending lending, Object primerak){
		this.lending = lending;
		this.primerak = primerak;
	}
	
	public boolean isSaved(){
		return success;
	}
	
	public String getMessage(){
		return message;
	}

	@Override
	public void execute() {
		Transaction tx = session.beginTransaction();
		try{
			if (primerak != null)
				session.update(primerak);
			if (lending != null)
				session.update(lending);
			tx.commit();
			if (lending != null){
				log.info("discharging book successfully: "+ lending.getCtlgNo());
			} else {
				log.info("discharging book successfully: ");
			}
			success = true;
		}catch (StaleObjectStateException e){
	   		tx.rollback();
	   		if (lending != null){
				log.info("discharging book failed: "+ lending.getCtlgNo());
			} else {
				log.info("discharging book failed: ");
			}
	   		log.error(e);
	   		message = e.getMessage();
		}
	}

}
