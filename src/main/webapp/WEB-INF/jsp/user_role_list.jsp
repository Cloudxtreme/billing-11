<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.userRole"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <legend><spring:message code="label.userRole"/></legend>

        <fieldset>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-info" role="alert">${successMessage}</div>
            </c:if>
            <a href="${pageContext.request.contextPath}/user_role_form.html" class="btn btn-sm btn-primary" data-toggle="modal"><spring:message code="label.roleCreate"/></a>
            <a href="${pageContext.request.contextPath}/userpanel.html" class="margin-nav float-right">
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span> <spring:message code="label.userPanel"/>
            </a>
        <table id="userRoleTable" class="table table-striped table-hover">
            <tr>
                <th>&nbsp;</th>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
                <th><a href="${pageContext.request.contextPath}/activitylist.html"><spring:message code="label.activity"/> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a></th>
            </tr>
            <c:forEach items="${userRoleList}" var="userRole">
                <label for="${userRole.id}">
                    <tr id="${userRole.id}">
                        <td>
                            <a href="${pageContext.request.contextPath}/user_role/${userRole.id}/update"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                            &nbsp;&nbsp;
                            <a href="${pageContext.request.contextPath}/user_role/${userRole.id}/delete" onclick="return confirm('<spring:message javaScriptEscape="true" code="label.deleteUserRole"/>')"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                        </td>
                        <td>${userRole.name}</td>
                        <td>${userRole.description}</td>
                        <td>
                            <c:forEach items="${userRole.activityName}" var="userRoleActivityName">
                                ${userRoleActivityName}<br>
                            </c:forEach>
                        </td>
                    </tr>
                </label>
            </c:forEach>
        </table>
</div>




</body>
</html>
