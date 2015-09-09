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
  </div>

  <div  id="successMessage" class="alert alert-success" style="display: none">
  </div>


  <form:form commandName="uploadFile" id="upload" method="post" enctype="multipart/form-data" class="form">
    <div class="form-group" id="idForm">
      <label for="exampleInputFile"><h3>Select file to upload</h3></label>
      <input type="file" name="file" id="exampleInputFile" multiple >
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
    for (var h = 0, q; q = uniqFiles[h]; h++) {
      var sub = (q.name).substring(0, 20);
      $('#list').append('<li class="list-group-item" value="' + q.size + '"' + '><a class="deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a><strong> ' + sub + '...</strong> <b>File type:</b> ' + (q.type || 'n/a') + ' - ' +
              q.size + ' bytes, last modified: ' +
              (q.lastModifiedDate ? q.lastModifiedDate.toLocaleDateString() : 'n/a') +
              '</li>');
    }
  });

  $('html').on('click','.glyphicon' , function() {
    var $li = $(this).closest('li');
    var ident = $li.attr('value');
    for (var i = 0, p; p = uniqFiles[i]; i++){
      if (p.size != ident) {
      } else {
        uniqFiles.splice(i,1);
      }
    }
    var conf = confirm("Are you sure?");
    if (!conf) {
      console.log('decline');
    } else {
      $li.fadeOut('slow', function () {
        $li.remove();
      });
    }
  });


 /* $('.btn-default').on('click', function() {
    uploadFile(uniqFiles);
  });

 function uploadFile(file){
    var xhr = new XMLHttpRequest();
    xhr.upload.onprogress = function(event) {
      log(event.loaded + ' / ' + event.total);
    };


    xhr.onload = xhr.onerror = function() {
      if (this.status == 200) {
        log("success");
      } else {
        log("error " + this.status);
      }
    };


    xhr.open("POST", "${pageContext.request.contextPath}/uploadfile", true);
    xhr.send(file);
  };*/


/*
 $('.btn-default').on('click', function() {
    console.log(this);
    $.ajax({
      url: '${pageContext.request.contextPath}/uploadFile',
      success: function (data) {
        if (data == '1') {
          document.getElementById('succesMessage').style.display="block";
          $('#successMessage').append('<strong>Your file is succesfully uploaded to the server</strong>');
          setTimeout(function() {
            $("#succesMessage").fadeOut(2000);
          });
        } else if (data == '2'){
          document.getElementById('errorMessage').style.display="block";
          $('#errorMessage').append('<strong>File type is not IMAGE/PNG</strong>');
          setTimeout(function() {
            $("#errorMessage").fadeOut(2000);
          });
        } else if (data == '3'){
          document.getElementById('errorMessage').style.display="block";
          $('#errorMessage').append('<strong>You failed to upload file, please try again</strong>');
          setTimeout(function() {
            $("#errorMessage").fadeOut(2000);
          });
        } else {
          document.getElementById('errorMessage').style.display = "block";
          $('#errorMessage').append('<strong>Your file is empty</strong>');
          setTimeout(function() {
            $("#errorMessage").fadeOut(2000);
          });
        }


      }
    });
    return false;
  });
*/



</script>


</body>
</html>
