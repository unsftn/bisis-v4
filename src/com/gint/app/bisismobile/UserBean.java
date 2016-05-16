package com.gint.app.bisismobile;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserBean implements Serializable{
	
	private String user;
	private String pass;
	private String msg;
	private String text;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
