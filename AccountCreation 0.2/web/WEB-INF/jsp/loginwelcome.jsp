<%-- 
    Document   : loginwelcome
    Created on : 11-Feb-2016, 2:20:08 PM
    Author     : Alayna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
    </head>
    <body>
        <jsp:include page="testtemplate.jsp">
	<jsp:param name="content" value="welcome"/>
	<jsp:param name="title" value="Welcome"/>
        </jsp:include>
        <p>Welcome <%=((String)request.getSession().getAttribute("name"))%>!</p>
    </body>
</html>
