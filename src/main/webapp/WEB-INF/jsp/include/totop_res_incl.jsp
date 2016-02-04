<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%--JavaScripts--%>
<spring:url value="/resources/js/totop/totop.js" var="totop"/>
<script src="${totop}"></script>
<spring:url value="/resources/js/totop/connectToTop.js" var="totopConnect"/>
<script src="${totopConnect}"></script>

<%--StyleSheet--%>
<spring:url value="/resources/css/totopCSS/totop.css" var="totopCSS"/>
<link href="${totopCSS}" rel="stylesheet"/>
