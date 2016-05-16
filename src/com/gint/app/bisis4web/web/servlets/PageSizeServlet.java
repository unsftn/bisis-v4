package com.gint.app.bisis4web.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gint.app.bisis4web.web.beans.ErrorInfo;
import com.gint.app.bisis4web.web.beans.WebUser;

/**
 * Servlet Class
 *
 * @web.servlet              
 *   name="PageSize"
 *   display-name="PageSize"
 *   description="Sets the page size for viewing records"
 * @web.servlet-mapping      
 *   url-pattern="/PageSize"
 */
@SuppressWarnings("serial")
public class PageSizeServlet extends HttpServlet {

  public static final String NEXT_PAGE = "/display.jsp";
  public static final String ERROR_PAGE = "/error.jsp";

  public PageSizeServlet() {
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
  
    try {
      WebUser webUser = (WebUser)request.getSession().getAttribute("user");
      String sPageSize = request.getParameter("pageSize");
      int pageSize = 5;
      try { pageSize = Integer.parseInt(sPageSize); } catch (Exception ex) { }
      webUser.setPageSize(pageSize);
      getServletConfig().getServletContext().getRequestDispatcher(
          NEXT_PAGE).forward(request, response);
    } catch (Exception ex) {
      ex.printStackTrace();
      errorInfo.setException(ex);
      getServletConfig().getServletContext().getRequestDispatcher(
          ERROR_PAGE).forward(request, response);
    }
  }
}