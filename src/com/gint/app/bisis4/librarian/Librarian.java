package com.gint.app.bisis4.librarian;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.commands.ExecuteQueryCommand;

//import com.gint.app.bisis4.client.BisisApp;
//import com.gint.app.bisis4.client.circ.Cirkulacija;
//import com.gint.app.bisis4.client.circ.commands.ExecuteWorkCommand;
//import com.gint.app.bisis4.client.commands.LibLogin;
//import com.gint.app.bisis4.client.commands.LibLoginCommand;
//import com.gint.app.bisis4.commandservice.CommandType;
//import com.gint.app.bisis4.commandservice.Service;
//import com.gint.app.bisis4.commandservice.ServiceFactory;
//import com.gint.app.bisis4.commandservice.ServiceType;

public class Librarian implements Serializable{

  private String username;
  private String password;
  private String ime;
  private String prezime;
  private String email;
  private String napomena;
  private boolean cataloguing;
  private boolean circulation;
  private boolean administration;
  private LibrarianContext context;
  private ProcessType currentProcessType;

  public Librarian() {  	
  }
  public Librarian(String username, String password) {
    this.username = username;
    this.password = password;    
  }
  public Librarian(String username, String password, String ime, String prezime, 
  		String email, String napomena, boolean cataloguing,
  		boolean circulation, boolean administration, LibrarianContext context) {
    this.username = username;
    this.password = password;
    this.ime = ime;
    this.prezime = prezime;
    this.email = email;
    this.napomena = napomena;
    this.cataloguing = cataloguing;
    this.circulation = circulation;
    this.administration = administration;
    this.context = context;
  }
  public boolean isAdministration() {
    return administration;
  }
  public void setAdministration(boolean administration) {
    this.administration = administration;
  }
  public boolean isCataloguing() {
    return cataloguing;
  }
  public void setCataloguing(boolean cataloguing) {
    this.cataloguing = cataloguing;
  }
  public boolean isCirculation() {
    return circulation;
  }
  public void setCirculation(boolean circulation) {
    this.circulation = circulation;
  }
  public LibrarianContext getContext() {
    return context;
  }
  public void setContext(LibrarianContext context) {
    this.context = context;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public ProcessType getCurrentProcessType() {
    return currentProcessType;
  }
  public void setCurrentProcessType(ProcessType currentProcessType) {
    this.currentProcessType = currentProcessType;
  }
  
  public boolean login(Connection conn) {
    try {
      PreparedStatement stmt = conn.prepareStatement(
          "SELECT obrada, cirkulacija, administracija, context, ime, prezime, email, napomena FROM Bibliotekari WHERE username=? AND password=?");
      stmt.setString(1, username);
      stmt.setString(2, password);
      ResultSet rset = stmt.executeQuery();
      if (rset.next()) {
        cataloguing = (rset.getInt(1) == 1);
        circulation = (rset.getInt(2) == 1);
        administration = (rset.getInt(3) == 1);
        context = LibrarianContext.getLibrarianContext(rset.getString(4));
        currentProcessType = context.getDefaultProcessType();
        ime = rset.getString(5);
        prezime = rset.getString(6);
        email = rset.getString(7);
        napomena = rset.getString(8);
      } else {
        username = "";
        password = "";
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return !"".equals(username);
  }
  
  public boolean login(){
  	ExecuteQueryCommand command = new ExecuteQueryCommand();
  	String sqlString = "SELECT obrada, cirkulacija, administracija, context, ime, prezime, email, napomena " +
  			"FROM Bibliotekari WHERE username='"+username+"' AND password='"+password+"'";
  	command.setSqlString(sqlString);
  	command = (ExecuteQueryCommand)	BisisApp.getJdbcService().executeCommand(command);
  	if(command!=null){
  	ArrayList<ArrayList<Object>> result = command.getResults();
  	if(result==null || result.size()==0){
  		username = "";
    password = "";
  	}else{
  		ArrayList<Object> row = result.get(0);
  		cataloguing = ((Integer)row.get(0)).equals(1);
  		circulation = ((Integer)row.get(1)).equals(1);
  		administration = ((Integer)row.get(2)).equals(1);
  		context = LibrarianContext.getLibrarianContext((String)row.get(3));  		
  		ime = (String)row.get(4);
  		prezime = (String)row.get(5);
  		email = (String)row.get(6);
  		napomena = (String)row.get(7);  		
  	}
  	return !"".equals(username);  	
  	}
  	else{
  		return false;
  	}  	
  }
  
  
//  public boolean login(){
//	LibLoginCommand work = new LibLoginCommand(this);
//	ServiceFactory factory= BisisApp.getFactory(CommandType.JDBC);
//	Service service = factory.createService(ServiceType.LOCAL, BisisApp.getINIFile().getString("circ", "service"));
//	work = (LibLoginCommand)service.executeCommand(work);
//	return !"".equals(username);
////	LibLogin work = new LibLogin(this);
////	ExecuteWorkCommand command = new ExecuteWorkCommand(work);
////	command = (ExecuteWorkCommand)Cirkulacija.getApp().getService().executeCommand(command);
////	return !"".equals(username);
//  }
  //TODO
  
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getNapomena() {
		return napomena;
	}
	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	}
