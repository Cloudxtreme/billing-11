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
    <title><spring:message code="as.searchByResuls"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util"/>
    <spring:url value="/resources/js/popup.js" var="popup"/>
    <script src="${popup}"></script>
    <script src="${util}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
    <div id="totopscroller"> </div>

    <div class="row" style="font-size: 15px; margin-left: 0 !important;">
        <a href="${pageContext.request.contextPath}/accounts/accountHome.html"  style="color: #444444;">
            <span class="glyphicon glyphicon-arrow-left"  style="color: #3a87ad !important;" ></span>
            <spring:message code="as.goToAccList"/>
        </a>
    </div>
    &nbsp;&nbsp;&nbsp;
    <c:if test="${not empty message}">
        <div class="alert alert-info fade in text-center" style="width: 40% !important; margin-left: 30% !important;" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <table class="table table-striped" id='table'>
        <caption class="text-center" style="padding-top: 0px !important;"><h3><spring:message code="as.searchResultAcc"/></h3></caption>

        <TH><spring:message code="as.actions"/></th>
        <TH><spring:message code="as.id"/></th>
        <th><spring:message code="as.fio"/></th>
        <th><spring:message code="as.searchCompares"/></th>
        <th><spring:message code="as.type"/></th>
        <th><spring:message code="as.balance"/></th>
        <th><spring:message code="label.status"/></th>
        <c:forEach items="${accountList}" var="current">
            <label for="${current.id}">
                <tr id="${current.id}">
                    <td>
                        <a href="${pageContext.request.contextPath}/accounts/editFull/${current.id}"><span
                                class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        &nbsp;&nbsp;
                        <a data-href="${pageContext.request.contextPath}/accounts/delete/${current.id}"
                           id="deleting" data-toggle="modal" data-target="#confirm-delete">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </a>
                        &nbsp;&nbsp;
                        <a id="info" href="${pageContext.request.contextPath}/objectinfo/${current.id}?type=Account"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></a>
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
                    <c:choose>
                        <c:when test="${current.status eq 'DELETED'}">
                            <td><strong style="font-style: italic !important; color: rgba(253, 28, 0, 0.81)">${current.status}</strong></td>
                        </c:when>
                        <c:otherwise>
                            <td>${current.status}</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </label>
        </c:forEach>
    </table>

    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><strong><spring:message code="label.accountDeleting"/></strong></h4>
                </div>
                <div class="modal-body">
                    <spring:message code="label.accountDelete"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.cancel"/></button>
                    <a type="button" id="deleteBtn" class="btn btn-primary btn-ok"><spring:message code="label.submitDelete"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
