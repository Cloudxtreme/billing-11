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
    <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>
    <spring:url value="/resources/css/loader-style.css" var="loader" />
    <link href="${loader}" rel="stylesheet"/>
    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>

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
    <div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div id="spinner" class="modal-dialog" style="padding-top: 10%;">
            <div class="loader"></div>
        </div>
    </div>

    <%--Icons with action (Spans)--%>
    <legend>
        <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span> <spring:message code="label.selectKdf"/>
        &nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/uploadedfiles" style="text-decoration: none; color: rgba(88,124,173,0.54)" id="aFolder">
            <span class="glyphicon glyphicon-folder-close" id="folderIcon"></span>&nbsp;<spring:message code="label.uploadedKDF"/>
        </a>
    </legend>

    <%--Form for uploading files--%>
    <form:form commandName="uploadFile" id="upload" method="post" enctype="multipart/form-data" class="form">
        <div class="form-group" id="idForm">
            <span class="file-input btn btn-info btn-file">
                <spring:message code="label.browse"/> <input type="file" id="exampleInputFile" multiple>
            </span>
            <button type="button" value="upload" id="uploadFile" class="btn btn-toolbar" style="margin-left: 2%;"><spring:message code="label.upload"/></button>
            <ul id="list" class="list-group" style="margin-top: 2%;"></ul>
        </div>

    </form:form>

    <div id="totopscroller"></div>
</div>

<script type="text/javascript">
    $(function () {
        $("li").removeClass('active');
        $("#linkToUtils").addClass('selected');
        $("#linkToFileUploading").addClass('active');
        $("#linkToFile").addClass('active');
    });

    var uniqFiles = [];
    var filesSize = 0;
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
            filesSize += q.size;
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
                filesSize -= ident;
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

    var folder = $('#folderIcon');
    $("#folderIcon, #aFolder").hover(
            function () {
                $(folder).toggleClass('glyphicon-folder-close', false);
                $(folder).toggleClass('glyphicon-folder-open', true);
            },
            function () {
                $(folder).toggleClass('glyphicon-folder-close', true);
                $(folder).toggleClass('glyphicon-folder-open', false);
            }
    );

    $('.btn-toolbar').on('click', function () {
        var reader = new FileReader();
        var data = new FormData();
        var $errorMessage = $('#errorMessage');
        if (uniqFiles.length == 0 || filesSize > 50000000) {
            $('#spinner').hide();
            $errorMessage.show();
            window.location.hash = '#errorMessage';
            if(uniqFiles.length == 0 ) {
                $errorMessage.append('<strong><spring:message javaScriptEscape="true" code="label.selectFile"/></strong>');
            }else{
                $errorMessage.append('<strong><spring:message javaScriptEscape="true" code="label.maxSizeError"/></strong>');
            }
            setTimeout(function () {
                $errorMessage.fadeOut(15000, function () {
                    $("#errorMessage strong").remove();
                });
            });
            return false;
        }
        $('#myModal').modal('show');
        for (var i = 0; i < uniqFiles.length; i++) {
            data.append(i, uniqFiles[i]);
        }
        $.ajax({
            dataType: 'json',
            url: "${pageContext.request.contextPath}/uploadfile",
            data: data,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (result) {
                $('#myModal').modal('hide');
                if (result == "SUCCESS") {
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
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong><spring:message javaScriptEscape="true" code="label.incorrectFile" /></strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(4000, function () {
                            $("#errorMessage strong").remove();
                        });
                    });

                } else if (result == "ERROR") {
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong><spring:message javaScriptEscape="true" code="label.failUpload" /></strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(2500, function () {
                            $("#errorMessage strong").remove();
                        });
                    });

                } else {
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



