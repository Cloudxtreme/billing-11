<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Welcome</title>

<link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
<spring:url value="/resources/css/bootstrap-minimalist.css" var="bootstrapCss" />
<spring:url value="/resources/css/main-billing.css" var="mainCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${mainCss}" rel="stylesheet" />

</head>
<body>
<div class="well-lg">
        <a href="?lang=en"><img class="flag flag-gb"/></a>
        <a href="?lang=ru"><img class="flag flag-ru"/></a>
<div class="col-lg-6 Absolute-Center Center-Container">

    <%--<c:set var="msg" value="${errorMessage}" scope="request"/>--%>
    <form:form class="form-horizontal" method="POST" commandName="userForm" action="${pageContext.request.contextPath}/login.html">
        <fieldset>
            <legend><spring:message code="label.authentication"/></legend>
            <c:if test="${not empty errorMessage}">
                <div class="form-group">
                    <label class="col-lg-8 text-warning"><spring:message code="authError"/></label>
                </div>
            </c:if>
            <div class="form-group">
                <label for="inputLogin" class="col-lg-2 control-label"><spring:message code="label.login"/></label>
                <div class="col-lg-10">
                    <form:input path="username" class="form-control" id="inputLogin" placeholder="user"/>
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword" class="col-lg-2 control-label"><spring:message code="label.password"/></label>
                <div class="col-lg-10">
                    <form:password path="password" class="form-control" id="inputPassword" placeholder="Enter your password"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <button type="reset" class="btn btn-default"><spring:message code="label.cancel"/></button>
                    <button type="submit" class="btn btn-primary"><spring:message code="label.submit"/></button>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>
</div>





</body>
</html>