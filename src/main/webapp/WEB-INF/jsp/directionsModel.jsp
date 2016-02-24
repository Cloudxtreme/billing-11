<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><spring:message code="label.deviceList"/></title>
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>

    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>
    <spring:url value="/resources/js/direction.js" var="direction"/>
    <script src="${direction}"></script>
    <spring:url value="/resources/js/popup.js" var="popup"/>
    <script src="${popup}"></script>



    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
    <div id="totopscroller"></div>



    <div id="messagesDiv">
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">
                <strong>${successMessage}</strong>
            </div>
        </c:if>&nbsp;
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                <strong>${errorMessage}</strong>
            </div>
        </c:if>
    </div>
    &nbsp;

    <label>
        <spring:message code="label.show"/>
        <select class="selectpicker" data-style="btn-info" id="selectEntries">
            <option value="10">10</option>
            <option value="15">15</option>
            <option value="20">20</option>
            <option value="25" selected="selected">25</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        <spring:message code="label.entries"/>
    </label>
    <table class="table table-striped" id='table'>
        <tr>
            <th style="width: 10%;"></th>
            <th style="width: 20%;"><spring:message code="label.direction"/></th>
            <th style="width: 20%;"><spring:message code="label.prefix"/></th>
            <th style="width: 20%;"><spring:message code="label.tariffZoneName"/></th>
            <th style="width: 15%;"><spring:message code="label.tariff"/></th>
            <th style="width: 15%;"><spring:message code="label.tariffPref"/></th>
        </tr>
    </table>
    <div id="tableNavigation">
        <spring:message code="label.page"/>: <label id="pageNumber">${pageNum}</label> <spring:message code="label.from"/> <label id="totalPages">${pagesTotal}</label>
        <a href="#" class="btn btn-primary btn-sm link-btn" id="goPrev" onClick="goToPrevPage();"><span
                class="glyphicon glyphicon-backward" aria-hidden="true"></span></a>
        &nbsp;&nbsp;
        <a href="#" class="btn btn-primary btn-sm link-btn" id="goNext" onClick="goToNextPage();"><span
                class="glyphicon glyphicon-forward" aria-hidden="true"></span></a>
    </div>


    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><strong><spring:message code="label.directionDeleting"/></strong></h4>
                </div>
                <div class="modal-body">
                    <spring:message code="label.directionDelete"/>
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
