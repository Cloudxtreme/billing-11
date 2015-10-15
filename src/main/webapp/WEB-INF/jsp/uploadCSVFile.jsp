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

  <spring:url value="/resources/js/uploadCSVFile.js" var="csvScript"/>
  <script src="${csvScript}"></script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">

  <%--Error Message body--%>
  <div id="errorMessage" class="alert alert-warning" style="display: none">
  </div>

  <%--Success message body--%>
  <div id="successMessage" class="alert alert-success" style="display: none">
  </div>




  <legend>
    <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span> Select CSV file to upload
    &nbsp;&nbsp;
    <a id="reportsList" href="#reportModal" data-toggle="modal" style="text-decoration: none; color: rgba(88,124,173,0.54)"><span
            class="glyphicon glyphicon-th-large"></span> Generate report</a>
  </legend>
    <div class="modal" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="myReportModal" aria-hidden="true">
      <div class="modal-content">
        <div class="modal-body">
          <a class="undefaultStyleA" name="longReport"><span class="glyphicon glyphicon-save-file"></span>Long report</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="longReportRA"><span class="glyphicon glyphicon-save-file"></span>Long report RA</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="longReportRAUkrTel"><span class="glyphicon glyphicon-save-file"></span>Long report RA ukrtel</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="longReportRAVega"><span class="glyphicon glyphicon-save-file"></span>Long report RA vega</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="longReportVega"><span class="glyphicon glyphicon-save-file"></span>Long report vega</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="shortReportRE"><span class="glyphicon glyphicon-save-file"></span>Short report RE</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="shortReportREUkrTel"><span class="glyphicon glyphicon-save-file"></span>Short report RE ukrtel</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="shortReportREVega"><span class="glyphicon glyphicon-save-file"></span>Short report RE Vega</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="shortReportVega"><span class="glyphicon glyphicon-save-file"></span>Short report Vega</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="localCallsCostReport"><span class="glyphicon glyphicon-save-file"></span>Local calls cost report</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="localCallsDetailReport"><span class="glyphicon glyphicon-save-file"></span>Local calls detail report</a>
          &nbsp;&nbsp;
          <a class="undefaultStyleA" name="localCallsMainReport"><span class="glyphicon glyphicon-save-file"></span>Local calls main report</a>
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
    <button type="button" value="upload" id="uploadFile" class="btn btn-toolbar" href="#myModal" data-toggle="modal">Upload CSV file</button>

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
</div>
</body>
</html>
