package com.gint.app.bisis4.client.circ.commands;

import java.util.List;
import org.hibernate.Transaction;


public class GetAllCommand extends HibernateCommand {
	
	List list;
	Class arg;
	
	public List getList(){
		return list;
	}
	
	public void setArg(Class arg){
		this.arg = arg;
	}

	@Override
	public void execute() {
		try{
		 Transaction tx = session.beginTransaction();
		 list = session.createCriteria(arg).list();
		 tx.commit();
		 log.info("loading all: " + arg.getName());
		}catch (Exception e){
			log.error("loading all failed: " + arg.getName());
			log.error(e.getMessage());
		}
	}

}
