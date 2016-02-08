<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="label.accounts"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/accounts.js" var="accounts" />
    <script src="${accounts}"></script>

    <spring:url value="/resources/js/bootstrap-switch.min.js" var="jBootstrapSwitch" />
    <script src="${jBootstrapSwitch}"></script>
    <spring:url value="/resources/js/bootstrap-typeahead.js" var="typeahead" />
    <script src="${typeahead}"></script>

    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
        <div id="messagesDiv">
            <c:if test="${not empty successMessage}">
            <div class="alert alert-success fade in" role="alert">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${successMessage}</strong>
            </div>
            </c:if>&nbsp;
            <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger fade in" role="alert">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${errorMessage}</strong>
            </div>
            </c:if>
        </div>&nbsp;

        <div id="successMessageEditAccount" class="alert alert-success" style="display: none">
            <strong><spring:message code="account.success.update"/></strong>
        </div>


        <a type="button" href="#accAccountModal" class="btn btn-sm btn-primary" data-toggle="modal"><spring:message code="label.accountCreate"/></a>
        <div id="accAccountModal" class="modal fade">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><spring:message code="label.accountMessage"/> </h4>
                </div>

                <div class="modal-body">
                    <div class="">
                        <form:form class="form" id="crtAccountForm" method="POST" modelAttribute="accountForm" action="${pageContext.request.contextPath}/accounts/add">
                            <fieldset>
                                <form:input path="id" id="id" type="hidden"/>
                                <form:input path="currentBalance" id="currentBalance" type="hidden"/>
                                <form:input path="status" id="status" type="hidden"/>
                                <div class="form-group">
                                    <label for="accountName" class="col-lg-5 control-label"><spring:message code="label.account"/></label>
                                    <div class="col-lg-9">
                                        <spring:message code="label.account" var="acc"/>
                                        <form:input path="accountName" class="form-control" id="accountName" placeholder="${acc}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="accountType" class="col-lg-5 control-label"><spring:message code="label.accountType"/></label>
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
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.cancel"/></button>
                    <button type="button" id="crtAccount" class="btn btn-primary"><spring:message code="label.submit"/></button>
                </div>
            </div>
        </div>
    </div>
    <div id="accountsTableDiv">
        <table id="accountsTable" class="table table-striped">
            <tr>
                <th><spring:message code="label.action"/></th>
                <th><spring:message code="label.id"/></th>
                <th><spring:message code="label.fio"/> </th>
                <th><spring:message code="label.accountType"/> </th>
                <th><spring:message code="label.balance"/> </th>
                <th><spring:message code="label.status"/></th>
            </tr>
        </table>
        <div id="tableNavigation">
            <spring:message code="label.page"/>:<label id="pageNumber">${pageNum}</label> <spring:message code="label.from"/> <label id="totalPages">${pagesTotal}</label>
            <a type="button" href="#" class="btn btn-primary btn-sm link-btn" id="goPrev" onClick = "goToPrevPage();"><span class="glyphicon glyphicon-backward" aria-hidden="true"></span></a>
            &nbsp;&nbsp;
            <a type="button" href="#" class="btn btn-primary btn-sm link-btn" id="goNext" onClick = "goToNextPage();"><span class="glyphicon glyphicon-forward" aria-hidden="true"></span></a>
        </div>
    </div>

    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><strong><spring:message code="label.accountDeleting"/></strong></h4>
                </div>
                <div class="modal-body">
                    <spring:message code="label.accountDelete"/>
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