<%@page contentType="text/html; charset=utf-8" %>
<%@ page import="com.gint.app.bisis4web.web.Messages" %>
<jsp:useBean id="user" class="com.gint.app.bisis4web.web.beans.WebUser" scope="session"/>
<jsp:useBean id="errorInfo" class="com.gint.app.bisis4web.web.beans.ErrorInfo" scope="session"/>
<jsp:include page="header.jsp" flush="true"/>
<p align="right">
<a href="manual/<%=user.getLocale()%>/index.html" class="mainlink"><%=Messages.get("HELP", user.getLocale())%></a><br/>
</p>
<p style="padding-left: 50px;">
<b><%=Messages.get("CHOOSE_SEARCH_MODE", user.getLocale())%></b><br><br>
<a href="author.jsp" class="mainlink"><img src="images/bullet.gif" height="10" width="10" border="0"/> <%=Messages.get("BY_AUTHOR", user.getLocale())%></a><br/>
<a href="title.jsp" class="mainlink"><img src="images/bullet.gif" height="10" width="10" border="0"/> <%=Messages.get("BY_TITLE", user.getLocale())%></a><br/>
<a href="keywords.jsp" class="mainlink"><img src="images/bullet.gif" height="10" width="10" border="0"/> <%=Messages.get("BY_KEYWORDS", user.getLocale())%></a><br/><br/>
<a href="custom.jsp" class="mainlink"><img src="images/bullet.gif" height="10" width="10" border="0"/> <%=Messages.get("BY_ADVANCED", user.getLocale())%></a><br/>
</p>

<jsp:include page="footer.jsp" flush="true"/>

