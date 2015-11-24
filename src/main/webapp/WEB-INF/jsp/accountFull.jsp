<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Account Detail</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/bootstrap-typeahead.js" var="typeahead" />
    <script src="${typeahead}"></script>
    <spring:url value="/resources/js/accounts.js" var="accounts" />
    <script src="${accounts}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>



<div class="well">
        <a href="#" id="accauntSaveBut" class="btn btn-sm btn-primary">Update account</a>
        <a href="${pageContext.request.contextPath}/transaction/${accountForm.id}/catalog/" id="viewTransactions" class="btn btn-sm btn-success">View Transactions</a>

    <div id="accountMainDetail">
        <form:form class="form-horizontal" id="fullAccountForm" method="POST" commandName="accountForm" action="${pageContext.request.contextPath}/accounts/save.html">
            <fieldset>
                <form:input path="id" id="id" type="hidden"/>
                <form:input path="currentBalance" id="currentBalance" type="hidden"/>
                <form:input path="status" id="status" type="hidden"/>


                <div id="generalBlock" class="col-lg-6">
                    <div class="col-lg-12">
                        <label class="">&nbsp;</label>
                        <div class="panel panel-default  ">
                            <div class="panel-heading">
                                <h3 class="panel-title">Общие данные</h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="accountName" class="col-lg-2 control-label">Account #</label>
                                    <div class="col-lg-9">
                                        <form:input path="accountName" class="form-control" id="accountName" placeholder="Account"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="accountType" class="col-lg-2 control-label">Account Type</label>
                                    <div class="col-lg-9">
                                        <form:select path="accountType" class="form-control" id="accountType">
                                            <form:options items="${accountTypeList}" />
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="phyAddrBlock" class="col-lg-6">
                        <form:input path="phyAddress.id" id="id" type="hidden"/>
                        <%--<form:input path="phyAddress.status" id="status" type="hidden"/>--%>

                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Физический Адрес</h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="phyAddressStreet" class="col-lg-2 control-label">Street</label>
                                    <div class="col-lg-9">
                                        <form:input path="phyAddress.street" class="form-control" id="phyAddressStreet"
                                                    data-provide="typeahead" placeholder="5th Avenue"/>
                                        <form:input path="phyAddress.streetId" id="phyAddressStreetId" type="hidden"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="phyAddressBuilding" class="col-lg-2 control-label">Building</label>
                                    <div class="col-lg-9">
                                        <form:input path="phyAddress.building" class="form-control" id="phyAddressBuilding" placeholder="number"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="phyAddressFlat" class="col-lg-2 control-label">Flat</label>
                                    <div class="col-lg-9">
                                        <form:input path="phyAddress.flat" class="form-control" id="phyAddressFlat" placeholder="number"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="legAddrBlock" class="col-lg-6">
                        <form:input path="legalAddress.id" id="id" type="hidden"/>
                       <%-- <form:input path="legalAddress.status" id="status" type="hidden"/>--%>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Юридический Адрес</h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="legalAddressStreet" class="col-lg-2 control-label">Street</label>
                                    <div class="col-lg-9">
                                        <form:input path="legalAddress.street" class="form-control" id="legalAddressStreet"
                                                    data-provide="typeahead" placeholder="5th Avenue"/>
                                        <form:input path="legalAddress.streetId" id="legalAddressStreetId" type="hidden"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="legalAddressBuilding" class="col-lg-2 control-label">Building</label>
                                    <div class="col-lg-9">
                                        <form:input path="legalAddress.building" class="form-control" id="legalAddressBuilding" placeholder="number"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="legAddressFlat" class="col-lg-2 control-label">Flat</label>
                                    <div class="col-lg-9">
                                        <form:input path="legalAddress.flat" class="form-control" id="legAddressFlat" placeholder="number"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="serviceBlock" class="col-lg-6">
                    <label class="">&nbsp;</label>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Сервисы
                                <a href="${pageContext.request.contextPath}/service/account/${accountForm.id}/0/modify" style="line-height: 0.8 !important; color: #ffffff !important" class="btn btn-sm btn-primary float-right" data-toggle="modal">New</a>
                            </h3>
                        </div>
                        <div class="panel-body">
                            <table id="accountServiceTable" class="table table-striped table-hover">
                                <tr>
                                    <th>&nbsp;</th>
                                    <th>Service</th>
                                    <th>Start Date</th>
                                    <th>Period</th>
                                    <th>Price</th>
                                </tr>

                                <c:forEach items="${accountForm.serviceForms}" var="accountService">
                                    <label for="${accountService.id}">
                                        <tr id="${accountService.id}">
                                            <td>
                                                <a href="${pageContext.request.contextPath}/service/account/${accountForm.id}/${accountService.id}/modify"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                                                &nbsp;&nbsp;
                                                <a href="${pageContext.request.contextPath}/service/account/${accountForm.id}/${accountService.id}/delete" onclick="return confirm('Do you really want to delete account service?')"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                                            </td>
                                            <td>${accountService.serviceType.name}</td>
                                            <td><fmt:formatDate value="${accountService.dateStart}" pattern="yyyy-MM-dd" /></td>
                                            <td>${accountService.period}</td>
                                            <td>${accountService.serviceType.price}</td>
                                        </tr>
                                    </label>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-info" role="alert">${successMessage}</div>
                    </c:if>&nbsp;
                </div>

            </fieldset>
        </form:form>


    </div>

</div>






</body>
</html>