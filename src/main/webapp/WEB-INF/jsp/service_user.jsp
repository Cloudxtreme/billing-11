<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Services</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">

    <legend>Users Services</legend>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-info" role="alert">${successMessage}</div>
    </c:if>

    <a href="${pageContext.request.contextPath}/service/user/1/modify" class="btn btn-sm btn-primary" data-toggle="modal">Add Service to User</a>


    <table id="userRoleTable" class="table table-striped table-hover">
        <tr>
            <th>&nbsp;</th>
            <th>User Name</th>
            <th>User Service List</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Price</th>
        </tr>

        </tr>
        <c:forEach items="${serviceList}" var="service">
            <label for="${service.id}">
                <tr id="${service.id}">
                    <td>
                        <a href="${pageContext.request.contextPath}/service/${service.id}/update"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        &nbsp;&nbsp;
                        <a href="${pageContext.request.contextPath}/service/${service.id}/delete" onclick="return confirm('Do you really want to delete service?')"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                    </td>
                    <td>${service.name}</td>
                    <td>${service.description}</td>
                    <td>${service.price}</td>
                </tr>
            </label>
        </c:forEach>

    </table>

</div>

</body>
</html>
