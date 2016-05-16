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
 *   name = "MultiQuery"
 *   display-name = "MultiQuery"
 *   description = "Constructs a Dialog query from a single-prefix form and executes it"
 * @web.servlet-mapping 
 *   url-pattern = "/MultiQuery"
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
public class MultiQueryServlet extends HttpServlet {

  public static final int FIELD_COUNT = 5;
  
  public static final String NEXT_PAGE = "/hitcount.jsp";
  public static final String ERROR_PAGE = "/error.jsp";
  
  public MultiQueryServlet() {
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
    
    String prefix = null;
    String content = null;
    String operator = "";
    String displayQuery = "";
    String empty = "";
    for (int i = 0; i < FIELD_COUNT; i++) {
    	if (i > 0) {
      	if (!"".equals(content)){
      		operator = request.getParameter("operator" + (i));
      	}
        if (operator == null) {
          errorInfo.setErrorMessage("Invalid query parameters.");
          errorInfo.setErrorOccured(true);
          getServletConfig().getServletContext().getRequestDispatcher(
              ERROR_PAGE).forward(request, response);
          return;
        }
      }
      prefix = request.getParameter("prefix" + (i+1));
      content = request.getParameter("content" + (i+1));
      if (prefix == null || content == null) {
        errorInfo.setErrorMessage("Invalid query parameters.");
        errorInfo.setErrorOccured(true);
        getServletConfig().getServletContext().getRequestDispatcher(
            ERROR_PAGE).forward(request, response);
        return;
      }
      
      content = content.trim();
      if (!"".equals(content)){
        displayQuery += " "+operator+" " + prefix + "=" + content;
        empty += content;
      }
    }
    
    if ("".equals(empty)) {
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

    Query q = QueryUtils.makeLuceneAPIQuery(request.getParameter("prefix1"), request.getParameter("operator1"), request.getParameter("content1"),
    		request.getParameter("prefix2"), request.getParameter("operator2"), request.getParameter("content2"),
    		request.getParameter("prefix3"), request.getParameter("operator3"), request.getParameter("content3"),
    		request.getParameter("prefix4"), request.getParameter("operator4"), request.getParameter("content4"),
    		request.getParameter("prefix5"), request.getParameter("content5"));  
    
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
      webUser.setCurrentQuery(displayQuery);
      webUser.setStartIndex(0);
      getServletConfig().getServletContext().getRequestDispatcher(
          NEXT_PAGE).forward(request, response);
    } else {
    	RecordID[] ids = new RecordID[0];
      WebUser webUser = (WebUser)request.getSession().getAttribute("user");
      webUser.setRecordIDs(ids);
      webUser.setCurrentQuery(displayQuery);
      webUser.setStartIndex(0);
      getServletConfig().getServletContext().getRequestDispatcher(
          NEXT_PAGE).forward(request, response);
    }
  }
}