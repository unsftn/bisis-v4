package com.gint.app.bisis4.client.circ;

import org.apache.commons.lang.SerializationUtils;

import com.gint.app.bisis4.commandservice.HibernateServiceFactory;
import com.gint.app.bisis4.commandservice.Service;
import com.gint.app.bisis4.commandservice.ServiceFactory;
import com.gint.app.bisis4.commandservice.ServiceType;
import com.gint.app.bisis4.client.circ.commands.GetAllCommand;
import com.gint.app.bisis4.client.circ.commands.GetLastUserIDCommand;
import com.gint.app.bisis4.client.circ.commands.GetSysIDCommand;
import com.gint.app.bisis4.client.circ.commands.GetUserCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.Signing;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//getUser();
		//getAll();
		//getSysId();
		getUserID();
	}
	
	public static void getUser(){
		GetUserCommand getUser = new GetUserCommand("00000000001");
		//Service service = ServiceFactory.createService(ServiceType.REMOTE, "http://localhost:80/circService/CircService");
		Service service = (new HibernateServiceFactory()).createService(ServiceType.LOCAL, null);
		if (service != null){
			//getUser = (GetUserCommand) SerializationUtils.deserialize(service.executeCommandSerialize(SerializationUtils.serialize(getUser)));
			getUser = (GetUserCommand) service.executeCommand(getUser);
			System.out.println(getUser.getUser().getFirstName());
			System.out.println(((Signing)getUser.getUser().getSignings().iterator().next()).getSignDate().toString());
			System.out.println(getUser.getUser().getUserCategs().getId());
			System.out.println(getUser.getType());
		}

	}
	
	public static void getSysId(){
		GetSysIDCommand getSysID = new GetSysIDCommand("01000000003");
		Service service = (new HibernateServiceFactory()).createService(ServiceType.REMOTE, "http://localhost:80/circService/CircService");
		//CirculationService service = CirculationServiceFactory.createService(ServiceType.LOCAL, null);
		if (service != null){
			//getUser = (GetUserCommand) SerializationUtils.deserialize(service.executeCommand(SerializationUtils.serialize(getUser)));
			getSysID = (GetSysIDCommand ) service.executeCommand(getSysID);
			System.out.println(getSysID.getSysID());
		}
	}
	
	public static void getAll(){
		GetAllCommand getAll = new GetAllCommand();
		getAll.setArg(Location.class);
		//CirculationService service = CirculationServiceFactory.createService(ServiceType.LOCAL, null);
		Service service = (new HibernateServiceFactory()).createService(ServiceType.REMOTE, "http://localhost:80/circService/CircService");
		if (service != null){
			getAll = (GetAllCommand)service.executeCommand(getAll);
			System.out.println(((Location)getAll.getList().get(0)).getName());
		}
		
	}
	public static void getUserID(){
		GetLastUserIDCommand getLastID = new GetLastUserIDCommand(0);
		//Service service = (new HibernateServiceFactory()).createService(ServiceType.LOCAL, null);
		Service service = (new HibernateServiceFactory()).createService(ServiceType.REMOTE, "http://localhost:80/commandsrv/Service");
		if (service != null){
			getLastID = (GetLastUserIDCommand)service.executeCommand(getLastID);
			System.out.println(getLastID.getLastID());
		}
		
	}

}
