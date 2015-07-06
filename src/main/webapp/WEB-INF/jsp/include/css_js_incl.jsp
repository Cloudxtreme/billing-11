<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%--css--%>
<spring:url value="/resources/css/bootstrap-minimalist.css" var="bootstrap" />
<%--<spring:url value="/resources/css/bootstrap-theme.css" var="bootstrap_minimalist" />--%>
<spring:url value="/resources/css/main-billing.css" var="mainCss" />

<%--javascript--%>
<spring:url value="/resources/js/jquery-1.11.3.js" var="jquery" />
<spring:url value="/resources/js/bootstrap.js" var="bootstrapJs" />

<script src="${jquery}"></script>
<script src="${bootstrapJs}"></script>
<link href="${bootstrap}" rel="stylesheet"/>
<%--<link href="${bootstrap_minimalist}" rel="stylesheet"/>--%>
<link href="${mainCss}" rel="stylesheet"/>

