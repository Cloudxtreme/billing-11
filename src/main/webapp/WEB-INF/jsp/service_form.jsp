<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Add/Edit Service</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/service.js" var="service" />
    <script src="${service}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="serviceForm" action="${pageContext.request.contextPath}/service/form">
        <fieldset>
            <legend>Add/Edit Service</legend>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Servise Type</h3>
                </div>
                <div class="panel-body">
                    <label for="internetBtn" class="control-label col-lg-3"><strong>
                        <form:radiobutton path="serviceType" value="internet" id="internetBtn"/> Internet
                    </strong></label>
                    <label for="phoneBtn" class="control-label col-lg-3"><strong>
                        <form:radiobutton path="serviceType" value="phone" id="phoneBtn"/> Phone
                    </strong></label>
                </div>
                <div class="panel-heading border-top" id="formServiceHead">
                    <h3 class="panel-title">Service Form</h3>
                </div>
                <div class="panel-body" id="formServiceBody">
                    <div class="form-group">
                        <label class="col-lg-8 ${errorClass}">Please fill in all fields below.</label>
                    </div>
                    <div class="form-group">
                        <label for="serviceName" class="col-lg-3 control-label">Service Name</label>
                        <div class="col-lg-9">
                            <form:input path="name" class="form-control" id="serviceName" placeholder="Name" />
                            <form:errors path="name" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="serviceDescription" class="col-lg-3 control-label">Description</label>
                        <div class="col-lg-9">
                            <form:input path="description" class="form-control" id="serviceDescription" placeholder="Description"/>
                            <form:errors path="description" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-lg-3 control-label">Price</label>
                        <div class="col-lg-9">
                            <form:input path="price" class="form-control" id="price" placeholder="Price"/>
                            <form:errors path="price" cssClass="alert-danger" />
                        </div>
                    </div>
                <div id="internetService">
                    <div class="form-group">
                        <label for="username" class="col-lg-3 control-label">User Name</label>
                        <div class="col-lg-9">
                            <form:input path="username" class="form-control" id="username" placeholder="User Name"/>
                            <form:errors path="username" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-lg-3 control-label">Password</label>
                        <div class="col-lg-9">
                            <form:password path="password" class="form-control" id="password" placeholder="Password"/>
                            <form:errors path="password" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="macaddress" class="col-lg-3 control-label">MacAddress</label>
                        <div class="col-lg-9">
                            <form:input path="macaddress" class="form-control" id="macaddress" placeholder="MacAddress"/>
                            <form:errors path="macaddress" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="ip" class="col-lg-3 control-label">IP</label>
                        <div class="col-lg-9">
                            <form:input path="ip" class="form-control" id="ip" placeholder="IP"/>
                            <form:errors path="ip" cssClass="alert-danger" />
                        </div>
                    </div>
                </div>
                    <div class="form-group" id="phoneService">
                        <label for="phoneNumber" class="col-lg-3 control-label">Phone Number</label>
                        <div class="col-lg-9">
                            <form:input path="phoneNumber" class="form-control" id="phoneNumber" placeholder="Phone Number"/>
                            <form:errors path="phoneNumber" cssClass="alert-danger" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <form:hidden path="id" />
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>
    </form:form>

</div>

</body>
</html>