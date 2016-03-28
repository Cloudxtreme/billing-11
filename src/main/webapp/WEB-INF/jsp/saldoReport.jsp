<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="label.accounts"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <spring:url value="/resources/js/saldo.js" var="saldo" />
    <script src="${accounts}"></script>

    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well">
        <div id="messagesDiv">
            <c:if test="${not empty successMessage}">
            <div class="alert alert-success fade in" role="alert">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${successMessage}</strong>
            </div>
            </c:if>&nbsp;
            <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger fade in" role="alert">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${errorMessage}</strong>
            </div>
            </c:if>
        </div>&nbsp;


    <div id="saldoTableDiv">
        <table border="1" id="saldoTable" class="table table-striped">
            <tr>

                <th></th>
                <th></th>
                <th align="center" colspan="2"><spring:message code="label.saldo.na.nachalo"/></th>
                <th align="center" colspan="9"><spring:message code="label.saldo.oborot"/></th>
                <th colspan="5"><spring:message code="label.saldo.oplata"/></th>
                <th colspan="2"><spring:message code="label.saldo.na.konets"/></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <th><spring:message code="label.saldo.lits.schet"/></th>
                <th><spring:message code="label.saldo.fio"/></th>
                <th><spring:message code="label.saldo.debet"/></th>
                <th><spring:message code="label.saldo.kredit"/></th>
                <th><spring:message code="label.saldo.yst"/></th>
                <th><spring:message code="label.saldo.abonpl"/></th>
                <th><spring:message code="label.saldo.mg"/></th>
                <th><spring:message code="label.saldo.internet"/></th>
                <th><spring:message code="label.saldo.kod"/></th>
                <th><spring:message code="label.saldo.ton"/></th>
                <th><spring:message code="label.saldo.spravka"/></th>
                <th><spring:message code="label.saldo.yvv"/></th>
                <th><spring:message code="label.saldo.itogo"/></th>
                <th><spring:message code="label.saldo.pivd"/></th>
                <th><spring:message code="label.saldo.ysb"/></th>
                <th><spring:message code="label.saldo.kassa"/></th>
                <th><spring:message code="label.saldo.24"/></th>
                <th><spring:message code="label.saldo.itogo"/></th>
                <th><spring:message code="label.saldo.debet"/></th>
                <th><spring:message code="label.saldo.kredit"/></th>
                <th><spring:message code="label.saldo.avans.proshl.per"/></th>
                <th><spring:message code="label.saldo.avans.tek.per"/></th>
                <th><spring:message code="label.saldo.with.nds"/></th>
            </tr>

            <c:forEach items="${report.saldoForms}" var="accountSaldo">
                <tr>
                    <td>${accountSaldo.accountName}</td>
                    <td>${accountSaldo.fio}</td>
                    <td>${accountSaldo.startDebet}</td>
                    <td>${accountSaldo.startKredit}</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>${accountSaldo.debet}</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>${accountSaldo.summAllNachisl}</td>
                    <td>${accountSaldo.kreditPivd}</td>
                    <td>${accountSaldo.kreditYsb}</td>
                    <td>${accountSaldo.kreditKassa}</td>
                    <td>${accountSaldo.kredit24}</td>
                    <td>${accountSaldo.sumOplat}</td>
                    <td>${accountSaldo.finishDebet}</td>
                    <td>${accountSaldo.finishKredit}</td>
                    <td>${accountSaldo.avansPrev}</td>
                    <td>${accountSaldo.avans}</td>
                    <td>${accountSaldo.totalNds}</td>
                </tr>
            </c:forEach>

            <tfoot>
            <tr>
                <th>#</th>
                <th>Summary</th>
                <th>${report.totalStartDebet}</th>
                <th>${report.totalStartKredit}</th>
                <th></th>
                <th></th>
                <th></th>
                <th>${report.totalDebet}</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th>${report.totalSumNachisl}</th>
                <th>${report.totalKreditPidv}</th>
                <th>${report.totalKreditYsb}</th>
                <th>${report.totalKreditKassa}</th>
                <th>${report.totalKredit24}</th>
                <th>${report.totalSumOplaty}</th>
                <th>${report.totalFinishDebet}</th>
                <th>${report.totalFinishKredit}</th>
                <th>${report.totalAvansPrev}</th>
                <th>${report.totalAvans}</th>
                <th>${report.totalNds}</th>
            </tr>
            </tfoot>

        </table>
    </div>

</div>
</body>
</html>