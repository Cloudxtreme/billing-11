<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title><spring:message code="audit.title"/></title>

  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
  <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>

  <spring:url value="/resources/js/util.js" var="util"/>
  <spring:url value="/resources/js/popup.js" var="popup"/>
  <script src="${popup}"></script>
  <script src="${util}"></script>
  <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
  <div id="totopscroller"> </div>

    <div class="row" style="margin-bottom: 15px; margin-left: 0; font-size: 15px;">
        <a onclick="window.history.back();" style="color: #444444;" class="aHover">
            <span class="glyphicon glyphicon-arrow-left" style="color: #3a87ad !important;"></span>
            <spring:message code="label.returnToPrevious"/>
        </a>
    </div>

  <c:if test="${empty auditedList}">
    <div  id="warning" class="alert alert-warning" style="display: block; text-align: center !important;">
      <strong><spring:message code="audit.objectWithotChanges"/></strong>
    </div>
  </c:if>

  <c:if test="${not empty createdBy && not empty createdDate}">
    <div class="col-lg-6">
      <strong><spring:message code="audit.createdBy"/> ${createdBy} <spring:message code="audit.in"/> <fmt:formatDate value="${createdDate}" type="both" /></strong>
    </div>
  </c:if>

  <table class="table table-striped" id='table'>
    <caption class="text-center" style="padding-top: 0 !important;"><h3><spring:message code="audit.title"/></h3></caption>
    <th style="width: 20% !important;"><spring:message code="audit.date"/></th>
    <th style="width: 10% !important;"><spring:message code="audit.changedBy"/></th>
    <th style="width: 70% !important;"><spring:message code="audit.changedObject"/></th>
    <c:forEach items="${auditedList}" var="audited">
        <c:if test="${fn:length(audited.changesList) > 0}">
          <tr id="${audited.id}">
            <td><fmt:formatDate value="${audited.changesDate}" type="both" /></td>
            <td>${audited.changedBy}</td>
            <td>
              <table class="table table-inner">
                <th style="width: 30% !important;" class="text-info"><spring:message code="audit.fieldName"/></th>
                <th style="width: 30% !important" class="text-danger"><spring:message code="audit.oldValue"/></th>
                <th style="width: 40% !important;" class="text-success"><spring:message code="audit.newValue"/></th>
                <c:forEach items="${audited.changesList}" var="changesList">
                  <tr>
                    <td class="text-info">${changesList.fieldName}</td>
                    <td class="text-danger">${changesList.oldValue}</td>
                    <td class="text-success">${changesList.newValue}</td>
                  </tr>
                </c:forEach>
              </table>
            </td>
          </tr>
        </c:if>
    </c:forEach>
  </table>
</div>

</body>
</html>
