package com.gint.app.bisis4.client.circ.archive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gint.app.bisis4.client.circ.commands.ArchiveUserCommand;
import com.gint.app.bisis4.client.circ.commands.DeleteObjectCommand;
import com.gint.app.bisis4.client.circ.commands.DeleteUserCommand;
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



public class ArchiveData {
	
	public static void main(String[] args) throws Exception{
		if(args.length != 8){
			System.out.println("archive: <address> <database> <user> <password> <archivedatabase> <archiveuser> <archivepassword> <membership_expire_date yyyy/mm/dd> ");
			return;
		}
		String address=args[0];
		String database=args[1];
		String user=args[2];
		String password=args[3];
		String archivedatabase=args[4];
		String archiveuser=args[5];
		String archivepassword=args[6];
		String datestring=args[7];
		
		Date date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
		try{
			date = sdf.parse(datestring);
		} catch (Exception e){
			System.out.println("Bad date format! ");
			return;
		}
		
		Service service = new Service(address, database, user, password);
		Service serviceArchive = new Service(address, archivedatabase, archiveuser, archivepassword);
		
		
		GetUsersForArchiveCommand getUsers = new GetUsersForArchiveCommand(date);
		getUsers = (GetUsersForArchiveCommand)service.executeCommand(getUsers);
		List<String> users = getUsers.getList();
		System.out.println("Users for archive: "+ users.size());
		int count = 0;
		for (String userId : users){
			GetAllUserDataCommand getUserData = new GetAllUserDataCommand(userId);
			getUserData = (GetAllUserDataCommand)service.executeCommand(getUserData);
				if (getUserData != null){
					Users userdata = getUserData.getUser();
					int sys_id = userdata.getSysId();
					ArchiveUserCommand archiveUser = new ArchiveUserCommand();
					archiveUser.setUserID(userId);
					archiveUser.setUser(userdata);
					List<Object> list = new ArrayList<Object>();
					list.addAll(userdata.getSignings());
					list.addAll(userdata.getAlllendings());
					list.addAll(userdata.getDuplicates());
					list.addAll(userdata.getPicturebooks());
					for (Object lending: userdata.getAlllendings()){
						list.addAll(((Lending)lending).getWarningses());
					}
					archiveUser.setChildren(list);
					archiveUser = (ArchiveUserCommand)serviceArchive.executeCommand(archiveUser);
					if (archiveUser == null){
						System.out.println("Command is null!");
						return;
					}
					if (archiveUser.isSaved()){
						DeleteUserCommand deleteUser = new DeleteUserCommand(sys_id);
						deleteUser = (DeleteUserCommand)service.executeCommand(deleteUser);
						if (!deleteUser.isSaved()){
							System.out.println(deleteUser.getMessage());
							return;
						}
						
					} else {
						System.out.println(archiveUser.getMessage());
						return ;
					}
				} else {
					System.out.println("Command is null!");
					return;
				}
				if (++count % 1000 == 0)
			        System.out.println("Archived: " + count);
		}
		
		
		
	}

}
