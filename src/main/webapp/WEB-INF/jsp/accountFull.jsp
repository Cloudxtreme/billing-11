<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.accountDetail"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/css/bootstrap-switch.min.css" var="bootstrapSwitch" />
    <link href="${bootstrapSwitch}" rel="stylesheet"/>

    <spring:url value="/resources/js/date_parsing.js" var="dateParsing"/>
    <script src="${dateParsing}"></script>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/bootstrap-typeahead.js" var="typeahead" />
    <script src="${typeahead}"></script>
    <spring:url value="/resources/js/accounts.js" var="accounts" />
    <script src="${accounts}"></script>
    <spring:url value="/resources/js/bootstrap-switch.min.js" var="jBootstrapSwitch" />
    <script src="${jBootstrapSwitch}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>



<div class="well">
    <legend>
        <a href="#" id="accauntSaveBut" class="btn btn-sm btn-primary"><spring:message code="label.accountUpdate"/></a>
        <a href="${pageContext.request.contextPath}/transaction/${accountForm.id}/catalog/" id="viewTransactions" class="btn btn-sm btn-success"><spring:message code="label.viewTransaction"/></a>
        <span class="btn btn-sm btn-warning float-right"><spring:message code="label.balance"/> ${accountForm.currentBalance}</span>
    </legend>

    <div id="accountMainDetail">
        <form:form class="form-horizontal" id="fullAccountForm" method="POST" commandName="accountForm" action="${pageContext.request.contextPath}/accounts/save.html">
            <fieldset>
                <form:input path="id" id="id" type="hidden"/>
                <form:input path="currentBalance" id="currentBalance" type="hidden"/>
                <form:input path="status" id="status" type="hidden"/>


                <div id="generalBlock" class="col-lg-6">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><spring:message code="label.totalInfo"/></h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="accountName" class="col-lg-2 control-label"><spring:message code="label.account"/></label>
                                    <div class="col-lg-9">
                                        <form:input path="accountName" class="form-control" id="accountName" placeholder="Account"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="accountFIO" class="col-lg-2 control-label"><spring:message code="label.fio"/></label>
                                    <div class="col-lg-9">
                                        <form:input path="fio" class="form-control" id="accountFIO" placeholder="Фио"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="accountType" class="col-lg-2 control-label"><spring:message code="label.accountType"/></label>
                                    <div class="col-lg-9">
                                        <form:select path="accountType" class="form-control" id="accountType">
                                            <form:options items="${accountTypeList}" />
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="addressBlock" class="col-lg-6">
                    <div id="phyAddrBlock" class="col-lg-6 margin-top-cancel">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><spring:message code="label.physAddress"/></h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="phyAddressStreet" class="col-lg-2 control-label"><spring:message code="label.street"/></label>
                                    <div class="col-lg-9">
                                        <spring:message code="label.street" var="street"/>
                                        <form:input path="phyAddress.street" class="form-control" id="phyAddressStreet"
                                                    data-provide="typeahead" placeholder="${street}" autocomplete="off"/>
                                        <form:input path="phyAddress.id" id="phyAddressStreetId" type="hidden"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="phyAddressBuilding" class="col-lg-2 control-label"><spring:message code="label.building"/></label>
                                    <div class="col-lg-9">
                                        <spring:message code="label.number" var="number"/>
                                        <form:input path="phyAddress.building" class="form-control" id="phyAddressBuilding" placeholder="${number}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="phyAddressFlat" class="col-lg-2 control-label"><spring:message code="label.flat"/></label>
                                    <div class="col-lg-9">
                                        <form:input path="phyAddress.flat" class="form-control" id="phyAddressFlat" placeholder="${number}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <form:input path="phyAddress.id" id="id" type="hidden"/>
                    </div>

                    <div id="legAddrBlock" class="col-lg-6 margin-top-cancel">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><spring:message code="label.legalAddress"/></h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="legalAddressStreet" class="col-lg-2 control-label"><spring:message code="label.street"/></label>
                                    <div class="col-lg-9">
                                        <form:input path="legalAddress.street" class="form-control" id="legalAddressStreet"
                                                    data-provide="typeahead" placeholder="${street}" autocomplete="off"/>
                                        <form:input path="legalAddress.id" id="legalAddressStreetId" type="hidden"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="legalAddressBuilding" class="col-lg-2 control-label"><spring:message code="label.building"/></label>
                                    <div class="col-lg-9">
                                        <form:input path="legalAddress.building" class="form-control" id="legalAddressBuilding" placeholder="${number}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="legAddressFlat" class="col-lg-2 control-label"><spring:message code="label.flat"/></label>
                                    <div class="col-lg-9">
                                        <form:input path="legalAddress.flat" class="form-control" id="legAddressFlat" placeholder="${number}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <form:input path="legalAddress.id" id="id" type="hidden"/>
                    </div>
                </div>
                <div id="serviceBlock" class="col-lg-12">
                    <label class="">&nbsp;</label>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title"><spring:message code="label.services"/>
                                <a href="${pageContext.request.contextPath}/service/account/${accountForm.id}/0/modify" style="line-height: 0.8 !important; color: #ffffff !important" class="btn btn-sm btn-primary float-right" data-toggle="modal">
                                    <spring:message code="label.new"/>
                                </a>
                            </h3>
                        </div>
                        <div class="panel-body">
                            <table id="accountServiceTable" class="table table-striped table-hover">
                                <tr>
                                    <th>&nbsp;</th>
                                    <th><spring:message code="label.service"/></th>
                                    <th><spring:message code="label.detail"/></th>
                                    <th><spring:message code="label.softbock"/></th>
                                    <th><spring:message code="label.startTime"/></th>
                                    <th><spring:message code="label.period"/></th>
                                    <th><spring:message code="label.price"/></th>
                                    <th><spring:message code="label.status"/></th>
                                </tr>

                                <c:forEach items="${accountForm.serviceForms}" var="accountService">
                                    <label for="${accountService.id}">
                                        <tr id="${accountService.id}">
                                            <td>
                                                <a href="${pageContext.request.contextPath}/service/account/${accountForm.id}/${accountService.id}/modify"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                                                &nbsp;&nbsp;
                                                <a data-href="${pageContext.request.contextPath}/service/account/${accountForm.id}/${accountService.id}/delete" id="deleting" data-toggle="modal" data-target="#confirm-delete">
                                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                                </a>
                                            </td>
                                            <td>${accountService.serviceType.name}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${accountService.serviceType.serviceType == 'INTERNET'}">
                                                        login: ${accountService.serviceInternet.username}<br>
                                                        ip: ${accountService.serviceInternet.ip.ipName}<br>
                                                        <a href="${pageContext.request.contextPath}/statistic?login=${accountService.serviceInternet.username}" id="visitsStatistic" class="btn btn-sm btn-info">Statistic</a>
                                                    </c:when>
                                                    <c:when test="${accountService.serviceType.serviceType == 'PHONE'}">
                                                        ${accountService.servicePhone.phoneNumber}
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:if test="${accountService.serviceType.serviceType == 'INTERNET'}">
                                                    <c:choose>
                                                        <c:when test="${accountService.serviceInternet.softblock == true}">
                                                            <input id="${accountService.id}" name="softblock" type="checkbox" checked data-size="mini">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input id="${accountService.id}" name="softblock" type="checkbox" data-size="mini">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </td>
                                            <td><fmt:formatDate value="${accountService.dateStart}" pattern="yyyy-MM-dd" /></td>
                                            <td>${accountService.period}</td>
                                            <td>${accountService.serviceType.price}</td>
                                            <td>${accountService.status}</td>
                                        </tr>
                                    </label>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success fade in" role="alert" style="text-align: center !important;">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <strong>${successMessage}</strong>
                        </div>
                    </c:if>&nbsp;
                </div>

                <div id="transactionBlock" class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="panel-title">
                                <spring:message code="label.transactions"/>
                                <select class="selectpicker" data-style="btn-info" id="transactionListLimit" style="font-size: 12px !important; ">
                                    <option value="10">10</option>
                                    <option value="20" selected="selected">20</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                                <a href="${pageContext.request.contextPath}/transaction/${accountForm.id}/form?returnPage=accounts/editFull/${accountForm.id}" style="line-height: 0.8 !important; color: #ffffff !important" class="btn btn-sm btn-primary float-right" data-toggle="modal">
                                    <spring:message code="label.new"/>
                                </a>
                            </div>
                        </div>
                        <div class="panel-body">
                            <table id="transactionTable" class="table table-striped table-hover">
                                <tr>
                                    <th><spring:message code="label.account"/></th>
                                    <th><spring:message code="label.startTime"/></th>
                                    <th><spring:message code="label.direction"/></th>
                                    <th><spring:message code="label.source"/></th>
                                    <th><spring:message code="label.price"/></th>
                                    <th><spring:message code="label.comment"/></th>
                                </tr>

                                <c:forEach items="${transactionList}" var="transaction">
                                    <label for="${transaction.id}">
                                        <tr id="${transaction.id}">
                                            <td>${transaction.account.accountName}</td>
                                            <td><fmt:formatDate value="${transaction.date}" pattern="yyyy-MM-dd HH:mm"/></td>
                                            <td>${transaction.direction}</td>
                                            <td>${transaction.source}</td>
                                            <td>${transaction.price}</td>
                                            <td>${transaction.comment}</td>
                                        </tr>
                                    </label>
                                </c:forEach>
                                <div id="accountId" style="display: none;">${accountForm.id}</div>
                            </table>
                        </div>
                    </div>
                    <c:if test="${not empty successMessageTrans}">
                        <div class="alert alert-info" role="alert">${successMessageTrans}</div>
                    </c:if>&nbsp;
                </div>

            </fieldset>
        </form:form>


    </div>

    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><strong><spring:message code="service.deleting"/></strong></h4>
                </div>
                <div class="modal-body">
                    <spring:message code="service.delete"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.cancel"/></button>
                    <a id="deleteBtn" class="btn btn-primary btn-ok"><spring:message code="label.submitDelete"/></a>
                </div>
            </div>
        </div>
    </div>

</div>






</body>
</html>