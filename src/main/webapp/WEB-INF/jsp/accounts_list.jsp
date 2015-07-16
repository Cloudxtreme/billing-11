<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Accounts</title>

<jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
<spring:url value="/resources/js/accounts.js" var="accounts" />
<script src="${accounts}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>



<div class="well">
    <a href="#accAccountModal" class="btn btn-lg btn-primary" data-toggle="modal">Create New Account</a>

    <li><a href=""><i class=" icon-pencil icon-2x"></i>About Us</a></li>

    <div id="accAccountModal" class="modal fade">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Please fill required info to create new Account</h4>
            </div>

            <div class="modal-body">
                <div class="">
                    <form:form class="form" id="crtAccountForm" method="POST" commandName="accountForm" action="${pageContext.request.contextPath}/accounts/add.html">
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
                            <div class="form-group">
                                <label for="accountName" class="col-lg-5 control-label">Account #</label>
                                <div class="col-lg-9">
                                    <form:input path="accountName" class="form-control" id="accountName" placeholder="Account"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="accountType" class="col-lg-5 control-label">Account Type</label>
                                <div class="col-lg-9">
                                    <form:select path="accountType" class="form-control" id="accountType">
                                        <form:options items="${accountTypeList}" />
                                    </form:select>
                                </div>
                            </div>


                        </fieldset>
                    </form:form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" id="crtAccount" class="btn btn-primary">Create</button>
            </div>
        </div>
    </div>
</div>


<div id="accountsTableDiv">
    <table id="accountsTable" class="table table-striped">
        <tr>
            <th>Action</th>
            <th>Account Name</th>
            <th>Account Type</th>
            <th>Balance</th>
            <th>Status</th>
        </tr>
    </table>
</div>


</div>
<%--

<div class="container">
    <div class="well well-large">
        <div id="form-content" class="modal fade in hide" style="display: none;">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h3>Send me a message</h3>
            </div>
            <div class="modal-body">
                <form class="contact" name="contact">
                    <label class="label" for="name">Your Name</label><br>
                    <input type="text" name="name" class="input-xlarge"><br>
                    <label class="label" for="email">Your E-mail</label><br>
                    <input type="email" name="email" class="input-xlarge"><br>
                    <label class="label" for="message">Enter a Message</label><br>
                    <textarea name="message" class="input-xlarge"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <input class="btn btn-success" type="submit" value="Send!" id="submit">
                <a href="#" class="btn" data-dismiss="modal">Nah.</a>
            </div>
        </div>
        <div id="newAccountButton"><p><a data-toggle="modal" href="#form-content" role="button" class="btn btn-primary btn-large">New Account</a></p></div>

        <div id="listOfAccounts">
            <h1>Accounts list here!</h1>
        </div>

    </div>
</div>

--%>






</body>
</html>