package com.gint.app.bisis4.client.circ.commands;

import org.hibernate.jdbc.Work;


public class ExecuteWorkCommand extends HibernateCommand {
	
	Work work;
	
	
	public ExecuteWorkCommand(Work work){
		this.work = work;
	}
	
	
	@Override
	public void execute() {
		try{
			session.doWork(work);
			log.info("executing work");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
