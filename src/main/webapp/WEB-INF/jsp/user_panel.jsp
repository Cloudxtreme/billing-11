<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>User Panel</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/utils.js" var="utils" />
    <script src="${utils}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">

    <legend><span class="glyphicon glyphicon-user" aria-hidden="true"></span> User Panel
        </legend>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-info" role="alert">${successMessage}</div>
    </c:if>

    <a href="${pageContext.request.contextPath}/user_form.html" class="btn btn-sm btn-primary" data-toggle="modal">Create New User</a>
    <a href="${pageContext.request.contextPath}/user_role_list.html" class="btn btn-sm btn-success float-right" data-toggle="modal">User Role</a>
    <a href="${pageContext.request.contextPath}/activity_list.html" class="btn btn-sm btn-info float-right" data-toggle="modal">Activity</a>

    <table id="userRoleTable" class="table table-striped table-hover">
        <tr>
            <th>&nbsp;</th>
            <th>User Name</th>
            <th><a href="${pageContext.request.contextPath}/user_role_list.html">User Role <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a></th>
        </tr>
        <c:forEach items="${userList}" var="user">
            <label for="${user.id}">
                <tr id="${user.id}">
                    <td>
                        <a href="${pageContext.request.contextPath}/user/${user.id}/update"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        &nbsp;&nbsp;
                        <a href="${pageContext.request.contextPath}/user/${user.id}/delete" onclick="return confirm('Do you really want to delete user?')"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                    </td>
                    <td>${user.username}</td>
                    <td>
                        <c:forEach items="${user.userRoles}" var="roleList">
                            ${roleList.name}<br>
                        </c:forEach>
                    </td>
                </tr>
            </label>
        </c:forEach>
    </table>

</div>

</body>
</html>
