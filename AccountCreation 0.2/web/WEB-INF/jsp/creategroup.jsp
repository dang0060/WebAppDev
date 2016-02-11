<%-- 
    Document   : creategroup
    Created on : 11-Feb-2016, 3:35:35 PM
    Author     : Alayna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Group</title>
    </head>
    <body>
       <jsp:include page="testtemplate.jsp">
	<jsp:param name="content" value="creatgroup"/>
	<jsp:param name="title" value="Create New Group"/>
	</jsp:include>
	<form action="createGroupServlet" method="post">
		<fieldset style="width: 300px">
			<legend>Create a Group</legend>
			<table>
				<tr>
					<td>Group Name</td>
					<td><input type="text" name="groupname" required="required" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="userpass" required="required" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Sign Up" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
    </body>
</html>
