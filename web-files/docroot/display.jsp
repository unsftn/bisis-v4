<% response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
response.setHeader("Pragma","no-cache"); //HTTP 1.0 
response.setHeader ("Expires", "0"); //prevents caching at the proxy server %>
<%@page contentType="text/html; charset=utf-8" %>
<%@ page import="com.gint.app.bisis4web.web.Messages" %>
<%@ taglib uri="http://www.bisis.ns.ac.yu/jsptags/display" prefix="bisis" %>
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
  <tr>
    <td><%=Messages.get("HITS_DISPLAYED", user.getLocale())%>:&nbsp; </td>
    <td><b><%= user.getStartIndex()+1 %>-<%= user.getEndIndex() %></b></td>
  </tr>
</table>
<form name="pageSize" method="post" action="PageSize">
  <select name="pageSize" onChange="submit()">
    <option value="5" <%if (user.getPageSize()==5){%>selected<%}%>><%=Messages.get("DISPLAY_5", user.getLocale())%></option>
    <option value="10" <%if (user.getPageSize()==10){%>selected<%}%>><%=Messages.get("DISPLAY_10", user.getLocale())%></option>
    <option value="20" <%if (user.getPageSize()==20){%>selected<%}%>><%=Messages.get("DISPLAY_20", user.getLocale())%></option>
  </select>
  <input type="image" src="images/bullet.gif">
</form>

<p></p>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
  <td align="left">
    <a href="PrevPage"><img src="images/<%=user.getLocale()%>/prev.gif" border="0"></a>
    <a href="NextPage"><img src="images/<%=user.getLocale()%>/next.gif" border="0"></a>
    <a href="index.jsp"><img src="images/<%=user.getLocale()%>/newquery.gif" border="0"></a>
  </td>
  <td align="right">
    <a href="BriefFormat"><img src="images/<%=user.getLocale()%>/brief.gif" border="0"></a> 
	  <a href="DetailFormat"><img src="images/<%=user.getLocale()%>/detail.gif" border="0"></a> 
  	<a href="FullFormat"><img src="images/<%=user.getLocale()%>/full.gif" border="0"></a>
  </td>
</tr>
</table>

<bisis:display 
	user="<%=user%>"
	tableClass="displaytable" 
	cellClass="displaycell"/>

<jsp:include page="footer.jsp" flush="true"/>
