<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Device</title>
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>


<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">


    <a type="button" class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/adddevice">Add New
        Device</a>

    <div>

        <div id="succesMessage" class="alert alert-success" style="display: none">
            <strong>Great. Nice job!</strong>
        </div>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">${successMessage}</div>
        </c:if>&nbsp;
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">${errorMessage}</div>
        </c:if>&nbsp;


        <table class="table table-striped" id='table'>
            <th></th>
            <TH>Name</th>
            <TH>Type</th>
            <th>Descpition</th>
            <th>Street</th>
            <th>Building</th>
            <th>Flat</th>
            <th>Community</th>
            <th>Ip-address</th>
            <c:forEach items="${list}" var="current">
                <label for="${current.id}">
                    <tr id="${current.id}">
                        <td>
                            <a href="${pageContext.request.contextPath}/device/${current.id}/update.html"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                            &nbsp;&nbsp;
                            <a id="deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                        </td>
                        <td>${current.name}</td>
                        <td>${current.devType.deviceType}</td>
                        <td>${current.description}</td>
                        <td>${current.deviceAddressForm.street}</td>
                        <td>${current.deviceAddressForm.building}</td>
                        <td>${current.deviceAddressForm.flat}</td>
                        <td>${current.community}</td>
                        <td>${current.ipForm.ipName}</td>
                    </tr>
                </label>
            </c:forEach>
        </table>

        <script type="text/javascript">

            $('#table tr #deleting').click(function () {
                console.log(this);
                var $tr = $(this).closest('tr');
                var conf = confirm("Are you sure?");
                if (conf == true) {
                    $.ajax({
                        type: "POST",
                        url: '${pageContext.request.contextPath}/device/delete.html',
                        data: $tr.attr('id'),
                        datatype: "JSON",
                        contentType: "application/json",
                        success: function (data) {
                            if (data == "SUCCESS") {
                                $tr.fadeOut('slow', function () {
                                    $tr.remove()
                                })
                                document.getElementById('succesMessage').style.display = "block";
                                setTimeout(function () {
                                    $("#succesMessage").fadeOut(2000);
                                });
                            } else {
                                alert("Delete is not finished");
                            }
                        }
                    });
                } else {
                    alert("Thats right decision");
                }
            });
        </script>


    </div>
</div>


</body>
</html>