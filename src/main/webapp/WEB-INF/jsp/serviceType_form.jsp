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

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="serviceForm" action="${pageContext.request.contextPath}/serviceType/form">
        <fieldset>
            <legend>Add/Edit Service</legend>
                    <div class="form-group">
                        <label class="col-lg-8 ${errorClass}">Please fill in all fields below.</label>
                    </div>
                    <div class="form-group">
                            <label class="col-lg-3 control-label">Service Type</label>
                            <div class="col-lg-9">
                                <label for="internetService" class="radio-inline"><strong>
                                    <form:radiobutton path="serviceType" value="internet" id="internetService"/> Internet
                                </strong></label>
                                <label for="phoneService" class="radio-inline"><strong>
                                    <form:radiobutton path="serviceType" value="phone" id="phoneService"/> Phone
                                </strong></label>
                                <form:errors path="serviceType" cssClass="alert-danger" />
                            </div>
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
                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <form:hidden path="id" />
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>
        </fieldset>
    </form:form>

</div>

</body>
</html>