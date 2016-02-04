<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <title><spring:message code="label.uploadedKDF"/> </title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>

    <spring:url value="/resources/js/uploaded_files.js" var="uploaded_files"/>
    <script src="${uploaded_files}"></script>

</head>
<body>
    <jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>
<body>

<div class="well">
    <div id="totopscroller"> </div>
    <div class="row" style="margin-bottom: 15px; margin-left: 0; font-size: 15px;">
        <a href="${pageContext.request.contextPath}/uploadfile.html" style="color: #444444;">
            <span class="glyphicon glyphicon-arrow-left" style="color: #3a87ad !important;"></span>
            <spring:message code="label.goToKDF"/>
        </a>
    </div>

    <div  id="successMessage" class="alert alert-success" style="display: none; text-align: center !important;">
        <strong><spring:message code="label.success"/></strong>
    </div>
    <div  id="errorMessage" class="alert alert-danger" style="display: none; text-align: center !important;">
        <strong><spring:message code="label.fail"/></strong>
    </div>
    <div  id="successMessageReload" class="alert alert-success" style="display: none; text-align: center !important;">
        <strong><spring:message code="label.handling"/></strong>
    </div>

    <div class="row">
        <a type="button" id="handleBtn" class="btn btn-primary" href="" style="margin-right: 10px;"><spring:message code="label.handle"/></a>
        <a type="button" id="handleCostTotal" class="btn btn-default" href=""><spring:message code="label.calculateCost"/></a>

        <button id="selectNew" class="btn btn-sm btn-info float-right"><spring:message code="label.selectNew"/></button>
        <button id="selectProcessed" class="btn btn-sm btn-danger float-right"><spring:message code="label.selectProcessed"/></button>
    </div>

    <div class="progress" style="display: none;" id="progress">
        <div class="progress-bar" id="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
            <span class="sr-only">60% Complete</span>
        </div>
    </div>

    <table class="table table-striped" id ='table'>
        <th></th>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.status"/></th>
        <th><spring:message code="label.size"/></th>
        <th><button id="deleteSelected" class="btn btn-sm btn-warning" data-toggle="modal" data-target="#confirm-delete" style="display: none;"><spring:message code="label.submitDelete"/></button></th>
        <c:forEach items="${uploadedList}" var="current">
            <tr id="${current.id}" class="">
                <td>
                    <div class=".col-xs-6 .col-sm-3">
                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                    </div>
                </td>
                <td>${current.path}</td>
                <td>${current.fileStatus}</td>
                <td>${current.fileSize}  <spring:message code="label.byte"/></td>
                <td><a id="deleting" data-toggle="modal" data-target="#confirm-delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
            </tr>
        </c:forEach>
    </table>

    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><strong><spring:message code="file.deleting"/></strong></h4>
                </div>
                <div class="modal-body">
                    <spring:message code="file.delete"/>
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