package com.gint.app.bisis4.client.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gint.app.bisis4.commandservice.Command;
import com.gint.app.bisis4.commandservice.CommandType;
import com.gint.app.bisis4.librarian.Librarian;
import com.gint.app.bisis4.librarian.LibrarianContext;

public class LibLoginCommand extends Command{
	
	Librarian lib;
	Connection conn;
	
	public LibLoginCommand(Librarian lib){
		this.type = CommandType.JDBC;
		this.lib = lib;
	}
	
	public Librarian getLibrarian(){
		return lib;
	}
	
	public void setContext(Object context){
		conn = (Connection)context;
	}
	
	public void execute() {
		  try {
			PreparedStatement stmt = conn.prepareStatement(
		        "SELECT obrada, cirkulacija, administracija, context, ime, prezime, email, napomena FROM Bibliotekari WHERE username=? AND password=?");
		    stmt.setString(1, lib.getUsername());
		    stmt.setString(2, lib.getPassword());
		    ResultSet rset = stmt.executeQuery();
		    if (rset.next()) {
		      lib.setCataloguing(rset.getInt(1) == 1);
		      lib.setCirculation(rset.getInt(2) == 1);
		      lib.setAdministration(rset.getInt(3) == 1);
		      lib.setContext(LibrarianContext.getLibrarianContext(rset.getString(4)));
		      lib.setCurrentProcessType(lib.getContext().getDefaultProcessType());
		      lib.setIme(rset.getString(5));
		      lib.setPrezime(rset.getString(6));
		      lib.setEmail(rset.getString(7));
		      lib.setNapomena(rset.getString(8));
		    } else {
		      lib.setUsername("");
		      lib.setPassword("");
		    }
		    rset.close();
		    stmt.close();
		  } catch (SQLException ex) {
		    ex.printStackTrace();
		  }
		}
}
