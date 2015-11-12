<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Transaction Catalog</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <legend>Transaction Catalog</legend>

    <fieldset>
        <c:if test="${not empty successMessage}">
        <div class="alert alert-info" role="alert">${successMessage}</div>
        </c:if>
        <a href="${pageContext.request.contextPath}/transaction/${selectedAccount}/form" class="btn btn-sm btn-primary" data-toggle="modal">Create Transaction</a>
        <table id="transactionTable" class="table table-striped table-hover">
            <tr>
                <th>Account</th>
                <th>Date</th>
                <th>Direction</th>
                <th>Source</th>
                <th>Price</th>
                <th>Comment</th>
            </tr>
            <c:forEach items="${transactionList}" var="transaction">
                <label for="${transaction.id}">
                    <tr id="${transaction.id}">
                        <td>${transaction.account.accountName}</td>
                        <td>${transaction.date}</td>
                        <td>${transaction.direction}</td>
                        <td>${transaction.source}</td>
                        <td>${transaction.price}</td>
                        <td>${transaction.comment}</td>
                    </tr>
                </label>
            </c:forEach>
        </table>
    </fieldset>
</div>




</body>
</html>
