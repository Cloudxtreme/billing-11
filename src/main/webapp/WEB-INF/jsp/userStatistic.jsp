<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>User Statistic</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/jquery-ui.min.js" var="jqueryMin" />
    <script src="${jqueryMin}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/userStatistic.js" var="stat" />
    <script src="${stat}"></script>

    <spring:url value="/resources/css/jquery-ui.css" var="jqueryUi" />
    <link href="${jqueryUi}" rel="stylesheet"/>


</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-12">

    <legend><span class="glyphicon glyphicon-user" aria-hidden="true"></span> User Statistic
    </legend>

    <form class="navbar-form" role="search">
        <div class="pull-right">
            <div class="form-group ">
                <label for="startDate">Date Start:</label>
                <input name="startDate" id="startDate" class="date-picker" value="${startDate}"/>
            </div>
            <div class="form-group ">
                <label for="endDate">Date End:</label>
                <input name="endDate" id="endDate" class="date-picker" value="${endDate}"/>
            </div>
            <a class="btn btn-default" id="searchBtn">Search</a>
            <br>
        </div>
    </form>

    <div role="toolbar" aria-label="calendar" id="calendarGroup"></div>

    <div id="statisticTable"></div>

<%--
    <div class="btn-toolbar" role="toolbar" aria-label="calendar" id="calendarGroup">
        <c:forEach items="${displayCalendar}" var="calendar">
            <div class="btn-group" role="group" aria-label="${calendar.monthName}">
            <button class="btn-toolbar" style="width:45px;">${calendar.monthName}</button>
                <c:forEach items="${calendar.dayList}" var="day">
                    <button class="btn-toolbar" ${day.disabled} value="${calendar.year}-${calendar.monthNumber}-${day.dayNumber}">${day.dayNumber}</button>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
--%>
<%--
    <br><br>
    <div class="btn-toolbar" role="toolbar" aria-label="calendar">
        <c:forEach items="${displayCalendar}" var="calendar">
            <div class="btn-group" role="group" aria-label="${calendar.monthName}">
                <button class="btn-toolbar" style="width:45px;">${calendar.monthName}</button>
                <c:forEach items="${calendar.dayList}" var="day">
                    <button class="btn-toolbar btn-success" ${day.disabled} value="${calendar.year}-${calendar.monthNumber}-${day.dayNumber}">${day.dayNumber}</button>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
--%>

    <div id="login" style="display: none;">${login}</div>

</div>

</body>
</html>
