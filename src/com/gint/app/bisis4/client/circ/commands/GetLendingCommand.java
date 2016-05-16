package com.gint.app.bisis4.client.circ.commands;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Lending;
import com.gint.app.bisis4.client.circ.model.Primerci;


public class GetLendingCommand extends HibernateCommand {
	
	String ctlgno;
	Lending lending = null;
	
	
	public GetLendingCommand(String ctlgno){
		this.ctlgno = ctlgno;
	}
	
	public Lending getLending(){
		return lending;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("from Lending l where l.returnDate is null and l.ctlgNo = :ctlgno");
		    q.setString("ctlgno", ctlgno);
		    Iterator it = q.list().iterator();
		    if (it.hasNext()){
		    	lending = (Lending)it.next();
		    }
			tx.commit();
		}catch (Exception e){
			log.error(e);
		}
	}

}
