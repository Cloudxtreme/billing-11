<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title>Add new device</title>
  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>

</head>
<body>
  <jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


  <%--Modal window--%>
  <form:form commandName="deviceTypeModalForm" class="form" action="${pageContext.request.contextPath}/adddevicetype.html" >
      <fieldset>
          <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div class="modal-dialog modal-lg" role="document">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                          <h4 class="modal-title" id="myModalLabel">Add new device type</h4>
                      </div>
                      <div class="modal-body">

                          <form:input path="id"  type="hidden"/>
                          <div class="col-lg-9">
                              <form:input path="deviceType" class="form-control" id="deviceTypeForm" placeholder="Type the new device type name"/>
                          </div>


                      </div>
                      <div class="modal-footer">
                          <button type="button" class="btn btn-default" data-dismiss="modal">Decline</button>
                          <input type="submit" class="btn btn-primary" value="Add new device Type"/>
                      </div>
                  </div>
              </div>
          </div>
      </fieldset>
  </form:form>
  <%--Main logic--%>

  <div class="col-lg-6">
    <form:form class="form" id="addDeviceForm" method="POST" commandName="deviceForm" action="${pageContext.request.contextPath}/adddevice.html">
      <fieldset>
          <%--<c:if  test="${empty errorMessage}">
              <div class="form-group">
                  <label class="col-lg-8">Please fill in all fields below.</label>
              </div>
          </c:if>
          <c:if test="${not empty errorMessage}">
              <div class="form-group">
                  <label class="col-lg-8 text-warning">${errorMessage}</label>
              </div>
          </c:if>--%>

        <form:input path="id" id="id" type="hidden"/>
        <form:input path="status" id="status" type="hidden"/>

        <div class="form-group">
          <label for="name" class="col-lg-5 control-label">Device name #</label>
          <div class="col-lg-9">
            <form:input path="name" class="form-control" id="deviceName" placeholder="Device name"/>
          </div>
        </div>
        <div class="form-group">
          <label for="deviceTypes" class="col-lg-5 control-label">Device typeS</label>
          <div class="col-lg-9">
                  <form:select path="devType.id" class="form-control" id="deviceTypes">
                  <form:options items="${deviceTypesMap}" />
                  </form:select>
              <!-- Button trigger modal -->
              <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="modal" data-target="#myModal">
                  Add new Device Type
              </button>
          </div>

        </div>

            <div class="form-group">
              <label for="description" class="col-lg-5 control-label">Device description #</label>
              <div class="col-lg-9">
                <form:input path="description" class="form-control" id="deviceDescription" placeholder="Device description"/>
              </div>
            </div>

            <div class="form-group">
              <label for="community" class="col-lg-5 control-label">Device community #</label>
              <div class="col-lg-9">
                <form:input path="community" class="form-control" id="deviceCommunity" placeholder="Device community"/>
              </div>
            </div>

              <div class="form-group">
                      <label for="ip" class="col-lg-5 control-label">Ip address</label>
                  <div class="col-lg-9">
                      <form:select path="ipForm.id" class="form-control" id="ip">
                          <form:options items="${ipAddressList}" />
                      </form:select>
                      <input type="checkbox" onclick="toggle()" id="chkNet"/>
                      <label for="chkNet">Show Network</label>
                  </div>
              </div>



              <script type="text/javascript">
                  function toggle() {
                      if (document.getElementById('chkNet').checked){
                          document.getElementById('ipNetDiv').style.visibility = 'visible'
                      } else {
                          document.getElementById('ipNetDiv').style.visibility = 'hidden'
                      }

                  }

              </script>
              <div class="form-group" style="visibility: hidden" id="ipNetDiv">
                  <label for="ipNet" class="col-lg-5 control-label">IpNet</label>
                  <div class="col-lg-9">
                      <form:select path="id" class="form-control" id="ipNet">
                          <form:options items="${ipNetList}" />
                      </form:select>
                  </div>
              </div>


            <div class="form-group">
              <div class="col-lg-10 col-lg-offset-2">
                  <button type="reset" class="btn btn-default" onclick="redirectToDevicePage();">Cancel</button>
                  <script type="text/javascript">
                      function redirectToDevicePage(){
                          window.location="${pageContext.request.contextPath}/device.html";
                      }
                  </script>
                  <button type="submit" class="btn btn-primary">Submit</button>
              </div>
            </div>


        </fieldset>
    </form:form>
  </div>



</body>
</html>
