package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.StatusPrimerka;



public class GetStatusPrimerkaCommand extends HibernateCommand {
	
	String id;
	StatusPrimerka status = null;
	
	
	public GetStatusPrimerkaCommand(String id){
		this.id = id;
	}
	
	public StatusPrimerka getStatus(){
		return status;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("from StatusPrimerka sp where sp.statusId = :id");
			q.setCharacter("id", id.charAt(0));
			status = (StatusPrimerka)q.uniqueResult();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
