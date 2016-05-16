package com.gint.app.bisis4.client.circ.commands;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.gint.app.bisis4.client.circ.model.Membership;
import com.gint.app.bisis4.client.circ.model.MmbrTypes;
import com.gint.app.bisis4.client.circ.model.UserCategs;
import com.gint.app.bisis4.client.circ.model.Users;


public class GetMembershipCommand extends HibernateCommand {
	
	MmbrTypes mt;
	UserCategs uc;
	Membership mmbrship = null;
	
	public GetMembershipCommand(MmbrTypes mt, UserCategs uc){
		this.mt = mt;
		this.uc = uc;
	}
	
	public Membership getMembership(){
		return mmbrship;
	}

	@Override
	public void execute() {
		try{
			Transaction tx = session.beginTransaction();
			List list = session.createCriteria(Membership.class)
				.add(Expression.eq("mmbrTypes", mt))
				.add(Expression.eq("userCategs", uc)).list();
			if (!list.isEmpty())
				mmbrship = (Membership)list.iterator().next();
			tx.commit();
		}catch (Exception e){
			log.error("loading memebership failed");
			log.error(e);
		}
	}

}
