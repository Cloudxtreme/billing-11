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
    <title>Add/Edit Account to Service</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/service.js" var="service" />
    <script src="${service}"></script>

<%-- Includes for DatePicker --%>
    <spring:url value="/resources/js/moment-with-locales.min.js" var="moment-datepicker" />
    <script src="${moment-datepicker}"></script>
    <spring:url value="/resources/js/bootstrap-datepicker.min.js" var="datepicker" />
    <script src="${datepicker}"></script>
    <spring:url value="/resources/css/bootstrap-datepicker.min.css" var="datepickerCss" />
    <link href="${datepickerCss}" rel="stylesheet"/>


</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="serviceForm" action="${pageContext.request.contextPath}/service/account/form">
        <fieldset>
            <legend>Add/Edit Service for Account "<strong>${account.accountName}</strong>"</legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}">Please fill in all fields below.</label>
            </div>
            <div class="form-group">
                <label class="col-lg-3 control-label">Services</label>
                <div class="col-lg-9">
                    <c:set var="disable" value="false"/>
                    <c:if test="${not empty serviceForm.serviceType.id}">
                        <c:set var="disable" value="true"/>
                        <form:hidden path="serviceType.id" id="getServiceType" data-parameter="${serviceForm.serviceType.serviceType}"/>
                    </c:if>

                    <form:select path="serviceType.id" multiple="false" class="form-control" disabled="${disable}">
                    <form:option value="0" label="Choose..." onclick="hideServiceForm()"/>
                    <c:forEach items="${serviceTypeList}" var="services">
                        <form:option value="${services.id}" label="${services.name}  (${services.price} грн.)" onclick="showServiceForm('${services.serviceType}')"/>
                    </c:forEach>
                    </form:select>
                    <form:errors path="serviceType.id" cssClass="alert-danger" />
                </div>
            </div>
            <div id="sharedServiceForm">
                <div class="form-group">
                    <label for="dateStart" class="col-lg-3 control-label">Date Start</label>
                    <div class="col-lg-9">
                        <fmt:formatDate value="${dateStart.date}" var="dateString" pattern="yyyy-MM-dd" />
                        <div class='input-group date' id='datepicker1'>
                            <form:input path="dateStart" class="form-control" value="${dateString}" placeholder="yyyy-MM-dd" readonly="true"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                        <form:errors path="dateStart" cssClass="alert-danger" />
                    </div>
                </div>
                <script type="text/javascript">
                    $(function () {
                        $('#datepicker1').datepicker({
                            format: 'yyyy-mm-dd'
                        });
                        $('#datepicker2').datepicker({
                            format: 'yyyy-mm-dd'
                        });
                    });
                </script>
                <div class="form-group">
                    <label for="dateEnd" class="col-lg-3 control-label">Date End</label>
                    <div class="col-lg-9">
                        <fmt:formatDate value="${dateEnd.date}" var="dateString" pattern="yyyy-MM-dd" />
                        <div class='input-group date' id='datepicker2'>
                            <form:input path="dateEnd" class="form-control" value="${dateEnd}" placeholder="yyyy-MM-dd"  readonly="true"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                        <form:errors path="dateEnd" cssClass="alert-danger" />
                    </div>
                </div>
            </div>

            <div id="internetService">
                <div class="form-group">
                    <label for="username" class="col-lg-3 control-label">User Name</label>
                    <div class="col-lg-9">
                        <form:errors path="serviceInternet.username" cssClass="alert-danger" />
                        <form:input path="serviceInternet.username" class="form-control" id="username" placeholder="User Name"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-lg-3 control-label">Password</label>
                    <div class="col-lg-9">
                        <form:password path="serviceInternet.password" class="form-control" id="password" placeholder="Password"/>
                        <form:errors path="serviceInternet.password" cssClass="alert-danger" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="macaddress" class="col-lg-3 control-label">MacAddress</label>
                    <div class="col-lg-9">
                        <form:input path="serviceInternet.macaddress" class="form-control" id="macaddress" placeholder="MacAddress"/>
                        <form:errors path="serviceInternet.macaddress" cssClass="alert-danger" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="ip" class="col-lg-3 control-label">IP</label>
                    <div class="col-lg-9">
                        <form:input path="serviceInternet.ip" class="form-control" id="ip" placeholder="IP"/>
                        <form:errors path="serviceInternet.ip" cssClass="alert-danger" />
                    </div>
                </div>
            </div>
            <div class="form-group" id="phoneService">
                <label for="phoneNumber" class="col-lg-3 control-label">Phone Number</label>
                <div class="col-lg-9">
                    <form:input path="servicePhone.phoneNumber" class="form-control" id="phoneNumber" placeholder="Phone Number"/>
                    <form:errors path="servicePhone.phoneNumber" cssClass="alert-danger" />
                </div>
            </div>

            <div class="form-group" id="submitBtn">
                <div class="col-lg-9 col-lg-offset-3">
                    <form:hidden path="id" />
                    <form:hidden path="accountId"/>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </fieldset>
    </form:form>

</div>

</body>
</html>