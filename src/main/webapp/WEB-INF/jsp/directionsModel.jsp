<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><spring:message code="label.directionList"/></title>
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>

    <spring:url value="/resources/css/bootstrap-switch.min.css" var="bootstrapSwitch" />
    <link href="${bootstrapSwitch}" rel="stylesheet"/>

    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>
    <spring:url value="/resources/js/direction.js" var="direction"/>
    <script src="${direction}"></script>
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
        <strong><spring:message code="direction.success.edit"/></strong>
    </div>
    <div id="successMessageADD" class="alert alert-success" style="display: none">
        <strong><spring:message code="direction.success.add"/></strong>
    </div>
    <div id="errorMessage" class="alert alert-danger" style="display: none">
        <strong><spring:message code="direction.error"/></strong>
    </div>
    <div id="nothingFound" class="alert alert-info text-center" style="display: none">
        <strong><spring:message code="search.message"/></strong>
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

    <div class="col-lg-6">
        <a type="button" href="#directionCreateModal" id="addDirection" class="btn btn-sm btn-primary" data-toggle="modal"><spring:message code="label.directionCreate"/></a>
        <div class="form-group" style="padding-top: 20px;">
            <label>
                <spring:message code="label.show"/>
                <select class="selectpicker" data-style="btn-info" id="selectEntries">
                    <option value="10">10</option>
                    <option value="15">15</option>
                    <option value="20">20</option>
                    <option value="25" selected="selected">25</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                </select>
                <spring:message code="label.entries"/>
            </label>
        </div>
    </div>
        <div class="col-lg-6">
            <div class="col-sm-6 col-sm-offset-3" style="margin-left: 50%;">
                <div id="imaginary_container">
                    <div class="input-group stylish-input-group">
                        <spring:message code="search.searchPrefix" var="searchpref"/>
                        <input type="text" class="form-control"  placeholder="${searchpref}" id="searchPrefInput">
                    <span class="input-group-addon" id="searchPref">
                        <button type="submit" >
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
                    <span class="input-group-addon" id="eraseField">
                        <button type="submit">
                            <span class="glyphicon glyphicon-erase float-lt"></span>
                        </button>
                    </span>
                    </div>
                </div>
                <div id="errorMessageNAN" class="error alert-warning" style="display: none">
                    <strong><spring:message code="label.diggits"/></strong>
                </div>
            </div>
        </div>
    <div id="directionCreateModal" class="modal fade">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><spring:message code="label.directionAction"/> </h4>
                </div>

                <div class="modal-body">
                    <div class="">
                        <form:form class="form" id="crtDirectionForm" method="POST" modelAttribute="directionForm" action="${pageContext.request.contextPath}/direction/add">
                            <fieldset>
                                <form:input path="id" id="id" type="hidden"/>

                                <div class="col-lg-12">
                                    <div class="col-lg-4">
                                        <label for="description" class="col-lg-9 control-label"><spring:message code="label.description"/></label>
                                        <div class="col-lg-12">
                                            <spring:message code="label.description" var="descr"/>
                                            <form:input path="description" class="form-control" id="description" placeholder="${descr}"/>
                                            <span class="help-inline text-danger" id="descriptionWarn" style="display: none"><spring:message code="description.required"/></span>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <label for="prefix" class="col-lg-9 control-label"><spring:message code="label.prefix"/></label>
                                        <div class="col-lg-12">
                                            <spring:message code="label.prefix" var="pref"/>
                                            <form:input path="prefix" class="form-control" id="prefix" placeholder="${pref}"/>
                                            <span class="help-inline text-danger" id="prefixWarn" style="display: none"><spring:message code="prefix.required"/></span>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <label for="additionalKode" class="col-lg-9 control-label"><spring:message code="label.additionalKode"/></label>
                                        <div class="col-lg-12">
                                            <spring:message code="label.additionalKode" var="addkode"/>
                                            <form:input path="additionalKode" class="form-control" id="additionalKode" placeholder="${addkode}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="col-lg-4">
                                        <label for="trunkgroup" class="col-lg-9 control-label"><spring:message code="label.trunkgroup"/></label>
                                        <div class="col-lg-12">
                                            <spring:message code="label.trunkgroup" var="trunk"/>
                                            <form:input path="trunkgroup" class="form-control" id="trunk" placeholder="${trunk}"/>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                            <label for="validFrom" class="col-lg-9 control-label"><spring:message code="label.startDate"/></label>
                                            <div class="col-lg-12">
                                                <form:input path="validFrom" class="form-control" id="validFrom" type="date"/>
                                            </div>
                                     </div>
                                    <div class="col-lg-4">
                                        <label for="validTo" class="col-lg-9 control-label"><spring:message code="label.endDate"/></label>
                                        <div class="col-lg-12">
                                            <form:input path="validTo" class="form-control" id="validTo" type="date"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-8 float-right">
                                    <span class="help-inline text-danger" id="dateWarn" style="display: none"><spring:message code="label.dateFormat"/></span>
                                </div>
                                <div class="col-lg-12">
                                    <div class="col-lg-12">
                                        <label for="tarriffZoneId" class="col-lg-12 control-label"><spring:message code="label.tariffZone"/></label>
                                        <div class="col-lg-8">
                                            <form:select path="zoneId" class="form-control" id="tarriffZoneId" multiple="false">
                                            </form:select>
                                        </div>
                                        <div class="col-lg-4 text-center">
                                            <input id="getAll" name="zonelist" type="checkbox" data-size="normal" >
                                        </div>

                                    </div>
                                </div>
                            </fieldset>
                        </form:form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="dismissAction"><spring:message code="label.cancel"/></button>
                    <button type="button" id="crtDirection" class="btn btn-primary" ><spring:message code="label.submit"/></button>
                </div>
            </div>
        </div>
    </div>


    <table class="table table-striped" id='table'>
        <tr>
            <th style="width: 10%;"></th>
            <th style="width: 30%;"><spring:message code="label.direction"/></th>
            <th style="width: 20%;"><spring:message code="label.prefix"/></th>
            <th style="width: 20%;"><spring:message code="label.tariffZoneName"/></th>
            <th style="width: 10%;"><spring:message code="label.validFrom"/></th>
            <th style="width: 10%;"><spring:message code="label.validTo"/></th>
        </tr>
    </table>
    <div id="tableNavigation">
        <spring:message code="label.page"/>: <label id="pageNumber">${pageNum}</label> <spring:message code="label.from"/> <label id="totalPages">${pagesTotal}</label>
        <a href="#" class="btn btn-primary btn-sm link-btn" id="goPrev" onClick="goToPrevPage();"><span
                class="glyphicon glyphicon-backward" aria-hidden="true"></span></a>
        &nbsp;&nbsp;
        <a href="#" class="btn btn-primary btn-sm link-btn" id="goNext" onClick="goToNextPage();"><span
                class="glyphicon glyphicon-forward" aria-hidden="true"></span></a>
    </div>


    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><strong><spring:message code="label.directionDeleting"/></strong></h4>
                </div>
                <div class="modal-body">
                    <spring:message code="label.directionDelete"/>
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
                    <h4 class="modal-title"><strong><spring:message code="direction.busy"/></strong></h4>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>
