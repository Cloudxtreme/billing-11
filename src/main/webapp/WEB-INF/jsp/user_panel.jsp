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

    <legend><span class="glyphicon glyphicon-user" aria-hidden="true"></span> User Panel
        <a href="${pageContext.request.contextPath}/user_role_list.html"><span class="label label-info" style="float: right; margin: 5px;">User Role</span></a>
        &nbsp; &nbsp;<a href="${pageContext.request.contextPath}/activity_list.html"><span class="label label-success" style="float: right; margin: 5px;">Activity</span></a>
    </legend>
    <a href="${pageContext.request.contextPath}/user_form.html" class="btn btn-lg btn-primary" data-toggle="modal">Create New User</a>

    <table id="userRoleTable" class="table table-striped table-hover">
        <tr>
            <th>&nbsp;</th>
            <th>User Name</th>
            <th>Role</th>
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
<%--
                        <c:forEach items="${user.roles}" var="userRoles">
                            ${userRoles.name}<br>
                        </c:forEach>
--%>
                    </td>
                </tr>
            </label>
        </c:forEach>
    </table>

<%--
    <form:form class="form-horizontal" method="POST" commandName="userRoleForm" action="${pageContext.request.contextPath}/role.html">
        <fieldset>
            <legend>User Role</legend>
                <div class="form-group">
                    <label class="col-lg-8 ${errorClass}">Please fill in all fields below.</label>
                </div>
            <div class="form-group">
                <label for="roleName" class="col-lg-3 control-label">Role Name</label>
                <div class="col-lg-9">
                    <form:input path="name" class="form-control" id="roleName" placeholder="Role Name" />
                    <form:errors path="name" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label for="roleDescription" class="col-lg-3 control-label">Role Description</label>
                <div class="col-lg-9">
                    <form:input path="description" class="form-control" id="roleDescription" placeholder="Role Description" />
                    <form:errors path="description" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-3 control-label">Activity</label>
                <div class="col-lg-9">
                    <c:forEach items="${activityList}" var="activity">
                        <label for="${activity.id}" class="control-label"><form:checkbox path="activityId" value="${activity.id}" id="${activity.id}" /> ${activity.name}</label><br>
                    </c:forEach>
                    <form:errors path="activityId" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <button type="reset" class="btn btn-default">Cancel</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </fieldset>
    </form:form>
--%>


<%--
    <label class="col-lg-3 control-label">User Role</label>
    <div id="userRoleTableDiv">
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
--%>


<%--
    <form:form class="form-horizontal" method="POST" commandName="userPanelForm" action="${pageContext.request.contextPath}/activity.html">
        <fieldset>
            <legend>Modificate Activity</legend>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-info" role="alert">${successMessage}</div>
            </c:if>
            <c:if test="${not empty deleteMessage}">
                <div class="alert alert-info" role="alert">${deleteMessage}</div>
            </c:if>
            <form:select path="activityId" multiple="">
                <c:forEach items="${activityList}" var="activity">
                    <form:option value="${activity.id}">${activity.name}</form:option>
                </c:forEach>
            </form:select>
        <button class="btn btn-success btn-xs" type="submit" formaction="${pageContext.request.contextPath}/activity_add.html" name="add">add</button>
        <button type="submit" class="btn btn-info btn-xs"  formaction="${pageContext.request.contextPath}/activity_edit.html" name="edit">edit selected</button>
        <button type="submit" class="btn btn-danger btn-xs"  formaction="${pageContext.request.contextPath}/activity_delete.html" name="delete" onclick="return confirm('Do you really want to delete activity?')">delete selected</button>
        </fieldset>
    </form:form>
--%>

</div>

</body>
</html>
