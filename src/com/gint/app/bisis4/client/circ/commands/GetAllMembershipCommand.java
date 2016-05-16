package com.gint.app.bisis4.client.circ.commands;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Transaction;

import com.gint.app.bisis4.client.circ.model.Membership;


public class GetAllMembershipCommand extends HibernateCommand {
	
	List list;
	
	public List getList(){
		return list;
	}
	
	@Override
	public void execute() {
		try{
		 Transaction tx = session.beginTransaction();
		 list = session.createCriteria(Membership.class).setFetchMode("mmbrTypes", FetchMode.JOIN).setFetchMode("userCategs", FetchMode.JOIN).list();
		 tx.commit();
		 log.info("loading all: Membership");
		}catch (Exception e){
			log.error("loading all failed: Membership");
			log.error(e.getMessage());
		}
	}

}
