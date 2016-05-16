<jsp:useBean id="userbean" class="com.gint.app.bisisadmin.UserBean" scope="session"></jsp:useBean>

<form method="post" action="AdminServlet">
<table width="70%">
	<tr>
		<td align="right">
			Loged: <%= userbean.getUser() %>		
			<input type="submit" name="logout" value="Log out">
		</td>
	</tr>
</table>
</form>