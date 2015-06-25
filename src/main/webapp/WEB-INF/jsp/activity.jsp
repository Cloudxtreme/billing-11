<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Add Activity</title>

    <%--css--%>
    <spring:url value="/resources/css/bootstrap.css" var="bootstrap" />
    <spring:url value="/resources/css/bootstrap-theme.css" var="bootstrap_minimalist" />
    <spring:url value="/resources/css/main-billing.css" var="mainCss" />

    <%--javascript--%>
    <spring:url value="/resources/js/jquery-1.11.3.js" var="jquery" />
    <spring:url value="/resources/js/bootstrap.js" var="bootstrapJs" />


    <link href="${bootstrap}" rel="stylesheet"/>
    <link href="${bootstrap_minimalist}" rel="stylesheet"/>
    <link href="${mainCss}" rel="stylesheet"/>
    <script src="${jquery}"></script>
    <script src="${bootstrapJs}"></script>

</head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">ELS Telecom</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
                <li><a href="#">Link</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/12">Action</a></li>
                        <li><a href="/32">Another action</a></li>
                        <li><a href="/44">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/55">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="tt">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search account">
                </div>
                <button type="submit" class="btn btn-default">Search</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Link</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown $1 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/link1">Action</a></li>
                        <li><a href="/link2">Another action</a></li>
                        <li><a href="link21">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/links">Separated link</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


<h1>User Control Page</h1>

<div class="col-lg-6 Absolute-Center Center-Container">
    <form:form class="form-horizontal" method="POST" commandName="activityForm" action="${pageContext.request.contextPath}/activity.html">
        <fieldset>
            <legend>Please, add new User Activity</legend>
            <div class="form-group">
                <label for="activityName" class="col-lg-2 control-label">Activity Name</label>
                <div class="col-lg-10">
                    <form:input path="name" class="form-control" id="activityName" placeholder="Name"/>
                </div>
            </div>
            <div class="form-group">
                <label for="activityDescription" class="col-lg-2 control-label">Activity Description</label>
                <div class="col-lg-10">
                    <form:input path="description" class="form-control" id="activityDescription" placeholder="Description"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <button type="reset" class="btn btn-default">Cancel</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>




</body>
</html>