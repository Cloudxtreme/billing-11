<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>List of budgets</title>
</head>
<body>
<h1>Please input Your login and pass</h1>




<form:form method="POST" commandName="userForm" action="${pageContext.request.contextPath}/userCheck.html">
    <table>
        <tbody>
        <tr>
            <td>Login:</td>
            <td><form:input path="userName" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:password path="userPass" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Login" /></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</form:form>

</body>
</html>