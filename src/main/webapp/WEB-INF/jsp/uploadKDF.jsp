<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title><spring:message code="label.kdf"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

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


    <%--Spinner(Loader) body--%>
    <div id="spinner" class="spinner" style="display:none;">
        <img id="img-spinner" src="resources/images/ajax-loader.gif" alt="Loading"/>
    </div>

    <%--Icons with action (Spans)--%>
    <legend>
        <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span> <spring:message code="label.selectKdf"/>
        &nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/uploadedfiles"
           style="text-decoration: none; color: rgba(88,124,173,0.54)"><span
                class="glyphicon glyphicon-folder-open"></span>&nbsp;<spring:message code="label.uploadedKDF"/></a>
    </legend>

    <%--Form for uploading files--%>
    <form:form commandName="uploadFile" id="upload" method="post" enctype="multipart/form-data" class="form">
        <div class="form-group" id="idForm">
      <span class="file-input btn btn-info btn-file">
        <spring:message code="label.browse"/> <input type="file" id="exampleInputFile" multiple>
      </span>
            <ul id="list" class="list-group"></ul>

        </div>
        <button type="button" value="upload" id="uploadFile" class="btn btn-toolbar"><spring:message code="label.upload"/></button>
    </form:form>
</div>

<script type="text/javascript">
    $(function () {
        $("li").removeClass('active');
        $("#linkToUtils").addClass('selected');
        $("#linkToFileUploading").addClass('active');
        $("#linkToFile").addClass('active');
    });

    var uniqFiles = [];
    $('input:file').on('change', function (evt) {
        var files = evt.target.files;
        for (var i = 0, f; f = files[i]; i++) {
            if ($.inArray(f, uniqFiles) == -1) {
                uniqFiles.push(f);
            }
            else {
                console.log('blalbla');
                $('#errorMessage').css('display', 'block');
                setTimeout(function () {
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

    $('html').on('click', '.glyphicon-remove', function () {
        var $li = $(this).closest('li');
        var ident = $li.attr('value');
        for (var i = 0, p; p = uniqFiles[i]; i++) {
            if (p.size != ident) {
            } else {
                uniqFiles.splice(i, 1);
            }
        }
        var conf = confirm("<spring:message javaScriptEscape="true" code="label.sure" />");
        if (!conf) {
            console.log('decline');
        } else {
            $li.fadeOut('slow', function () {
                $li.remove();
            });
        }
    });

    function isEmpty(myObject) {
        for (var key in myObject) {
            if (myObject.hasOwnProperty(key)) {
                return false;
            }
        }
        return true;
    }

    $('.btn-toolbar').on('click', function () {
        var reader = new FileReader();
        var data = new FormData();

        if (uniqFiles.length == 0) {
            $('#spinner').hide();
            document.getElementById('errorMessage').style.display = "block";
            $('#errorMessage').append('<strong><spring:message javaScriptEscape="true" code="label.selectFile"/></strong>');
            setTimeout(function () {
                $("#errorMessage").fadeOut(3000, function () {
                    $("#errorMessage strong").remove();
                });
            });
            return false;
        }
        for (var i = 0; i < uniqFiles.length; i++) {
            data.append(i, uniqFiles[i]);
        }


        $('#spinner').show();

        $.ajax({
            dataType: 'json',
            url: "${pageContext.request.contextPath}/uploadfile",
            data: data,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (result) {
                if (result == "SUCCESS") {
                    $('#spinner').hide();
                    document.getElementById('successMessage').style.display = "block";
                    $('#successMessage').append('<strong><spring:message javaScriptEscape="true" code="label.successFile" /></strong>');
                    uniqFiles = [];
                    setTimeout(function () {
                        $("#successMessage").fadeOut(2500, function () {
                            $("#successMessage strong").remove();
                            $("#list li").remove();
                        });
                    });
                    $('body').scrollTop(0);

                } else if (result == "INCORRECTTYPE") {
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong><spring:message javaScriptEscape="true" code="label.incorrectFile" /></strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(4000, function () {
                            $("#errorMessage strong").remove();
                        });
                    });


                } else if (result == "ERROR") {
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong><spring:message javaScriptEscape="true" code="label.failUpload" /></strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(2500, function () {
                            $("#errorMessage strong").remove();
                        });
                    });

                } else {
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong><spring:message javaScriptEscape="true" code="label.notAvail" /></strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(2500, function () {
                            $("#errorMessage strong").remove();
                        });
                    });

                }
            }
        });

    });
</script>

</body>
</html>



