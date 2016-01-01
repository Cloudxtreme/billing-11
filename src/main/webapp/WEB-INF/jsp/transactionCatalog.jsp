<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.transactionCatalog"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />


    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/transaction.js" var="trans" />
    <script src="${trans}"></script>

    <spring:url value="/resources/js/date_parsing.js" var="dateParsing"/>
    <script src="${dateParsing}"></script>
    <spring:url value="/resources/js/daterangepicker/moment.min.js" var="momentMin"/>
    <script type="text/javascript" src="${momentMin}"></script>
    <spring:url value="/resources/js/daterangepicker/daterangepicker.js" var="daterangepicker"/>
    <script type="text/javascript" src="${daterangepicker}"></script>
    <spring:url value="/resources/css/daterangepickerCSS/daterangepicker.css" var="daterangepickerCSS"/>
    <link href="${daterangepickerCSS}" rel="stylesheet"/>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well-lg">
    <legend><spring:message code="label.transactionCatalog"/>
        <a href="${pageContext.request.contextPath}/transaction/${selectedAccount}/form?returnPage=transaction/${selectedAccount}/catalog/" class="btn btn-sm btn-primary float-right" data-toggle="modal">
            <spring:message code="label.transactionCreate"/>
        </a>
    </legend>

    <fieldset>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success fade in" role="alert">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${successMessageTrans}
            </div>
        </c:if>

        <div class="well">
            <form class="navbar-form" role="search">
                <div class="form-group pull-left margin-left-7">
                    <spring:message code="label.show"/>
                    <select class="selectpicker" data-style="btn-info" id="transactionListLimit">
                        <option value="10">10</option>
                        <option value="20" selected="selected">20</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                </div>
                <div class="pull-right">
                    <div class="form-group">
                        <a class="btn btn-primary link-btn" id="eraseSearch" title="<spring:message code="label.erasesearch"/>"><span class="glyphicon glyphicon-erase" aria-hidden="true"></span></a>
                    </div>
                    <div class="form-group">
                        <spring:message code="label.account" var="acc"/>
                        <input type="text" class="form-control" placeholder="${acc}" id="searchAccountName">
                    </div>
                    <div class="form-group ">
                        <spring:message code="label.startTime" var="csd"/>
                        <input type="text" name="daterange" class="form-control col-xs-6" placeholder="${csd}" id="searchDate"/>
                    </div>
                    <a class="btn btn-default" id="searchBtn"><spring:message code="label.search"/></a>
                    <br>
                </div>
            </form>

            <div class="alert alert-info" role="alert" style="display: none"><%--${notFoundMessage}--%></div>

            <table id="transactionTable" class="table table-striped table-hover">
            <tr>
                <th><spring:message code="label.account"/></th>
                <th><spring:message code="label.startTime"/></th>
                <th><spring:message code="label.direction"/></th>
                <th><spring:message code="label.source"/></th>
                <th><spring:message code="label.price"/></th>
                <th><spring:message code="label.comment"/></th>
            </tr>
            <c:forEach items="${transactionList}" var="transaction">
                <label for="${transaction.id}">
                    <tr id="${transaction.id}">
                        <td>${transaction.account.accountName}</td>
                        <td><fmt:formatDate value="${transaction.date}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td>${transaction.direction}</td>
                        <td>${transaction.source}</td>
                        <td>${transaction.price}</td>
                        <td>${transaction.comment}</td>
                    </tr>
                </label>
            </c:forEach>
            <div id="accountId" style="display: none;">${selectedAccount}</div>
            </table>
        </div>
    </fieldset>
</div>




</body>
</html>
