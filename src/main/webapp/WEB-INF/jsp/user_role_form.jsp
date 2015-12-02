<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>User Role Add/Update</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="userRoleForm" action="${pageContext.request.contextPath}/user_role_form.html">
        <fieldset>
            <legend>User Role</legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}">Please fill in all fields below.</label>
            </div>
            <form:hidden path="id" />
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
                <div class="col-lg-9 col-lg-offset-3">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </fieldset>
    </form:form>


</div>




</body>
</html>
