<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete User</title>
</head>
<body>
	<jsp:include page="testtemplate.jsp">
	<jsp:param name="content" value="deleteaccount"/>
	<jsp:param name="title" value="Delete User"/>
	</jsp:include>
	<form action="deleteAccountServlet" method="post">
		<fieldset style="width: 300px">
			<table>
				<tr>
					<td>Enter Password</td>
					<td><input type="password" name="userpass" required="required" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Delete Account" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>