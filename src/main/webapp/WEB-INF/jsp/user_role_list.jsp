<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Add Activity</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/utils.js" var="utils" />
    <script src="${utils}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
        <fieldset>
            <c:if test="${not empty successMessage}">
            <div class="alert alert-info" role="alert">${successMessage}</div>
            </c:if>
            <a href="${pageContext.request.contextPath}/user_role_form.html" class="btn btn-lg btn-primary" data-toggle="modal">Create New User Role</a>

        <table id="userRoleTable" class="table table-striped">
            <tr>
                <th>&nbsp;</th>
                <th>Role Name</th>
                <th>Role Description</th>
                <th>Role Activities</th>
            </tr>
            <c:forEach items="${userRoleList}" var="userRole">
                <label for="${userRole.id}">
                    <tr id="${userRole.id}">
                        <td>
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                            &nbsp;&nbsp;
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
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
