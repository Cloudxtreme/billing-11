<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>Device</title>


<jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well">
    <button type="button" class="btn btn-lg btn-primary">Add New Device</button>
    <div>
           <table class="table table-striped">
              <TH>Name</th>
              <TH>Type</th>
              <th>Descpition</th>
              <c:forEach items="${list}" var="current">
                <tr>
                  <td>${current.name}</td>
                  <td>${current.type}</td>
                  <td>${current.desc}</td>
                </tr>
              </c:forEach>
        </table>
    </div>
</div>


</body>
</html>