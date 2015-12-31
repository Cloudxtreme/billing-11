<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.services"/> </title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">

    <legend><spring:message code="label.services"/> </legend>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${successMessage}
        </div>
    </c:if>

    <a href="${pageContext.request.contextPath}/serviceType/form" class="btn btn-sm btn-primary" data-toggle="modal"><spring:message code="label.serviceCreate"/></a>


    <table id="userRoleTable" class="table table-striped table-hover">
        <tr>
            <th>&nbsp;</th>
            <th><spring:message code="label.type"/></th>
            <th><spring:message code="label.name"/></th>
            <th><spring:message code="label.description"/></th>
            <th><spring:message code="label.type"/></th>
            <th><spring:message code="label.price"/></th>
        </tr>

        </tr>
        <c:forEach items="${serviceTypeList}" var="service">
            <label for="${service.id}">
                <tr id="${service.id}">
                    <td>
                        <a href="${pageContext.request.contextPath}/serviceType/${service.id}/update"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        &nbsp;&nbsp;
                        <a href="${pageContext.request.contextPath}/serviceType/${service.id}/delete" onclick="return confirm('<spring:message javaScriptEscape="true" code="label.deleteService"/>')">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </a>
                    </td>
                    <td>${service.serviceType}</td>
                    <td>${service.name}</td>
                    <td>${service.description}</td>
                    <td>${service.bussType}</td>
                    <td>${service.price}</td>
                </tr>
            </label>
        </c:forEach>

    </table>

</div>

</body>
</html>
