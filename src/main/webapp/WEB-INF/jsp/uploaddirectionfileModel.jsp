<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title><spring:message code="label.directionfileparse"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>

    <spring:url value="/resources/js/uploaddirectionfile.js" var="fileScript"/>
    <script src="${fileScript}"></script>
    <spring:url value="/resources/js/jquery-ui.min.js" var="jqueryMinUi"/>
    <script src="${jqueryMinUi}"></script>
    <spring:url value="/resources/js/jquery.mjs.nestedSortable.js" var="nestedFileTree"/>
    <script src="${nestedFileTree}"></script>
    <spring:url value="/resources/css/loader-style.css" var="loader" />
    <link href="${loader}" rel="stylesheet"/>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well well-lg">
    <div id="totopscroller"></div>

    <div id="errorMessage" class="alert alert-danger" style="display: none">
        <strong><spring:message code="dirFile.error"/></strong>
    </div>
    <div id="errorMessageEmpty" class="alert alert-danger" style="display: none">
        <strong><spring:message code="dirFile.empty"/></strong>
    </div>
    <div id="errorMessageIncType" class="alert alert-danger" style="display: none">
        <strong><spring:message code="dirFile.incType"/></strong>
    </div>
    <div id="successMessage" class="alert alert-success" style="display: none">
        <strong><spring:message code="dirFile.success"/></strong>
    </div>


    <legend>
        <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span><spring:message code="label.selectDirectionDocFile"/>
    </legend>

    <div class="progress" style="display: none;" id="progress">
        <div class="progress-bar" id="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
            <span class="sr-only">60% Complete</span>
        </div>
    </div>

    <form:form commandName="uploadFile" id="upload" method="post" enctype="multipart/form-data" class="form">
        <div class="form-group" id="idForm">
            <span class="file-input btn btn-info btn-file">
                <spring:message code="label.browse"/> <input type="file" id="exampleInputFile" name="file"/>
            </span>
            <ul id="list" class="list-group"></ul>
        </div>

        <button type="button" value="upload" id="uploadFile" class="btn btn-toolbar" href="#myModal" data-toggle="modal"><spring:message code="label.handleDirectionFile"/></button>

    </form:form>

    <div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div id="spinner" class="modal-dialog" style="display:none;">
            <div class="modal-content" style="width: 298px !important;">
                <div class="modal-body">
                    <img id="img-responsive" src="resources/images/loaderLine.gif" alt="Loading"/>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
