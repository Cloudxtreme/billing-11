<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title>Device</title>


  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well">

  <div  id="errorMessage" class="alert alert-warning" style="display: none">
    <strong>This file have been add early</strong>
  </div>


  <form:form commandName="uploadFile" id="upload" method="post" enctype="multipart/form-data" class="form">
    <div class="form-group">
      <label for="exampleInputFile"><h3>Select file to upload</h3></label>
      <input type="file" id="exampleInputFile" multiple >
      <ul id="list" class="list-group"></ul>

      <p class="help-block">Example block-level help text here.</p>
    </div>
    <button type="submit" value="upload" class="btn btn-default">Upload File</button>
  </form:form>
</div>


<script type="text/javascript">

  var uniqFiles = [];
  $('input:file').on('change',function(evt){
    var files = evt.target.files;
    for( var i = 0,f; f = files[i]; i++ ) {
      if ($.inArray(f, uniqFiles) == -1) {
        uniqFiles.push(f);
      }
      else {
        document.getElementById('errorMessage').style.display="block";
        setTimeout(function() {
          $("#errorMessage").fadeOut(2000);
        });

      }
    }

    $('#list').html('');
    for( var h = 0, q; q = uniqFiles[h]; h++ ){
      var sub = (q.name).substring(0, 20);
      $('#list').append('<li class="list-group-item" value="' + q.size + '"'+ '<a id ="+deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>' +'><strong>' + sub + '...</strong> <b>File type:</b> ' + (q.type || 'n/a') + ' - ' +
              q.size + ' bytes, last modified: ' +
              (q.lastModifiedDate ? q.lastModifiedDate.toLocaleDateString() : 'n/a') +
              '</li>');
    }
  });



</script>


</body>
</html>
