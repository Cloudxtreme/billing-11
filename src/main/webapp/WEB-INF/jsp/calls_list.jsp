<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><spring:message code="label.callsList"/> </title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>
    <spring:url value="/resources/js/callslist.js" var="callslist"/>
    <script src="${callslist}"></script>
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


<div class="well">
    <div id="successMessageHandle" class="alert alert-success" style="display: none">
        <strong><spring:message code="hadleCallsCost.success"/></strong>
    </div>
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
    <form class="navbar-form" role="search">
        <div class="form-group">
            <a href="#" class="btn btn-primary link-btn" id="eraseSearch"><span
                    class="glyphicon glyphicon-erase" aria-hidden="true" title="<spring:message code="label.erasesearch"/>"></span></a>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="<spring:message code="label.numberA"/>" id="searchNumberA">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="<spring:message code="label.numberB"/>" id="searchNumberB">
        </div>
        <div class="form-group ">
            <input type="text" name="daterange" class="form-control col-xs-6" placeholder="<spring:message code="label.callsStartDate"/>"
                   id="searchDate"/>
        </div>
        <a class="btn btn-default" id="searchBtn"><spring:message code="label.search"/></a>
        &nbsp;&nbsp;
        <div class="form-group">
            <div  id="errorMessage" class="error alert-warning" style="display: none">
                <strong><spring:message code="label.diggits"/></strong>
            </div>
        </div>
        <div class="form-group float-right">
            <a type="button" id="handleCostTotal" class="btn btn-default" href=""><spring:message code="label.calculateCost"/></a>
        </div>
    </form>

    <div class="progress" style="display: none; padding-top: 10px;" id="progress">
        <div class="progress-bar" id="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
            <span class="sr-only">60% Complete</span>
        </div>
    </div>


    <div id="callsTableDiv">
        <table id="callsTable" class="table table-striped">
            <tr>
                <th><spring:message code="label.numberA"/></th>
                <th><spring:message code="label.numberB"/></th>
                <th><spring:message code="label.startTime"/></th>
                <th><spring:message code="label.duration"/></th>
                <th><spring:message code="label.aon"/></th>
                <th><spring:message code="label.dvoA"/></th>
                <th><spring:message code="label.dvoB"/></th>
                <th><spring:message code="label.costTotal"/></th>
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


    </div>
</div>
</body>
</html>