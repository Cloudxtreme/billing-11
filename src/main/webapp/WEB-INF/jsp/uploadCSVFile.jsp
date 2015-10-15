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
  </legend>

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
        <div class="modal-content">
          <div class="modal-body">
            <img id="img-responsive" src="resources/images/720.gif" alt="Loading"/>
          </div>
        </div>
      </div>

    </div>

  </form:form>
</div>
</body>
</html>
