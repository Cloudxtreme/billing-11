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
  <spring:url value="/resources/js/prefRule.js" var="prefRule"/>
  <script src="${prefRule}"></script>
  <spring:url value="/resources/js/smoothScroll.js" var="smooth"/>
  <script src="${smooth}"></script>
  <spring:url value="/resources/js/popup.js" var="popup"/>
  <script src="${popup}"></script>
  <spring:url value="/resources/js/overall_functions.js" var="overall"/>
  <script src="${overall}"></script>

  <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
  <div id="totopscroller"></div>


  <div id="successMessageEdit" class="alert alert-success" style="display: none">
    <strong><spring:message code="rule.success.edit"/></strong>
  </div>
  <div id="successMessageADD" class="alert alert-success" style="display: none">
    <strong><spring:message code="rule.success.add"/></strong>
  </div>
  <div id="errorMessage" class="alert alert-danger" style="display: none">
    <strong><spring:message code="rule.error"/></strong>
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

  <a type="button" href="#ruleModal" id="addRule" class="btn btn-sm btn-primary" data-toggle="modal"><spring:message code="rule.create"/></a>
  <div id="ruleModal" class="modal fade">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title"><spring:message code="rule.action"/> </h4>
        </div>

        <div class="modal-body">
          <div class="">
            <form:form class="form" id="crtRuleForm" method="POST" modelAttribute="ruleForm" action="${pageContext.request.contextPath}/preferencerule/add">
              <fieldset>
                <form:input path="id" id="id" type="hidden"/>
                <div class="col-lg-12">
                  <div class="col-lg-3">
                    <label for="profileId" class="control-label"><spring:message code="rule.profileId"/></label>
                      <form:input path="profileId" class="form-control" id="profileId" type="number" min="1" step="1"/>
                      <span class="help-inline text-danger" id="profileIdWarn" style="display: none"><spring:message code="rule.profileId.required"/></span>
                  </div>
                  <div class="col-lg-3">
                    <label for="rulePriority" class="control-label"><spring:message code="rule.profileId"/></label>
                    <form:input path="rulePriority" class="form-control" id="rulePriority" type="number" min="1" step="1"/>
                    <span class="help-inline text-danger" id="rulePriorityWarn" style="display: none"><spring:message code="rule.rulePriority.required"/></span>
                  </div>
                  <div class="col-lg-3">
                    <label for="dayOfMonth" class="control-label"><spring:message code="rule.dayOfMonth"/></label>
                      <form:select path="dayOfMonth" class="form-control" id="dayOfMonth" multiple="false">
                        <form:option value=""><spring:message code="label.none"/> </form:option>
                        <form:option value="0">0</form:option>
                        <form:option value="1">1</form:option>
                      </form:select>
                  </div>
                  <div class="col-lg-3">
                    <label for="month" class="control-label"><spring:message code="rule.month"/></label>
                      <form:select path="month" class="form-control" id="month" multiple="false">
                        <form:option value=""><spring:message code="label.none"/> </form:option>
                        <form:option value="0">0</form:option>
                        <form:option value="1">1</form:option>
                      </form:select>
                  </div>
                </div>

                <div class="col-lg-12" style="padding-top: 2%;">
                  <div class="col-lg-3">
                    <label for="dayOfWeek" class="control-label"><spring:message code="rule.dayOfWeek"/></label>
                      <form:select path="dayOfWeek" class="form-control" id="dayOfWeek" multiple="false">
                        <form:option value=""><spring:message code="label.none"/> </form:option>
                        <form:option value="0"><spring:message code="label.Monday"/></form:option>
                        <form:option value="1"><spring:message code="label.Tuesday"/></form:option>
                        <form:option value="2"><spring:message code="label.Wednesday"/></form:option>
                        <form:option value="3"><spring:message code="label.Thursday"/></form:option>
                        <form:option value="4"><spring:message code="label.Friday"/></form:option>
                        <form:option value="5"><spring:message code="label.Saturday"/></form:option>
                        <form:option value="6"><spring:message code="label.Sunday"/></form:option>
                      </form:select>
                  </div>
                  <div class="col-lg-3">
                    <label for="starttime" class="control-label"><spring:message code="rule.starttime"/></label>
                      <form:input path="starttime" class="form-control" id="starttime" type="time" step="1"/>
                  </div>
                  <div class="col-lg-3">
                    <label for="finishtime" class="control-label"><spring:message code="rule.finishtime"/></label>
                      <form:input path="finishtime" class="form-control" id="finishtime" type="time" step="1"/>
                  </div>
                  <div class="col-lg-3">
                    <label for="tarif" class="control-label"><spring:message code="rule.tarif"/></label>
                      <form:input path="tarif" class="form-control" id="tarif" type="number" min="0" step="any"/>
                  </div>
                </div>


              </fieldset>
            </form:form>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" id="dismissAction"><spring:message code="label.cancel"/></button>
          <button type="button" id="crtRule" class="btn btn-primary"><spring:message code="label.submit"/></button>
        </div>
      </div>
    </div>
  </div>


  <table class="table table-striped table-hover" id='table'>
    <tr>
      <th style="width: 20%;"></th>
      <th style="width: 10%;"><spring:message code="rule.profileId"/></th>
      <th style="width: 10%;"><spring:message code="rule.rulePriority"/></th>
      <th style="width: 10%;"><spring:message code="rule.dayOfMonth"/></th>
      <th style="width: 10%;"><spring:message code="rule.month"/></th>
      <th style="width: 10%;"><spring:message code="rule.dayOfWeek"/></th>
      <th style="width: 10%;"><spring:message code="rule.starttime"/></th>
      <th style="width: 10%;"><spring:message code="rule.finishtime"/></th>
      <th style="width: 10%;"><spring:message code="rule.tarif"/></th>
    </tr>
    <c:forEach items="${ruleList}" var="ruleList">

      <tr id="${ruleList.id}">
        <td>
          <a  class="pushEdit" data-toggle="modal" data-id="${ruleList.id}"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
          &nbsp;&nbsp;
          <a data-href="${pageContext.request.contextPath}/preferencerule/delete/${ruleList.id}"  id="deleting" data-toggle="modal" data-target="#confirm-delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
          &nbsp;&nbsp;
          <a id="info" href="${pageContext.request.contextPath}/objectinfo/${ruleList.id}?type=PreferenceRule"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></a>
        </td>
        <td>${ruleList.profileId}</td>
        <td>${ruleList.rulePriority}</td>
        <td>${ruleList.dayOfMonth}</td>
        <td>${ruleList.month}</td>
        <td>
          <c:if test="${ruleList.dayOfWeek == 0}"><spring:message code="label.Monday"/></c:if>
          <c:if test="${ruleList.dayOfWeek == 1}"><spring:message code="label.Tuesday"/></c:if>
          <c:if test="${ruleList.dayOfWeek == 2}"><spring:message code="label.Wednesday"/></c:if>
          <c:if test="${ruleList.dayOfWeek == 3}"><spring:message code="label.Thursday"/></c:if>
          <c:if test="${ruleList.dayOfWeek == 4}"><spring:message code="label.Friday"/></c:if>
          <c:if test="${ruleList.dayOfWeek == 5}"><spring:message code="label.Saturday"/></c:if>
          <c:if test="${ruleList.dayOfWeek == 6}"><spring:message code="label.Sunday"/></c:if>
        </td>
        <td>${ruleList.starttime}</td>
        <td>${ruleList.finishtime}</td>
        <td>${ruleList.tarif}</td>
      </tr>

    </c:forEach>
  </table>

  <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"><strong><spring:message code="rule.deleting"/></strong></h4>
        </div>
        <div class="modal-body">
          <spring:message code="rule.delete"/>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.cancel"/></button>
          <a type="button" id="deleteBtn" class="btn btn-primary btn-ok"><spring:message code="label.submitDelete"/></a>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade central-modal" id="busyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 40%; text-align: center; top:40%">
      <div class="modal-content">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="padding: 0.5% 1%; font-size: 4em;"><span aria-hidden="true">&times;</span></button>
        <div class="modal-header bg-warning">
          <h4 class="modal-title"><strong><spring:message code="rule.busy"/></strong></h4>
        </div>
      </div>
    </div>
  </div>


</div>

</body>
</html>
