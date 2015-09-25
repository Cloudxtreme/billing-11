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

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>
    <spring:url value="/resources/js/callslist.js" var="callslist"/>
    <script src="${callslist}"></script>
    <spring:url value="/resources/js/date_parsing.js" var="dateParsing"/>
    <script src="${dateParsing}"></script>

    <script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />


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
            <option value="25">25</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        entries
    </label>

    <form class="navbar-form" role="search">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Number A" id="searchNumberA">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Number B" id="searchNumberB">
        </div>
        <div class="form-group ">
            <input type="text" name="daterange" class="form-control col-xs-6" placeholder="Calls' start time date" id="searchDate"/>
        </div>
        <a class="btn btn-default" id="searchBtn">Search</a>
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

        <%--<ul class="pagination">
            <li><a href="#" class="btn btn-primary btn-sm link-btn" id="goPrev" onClick="goToPrevPage();"><span
                    class="glyphicon glyphicon-backward" aria-hidden="true"></span></a></li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#" class="btn btn-primary btn-sm link-btn" id="goNext" onClick="goToNextPage();"><span
                    class="glyphicon glyphicon-forward" aria-hidden="true"></span></a></li>
        </ul>--%>


    </div>
</div>
</body>
</html>