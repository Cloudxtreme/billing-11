<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.localUserAddUpdate"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/charactersRemain/charactersCounter.js" var="charRemain" />
    <script src="${charRemain}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="localUserForm" action="${pageContext.request.contextPath}/userform.html">
        <fieldset>
            <legend><spring:message code="label.localUser"/></legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}"><spring:message code="label.fillField"/></label>
            </div>
            <form:hidden path="id" />
            <div class="form-group">
                <label for="username" class="col-lg-3 control-label"><spring:message code="label.name"/></label>
                <div class="col-lg-6">
                    <spring:message code="label.name" var="name"/>
                    <form:input path="username" class="form-control" id="username" placeholder="${name}" maxlength="16"/>
                    <form:errors path="username" cssClass="alert-danger" />
                </div>
                <div class="col-lg-3" style="display: table; height: 36px;">
                    <span style="display: table-cell; vertical-align: middle;">
                        <strong class="countdown"></strong>
                    </span>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-lg-3 control-label"><spring:message code="label.password"/></label>
                <div class="col-lg-9">
                    <spring:message code="label.password" var="pass"/>
                    <form:password path="password" class="form-control" id="password" placeholder="${pass}" />
                    <form:errors path="password" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-lg-3 control-label"><spring:message code="label.passwordConfirm"/></label>
                <div class="col-lg-9">
                    <spring:message code="label.passwordConfirm" var="confirm"/>
                    <form:password path="passwordConfirm" class="form-control" id="password" placeholder="${confirm}" />
                    <form:errors path="passwordConfirm" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-3 control-label"><spring:message code="label.userRole"/></label>
                <div class="col-lg-9">
                    <c:forEach items="${roleList}" var="role">
                        <label for="${role.id}" class="control-label"><form:checkbox path="roleId" value="${role.id}" id="${role.id}" /> ${role.name}</label><br>
                    </c:forEach>
                    <form:errors path="roleId" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-9 col-lg-offset-3">
                    <button type="submit" class="btn btn-primary"><spring:message code="label.submit"/></button>
                </div>
            </div>
        </fieldset>
    </form:form>

</div>

</body>
</html>
