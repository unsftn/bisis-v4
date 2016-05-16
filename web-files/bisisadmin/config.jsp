<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configuration</title>
</head>
<body>

<jsp:include page="header.jsp" flush="true"/>
<jsp:include page="loged.jsp" flush="true"/>

<jsp:useBean id="userbean" class="com.gint.app.bisisadmin.UserBean" scope="session"></jsp:useBean>
<jsp:setProperty name="userbean" property="text" param="text"/> 
<jsp:scriptlet>session.setAttribute("page", "config");
</jsp:scriptlet>

<form method="post" action="AdminServlet">
<table width="70%">
	<tr>
		<td>Configuration</td>
	</tr>
	<tr>
		<td><textarea rows="40" cols="100" name="text"><%= userbean.getText() %></textarea></td>
	</tr>
	<tr>
		<td><input type="submit" value="Save"></td>
	</tr>
</table>
</form>
</body>
</html>