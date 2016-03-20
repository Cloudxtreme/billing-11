<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="label.accounts"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/saldo.js" var="saldo" />
    <script src="${accounts}"></script>

    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
        <div id="messagesDiv">
            <c:if test="${not empty successMessage}">
            <div class="alert alert-success fade in" role="alert">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${successMessage}</strong>
            </div>
            </c:if>&nbsp;
            <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger fade in" role="alert">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${errorMessage}</strong>
            </div>
            </c:if>
        </div>&nbsp;


    <div id="saldoTableDiv">
        <table id="saldoTable" class="table table-striped">
            <tr>
                <th><spring:message code="label.saldo.tbd"/></th>
            </tr>
        </table>
    </div>

</div>
</body>
</html>