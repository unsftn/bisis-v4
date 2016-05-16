package com.gint.app.bisis4.client.circ.options;

import java.util.Iterator;
import java.util.List;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.commandservice.CommandType;
import com.gint.app.bisis4.commandservice.Service;
import com.gint.app.bisis4.commandservice.ServiceFactory;
import com.gint.app.bisis4.commandservice.ServiceType;
import com.gint.app.bisis4.client.circ.commands.GetAllCommand;
import com.gint.app.bisis4.client.circ.commands.SaveObjectCommand;
import com.gint.app.bisis4.client.circ.model.Configs;
import com.gint.app.bisis4.utils.NetUtils;

public class Manager {
	
	private static Service service;
	
	static{
		ServiceFactory factory = BisisApp.getFactory(CommandType.HIBERNATE);
		String mac = NetUtils.getMACAddress();
	    String category = "commandsrv";
	    if (BisisApp.getINIFile().getCategories().contains(mac)){
	    	category = mac;
	    }
	    if (BisisApp.getINIFile().getBoolean(category, "remote")){
	    	service = factory.createService(ServiceType.REMOTE, BisisApp.getINIFile().getString(category, "service"));
	    } else {
	    	service = factory.createService(ServiceType.LOCAL, null);
	    }
	}
		
	public static void loadDocs(){
		GetAllCommand getAll = new GetAllCommand();
		getAll.setArg(Configs.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		List list = getAll.getList();
		if (list != null){
  		Iterator it = list.iterator();
  		while (it.hasNext()){
  			Configs config = (Configs)it.next();
  			if (config.getName().equals("circ-options")){
  				EnvironmentOptions.setDoc(config.getText());
  			}
  			if (config.getName().equals("circ-validator")){
  				ValidatorOptions.setDoc(config.getText());
  			}
  		}
		}
	}
	
	public static boolean saveEnv(String xml){
		Configs config = new Configs();
		config.setName("circ-options");
		config.setText(xml);
		SaveObjectCommand save = new SaveObjectCommand(config);
		save = (SaveObjectCommand)service.executeCommand(save);
		return save.isSaved();
	}
	
	public static boolean saveValidator(String xml){
		Configs config = new Configs();
		config.setName("circ-validator");
		config.setText(xml);
		SaveObjectCommand save = new SaveObjectCommand(config);
		save = (SaveObjectCommand)service.executeCommand(save);
		return save.isSaved();
	}

}
