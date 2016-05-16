<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>

<jsp:include page="header.jsp" flush="true"/>

<jsp:useBean id="userbean" class="com.gint.app.bisisadmin.UserBean" scope="session"></jsp:useBean>
<jsp:scriptlet>session.setAttribute("page", "login");
</jsp:scriptlet>
<form method="post" action="AdminServlet">
<table>
	<tr>
		<td>Username</td>
		<td><input type="text" name="user"></td>
	</tr>
	<tr>
		<td>Password</td>
		<td><input type="password" name="pass"><input type="submit" value="Log in"></td>
	</tr>
</table>
</form>
</body>
</html>