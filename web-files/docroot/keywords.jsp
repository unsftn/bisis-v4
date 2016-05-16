<%@page contentType="text/html; charset=utf-8" %>
<%@ page import="com.gint.app.bisis4web.web.Messages" %>
<jsp:useBean id="user" class="com.gint.app.bisis4web.web.beans.WebUser" scope="session"/>
<jsp:useBean id="errorInfo" class="com.gint.app.bisis4web.web.beans.ErrorInfo" scope="session"/>
<jsp:include page="header.jsp" flush="true"/>

<h4><%=Messages.get("SEARCH_BY_KEYWORDS", user.getLocale())%></h4>

<form name="searchform" action="SingleQuery" method="post" accept-charset="utf-8">
<%=Messages.get("SEEK_KEYWORDS", user.getLocale())%><br>
<input type="text" size="40" name="content1"><br/>
<input type="submit" value=" <%=Messages.get("SUBMIT_BUTTON", user.getLocale())%> " name="submit">
<input type="hidden" name="prefix1" value="KW">
</form>


<jsp:include page="footer.jsp" flush="true"/>

