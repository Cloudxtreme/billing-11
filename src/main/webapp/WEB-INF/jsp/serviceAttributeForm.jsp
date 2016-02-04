<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.addEditAttr"/> </title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <label class="">&nbsp;</label>
    <form:form class="form-horizontal" method="POST" commandName="serviceAttributeForm" action="${pageContext.request.contextPath}/serviceAttribute/modify">
        <fieldset>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code="label.addEditAttr"/></h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-lg-8 ${errorClass}"><spring:message code="label.fillField"/></label>
                    </div>
                    <div class="form-group">
                        <label for="attribute" class="col-lg-3 control-label"><spring:message code="label.attr"/></label>
                        <div class="col-lg-9">
                            <form:input path="attribute" class="form-control" id="attribute" placeholder="PPPD-Upstream-Speed-Limit" />
                            <form:errors path="attribute" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="operation" class="col-lg-3 control-label"><spring:message code="label.operation"/></label>
                        <div class="col-lg-9">
                            <form:input path="operation" class="form-control" id="operation" placeholder="=   <=   >="/>
                            <form:errors path="operation" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="value" class="col-lg-3 control-label"><spring:message code="label.value"/></label>
                        <div class="col-lg-9">
                            <form:input path="value" class="form-control" id="value" placeholder="from client world 1024"/>
                            <form:errors path="value" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <form:hidden path="id" />
                            <form:hidden path="serviceTypeId" />
                            <button type="submit" class="btn btn-primary"><spring:message code="label.submit"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>

</body>
</html>