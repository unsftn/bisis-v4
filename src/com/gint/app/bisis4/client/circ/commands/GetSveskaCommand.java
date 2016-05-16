package com.gint.app.bisis4.client.circ.commands;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Sveske;


public class GetSveskaCommand extends HibernateCommand {
	
	String ctlgno;
	Sveske sveska = null;
	
	
	public GetSveskaCommand(String ctlgno){
		this.ctlgno = ctlgno;
	}
	
	public Sveske getSveska(){
		return sveska;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("from Sveske s where s.invBr = :ctlgno");
		    q.setString("ctlgno", ctlgno);
		    List list = q.list();
		    if (!list.isEmpty())
		    	sveska = (Sveske)list.iterator().next();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
