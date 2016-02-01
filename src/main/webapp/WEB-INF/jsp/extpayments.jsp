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
    <title><spring:message code="label.externalPayments"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/extPayments.js" var="trans" />
    <script src="${trans}"></script>

    <spring:url value="/resources/js/date_parsing.js" var="dateParsing"/>
    <script src="${dateParsing}"></script>
    <spring:url value="/resources/js/daterangepicker/moment.min.js" var="momentMin"/>
    <script type="text/javascript" src="${momentMin}"></script>
    <spring:url value="/resources/js/daterangepicker/daterangepicker.js" var="daterangepicker"/>
    <script type="text/javascript" src="${daterangepicker}"></script>
    <spring:url value="/resources/css/daterangepickerCSS/daterangepicker.css" var="daterangepickerCSS"/>
    <link href="${daterangepickerCSS}" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well">
    <legend><spring:message code="label.externalPayments"/></legend>

    <fieldset>
        <div class="well">

            <table id="extPaymentTable" class="table table-striped table-hover">
            <tr>
                <th align="center"><spring:message code="label.serviceId"/></th>
                <th align="center"><spring:message code="label.payAccount"/></th>
                <th align="center"><spring:message code="label.payAmount"/></th>
                <th align="center"><spring:message code="label.receiptNum"/></th>
                <th align="center"><spring:message code="label.payId"/></th>
                <th align="center"><spring:message code="label.tradePoint"/></th>
                <th align="center"><spring:message code="label.date"/></th>
                <th align="center"><spring:message code="label.check"/></th>
            </tr>
            <c:forEach items="${payments}" var="payment">

                <tr id="${payment.serviceId}">
                    <td>${payment.serviceId}</td>
                    <td>${payment.payAccount}</td>
                    <td>${payment.payAmount}</td>
                    <td>${payment.receiptNum}</td>
                    <td>${payment.payId}</td>
                    <td>${payment.tradepoint}</td>
                    <td><fmt:formatDate value="${payment.timestamp}" pattern="yyyy-MM-dd HH:mm"/></td>

                    <c:if test="${payment.check eq 'true'}">
                        <td><span class="glyphicon glyphicon-ok"></span></td>
                    </c:if>
                    <c:if test="${payment.check eq 'false'}">
                        <td>
                            <a class="pushHandle btn btn-sm btn-primary" data-id="${payment.id}"><span class="glyphicon glyphicon-plus"></span></a>
                        </td>
                    </c:if>
                </tr>

            </c:forEach>
            </table>
        </div>
    </fieldset>
</div>




</body>
</html>
