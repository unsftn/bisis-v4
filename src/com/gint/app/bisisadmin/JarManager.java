package com.gint.app.bisisadmin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.jar.*;
import java.util.zip.ZipEntry;

//import sun.security.tools.jarsigner.Main;


public class JarManager implements Serializable{

	private String jarpath;
	private String jarname;
	private String keystore;
	private String storepass;
	private String keypass;
	private String alias;
	private String tsa;
	private String proxyHost;
	private String proxyPort;
	private JarFile jar;
	
	
	public JarManager(){	
		ResourceBundle rb = PropertyResourceBundle.getBundle(
	      "com.gint.app.bisisadmin.Config");
		  jarpath = rb.getString("path");
		  jarname = rb.getString("name");
		  keystore = rb.getString("keystore");
		  storepass = rb.getString("storepass");
		  keypass = rb.getString("keypass");
		  alias = rb.getString("alias");
		  tsa = rb.getString("tsa");
		  proxyHost = rb.getString("proxyHost");
		  proxyPort = rb.getString("proxyPort");
		  
	}
	
	public boolean loadConfig(UserBean user){
		StringWriter sw = new StringWriter();
		String line;
		try {
			jar = new JarFile(jarpath + jarname);
			InputStream in = jar.getInputStream(jar.getEntry("client-config.ini"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			while ((line = reader.readLine()) != null) {
			    sw.append(line).append("\n");
			}
			in.close();
			user.setText(sw.toString());
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			user.setMsg("Reading file error!");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			user.setMsg("Reading file error!");
			return false;
		}
		
	}
	
	public boolean saveConfig(UserBean user){
		try {
			Manifest manifest = jar.getManifest();
			Attributes attr = manifest.getMainAttributes();

			Manifest newManifest = new Manifest();
			newManifest.getMainAttributes().putValue("Manifest-Version", attr.getValue("Manifest-Version"));
			newManifest.getMainAttributes().putValue("Created-By", attr.getValue("Created-By"));
			
			String line;
			jar.getJarEntry("log4j.properties");
			JarOutputStream newJar = new JarOutputStream(new FileOutputStream(jarpath + "newConfig.jar"), newManifest);
			newJar.putNextEntry(new ZipEntry("log4j.properties"));
			InputStream in = jar.getInputStream(jar.getEntry("log4j.properties"));
			byte[] buffer = new byte[4096]; 
			int read; 
			while ((read = in.read(buffer)) != -1) { 
				newJar.write(buffer, 0, read); 
			} 
			newJar.closeEntry();
			in.close();
			
			newJar.putNextEntry(new ZipEntry("client-config.ini"));
			ByteArrayInputStream is = new ByteArrayInputStream(user.getText().getBytes("UTF-8"));
			while ((read = is.read(buffer)) != -1) { 
				newJar.write(buffer, 0, read); 
			} 
			newJar.closeEntry();
			is.close();
			newJar.close();
			jar.close();
			
			File oldFile = new File(jarpath + jarname);
			oldFile.delete();
			File newFile = new File(jarpath + "newConfig.jar");
			newFile.renameTo(oldFile);
			signJar();
			user.setMsg("File saved!");
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			user.setMsg("Saving file error!");
			return false;
		}
	}
	
	private void signJar(){
		try {
			String exeString;
			if (proxyHost.isEmpty()){
				exeString = "jarsigner"
					+ " -keystore "
					+ keystore
					+ " -storepass "
					+ storepass
					+ " -keypass "
					+ keypass
					+ " -tsa "
					+ tsa
					+ " "
					+ jarpath + jarname
					+ " "
					+ alias;
			} else {
				exeString = "jarsigner"
					+ " -keystore "
					+ keystore
					+ " -storepass "
					+ storepass
					+ " -keypass "
					+ keypass
					+ " -tsa "
					+ tsa
					+ " -J-Dhttp.proxyHost="
					+ proxyHost
					+ " -J-Dhttp.proxyPort="
					+ proxyPort
					+ " "
					+ jarpath + jarname
					+ " "
					+ alias;
			}
			//System.out.println(exeString);
			
			Process p = Runtime.getRuntime().exec(exeString);
		
			BufferedReader stdInput = new BufferedReader(new
	                 InputStreamReader(p.getInputStream()));
	 
            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));
            
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
                      
		} catch (IOException e) {
			e.printStackTrace();
		}
//		if (proxyHost.isEmpty()){
//			String[] args = { 
//				"-keystore", keystore, 
//				"-storepass", storepass, 
//				"-keypass", keypass,
//				"-tsa", tsa,
//				jarpath + jarname, 
//				alias }; 
//			Main signer = new Main(); 
//			signer.run(args);
//		} else {
//			String[] args = { 
//				"-keystore", keystore, 
//				"-storepass", storepass, 
//				"-keypass", keypass,
//				"-tsa", tsa,
//				"-J-Dhttp.proxyHost=" + proxyHost,
//				"-J-Dhttp.proxyPort=" + proxyPort,
//				jarpath + jarname, 
//				alias }; 
//			Main signer = new Main(); 
//			signer.run(args);
//		}
				 
	}

}
