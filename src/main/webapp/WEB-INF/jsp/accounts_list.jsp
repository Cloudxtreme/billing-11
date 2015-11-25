<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Accounts</title>

<jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
<spring:url value="/resources/js/util.js" var="util" />
<script src="${util}"></script>
<spring:url value="/resources/js/accounts.js" var="accounts" />
<script src="${accounts}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>



<div class="well">
    <a href="#accAccountModal" class="btn btn-sm btn-primary" data-toggle="modal">Create New Account</a>


    <div id="accAccountModal" class="modal fade">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Please fill required info to create new Account</h4>
            </div>

            <div class="modal-body">
                <div class="">
                    <form:form class="form" id="crtAccountForm" method="POST" modelAttribute="accountForm"
                                action="${pageContext.request.contextPath}/accounts/add.html">
                        <fieldset>
                                <%--<c:if  test="${empty errorMessage}">
                                    <div class="form-group">
                                        <label class="col-lg-8">Please fill in all fields below.</label>
                                    </div>
                                </c:if>
                                <c:if test="${not empty errorMessage}">
                                    <div class="form-group">
                                        <label class="col-lg-8 text-warning">${errorMessage}</label>
                                    </div>
                                </c:if>--%>
                            <form:input path="id" id="id" type="hidden"/>
                            <form:input path="currentBalance" id="currentBalance" type="hidden"/>
                            <form:input path="status" id="status" type="hidden"/>
                            <%--<form:input path="phyAddress" id="phyAddress" type="hidden"/>
                            <form:input path="legalAddress" id="legalAddress" type="hidden"/>--%>
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
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" id="crtAccount" class="btn btn-primary">Create</button>
            </div>
        </div>
    </div>
</div>


<div id="accountsTableDiv">
    <table id="accountsTable" class="table table-striped">
        <tr>
            <th>Action</th>
            <th>Account Name</th>
            <th>Account Type</th>
            <th>Balance</th>
            <th>Status</th>
        </tr>
    </table>
    <div id="tableNavigation">
        Page:<label id="pageNumber">${pageNum}</label> from <label id="totalPages">${pagesTotal}</label>
        <a href="#" class="btn btn-primary btn-sm link-btn" id="goPrev" onClick = "goToPrevPage();"><span class="glyphicon glyphicon-backward" aria-hidden="true"></span></a>
        &nbsp;&nbsp;
        <a href="#" class="btn btn-primary btn-sm link-btn" id="goNext" onClick = "goToNextPage();"><span class="glyphicon glyphicon-forward" aria-hidden="true"></span></a>
    <div/>



</div>






</body>
</html>