<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1"?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Users Online</title>

<jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
<spring:url value="/resources/js/util.js" var="util" />
<script src="${util}"></script>
<spring:url value="/resources/js/onlineStat.js" var="online" />
<script src="${online}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
    <%--<a href="#accAccountModal" class="btn btn-sm btn-primary" data-toggle="modal">Create New Account</a>--%>
    <span class="btn btn-sm btn-warning float-left" id="onlineUserCount">Users online: </span>
    <div id="userOnlineTableDiv">
        <table id="userOnlineTable" class="table table-striped">
            <tr>
                <th>username</th>
                <th>user_fio</th>
                <th>nasipaddress</th>
                <th>nasportid</th>
                <th>acctstarttime</th>
                <th>acctsessiontime</th>
                <th>framedipaddress</th>
                <th>acctinputoctets</th>
                <th>acctoutputoctets</th>
            </tr>
        </table>
    </div>

</div>
</body>
</html>