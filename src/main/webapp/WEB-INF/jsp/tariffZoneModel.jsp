<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title><spring:message code="tariff.list"/></title>
  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
  <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>

  <spring:url value="/resources/css/bootstrap-switch.min.css" var="bootstrapSwitch" />
  <link href="${bootstrapSwitch}" rel="stylesheet"/>

  <spring:url value="/resources/js/util.js" var="util"/>
  <script src="${util}"></script>
  <spring:url value="/resources/js/tariffzone.js" var="tarZoneJS"/>
  <script src="${tarZoneJS}"></script>
  <spring:url value="/resources/js/popup.js" var="popup"/>
  <script src="${popup}"></script>
  <spring:url value="/resources/js/bootstrap-switch.min.js" var="jBootstrapSwitch" />
  <script src="${jBootstrapSwitch}"></script>

  <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
  <div id="totopscroller"></div>


  <div id="successMessageEdit" class="alert alert-success" style="display: none">
    <strong><spring:message code="tariff.success.edit"/></strong>
  </div>
  <div id="successMessageADD" class="alert alert-success" style="display: none">
    <strong><spring:message code="tariff.success.add"/></strong>
  </div>
  <div id="errorMessage" class="alert alert-danger" style="display: none">
    <strong><spring:message code="tariff.error"/></strong>
  </div>

  <div id="messagesDiv">
    <c:if test="${not empty successMessage}">
      <div class="alert alert-success" role="alert">
        <strong>${successMessage}</strong>
      </div>
    </c:if>&nbsp;
    <c:if test="${not empty errorMessage}">
      <div class="alert alert-danger" role="alert">
        <strong>${errorMessage}</strong>
      </div>
    </c:if>
  </div>
  &nbsp;

  <a type="button" href="#tariffZoneModal" id="addZone" class="btn btn-sm btn-primary" data-toggle="modal"><spring:message code="tariff.create"/></a>
  <div id="tariffZoneModal" class="modal fade">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title"><spring:message code="tariff.action"/> </h4>
        </div>

        <div class="modal-body">
          <div class="">
            <form:form class="form" id="crtTariffZoneForm" method="POST" modelAttribute="tariffZoneForm" action="${pageContext.request.contextPath}/tariffzone/add">
              <fieldset>
                <form:input path="id" id="id" type="hidden"/>
                <form:input path="zoneId" id="zoneId" type="hidden"/>

                <div class="col-lg-12">
                  <div class="col-lg-4">
                    <label for="zoneName" class="control-label"><spring:message code="tariff.zoneName"/></label>
                      <spring:message code="tariff.zoneName" var="zoneName"/>
                      <form:input path="zoneName" class="form-control" id="zoneName" placeholder="${zoneName}"/>
                      <span class="help-inline text-danger" id="zoneNameWarn" style="display: none"><spring:message code="tariff.zoneName.required"/></span>
                  </div>
                  <div class="col-lg-4">
                    <label for="additionalKode" class="control-label"><spring:message code="tariff.additionalKode"/></label>
                      <spring:message code="tariff.additionalKode" var="additionalKode"/>
                      <form:input path="additionalKode" class="form-control" id="additionalKode" placeholder="${zoneName}"/>
                  </div>
                  <div class="col-lg-4" style="text-align: center;">
                    <label for="dollarPath" class="control-label"><spring:message code="tariff.dollar"/></label>
                      <form:input path="dollar" class="form-control" id="dollar" type="hidden"/>
                      <div class="col-lg-12">
                        <input id="dollarPath" name="softblock" type="checkbox" data-size="small">
                      </div>
                  </div>
                </div>

                <div class="col-lg-12">
                  <div class="col-lg-4">
                    <label for="tarif" class="control-label"><spring:message code="tariff.tarif"/></label>
                      <spring:message code="tariff.tarif" var="tarif"/>
                      <form:input path="tarif" class="form-control" id="tarif" placeholder="${tarif}" type="number" min="0" step="any"/>
                  </div>
                  <div class="col-lg-4">
                    <label for="tarifPref" class=" control-label"><spring:message code="tariff.tarifPref"/></label>
                      <spring:message code="tariff.tarifPref" var="tarifPref"/>
                      <form:input path="tarifPref" class="form-control" id="tarifPref" placeholder="${tarifPref}" type="number" min="0" step="any"/>
                  </div>

                  <div class="col-lg-4">
                    <label for="prefProfile" class="control-label"><spring:message code="rule.profileId"/></label>
                    <form:select path="prefProfile" class="form-control" id="prefProfile" multiple="false">
                      <c:forEach items="${prefProfileList}" var="prefProfileList">
                        <form:option value="${prefProfileList}">${prefProfileList}</form:option>
                      </c:forEach>
                    </form:select>
                  </div>
                </div>
              </fieldset>
            </form:form>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" id="dismissAction"> <spring:message code="label.cancel"/></button>
          <button type="button" id="crtTariffZone" class="btn btn-primary"><spring:message code="label.submit"/></button>
        </div>
      </div>
    </div>
  </div>


  <table class="table table-striped table-hover" id='table'>
    <tr>
      <th style="width: 10%;"></th>
      <th style="width: 30%;"><spring:message code="tariff.zoneName"/></th>
      <th style="width: 10%;"><spring:message code="tariff.additionalKode"/></th>
      <th style="width: 10%;"><spring:message code="tariff.dollar"/></th>
      <th style="width: 10%;"><spring:message code="tariff.tarif"/></th>
      <th style="width: 10%;"><spring:message code="tariff.tarifPref"/></th>
      <th style="width: 10%;"><spring:message code="rule.profileId"/></th>
    </tr>
      <c:forEach items="${tariffzoneList}" var="tariffZone">

          <tr id="${tariffZone.id}">
            <td>
              <a  class="pushEdit" data-toggle="modal" data-id="${tariffZone.id}"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
              &nbsp;&nbsp;
              <a data-href="${pageContext.request.contextPath}/tariffzone/delete/${tariffZone.id}"  id="deleting" data-toggle="modal" data-target="#confirm-delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
              &nbsp;&nbsp;
              <a id="info" href="${pageContext.request.contextPath}/objectinfo/${tariffZone.id}?type=TariffZone"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></a>
            </td>
            <td>${tariffZone.zoneName}</td>
            <td>${tariffZone.additionalKode}</td>
            <c:choose>
              <c:when test="${tariffZone.dollar == true}">
                <td class="bg-success" style="text-align: center;"><span class="glyphicon glyphicon-ok"></span></td>
              </c:when>
              <c:otherwise>
                <td></td>
              </c:otherwise>
            </c:choose>
            <td>${tariffZone.tarif}</td>
            <td>${tariffZone.tarifPref}</td>
            <td><a href="${pageContext.request.contextPath}/preferencerule/list?prefProfileId=${tariffZone.prefProfile}">${tariffZone.prefProfile}</a></td>
          </tr>

      </c:forEach>
  </table>

  <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"><strong><spring:message code="tariff.deleting"/></strong></h4>
        </div>
        <div class="modal-body">
          <spring:message code="tariff.delete"/>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.cancel"/></button>
          <a type="button" id="deleteBtn" class="btn btn-primary btn-ok"><spring:message code="label.submitDelete"/></a>
        </div>
      </div>
    </div>
  </div>

</div>

</body>
</html>
