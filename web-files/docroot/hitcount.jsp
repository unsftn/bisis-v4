<%@page contentType="text/html; charset=utf-8" %>
<%@ page import="com.gint.app.bisis4web.web.Messages" %>
<jsp:useBean id="user" class="com.gint.app.bisis4web.web.beans.WebUser" scope="session"/>
<jsp:useBean id="errorInfo" class="com.gint.app.bisis4web.web.beans.ErrorInfo" scope="session"/>
<jsp:include page="header.jsp" flush="true"/>

<h3><%=Messages.get("SEARCH_RESULTS", user.getLocale())%></h3>

<table cellpadding="0" cellspacing="0" border="0" style="font-size: 10pt;">
  <tr>
    <td><%=Messages.get("QUERY", user.getLocale())%>:&nbsp; </td>
    <td><b><%= user.getCurrentQuery() %></b></td>
  </tr>
  <tr>
    <td><%=Messages.get("HIT_COUNT", user.getLocale())%>:&nbsp; </td>
    <td><b><%= user.getHitCount() %></b></td>
  </tr>
</table>

<p></p>

<p>
<% if (user.getHitCount() > 0) {%>
<a href="display.jsp"><img src="images/<%= user.getLocale() %>/showresults.gif" border="0"></a>
<%}%>
<a href="index.jsp"><img src="images/<%= user.getLocale() %>/newquery.gif" border="0"></a>
</p>

<jsp:include page="footer.jsp" flush="true"/>
