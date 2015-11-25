<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Page not found. 404</title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
    <meta name="keywords"
          content="404 iphone web template, Andriod web template, Smartphone web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design"/>
    <spring:url value="/resources/css/404.css" var="PageNotFoundCss"/>
    <link href="${PageNotFoundCss}" rel="stylesheet"/>
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="wrap">
    <div class="content">
        <img src="${pageContext.request.contextPath}/resources/images/error-img.png" title="error"/>

        <p><span><label>O</label>hh.....</span>You Requested the page that is no longer There.</p>
        <a class="btn btn-default btn-lg" onclick="window.history.back();">Return to previous page</a>

        <div class="copy-right">
            <p>Or connect to our support team<br> Mail.Phone</p>
        </div>
    </div>

</div>

</body>
</html>
