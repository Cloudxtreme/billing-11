<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Services</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">

    <legend>Account Services</legend>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-info" role="alert">${successMessage}</div>
    </c:if>

    <table id="accountServiceTable" class="table table-striped table-hover">
        <tr>
            <th>&nbsp;</th>
            <th>Account</th>
            <th>Service</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Price</th>
        </tr>

        <c:forEach items="${accountList}" var="account">
            <label for="${account.id}">
                <tr id="${account.id}">
                    <td>
                        <a href="${pageContext.request.contextPath}/service/account/${account.id}/0/modify"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                    </td>
                    <td colspan="5"><a href="${pageContext.request.contextPath}/service/account/${account.id}/0/modify">${account.accountName}</a></td>
                </tr>


                <c:forEach items="${account.serviceForms}" var="accountService">
                    <label for="${accountService.id}">
                        <tr id="${accountService.id}">
                            <td>&nbsp;</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/service/account/${account.id}/${accountService.id}/modify"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                                &nbsp;&nbsp;
                                <a href="${pageContext.request.contextPath}/service/account/${accountService.id}/delete" onclick="return confirm('Do you really want to delete account service?')"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                            </td>
                            <td>${accountService.serviceType.name}</td>
                            <td><fmt:formatDate value="${accountService.dateStart}" pattern="yyyy-MM-dd" /></td>
                            <td><fmt:formatDate value="${accountService.dateEnd}" pattern="yyyy-MM-dd" /></td>
                            <td><%--${accountService.serviceType.price}--%></td>
                        </tr>
                    </label>
                </c:forEach>

            </label>
        </c:forEach>

    </table>

</div>

</body>
</html>
