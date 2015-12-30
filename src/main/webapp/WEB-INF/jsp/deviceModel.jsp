<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><spring:message code="label.deviceList"/> </title>
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>


<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">


    <a type="button" class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/adddevice"><spring:message code="label.addNewDevice"/></a>

    <div>

        <div id="succesMessage" class="alert alert-success" style="display: none">
            <strong><spring:message code="label.success"/></strong>
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


        <table class="table table-striped" id='table'>
            <th></th>
            <th><spring:message code="label.name"/></th>
            <th><spring:message code="label.type"/></th>
            <th><spring:message code="label.description"/></th>
            <th><spring:message code="label.street"/></th>
            <th><spring:message code="label.building"/></th>
            <th><spring:message code="label.flat"/></th>
            <th><spring:message code="label.community"/></th>
            <th><spring:message code="label.ip"/></th>
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

        <%--TODO  move js to separate file--%>
        <script type="text/javascript">

            $('#table tr #deleting').click(function () {
                console.log(this);
                var $tr = $(this).closest('tr');
                var conf = confirm("<spring:message javaScriptEscape="true" code="label.sure" />");
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
                                alert('<spring:message javaScriptEscape="true" code="label.fail" />');
                            }
                        }
                    });
                } else {
                    alert('<spring:message javaScriptEscape="true" code="label.alertDecline" />');
                }
            });
            $(document).ready(function(){
                setTimeout(function(){
                    $('.alert').fadeOut(5000);
                })
            });
        </script>


    </div>
</div>


</body>
</html>