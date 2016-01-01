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
    <title><spring:message code="label.serviceAdd"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/serviceAttribute.js" var="serviceAttribute" />
    <script src="${serviceAttribute}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">

    <div class="alert alert-danger fade in" role="alert" id="errorMessage" style="display: none;">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong><spring:message code="label.diggits"/></strong>
    </div>

    <label class="">&nbsp;</label>
    <form:form class="form-horizontal" method="POST" commandName="serviceForm" id="serviceForm" action="${pageContext.request.contextPath}/serviceType/form">
        <fieldset>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code="label.serviceAdd"/></h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-lg-8 ${errorClass}"><spring:message code="label.fillField"/></label>
                    </div>
                    <div class="form-group">
                            <label class="col-lg-3 control-label"><spring:message code="label.type"/></label>
                            <div class="col-lg-9">
                                <fieldset id="serviceType">
                                    <label class="radio-inline"><strong>
                                        <form:radiobutton path="serviceType" value="INTERNET"/> <spring:message code="label.internet"/>
                                    </strong></label>
                                    <label class="radio-inline"><strong>
                                        <form:radiobutton path="serviceType" value="PHONE"/> <spring:message code="label.phone"/>
                                    </strong></label>
                                    <label class="radio-inline"><strong>
                                        <form:radiobutton path="serviceType" value="MARKER"/> <spring:message code="label.marker"/>
                                    </strong></label>
                                </fieldset>
                                <form:errors path="serviceType" cssClass="alert-danger" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="serviceName" class="col-lg-3 control-label"><spring:message code="label.name"/></label>
                            <div class="col-lg-9">
                                <spring:message code="label.name" var="name"/>
                                <form:input path="name" class="form-control" id="serviceName" placeholder="${name}" />
                                <form:errors path="name" cssClass="alert-danger" />
                            </div>
                        </div>
                    <div class="form-group">
                        <label for="serviceDescription" class="col-lg-3 control-label"><spring:message code="label.description"/></label>
                        <div class="col-lg-9">
                            <spring:message code="label.description" var="decr"/>
                            <form:input path="description" class="form-control" id="serviceDescription" placeholder="${decr}"/>
                            <form:errors path="description" cssClass="alert-danger" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="serviceBussType" class="col-lg-3 control-label"><spring:message code="label.servBusinessType"/></label>
                        <div class="col-lg-9">
                            <form:select path="bussType" class="form-control" id="serviceBussType">
                                <form:options items="${bussTypes}" />
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="price" class="col-lg-3 control-label"><spring:message code="label.price"/></label>
                        <div class="col-lg-9">
                            <spring:message code="label.price" var="price"/>
                            <form:input type="number" min="0" step="any" path="price" class="form-control" id="price" placeholder="${price}"/>
                            <form:errors path="price" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <form:hidden path="id" />
                            <button type="submit" class="btn btn-primary"><spring:message code="label.submit"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>
    </form:form>

</div>

<div class="col-lg-6" id="serviceInternetAttribute">
    <label class="">&nbsp;</label>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code="label.servAttr"/>
                        <a href="${pageContext.request.contextPath}/serviceAttribute/${serviceForm.id}/0/modify" style="line-height: 0.8 !important; color: #ffffff !important" class="btn btn-sm btn-primary float-right" data-toggle="modal">
                            <spring:message code="label.new"/>
                        </a>
                    </h3>
                </div>
                <div class="panel-body">
                <table id="serviceInternetAttributeTable" class="table table-striped table-hover">
                    <tr>
                        <th>&nbsp;</th>
                        <th><spring:message code="label.attr"/></th>
                        <th><spring:message code="label.operation"/></th>
                        <th><spring:message code="label.value"/></th>
                    </tr>

                    <c:forEach items="${serviceInternetAttributeList}" var="serviceInternetAttribute">
                        <label for="${serviceInternetAttribute.id}">
                            <tr id="${serviceInternetAttribute.id}">
                                <td>
                                    <a href="${pageContext.request.contextPath}/serviceAttribute/${serviceForm.id}/${serviceInternetAttribute.id}/modify"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                                    &nbsp;&nbsp;
                                    <a href="${pageContext.request.contextPath}/serviceAttribute/${serviceForm.id}/${serviceInternetAttribute.id}/delete" onclick="return confirm('<spring:message javaScriptEscape="true" code="label.deleteAttr"/>')">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    </a>
                                </td>
                                <td>${serviceInternetAttribute.attribute}</td>
                                <td>${serviceInternetAttribute.operation}</td>
                                <td>${serviceInternetAttribute.value}</td>
                            </tr>
                        </label>
                    </c:forEach>
                </table>
                </div>
            </div>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-info fade in" role="alert">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        ${successMessage}
                </div>
            </c:if>&nbsp;
        </div>

</body>
</html>