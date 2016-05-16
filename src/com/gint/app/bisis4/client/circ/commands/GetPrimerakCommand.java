package com.gint.app.bisis4.client.circ.commands;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Primerci;


public class GetPrimerakCommand extends HibernateCommand {
	
	String ctlgno;
	Primerci primerak = null;
	
	
	public GetPrimerakCommand(String ctlgno){
		this.ctlgno = ctlgno;
	}
	
	public Primerci getPrimerak(){
		return primerak;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("from Primerci p where p.invBroj = :ctlgno");
		    q.setString("ctlgno", ctlgno);
		    List list = q.list();
		    if (!list.isEmpty())
		    	primerak = (Primerci)list.iterator().next();
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
