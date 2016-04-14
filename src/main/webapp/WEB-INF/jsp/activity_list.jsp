<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.activity"/></title>

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
    <legend><spring:message code="label.activity"/></legend>

    <fieldset>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success navbar-fixed-top text-center" role="alert">
                <strong>
                    ${successMessage}
                </strong>
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/addactivity.html" class="btn btn-sm btn-primary" data-toggle="modal"><spring:message code="label.activityCreate"/></a>
        <a href="${pageContext.request.contextPath}/userpanel.html" class="margin-nav float-right">
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span> <spring:message code="label.userPanel"/>
        </a>

        <table id="activityTable" class="table table-striped">
            <tr>
                <th>&nbsp;</th>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
            </tr>
            <c:forEach items="${activityList}" var="activity">
                <label for="${activity.id}">
                    <tr id="${activity.id}">
                        <td>
                            <a href="${pageContext.request.contextPath}/activity/${activity.id}/update"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                            &nbsp;&nbsp;
                            <a data-href="${pageContext.request.contextPath}/activity/${activity.id}/delete" id="deleting" data-toggle="modal" data-target="#confirm-delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                        </td>
                        <td>${activity.name}</td>
                        <td>${activity.description}</td>
                    </tr>
                </label>
            </c:forEach>
        </table>

    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><strong><spring:message code="label.activityDeleting"/></strong></h4>
                </div>
                <div class="modal-body">
                    <spring:message code="label.deleteActivity"/>
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
