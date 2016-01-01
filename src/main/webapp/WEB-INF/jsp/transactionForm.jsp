<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <title><spring:message code="label.transaction"/> </title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>

    <script type="text/javascript">
        $(function () {
            $('#datepicker').datepicker({
                format: 'yyyy-mm-dd'
            });
        });
    </script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="transactionForm" action="${pageContext.request.contextPath}/transaction/modifyForm?returnPage=${returnPage}">
        <fieldset>
            <legend><spring:message code="label.transaction"/></legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}"><spring:message code="label.fillField"/></label>
            </div>
            <div class="form-group" id="ipNetDiv">
                <label for="accountId" class="col-lg-3 control-label"><spring:message code="label.account"/></label>
                <div class="col-lg-9">
                    <form:select path="account.id" class="form-control" id="accountId">
                        <c:forEach items="${accountList}" var="account">
                            <form:option value="${account.id}" label="${account.accountName}"/>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <label for="direction" class="col-lg-3 control-label"><spring:message code="label.direction"/></label>
                <div class="col-lg-9">
                    <form:select path="direction" class="form-control" id="direction">
                        <form:options items="${transactionDirectionList}" />
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <label for="source" class="col-lg-3 control-label"><spring:message code="label.source"/></label>
                <div class="col-lg-9">
                    <form:select path="source" class="form-control" id="source">
                        <form:options items="${transactionSourceList}" />
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <label for="price" class="col-lg-3 control-label"><spring:message code="label.price"/></label>
                <div class="col-lg-9">
                    <form:input type="number" step="any" min="0" path="price" id="price" class="form-control" placeholder="00.00"/>
                    <form:errors path="price" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label for="comment" class="col-lg-3 control-label"><spring:message code="label.comment"/></label>
                <div class="col-lg-9">
                    <form:input path="comment" id="comment" class="form-control"/>
                </div>
            </div>

            <div class="form-group" id="submitBtn">
                <div class="col-lg-9 col-lg-offset-3">
                    <form:hidden path="id" />
                    <button type="submit" class="btn btn-primary"><spring:message code="label.submit"/></button>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>

</body>
</html>