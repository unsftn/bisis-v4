package com.gint.app.bisis4web.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.search.Query;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.utils.QueryUtils;
import com.gint.app.bisis4web.web.Settings;

@SuppressWarnings("serial")
public class MobileQueryServlet extends HttpServlet {
	 public static final String ERROR_PAGE = "/error.jsp";
	 public MobileQueryServlet() {
		    super();
		  }

		  public void init(ServletConfig config) throws ServletException {
		    super.init(config);
		  }
		  
		  public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8"); 
		    PrintWriter out = response.getWriter();
			String responseMessage = "";
		    response.setContentType("text/html; charset=utf-8");
		    request.setCharacterEncoding(response.getCharacterEncoding());	    
		    String prefix = request.getParameter("prefix");
		    String content = request.getParameter("content");	
		    String count=request.getParameter("hit");
		    content = content.trim();
            Query q = QueryUtils.makeQueryTerm(prefix, content.toLowerCase(), "", null);
			if (q == null) {
		      getServletConfig().getServletContext().getRequestDispatcher(
		          ERROR_PAGE).forward(request, response);
		      return;
		    }	       
		    int[] hits = Settings.getSettings().getRecMgr().select2(q, null);
		    if (count!=null && count.equals("1")){  //vraca broj pogodaka
		    	
		    	responseMessage = String. valueOf(hits.length);

		    }else{  //vraca pogodke
		    	if (hits != null){
		    		Record [] records=Settings.getSettings().getRecMgr().getRecords(hits);
		    		String title,name,surname,publisher,dataOfPublication;
		    		for (int i=0;i<hits.length;i++){
		    			title=records[i].getSubfieldContent("200a");
		    			name=records[i].getSubfieldContent("700a");
		    			surname=records[i].getSubfieldContent("700b");
		    			publisher=records[i].getSubfieldContent("210c");
		    			dataOfPublication=records[i].getSubfieldContent("210d");
		    			if (title==null){
		    				title=" ";
		    			}
		    			if (name==null){
		    				name=" ";
		    			}
		    			if (surname==null){
		    				surname=" ";
		    			}
		    			if (publisher==null){
		    				publisher=" ";
		    			}
		    			if (dataOfPublication==null){
		    				dataOfPublication=" ";
		    			}
		    			
		    			responseMessage=responseMessage+name+" "+surname+";"+title+";"+publisher+";"+dataOfPublication+'\r'+'\n';
		    			
		    			
		    		}
		    	 }
		    	
		    }
		    response.setContentLength(responseMessage.length());    
		    out.println(responseMessage);
	    	out.close();  
		  }

}
