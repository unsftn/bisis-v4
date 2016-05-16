/*
 * Created on 2004.10.24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4web.xmlmessaging.util;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Error {
	
	private String levelDesc;
	private String code;
	private String description;
	private int level;
	
	
	
	/**
	 * @param code
	 * @param description
	 * @param level
	 * @param levelDesc
	 */
	public Error(String levelDesc, String code, String description, int level) {
		super();
		this.code = code;
		this.description = description;
		this.level = level;
		this.levelDesc = levelDesc;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the level.
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level The level to set.
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return Returns the levelDesc.
	 */
	public String getLevelDesc() {
		return levelDesc;
	}
	/**
	 * @param levelDesc The levelDesc to set.
	 */
	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}
}
