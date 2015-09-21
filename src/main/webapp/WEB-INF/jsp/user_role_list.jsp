<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>User Role</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <legend>User Role</legend>

        <fieldset>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-info" role="alert">${successMessage}</div>
            </c:if>
            <a href="${pageContext.request.contextPath}/user_role_form.html" class="btn btn-sm btn-primary" data-toggle="modal">Create New User Role</a>
            <a href="${pageContext.request.contextPath}/user_panel.html" class="margin-nav float-right">
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span> User Panel
            </a>
        <table id="userRoleTable" class="table table-striped table-hover">
            <tr>
                <th>&nbsp;</th>
                <th>Role Name</th>
                <th>Role Description</th>
                <th><a href="${pageContext.request.contextPath}/activity_list.html">Activity <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a></th>
            </tr>
            <c:forEach items="${userRoleList}" var="userRole">
                <label for="${userRole.id}">
                    <tr id="${userRole.id}">
                        <td>
                            <a href="${pageContext.request.contextPath}/user_role/${userRole.id}/update"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                            &nbsp;&nbsp;
                            <a href="${pageContext.request.contextPath}/user_role/${userRole.id}/delete" onclick="return confirm('Do you really want to delete user role?')"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                        </td>
                        <td>${userRole.name}</td>
                        <td>${userRole.description}</td>
                        <td>
                            <c:forEach items="${userRole.activities}" var="userRoleActivities">
                                ${userRoleActivities.name}<br>
                            </c:forEach>
                        </td>
                    </tr>
                </label>
            </c:forEach>
        </table>
</div>




</body>
</html>
