<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title><spring:message code="label.deviceType"/></title>

  <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well">



  <div>

    <div  id="succesMessage" class="alert alert-success col-md-8" style="display: none">
      <strong><spring:message code="label.success"/></strong>
    </div>




    <table class="table table-striped" id ='table'>
      <th></th>
      <TH><spring:message code="label.name"/></th>
      <th><spring:message code="label.description"/></th>
      <th><spring:message code="label.ports"/></th>
      <c:forEach items="${devicetypelist}" var="current">
        <label for="${current.id}">
          <tr id="${current.id}">
            <td id="updating">
              <a id="updatingBtn"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
            </td>
            <td class="dname">${current.deviceType}</td>
            <td class="ddesc">${current.description}</td>
            <td class="dport">${current.portsNumber}</td>
          </tr>
        </label>
      </c:forEach>
    </table>


    <script type="text/javascript">

      $(document).ready(function() {
        $("td.dport").keydown(function(event) {

          if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 ||
                  (event.keyCode == 65 && event.ctrlKey === true) ||
                  (event.keyCode >= 35 && event.keyCode <= 39)) {
            return;
          }
          else {
            if ((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
              event.preventDefault();
            }
          }
        });
      });




      $('html').on('click', '.glyphicon-edit', function () {
        var $tr = $(this).closest('tr');
        $tr.children('td:not(#updating)').attr('contenteditable','true').css('background-color','#d9edf7');
        $(this).closest('#updatingBtn').remove();
        $tr.children('#updating').append('<a><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></a>&nbsp;&nbsp;');
        $tr.children('#updating').append('<a><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>');
      });

      $('html').on('click', '.glyphicon-remove', function () {
        var $tr = $(this).closest('tr');
        $tr.children('td:not(#updating)').attr('contenteditable','false').css('background-color','#fff');
        $tr.children('#updating').empty();
        $tr.children('#updating').append('<a id="updatingBtn"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>');
      });

      $('html').on('click', '.glyphicon-ok', function () {
        var $tr = $(this).closest('tr');
        var deviceTypesForm = {
          id: $tr.attr('id'),
          description: $tr.find('td:nth-child(3)').text(),
          portsNumber: $tr.find('td:nth-child(4)').text(),
          deviceType: $tr.find('td:nth-child(2)').text()
        };
        $.ajax({
          dataType: 'json',
          url: "${pageContext.request.contextPath}/editdevicetype",
          data: JSON.stringify(deviceTypesForm),
          type: "POST",
          contentType: "application/json",
          success: function (data) {
            if(data == 'SUCCESS') {
              document.getElementById('succesMessage').style.display="block";
              setTimeout(function() {
                $("#succesMessage").fadeOut(3000);
              });
            } else{
              alert("<spring:message javaScriptEscape="true" code="label.incorrectDataType" />");
            }
        }
      });
        $('body').scrollTop(0);
        $tr.children('td:not(#updating)').attr('contenteditable','false').css('background-color','#fff');
        $tr.children('#updating').empty();
        $tr.children('#updating').append('<a id="updatingBtn"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>');

      });
      $(function() {
        $("li").removeClass('active');
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');
      });


    </script>
  </div>
</div>


</body>
</html>