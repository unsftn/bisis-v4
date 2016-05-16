package com.gint.app.bisis4web.xmlmessaging.util;

import java.util.ResourceBundle;

public class ResourceReader{
	private static final String resourceLocation="com.gint.app.bisis4web.xmlmessaging.util.config";
	
	/**
	 * @return Returns the resourceLocation.
	 */
	public static String getResourceLocation() {
		return resourceLocation;
	}
	
	static ResourceBundle rb=null;
	
	static{
		try{
			rb=ResourceBundle.getBundle(resourceLocation);
		}catch(Exception e){
			if(MessagingEnvironment.DEBUG==1)
				System.out.println("ResourceReader exception");
			e.printStackTrace();
		}
	}
	
	public static ResourceBundle getRB(){
		return rb;
	}

}