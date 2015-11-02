<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title>File uploading</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/css/file-tree.min.css" var="fileTreeCss"/>
    <link href="${fileTreeCss}" rel="stylesheet"/>

    <spring:url value="/resources/js/uploadCSVFile.js" var="csvScript"/>
    <script src="${csvScript}"></script>
    <spring:url value="/resources/js/file-tree.min.js" var="fileTreeMin"/>
    <script src="${fileTreeMin}"></script>
    <spring:url value="/resources/js/jquery-ui.min.js" var="jqueryMinUi"/>
    <script src="${jqueryMinUi}"></script>
    <spring:url value="/resources/js/jquery.mjs.nestedSortable.js" var="nestedFileTree"/>
    <script src="${nestedFileTree}"></script>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well well-lg">

    <%--Error Message body--%>
    <div id="errorMessage" class="alert alert-warning" style="display: none">
    </div>

    <%--Success message body--%>
    <div id="successMessage" class="alert alert-success" style="display: none">
    </div>


    <legend>
        <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span> Select CSV file to upload
        &nbsp;&nbsp;
        <a id="reportsList" href="#reportModal" data-toggle="modal"
           style="text-decoration: none; color: rgba(88,124,173,0.54)"><span
                class="glyphicon glyphicon-th-large"></span> Generate report</a>
    </legend>

    <div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="myReportModal"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="tab-container">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myReportModal" style="color: #d9230f;">Select reports to
                                generate</h4>
                        </div>
                        <table class="table table-striped" id='table'>

                            <th></th>
                            <TH>Name</th>

                            <tr id="longReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report</td>
                            </tr>
                            <tr id="longReportRA">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report RA</td>
                            </tr>
                            <tr id="longReportRAUkrTel">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report RA UkrTel</td>
                            </tr>
                            <tr id="longReportRAVega">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report RA Vega</td>
                            </tr>
                            <tr id="longReportVega">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report Vega</td>
                            </tr>
                            <tr id="shortReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report</td>
                            </tr>
                            <tr id="shortReportRE">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report RE</td>
                            </tr>
                            <tr id="shortReportREUkrTel">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report RE UkrTel</td>
                            </tr>
                            <tr id="shortReportREVega">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report RE Vega</td>
                            </tr>
                            <tr id="shortReportVega">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report Vega</td>
                            </tr>
                            <tr id="localCallsCostReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Local Calls Cost Report</td>
                            </tr>
                            <tr id="localCallsDetailReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Local Calls Detail Report</td>
                            </tr>
                            <tr id="localCallsMainReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Local Calls Main Report</td>
                            </tr>

                        </table>
                        <button id="selectAllBtn" type="button" class="btn btn-info float-lt">Select all</button>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Decline</button>
                            <button type="button" class="btn btn-success" data-dismiss="modal" id="generateReport">
                                Generate
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <%--Form for uploading files--%>
    <form:form commandName="uploadFile" id="upload" method="post" enctype="multipart/form-data" class="form">
        <div class="form-group" id="idForm">
      <span class="file-input btn btn-info btn-file">
        Browse file to upload <input type="file" id="exampleInputFile" name="file"/>
      </span>
            <ul id="list" class="list-group"></ul>
        </div>
        <button type="button" value="upload" id="uploadFile" class="btn btn-toolbar" href="#myModal"
                data-toggle="modal">Upload CSV file
        </button>

        <%--Modal--%>
        <div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

                <%--Spinner(Loader) body--%>
            <div id="spinner" class="modal-dialog" style="display:none;">
                <div class="modal-content" style="width: 298px !important;">
                    <div class="modal-body">
                        <img id="img-responsive" src="resources/images/loaderLine.gif" alt="Loading"/>
                    </div>
                </div>
            </div>

        </div>

    </form:form>
        &nbsp;&nbsp;
    <div class="row">
        <div id="fileTree" class="col-md-4"></div>
    </div>


</div>
</body>
</html>
