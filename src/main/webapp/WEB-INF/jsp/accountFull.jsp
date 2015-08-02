<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Account Detail</title>

<jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
<spring:url value="/resources/js/accounts.js" var="accounts" />
<script src="${accounts}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>



<div class="well">
    <a href="#accauntSaveBut" class="btn btn-sm btn-primary">Update account</a>


    <div id="accountMainDetail">
        <form:form class="form" id="fullAccountForm" method="POST" commandName="accountForm" action="${pageContext.request.contextPath}/accounts/save.html">
            <fieldset>
                <form:input path="id" id="id" type="hidden"/>
                <form:input path="currentBalance" id="currentBalance" type="hidden"/>
                <form:input path="status" id="status" type="hidden"/>
                <div class="form-group">
                    <label for="accountName" class="col-lg-5 control-label">Account #</label>
                    <div class="col-lg-9">
                        <form:input path="accountName" class="form-control" id="accountName" placeholder="Account"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="accountType" class="col-lg-5 control-label">Account Type</label>
                    <div class="col-lg-9">
                        <form:select path="accountType" class="form-control" id="accountType">
                            <form:options items="${accountTypeList}" />
                        </form:select>
                    </div>
                </div>
            </fieldset>
        </form:form>

    </div>

</div>






</body>
</html>