package com.gint.app.bisis4web.web.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.search.Query;

import com.gint.app.bisis4web.common.RecordID;
import com.gint.app.bisis4web.web.Messages;
import com.gint.app.bisis4web.web.Settings;
import com.gint.app.bisis4web.web.beans.ErrorInfo;
import com.gint.app.bisis4web.web.beans.WebUser;
import com.gint.app.bisis4.utils.QueryUtils;

/**
 * Servlet Class
 *
 * @web.servlet 
 *   name = "SingleQuery"
 *   display-name = "SingleQuery"
 *   description = "Constructs a Dialog query from a single-prefix form and executes it"
 * @web.servlet-mapping 
 *   url-pattern = "/SingleQuery"
 * @web.ejb-local-ref 
 *   name = "ejb/bisis/RecordRetrieverLocal"
 *   type = "Session"
 *   home = "com.gint.app.bisis4web.common.ejb.interfaces.RecordRetrieverLocalHome"
 *   local= "com.gint.app.bisis4web.common.ejb.interfaces.RecordRetrieverLocal"
 *   link = "RecordRetriever"
 * @jboss.ejb-local-ref 
 *   ref-name = "ejb/bisis/RecordRetrieverLocal"
 *   jndi-name = "ejb/bisis/RecordRetrieverLocal"
 *                    
 */
@SuppressWarnings("serial")
public class SingleQueryServlet extends HttpServlet {

  public static final String NEXT_PAGE = "/hitcount.jsp";
  public static final String ERROR_PAGE = "/error.jsp";
  
  public SingleQueryServlet() {
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
    ErrorInfo errorInfo = (ErrorInfo)request.getSession().getAttribute(
        "errorInfo");
    if (errorInfo == null) {
      getServletConfig().getServletContext().getRequestDispatcher(
          ERROR_PAGE).forward(request, response);
      return;
    }
    errorInfo.setErrorOccured(false);
    errorInfo.setErrorMessage("");
    
    String prefix = request.getParameter("prefix1");
    String content = request.getParameter("content1");
    if (content == null || prefix == null) {
      errorInfo.setErrorOccured(true);
      errorInfo.setErrorMessage("Invalid page parameters.");
      getServletConfig().getServletContext().getRequestDispatcher(
          ERROR_PAGE).forward(request, response);
      return;
    }
    content = content.trim();
    if ("".equals(content)) {
      errorInfo.setErrorOccured(true);
      WebUser webUser = (WebUser)request.getSession().getAttribute("user");
      if (webUser != null)
        errorInfo.setErrorMessage(Messages.get("EMPTY_QUERY", webUser.getLocale()));
      else
        errorInfo.setErrorMessage(Messages.get("EMPTY_QUERY", "sr"));
      getServletConfig().getServletContext().getRequestDispatcher(
          ERROR_PAGE).forward(request, response);
      return;
    }

    
	Query q = QueryUtils.makeQueryTerm(prefix, content.toLowerCase(), "", null);
	
	if (q == null) {
      errorInfo.setErrorOccured(true);
      errorInfo.setErrorMessage("Invalid page parameters.");
      getServletConfig().getServletContext().getRequestDispatcher(
      ERROR_PAGE).forward(request, response);
      return;
	}
	
	if (Settings.getSettings().getShowRashod().equals("no")){
		q = QueryUtils.makeQueryTerm("XX", "aktivan", "AND", q);
	}
    
    int[] hits;
    if (Settings.getSettings().getStaticFilter() != null) {
    	hits = Settings.getSettings().getRecMgr().select3(q, QueryUtils.getQueryFilter(Settings.getSettings().getStaticFilter()), null);
    }else{
    	hits = Settings.getSettings().getRecMgr().select2(q, null);
    }

    if (hits != null){
      RecordID[] ids = new RecordID[hits.length];
      for (int i = 0; i < hits.length; i++)
        ids[i] = new RecordID(0, hits[i]);
      WebUser webUser = (WebUser)request.getSession().getAttribute("user");
      webUser.setRecordIDs(ids);
      webUser.setCurrentQuery(prefix + "=" + content);
      webUser.setStartIndex(0);
      getServletConfig().getServletContext().getRequestDispatcher(
          NEXT_PAGE).forward(request, response);
    } else {
    	RecordID[] ids = new RecordID[0];
      WebUser webUser = (WebUser)request.getSession().getAttribute("user");
      webUser.setRecordIDs(ids);
      webUser.setCurrentQuery(prefix + "=" + content);
      webUser.setStartIndex(0);
      getServletConfig().getServletContext().getRequestDispatcher(
          NEXT_PAGE).forward(request, response);
    }
  }
}