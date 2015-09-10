<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title>Files in folder</title>

  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<body>

<div class="well">

    <table class="table table-striped" id ='table'>
        <th></th>
        <th>File name</th>
        <th>File status</th>
        <c:forEach items="${uploadedList}" var="current">
            <tr>
                <td>
                    <a id ="deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                </td>
                <td>${current}</td>
                <td></td>
            </tr>
        </c:forEach>

    </table>
</div>


</body>
</html>
