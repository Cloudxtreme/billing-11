<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title>Add new device</title>
  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

</head>
<body>
  <jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

  <div class="">
    <form:form class="form" id="addDeviceForm" method="POST" commandName="deviceForm" action="${pageContext.request.contextPath}/adddevice.html">
      <fieldset>
          <%--<c:if  test="${empty errorMessage}">
              <div class="form-group">
                  <label class="col-lg-8">Please fill in all fields below.</label>
              </div>
          </c:if>
          <c:if test="${not empty errorMessage}">
              <div class="form-group">
                  <label class="col-lg-8 text-warning">${errorMessage}</label>
              </div>
          </c:if>--%>

        <%--<form:input path="id" id="id" type="hidden"/>--%>
        <%--<form:input path="status" id="status" type="hidden"/>--%>
        <div class="form-group">
          <label for="name" class="col-lg-5 control-label">Device name #</label>
          <div class="col-lg-9">
            <form:input path="name" class="form-control" id="deviceName" placeholder="Device name"/>
          </div>
        </div>
        <div class="form-group">
          <label for="deviceTypes" class="col-lg-5 control-label">Device typeS</label>
          <div class="col-lg-9">
                  <form:select path="devType" class="form-control" id="deviceTypes">
                  <form:options items="${deviceTypesMap}" />
                  </form:select>
          </div>
        </div>

            <div class="form-group">
              <label for="description" class="col-lg-5 control-label">Device description #</label>
              <div class="col-lg-9">
                <form:input path="description" class="form-control" id="deviceDescription" placeholder="Device description"/>
              </div>
            </div>

            <div class="form-group">
              <label for="community" class="col-lg-5 control-label">Device community #</label>
              <div class="col-lg-9">
                <form:input path="community" class="form-control" id="deviceCommunity" placeholder="Device community"/>
              </div>
            </div>

            <div class="form-group">
              <label for="ip" class="col-lg-5 control-label">Device IP #</label>
              <div class="col-lg-9">
                <form:input path="ip" class="form-control" id="deviceIp" placeholder="Device IP"/>
              </div>
            </div>

            <div class="form-group">
              <div class="col-lg-10 col-lg-offset-2">
                <button type="submit" class="btn btn-primary">Submit</button>
              </div>
            </div>

        </fieldset>
    </form:form>
  </div>



</body>
</html>
