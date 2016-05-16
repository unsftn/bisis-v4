package com.gint.app.bisis4.client.circ.commands;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.WarnCounters;
import com.gint.app.bisis4.client.circ.model.Warnings;




public class SaveWarningsCommand extends HibernateCommand {
	
	List<Warnings> warnings = null;
	List<WarnCounters> counters = null;
	boolean success = false;
	String message = null;
	
	public SaveWarningsCommand(List<Warnings> warnings, List<WarnCounters> counters){
		this.warnings = warnings;
		this.counters = counters;
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
			
			//TODO
			for (Warnings warn : warnings){
				session.saveOrUpdate(warn);
			}
	      for (WarnCounters warn : counters){
	        session.saveOrUpdate(warn);
	      }
			tx.commit();
			success = true;
			log.info("saving warnings successfully: " );
		} catch (HibernateException e) {
			tx.rollback();
			log.error("saving warnings failed: " );
			log.error(e);
			message = e.getMessage();
		}
	}

}
