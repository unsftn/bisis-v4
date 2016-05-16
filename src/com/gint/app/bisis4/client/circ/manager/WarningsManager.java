package com.gint.app.bisis4.client.circ.manager;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.commandservice.CommandType;
import com.gint.app.bisis4.commandservice.Service;
import com.gint.app.bisis4.commandservice.ServiceFactory;
import com.gint.app.bisis4.commandservice.ServiceType;
import com.gint.app.bisis4.client.circ.commands.GetAllCommand;
import com.gint.app.bisis4.client.circ.commands.GetWarnCountersCommand;
import com.gint.app.bisis4.client.circ.commands.GetWarnHistoryCommand;
import com.gint.app.bisis4.client.circ.commands.GetWarnUsersCommand;
import com.gint.app.bisis4.client.circ.commands.SaveObjectCommand;
import com.gint.app.bisis4.client.circ.commands.SaveWarningsCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.Users;
import com.gint.app.bisis4.client.circ.model.WarningTypes;
import com.gint.app.bisis4.client.circ.model.Warnings;
import com.gint.app.bisis4.client.circ.common.Utils;
import com.gint.app.bisis4.client.circ.warnings.Counters;
import com.gint.app.bisis4.client.circ.warnings.WarningsFrame;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.utils.NetUtils;
import com.gint.app.bisis4.utils.QueryUtils;

public class WarningsManager {
	
  private Service service;
	
	public WarningsManager(){
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
	
  public void loadCombos(WarningsFrame warnings) throws Exception{
	GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	Utils.loadCombo(warnings.getCmbBranch(), getAll.getList());
	getAll.setArg(WarningTypes.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	Utils.loadCombo(warnings.getCmbType(), getAll.getList());
  }
  
  public Counters getCounters(WarningTypes warn_type){
  	GetWarnCountersCommand getCounters = new GetWarnCountersCommand(warn_type);
  	getCounters = (GetWarnCountersCommand)service.executeCommand(getCounters);
    return new Counters(getCounters.getCounters(), warn_type);
  }
  
  public List<Users> getUsers(Date startDate, Date endDate, Location loc){
    if (startDate == null) {
      endDate = Utils.setMaxDate(endDate);
      startDate = Utils.setMinDate(endDate);
    } else if (endDate == null) {
      endDate = Utils.setMaxDate(startDate);
      startDate = Utils.setMinDate(startDate);
    } else {
      startDate = Utils.setMinDate(startDate);
      endDate = Utils.setMaxDate(endDate);
    }
    GetWarnUsersCommand getUsers = new GetWarnUsersCommand(startDate, endDate, loc);
    getUsers = (GetWarnUsersCommand)service.executeCommand(getUsers);
    if (getUsers == null)
    	return null;	
    return getUsers.getList();
  }
  
  public Record getRecord(String ctlgno){
    Record record = null;
    int[] hits = BisisApp.getRecordManager().select2x(SerializationUtils.serialize(QueryUtils.makeQueryTerm("IN", ctlgno, "", null)), null);
    if (hits.length != 0){
      record = BisisApp.getRecordManager().getRecord(hits[0]);
    }
    return record;
  }
  
  public boolean saveWarnings(List<Warnings> warnings, Counters counters){
  	SaveWarningsCommand save = new SaveWarningsCommand(warnings, counters.getList());
    save = (SaveWarningsCommand)service.executeCommand(save);
    return save.isSaved();
  }
  
  public List<Object[]> getHistory(Date startDate, Date endDate, WarningTypes wtype){
  	if (startDate == null) {
      endDate = Utils.setMaxDate(endDate);
      startDate = Utils.setMinDate(endDate);
    } else if (endDate == null) {
      endDate = Utils.setMaxDate(startDate);
      startDate = Utils.setMinDate(startDate);
    } else {
      startDate = Utils.setMinDate(startDate);
      endDate = Utils.setMaxDate(endDate);
    }
  	GetWarnHistoryCommand getHistory = new GetWarnHistoryCommand(startDate, endDate, wtype);
  	getHistory = (GetWarnHistoryCommand)service.executeCommand(getHistory);
  	if (getHistory == null)
  		return null;
    return getHistory.getList();
  }
  
  public boolean saveWarnTypes(WarningTypes wtype){
  	SaveObjectCommand save = new SaveObjectCommand(wtype);
    save = (SaveObjectCommand)service.executeCommand(save);
    return save.isSaved();
  }

}
