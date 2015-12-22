<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Search by results</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">

    <table class="table table-striped" id='table'>
        <th></th>
        <TH>Действия</th>
        <TH>ID(Лиц.счёт)</th>
        <th>ФИО(Организация)</th>
        <th>Тип</th>
        <th>Баланс</th>
        <th>Статус</th>
        <c:forEach items="${accountList}" var="current">
            <label for="${current.id}">
                <tr id="${current.id}">
                    <td>
                        <a href="${pageContext.request.contextPath}/editFull/${current.id}"><span
                                class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        &nbsp;&nbsp;
                        <a href="${pageContext.request.contextPath}/delete/${current.id}"><span
                                class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                    </td>
                    <td>${current.accountName}</td>
                    <td>${current.fio}</td>
                    <td>${current.accountType}</td>
                    <td>${current.currentBalance}</td>
                    <td>${current.status}</td>
                </tr>
            </label>
        </c:forEach>
    </table>
</div>

</body>
</html>
