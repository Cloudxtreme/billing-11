<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Local User Add/Update</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="localUserForm" action="${pageContext.request.contextPath}/user_form.html">
        <fieldset>
            <legend>Local User</legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}">Please fill in all fields below.</label>
            </div>
            <form:hidden path="id" />
            <div class="form-group">
                <label for="username" class="col-lg-3 control-label">Name</label>
                <div class="col-lg-9">
                    <form:input path="username" class="form-control" id="username" placeholder="User Name" />
                    <form:errors path="username" cssClass="alert-danger" />
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
                <label for="password" class="col-lg-3 control-label">Confirm Password</label>
                <div class="col-lg-9">
                    <form:password path="passwordConfirm" class="form-control" id="password" placeholder="Confirm your password" />
                    <form:errors path="passwordConfirm" cssClass="alert-danger" />
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
                <div class="col-lg-9 col-lg-offset-3">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </fieldset>
    </form:form>


</div>

</body>
</html>
