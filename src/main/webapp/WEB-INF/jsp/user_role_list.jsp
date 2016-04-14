<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.userRole"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/popup.js" var="popup"/>
    <script src="${popup}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well-lg">
    <legend><spring:message code="label.userRole"/></legend>

        <fieldset>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success fade in navbar-fixed-top text-center" role="alert">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>${successMessage}</strong>
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger fade in navbar-fixed-top text-center" role="alert">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        ${errorMessage}
                </div>
            </c:if>

            <a href="${pageContext.request.contextPath}/user_role_form.html" class="btn btn-sm btn-primary" data-toggle="modal"><spring:message code="label.roleCreate"/></a>
            <a href="${pageContext.request.contextPath}/userpanel.html" class="margin-nav float-right">
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span> <spring:message code="label.userPanel"/>
            </a>
        <table id="userRoleTable" class="table table-striped table-hover">
            <tr>
                <th>&nbsp;</th>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
                <th><a href="${pageContext.request.contextPath}/activitylist.html"><spring:message code="label.activity"/> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a></th>
            </tr>
            <c:forEach items="${userRoleList}" var="userRole">
                <label for="${userRole.id}">
                    <tr id="${userRole.id}">
                        <td>
                            <a href="${pageContext.request.contextPath}/user_role/${userRole.id}/update"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                            &nbsp;&nbsp;
                            <a data-href="${pageContext.request.contextPath}/user_role/${userRole.id}/delete" id="deleting" data-toggle="modal" data-target="#confirm-delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                        </td>
                        <td>${userRole.name}</td>
                        <td>${userRole.description}</td>
                        <td>
                            <c:forEach items="${userRole.activityName}" var="userRoleActivityName">
                                ${userRoleActivityName}<br>
                            </c:forEach>
                        </td>
                    </tr>
                </label>
            </c:forEach>
        </table>

        <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title"><strong><spring:message code="label.userRoleDeleting"/></strong></h4>
                    </div>
                    <div class="modal-body">
                        <spring:message code="label.deleteUserRole"/>
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
