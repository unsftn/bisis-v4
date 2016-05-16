package com.gint.app.bisis4.reports;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReportServlet extends HttpServlet {
	
  public void init(ServletConfig cfg) throws ServletException {
    super.init(cfg);
    reportDir = cfg.getInitParameter("reportDir");
    dir = new File(reportDir);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	log.info("Naziv direktorijuma "+reportDir);
    request.setCharacterEncoding(response.getCharacterEncoding());
    String file = request.getParameter("reportFile");
    if (file == null) {
      errorPage(request, response,
          "Invalid call to ReportServlet: missing parameter.");
      return;
    }
    if (file.equals("*")) {
      listContents(request, response);
      return;
    }else if(file.equals("invHoles")){
    	String odeljenje = request.getParameter("odeljenje");
    	String invKnj = request.getParameter("invKnj");
    	String odStr = request.getParameter("odStr");
    	String doStr = request.getParameter("doStr");
    	String str = InvNumberHolesReport
    		.getHoles(odeljenje, invKnj,Integer.parseInt(odStr), Integer.parseInt(doStr));
    	response.setContentType("text/xml; charset=utf-8");    	
    	response.getOutputStream().write(str.getBytes());
    	response.getOutputStream().close();
    	
    }else{    	
	    response.setContentType("text/xml; charset=utf-8");
	    String reportFile = reportDir + "/" + file;
	    log.info("request for file: " + reportFile);
	    File f = new File(reportFile);
	    if (!f.exists()) {
	      errorPage(request, response, "File does not exist: " + reportFile);
	      return;
	    }
	    BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
	    byte[] buff = new byte[BUFFER_SIZE];
	    int bytesRead = 0;
	    do {
	      bytesRead = in.read(buff);
	      response.getOutputStream().write(buff, 0, bytesRead);
	    } while (bytesRead == BUFFER_SIZE);
	    response.getOutputStream().close();
    }
  }

  private void errorPage(HttpServletRequest request,
      HttpServletResponse response, String message) throws ServletException,
      IOException {
    PrintWriter out = response.getWriter();
    out.println("<html><body> " + message + " </body></html>");
    out.close();
  }

  private void listContents(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    String[] files = dir.list();
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    for (String s : files)
      out.println(s);
    out.close();
  }

  private String reportDir;
  private File dir;
  private static Log log = LogFactory.getLog(ReportServlet.class);
  private static final int BUFFER_SIZE = 8192;
}
