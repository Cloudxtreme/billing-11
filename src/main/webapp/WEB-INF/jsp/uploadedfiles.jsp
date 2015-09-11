<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title>Files in folder</title>

  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<body>

<div class="well">

    <div  id="succesMessage" class="alert alert-success" style="display: none">
        <strong>Great. Nice job!</strong>
    </div>

    <a type="button" id="handleBtn" class="btn btn-lg btn-primary" href="">Handle selected files</a>
    <table class="table table-striped" id ='table'>
        <th></th>
        <th>File name</th>
        <th>File status</th>
        <th></th>
        <c:forEach items="${uploadedList}" var="current">
            <tr id="${current.path}" class="">
                <td>
                    <div class=".col-xs-6 .col-sm-3">
                    <input type="checkbox" name="optradio" class="check-box-table-cell">
                    </div>
                </td>
                <td>${current.name}</td>
                <td>${current.fileStatus}</td>
                <td><a id="deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
            </tr>
        </c:forEach>

    </table>
</div>

<script type="text/javascript">

    $('.check-box-table-cell').click(function() {
        var checked = $(this).attr('checked');
        if(checked){
            $(this).attr('checked', false);
            $(this).closest("tr").removeClass("info");

        }
        else{
            $(this).attr('checked', true);
            $(this).closest("tr").addClass("info")
        }
    });

    $('#handleBtn').click(function(){

        var values = new Array();
        $.each($(".check-box-table-cell:checked").closest("tr"),
                function(){
                   values.push($(this).attr('id'));
                });
        console.log(values);
        $.ajax({
            type:"post",
            url: '${pageContext.request.contextPath}/uploadedfiles/handle',
            data: JSON.stringify(values),
            datatype: "JSON",
            contentType: "application/json",
            success: function (data) {}
        })

    });

    $('#table tr #deleting').click(function () {
        console.log(this);
        var $tr = $(this).closest('tr');
        var conf = confirm("Are you sure?");
        if (conf == true){
            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/uploadedfiles/delete.html',
                data: $tr.attr('id'),
                datatype: "JSON",
                contentType: "application/json",
                success: function (data) {
                    if(data == "success") {
                        $tr.fadeOut('slow',function(){
                            $tr.remove()
                        });
                        document.getElementById('succesMessage').style.display="block";
                        setTimeout(function() {
                            $("#succesMessage").fadeOut(2000);
                        });
                    } else{
                        alert("Delete is not finished");
                    }
                }
            });
        } else {
            alert("Thats right decision");
        }
    });


</script>


</body>
</html>
