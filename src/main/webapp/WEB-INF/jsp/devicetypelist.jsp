<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title>Device Type</title>


  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well">



  <div>

    <div  id="succesMessage" class="alert alert-success" style="display: none">
      <strong>Great. Nice job!</strong>
    </div>




    <table class="table table-striped" id ='table'>
      <th></th>
      <TH>Name</th>
      <th>Descpition</th>
      <th>Number of the ports</th>
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
        var obj = {
          id: $tr.attr('id'),
          desc: $(this).closest('.ddesc').text(),
          portsNumber: $(this).closest('.dport').text(),
          devType: $(this).closest('.dname').text()
        };
        $.ajax({
          dataType: 'json',
          url: "${pageContext.request.contextPath}/editdevicetype",
          data: obj,
          type: "POST",
          success: function (result) {
        }
      });

      });

    </script>
  </div>
</div>


</body>
</html>