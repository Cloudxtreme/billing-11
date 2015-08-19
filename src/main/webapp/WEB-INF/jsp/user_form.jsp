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
    <form:form class="form-horizontal" method="POST" commandName="localUserForm" action="${pageContext.request.contextPath}/user_form.html">
        <fieldset>
            <legend>User</legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}">Please fill in all fields below.</label>
            </div>
            <form:hidden path="id" />
            <div class="form-group">
                <label for="name" class="col-lg-3 control-label">Name</label>
                <div class="col-lg-9">
                    <form:input path="name" class="form-control" id="name" placeholder="User Name" />
                    <form:errors path="name" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-lg-3 control-label">Password</label>
                <div class="col-lg-9">
                    <form:password path="password" class="form-control" id="password" placeholder="Enter your password" />
                    <form:errors path="password" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-3 control-label">Role</label>
                <div class="col-lg-9">
                    <c:forEach items="${roleList}" var="role">
                        <label for="${role.id}" class="control-label"><form:checkbox path="roleId" value="${role.id}" id="${role.id}" /> ${role.name}</label><br>
                    </c:forEach>
                    <form:errors path="roleId" cssClass="alert-danger" />
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


</div>

</body>
</html>