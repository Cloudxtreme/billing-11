<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <div class="col-md-4">
        <a href="${pageContext.request.contextPath}/accounts/accountHome.html">
            <span class="glyphicon glyphicon-arrow-left" style="font-size: 15px; color: #3a87ad !important;">
                Перейти к списку аккаунтов
            </span>
        </a>
    </div>
    &nbsp;&nbsp;&nbsp;
    <c:if test="${not empty message}">
        <div class="alert alert-info fade in text-center" style="width: 40% !important; margin-left: 30% !important;" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <c:choose>
                <c:when test="${message == 'withSearch'}">
                    <strong><spring:message code="searchMessage"/> </strong>
                </c:when>
                <c:otherwise>
                    <strong><spring:message code="searchMessageGet"/> </strong>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>

    <table class="table table-striped" id='table'>
        <caption class="text-center"><h3>Результаты поиска Аккаунтов</h3></caption>

        <TH>Действия</th>
        <TH>ID (Лиц.счёт)</th>
        <th>ФИО (Организация)</th>
        <th>Совпадение в поиске</th>
        <th>Тип</th>
        <th>Баланс</th>
        <th>Статус</th>
        <c:forEach items="${accountList}" var="current">
            <label for="${current.id}">
                <tr id="${current.id}">
                    <td>
                        <a href="${pageContext.request.contextPath}/accounts/editFull/${current.id}"><span
                                class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        &nbsp;&nbsp;
                        <a href="${pageContext.request.contextPath}/accounts/delete/${current.id}"><span
                                class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/accounts/editFull/${current.id}">${current.accountName}</a>
                    </td>
                    <td>${current.fio}</td>
                    <td style="color: #4cae4c !important;">
                        <c:if test="${fn:contains(current.searchCompares, 'Login')}">
                             <span class="glyphicon glyphicon-globe"
                                  style="font-size: 1.2em !important;"></span>
                             <strong style="font-style: italic !important;">${current.searchCompares}
                        </c:if>
                        <c:if test="${fn:contains(current.searchCompares, 'Phone Number')}">
                             <span class="glyphicon glyphicon-phone-alt"
                                  style="font-size: 1.2em !important;"></span>
                             <strong style="font-style: italic !important;">${current.searchCompares}
                        </c:if>
                    </td>
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
