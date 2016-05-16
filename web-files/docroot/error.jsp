<%@page contentType="text/html; charset=utf-8" %>
<jsp:useBean id="user" class="com.gint.app.bisis4web.web.beans.WebUser" scope="session"/>
<jsp:useBean id="errorInfo" class="com.gint.app.bisis4web.web.beans.ErrorInfo" scope="session"/>
<jsp:include page="header.jsp" flush="true"/>

<h3>BISIS Application Error</h3>

<pre>
<%= errorInfo.getErrorMessage() %>
</pre>

<jsp:include page="footer.jsp" flush="true"/>
