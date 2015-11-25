<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Calls List</title>
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
    <label>
        Show
        <select class="selectpicker" data-style="btn-info" id="selectEntries">
            <option value="10">10</option>
            <option value="15">15</option>
            <option value="20">20</option>
            <option value="25" selected="selected">25</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        entries
    </label>
    <form class="navbar-form" role="search">
        <div class="form-group">
            <a href="#" class="btn btn-primary link-btn" id="eraseSearch"><span
                    class="glyphicon glyphicon-erase" aria-hidden="true"></span></a>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Number A" id="searchNumberA">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Number B" id="searchNumberB">
        </div>
        <div class="form-group ">
            <input type="text" name="daterange" class="form-control col-xs-6" placeholder="Calls' start time date"
                   id="searchDate"/>
        </div>
        <a class="btn btn-default" id="searchBtn">Search</a>
        &nbsp;&nbsp;
        <div class="form-group">
            <div  id="errorMessage" class="error alert-warning" style="display: none">
                <strong>You can use only digits for searching</strong>
            </div>
        </div>
    </form>

    <div id="callsTableDiv">
        <table id="callsTable" class="table table-striped">
            <tr>
                <th>Number A</th>
                <th>Number B</th>
                <th>Start time</th>
                <th>Call's duration</th>
                <th>AON Category</th>
                <th>DVO Code A</th>
                <th>DVO Code B</th>
                <th>Cost call total</th>
            </tr>
        </table>
        <div id="tableNavigation">
            Page: <label id="pageNumber">${pageNum}</label> from <label id="totalPages">${pagesTotal}</label>
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