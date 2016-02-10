<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LFGIRL Login</title>
</head>
<body>
	<jsp:include page="testtemplate.jsp">
	<jsp:param name="content" value="login"/>
	<jsp:param name="title" value="Login"/>
	</jsp:include>
	<form action="loginServlet" method="post">
		<fieldset style="width: 300px">
			<legend> Login to LFGIRL </legend>
			<table>
				<tr>
					<td>User ID</td>
					<td><input type="text" name="username" required="required" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="userpass" required="required" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Login" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>

