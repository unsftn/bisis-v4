package com.gint.app.bisis4.client.circ.archive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gint.app.bisis4.client.circ.commands.ArchiveUserCommand;
import com.gint.app.bisis4.client.circ.commands.DeleteObjectCommand;
import com.gint.app.bisis4.client.circ.commands.GetAllCommand;
import com.gint.app.bisis4.client.circ.commands.GetAllUserDataCommand;
import com.gint.app.bisis4.client.circ.commands.GetUsersForArchiveCommand;
import com.gint.app.bisis4.client.circ.commands.ReplicateObjectsCommand;
import com.gint.app.bisis4.client.circ.commands.SaveObjectsCommand;
import com.gint.app.bisis4.client.circ.model.EduLvl;
import com.gint.app.bisis4.client.circ.model.Groups;
import com.gint.app.bisis4.client.circ.model.Languages;
import com.gint.app.bisis4.client.circ.model.Lending;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.MmbrTypes;
import com.gint.app.bisis4.client.circ.model.Organization;
import com.gint.app.bisis4.client.circ.model.UserCategs;
import com.gint.app.bisis4.client.circ.model.Users;
import com.gint.app.bisis4.client.circ.model.WarningTypes;



public class ArchiveCoders{
	
	public static void main(String[] args) throws Exception{
		if(args.length != 7){
			System.out.println("archiveCoders: <address> <database> <user> <password> <archivedatabase> <archiveuser> <archivepassword> ");
			return;
		}
		String address=args[0];
		String database=args[1];
		String user=args[2];
		String password=args[3];
		String archivedatabase=args[4];
		String archiveuser=args[5];
		String archivepassword=args[6];
		
		Service service = new Service(address, database, user, password);
		Service serviceArchive = new Service(address, archivedatabase, archiveuser, archivepassword);
		
	
		GetAllCommand getAll = new GetAllCommand();
		ReplicateObjectsCommand saveObjects = new ReplicateObjectsCommand();
		
		getAll.setArg(EduLvl.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		if (getAll != null && !getAll.getList().isEmpty()){
			saveObjects.setList(getAll.getList());
			saveObjects = (ReplicateObjectsCommand)serviceArchive.executeCommand(saveObjects);
			if (saveObjects.isSaved()){
				System.out.println("EduLvl archived!");
			}else{
				System.out.println("Edulvl:"+saveObjects.getMessage());
				return;
			}
		}else{
			System.out.println("Command is null!");
			return;
		}
			
		
		getAll.setArg(Languages.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		if (getAll != null){
			saveObjects.setList(getAll.getList());
			saveObjects = (ReplicateObjectsCommand)serviceArchive.executeCommand(saveObjects);
			if (saveObjects.isSaved()){
				System.out.println("Languages archived!");
			}else{
				System.out.println("Languages:"+saveObjects.getMessage());
				return;
			}
		}else{
			System.out.println("Command is null!");
			return;
		}
			
		
		getAll.setArg(Organization.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		if (getAll != null){
			saveObjects.setList(getAll.getList());
			saveObjects = (ReplicateObjectsCommand)serviceArchive.executeCommand(saveObjects);
			if (saveObjects.isSaved()){
				System.out.println("Organization archived!");
			}else{
				System.out.println("Organization:"+saveObjects.getMessage());
				return;
			}
		}else{
			System.out.println("Command is null!");
			return;
		}
			
		
		getAll.setArg(Groups.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		if (getAll != null){
			saveObjects.setList(getAll.getList());
			saveObjects = (ReplicateObjectsCommand)serviceArchive.executeCommand(saveObjects);
			if (saveObjects.isSaved()){
				System.out.println("Groups archived!");
			}else{
				System.out.println("Groups:"+saveObjects.getMessage());
				return;
			}
		}else{
			System.out.println("Command is null!");
			return;
		}
			
		
		getAll.setArg(Location.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		if (getAll != null){
			saveObjects.setList(getAll.getList());
			saveObjects = (ReplicateObjectsCommand)serviceArchive.executeCommand(saveObjects);
			if (saveObjects.isSaved()){
				System.out.println("Location archived!");
			}else{
				System.out.println("Location:"+saveObjects.getMessage());
				return;
			}
		}else{
			System.out.println("Command is null!");
			return;
		}
			
		
		getAll.setArg(MmbrTypes.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		if (getAll != null){
			saveObjects.setList(getAll.getList());
			saveObjects = (ReplicateObjectsCommand)serviceArchive.executeCommand(saveObjects);
			if (saveObjects.isSaved()){
				System.out.println("MmbrTypes archived!");
			}else{
				System.out.println("MmbrTypes:"+saveObjects.getMessage());
				return;
			}
		}else{
			System.out.println("Command is null!");
			return;
		}
			
		
		getAll.setArg(UserCategs.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		if (getAll != null){
			saveObjects.setList(getAll.getList());
			saveObjects = (ReplicateObjectsCommand)serviceArchive.executeCommand(saveObjects);
			if (saveObjects.isSaved()){
				System.out.println("UserCategs archived!");
			}else{
				System.out.println("UserCategs:"+saveObjects.getMessage());
				return;
			}
		}else{
			System.out.println("Command is null!");
			return;
		}
		
		getAll.setArg(WarningTypes.class);
		getAll = (GetAllCommand)service.executeCommand(getAll);
		if (getAll != null){
			saveObjects.setList(getAll.getList());
			saveObjects = (ReplicateObjectsCommand)serviceArchive.executeCommand(saveObjects);
			if (saveObjects.isSaved()){
				System.out.println("WarningTypes archived!");
			}else{
				System.out.println("WarningTypes:"+saveObjects.getMessage());
				return;
			}
		}else{
			System.out.println("Command is null!");
			return;
		}
		
	}

}
