<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
</head>
<body>
	<jsp:include page="testtemplate.jsp">
	<jsp:param name="content" value="createaccount"/>
	<jsp:param name="title" value="Sign Up"/>
	</jsp:include>
	<form action="signUpServlet" method="post">
		<fieldset style="width: 300px">
			<legend>Sign Up For LFGIRL</legend>
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" required="required" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="userpass" required="required" /></td>
				</tr>
				<tr>
					<td>Repeat Password</td>
					<td><input type="password" name="confirmpass"
						required="required" /></td>
				</tr>
				<tr>
					<td>Email address</td>
					<td><input type="email" name="useremail" required="required" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Sign Up" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>