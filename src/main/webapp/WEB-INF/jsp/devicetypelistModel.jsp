<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title><spring:message code="label.deviceType"/></title>

  <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
  <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>

  <spring:url value="/resources/js/deviceType.js" var="deviceType"/>
  <script src="${deviceType}"></script>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well">
  <div id="totopscroller"> </div>

  <div>
      <div  id="succesMessage" class="alert alert-success col-md-8" style="display: none">
          <strong><spring:message code="label.success"/></strong>
      </div>
      <div  id="errorMessage" class="alert alert-danger col-md-8" style="display: none">
          <strong><spring:message code="label.fail"/></strong>
      </div>


    <table class="table table-striped" id ='table'>
      <th></th>
      <TH><spring:message code="label.name"/></th>
      <th><spring:message code="label.description"/></th>
      <th><spring:message code="label.ports"/></th>
      <c:forEach items="${devicetypelist}" var="current">
        <label for="${current.id}">
          <tr id="${current.id}">
            <td id="updating">
              <a id="updatingBtn"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
            </td>
            <td class="dname">${current.deviceType}</td>
            <td class="ddesc">${current.description}</td>
            <td class="dport">${current.portsNumber}</td>
          </tr>
        </label>
      </c:forEach>
    </table>
  </div>
</div>


</body>
</html>