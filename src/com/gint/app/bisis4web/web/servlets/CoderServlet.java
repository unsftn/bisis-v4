package com.gint.app.bisis4web.web.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gint.app.bisis4web.views.CoderUtils;

/**
 * Returns a JSON array of coded values [ { "code": "", "value": "" }, ... ] for the given prefix
 */
@SuppressWarnings("serial")
public class CoderServlet extends HttpServlet {

  public CoderServlet() {
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
      
      response.setContentType("application/json; charset=utf-8");
      request.setCharacterEncoding(response.getCharacterEncoding());
      String prefix = request.getParameter("prefix");
      if (prefix == null)
        return;
      String codes = CoderUtils.getCodedValuesForPrefixJson(prefix);
      response.getWriter().write(codes);
  }  
}
