package com.gint.app.bisisadmin;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {
	
	 public AdminServlet() {
		    super();
		  }

		  public void init(ServletConfig config) throws ServletException {
		    super.init(config);
		  }
		  
		  public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		    	doPost(request, response);
		    }
		  
		  public void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding(response.getCharacterEncoding());	  
		      
		    String page = (String)request.getSession().getAttribute("page");
		    UserBean user = (UserBean)request.getSession().getAttribute("userbean");
		    
		    if (page == null){
		    	getServletConfig().getServletContext().getRequestDispatcher(
					"/login.jsp").forward(request, response);
		    } else if (request.getParameter("logout")!= null){
		    	request.getSession().invalidate();
		    	getServletConfig().getServletContext().getRequestDispatcher(
				"/login.jsp").forward(request, response);
		    } else if (page.equals("login")){
			    user.setUser(request.getParameter("user"));
			    user.setPass(request.getParameter("pass"));
		    	DBManager dbmng = new DBManager();
		    	boolean login = dbmng.login(user);
		    	if (login){
		    		JarManager jarmng = new JarManager();
		    		boolean success = jarmng.loadConfig(user);
		    		if (success){
		    			request.getSession().setAttribute("jarmng", jarmng);
		    			request.getSession().setAttribute("userbean", user);
		    			getServletConfig().getServletContext().getRequestDispatcher(
    						"/config.jsp").forward(request, response);
		    		} else {
		    			getServletConfig().getServletContext().getRequestDispatcher(
	    					"/msg.jsp").forward(request, response);
		    		}
		    	} else {
		    		getServletConfig().getServletContext().getRequestDispatcher(
		    				"/msg.jsp").forward(request, response);
		    	} 
		    } else if (page.equals("config")){
		    	JarManager jarmng = (JarManager)request.getSession().getAttribute("jarmng");
		    	user.setText(request.getParameter("text"));
		    	jarmng.saveConfig(user);
	    		getServletConfig().getServletContext().getRequestDispatcher(
						"/msg.jsp").forward(request, response);
		    } 
		
		  }

}
