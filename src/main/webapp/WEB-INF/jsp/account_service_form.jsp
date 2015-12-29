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
    <title><spring:message code="label.addeditAccount"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/service.js" var="service" />
    <script src="${service}"></script>

<%-- Includes for DatePicker --%>
    <spring:url value="/resources/js/moment-with-locales.min.js" var="moment_datepicker" />
    <script src="${moment_datepicker}"></script>
    <spring:url value="/resources/js/bootstrap-datepicker.min.js" var="datepicker" />
    <script src="${datepicker}"></script>
    <spring:url value="/resources/css/bootstrap-datepicker.min.css" var="datepickerCss" />
    <link href="${datepickerCss}" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />

    <script type="text/javascript">
        $(function () {
            $('#datepicker1').datepicker({
                format: 'yyyy-mm-dd'
            });
        });
    </script>



</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-7">
    <form:form class="form-horizontal" method="POST" commandName="serviceForm" action="${pageContext.request.contextPath}/service/account/form">
        <fieldset>
            <legend><spring:message code="label.addeditAccount"/> "<strong>${account.accountName}</strong>"</legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}"><spring:message code="label.fillField"/></label>
            </div>
            <c:if test="${empty serviceForm.serviceType.id}">
                <div class="form-group">
                    <label class="col-lg-2 control-label"><spring:message code="label.type"/></label>
                    <div class="col-lg-9">
                        <fieldset id="serviceType">
                            <label class="radio-inline"><strong>
                                <form:radiobutton path="serviceType.serviceType" value="INTERNET"/> <spring:message code="label.internet"/>
                            </strong></label>
                            <label class="radio-inline"><strong>
                                <form:radiobutton path="serviceType.serviceType" value="PHONE"/> <spring:message code="label.phone"/>
                            </strong></label>
                            <label class="radio-inline"><strong>
                                <form:radiobutton path="serviceType.serviceType" value="MARKER"/> <spring:message code="label.marker"/>
                            </strong></label>
                            <form:errors path="serviceType" cssClass="alert-danger" />
                        </fieldset>
                    </div>
                   </div>
            </c:if>
            <div id="sharedServiceForm">
                <div class="form-group">
                    <label class="col-lg-2 control-label"><spring:message code="label.name"/></label>
                    <div class="col-lg-8">
                        <c:set var="disable" value="false"/>
                        <c:if test="${not empty serviceForm.serviceType.id}">
                            <c:set var="disable" value="true"/>
                            <form:hidden path="serviceType.id" id="getServiceType" data-parameter="${serviceForm.serviceType.serviceType}"/>
                        </c:if>

                        <form:select path="serviceType.id" id="serviceTypeList" multiple="false" class="form-control" disabled="${disable}">
                        <c:forEach items="${serviceTypeList}" var="services">
                            <form:option value="${services.id}" label="${services.name}  (${services.price} грн.)" data-type="${services.serviceType}"/>
                        </c:forEach>
                        </form:select>
                    </div>
                    <div class="col-lg-1">
                        <a id="changeServiceType" class="btn btn-sm btn-success"><spring:message code="label.change"/></a>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dateStart" class="col-lg-2 control-label"><spring:message code="label.startTime"/></label>
                    <div class="col-lg-8">
                        <fmt:formatDate value="${dateStart.date}" var="dateString" pattern="yyyy-MM-dd" />
                        <div class='input-group date' id='datepicker1'>
                            <form:input path="dateStart" id="dateStart" class="form-control" value="${dateString}" placeholder="yyyy-MM-dd" readonly="true"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                        <form:errors path="dateStart" cssClass="alert-danger" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="period" class="col-lg-2 control-label"><spring:message code="label.period"/></label>
                    <div class="col-lg-8">
                        <form:select path="period" class="form-control" id="period">
                            <form:options items="${servicePeriodList}" />
                        </form:select>
                        <form:errors path="period" cssClass="alert-danger" />
                    </div>
                </div>
            </div>

            <div id="internetService">
                <div class="form-group">
                    <label for="username" class="col-lg-2 control-label"><spring:message code="label.userName"/></label>
                    <div class="col-lg-8">
                        <form:errors path="serviceInternet.username" cssClass="alert-danger" />
                        <spring:message code="label.userName" var="name"/>
                        <form:input path="serviceInternet.username" class="form-control" id="username" placeholder="${name}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-lg-2 control-label"><spring:message code="label.password"/></label>
                    <div class="col-lg-8">
                        <spring:message code="label.password" var="pass"/>
                        <form:input path="serviceInternet.password" class="form-control" id="password" placeholder="${pass}"/>
                        <form:errors path="serviceInternet.password" cssClass="alert-danger" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="softblock" class="col-lg-2 control-label"><spring:message code="label.softbock"/></label>
                    <div class="col-lg-8">
                        <form:checkbox path="serviceInternet.softblock" class="form-control fix-10px-width" id="softblock"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="macaddress" class="col-lg-2 control-label"><spring:message code="label.macAddress"/></label>
                    <div class="col-lg-8">
                        <spring:message code="label.macAddress" var="mac"/>
                        <form:input path="serviceInternet.macaddress" class="form-control" id="macaddress" placeholder="${mac}"/>
                        <form:errors path="serviceInternet.macaddress" cssClass="alert-danger" />
                    </div>
                </div>

                <div class="form-group" id="ipNetDiv">
                    <label for="ipNet" class="col-lg-2 control-label"><spring:message code="label.subnet"/></label>
                    <div class="col-lg-8">
                        <form:select path="serviceInternet.ip.ipSubnet.id" class="form-control" id="ipNet">
                            <c:forEach items="${ipNetList}" var="ipNets">
                                <form:option value="${ipNets.id}" label="${ipNets.ipSubnet}"/>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group" id="ipAddressDiv">
                    <label for="ipAddress" class="col-lg-2 control-label"><spring:message code="label.ip"/></label>
                    <label class="col-lg-5 control-label" id="ipAddressSelect">
                        <span id="ipAddressCurrent" class="form-control"><spring:message code="label.none"/></span>
                        <form:select path="serviceInternet.ip.id" class="form-control" id="ipAddress">
                            <form:options items="${ipAddressList}" />
                        </form:select>
                    </label>
                    <label for="changeIp" class="col-lg-2">
                        <input type="checkbox" class="checkbox" id="changeIp" href="#subnet"/> <spring:message code="label.changeIpAddress"/>
                    </label>
                </div>

                <div class="form-group">
                    <label for="device" class="col-lg-2 control-label"><spring:message code="label.device"/></label>
                    <div class="col-lg-8">
                        <form:select path="serviceInternet.device.id" class="form-control" id="device">
                            <c:forEach items="${devicesList}" var="devices">
                                <form:option value="${devices.id}" label="${devices.name}"/>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="devicePorts" class="col-lg-2 control-label"><spring:message code="label.port"/></label>
                    <div class="col-lg-8">
                        <form:select path="serviceInternet.port" class="form-control" id="devicePorts">
                            <form:options items="${devicePortList}" />
                        </form:select>
                    </div>
                </div>

            </div>



            <div class="form-group" id="phoneService">
                <label for="phoneNumber" class="col-lg-2 control-label"><spring:message code="label.phoneNumber"/></label>
                <div class="col-lg-8">
                    <spring:message code="label.phoneNumber" var="phonenumber"/>
                    <form:input path="servicePhone.phoneNumber" class="form-control" id="phoneNumber" placeholder="${phonenumber}"/>
                    <form:errors path="servicePhone.phoneNumber" cssClass="alert-danger" />
                </div>
            </div>

            <div class="form-group" id="submitBtn">
                <div class="col-lg-8 col-lg-offset-3">
                    <form:hidden path="id" />
                    <form:hidden path="accountId"/>
                    <button type="submit" class="btn btn-primary"><spring:message code="label.submit"/></button>
                </div>
            </div>
        </fieldset>
    </form:form>

</div>
</body>
</html>