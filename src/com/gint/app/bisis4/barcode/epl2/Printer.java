package com.gint.app.bisis4.barcode.epl2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Printer implements IPrinter {
  public static Printer LINUX_USB = new Printer("/dev/usb/lp0");
  public static Printer LINUX_LPT = new Printer("/dev/lp0");
  public static Printer LINUX_GEN = new Printer("/dev/barcode");
  public static Printer WINDOWS = new Printer("LPT1");
  public static Printer DEBUG = new Printer(System.getProperty("user.home") + 
      "/printer.out");
  
  public static Printer getDefaultPrinter(String port) {
    if ("DEBUG".equals(port))
      return DEBUG;
    String os = System.getProperty("os.name");
    
    if ("Linux".equals(os)){
    	if (new File("/dev/barcode").exists())
    		return LINUX_GEN;
    	
    	if("lpt".equals(port))
    		return LINUX_LPT;
    	return LINUX_USB;
    }else{
      return WINDOWS;
    }
  }
  
  private Printer(String fileName) {
    this.fileName = fileName;
  }
  
  public boolean print(Label label, String pageCode) {
    return print(label.getCommands(), pageCode);
  }
  
  public synchronized boolean print(String commands, String codePage) {
    try {
      PrintWriter out = new PrintWriter(new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(fileName), 
              "cp" + codePage)));
      out.print(commands);
      out.flush();
      out.close();
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }
  
  public String toString() {
    return "[" + fileName + "]";
  }
  
  private String fileName;
  
}
