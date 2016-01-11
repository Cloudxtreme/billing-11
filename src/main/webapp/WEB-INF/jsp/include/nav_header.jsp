<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

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
            <a class="navbar-brand" href="${pageContext.request.contextPath}/login.html"><spring:message code="label.els"/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li id="linkToAccounts"><a href="${pageContext.request.contextPath}/accounts/accountHome.html"><spring:message code="label.accounts"/><span class="sr-only">(current)</span></a></li>
                <li id="linkToUserOnline"><a href="${pageContext.request.contextPath}/statistic/statonline.html"><spring:message code="label.online"/></a></li>
                <li  id="linkToCatalogs" class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="label.catalogs"/> <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li id="linkToServiceCatalog"><a href="${pageContext.request.contextPath}/serviceType/catalog/"><spring:message code="label.serviceCatalog"/></a></li>
                        <li id="linkToTransactionCatalog"><a href="${pageContext.request.contextPath}/transaction/0/catalog/"><spring:message code="label.transactionCatalog"/></a></li>
                        <li id="linkToExternalPayments"><a href="${pageContext.request.contextPath}/extpayments/listlast/">External Payments</a></li>
                    </ul>
                </li>
                <li id="linkToCallsList"><a href="${pageContext.request.contextPath}/callshome"><spring:message code="label.callsList"/></a></li>
                <li class="dropdown" id="linkToUtils">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="label.dropdown"/><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li id="linkToDeviceList"><a href="${pageContext.request.contextPath}/device.html"><spring:message code="label.deviceList"/></a></li>

                        <li class="dropdown-submenu" id="linkToFile">
                            <a tabindex="-1" href="#"><spring:message code="label.filesUploading"/></a>
                            <ul class="dropdown-menu">
                                <li id="linkToFileUploading"><a href="${pageContext.request.contextPath}/uploadfile.html"><spring:message code="label.kdf"/></a></li>
                                <li id="linkToCSVFileUploading"><a href="${pageContext.request.contextPath}/uploadcsvfile.html"><spring:message code="label.csv"/></a></li>
                            </ul>
                        </li>

                        <li><a href="/44">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/55">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="tt">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
            <form action="${pageContext.request.contextPath}/accounts/accountsearch" method="Post" class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <spring:message code="label.accounts" var="acc"/>
                    <input id="searchInput" name="searchInput" type="text" class="form-control" placeholder="${acc}">
                </div>
                <button type="submit" id="searchButton" class="btn btn-default"><spring:message code="label.search"/></button>
            </form>
            <div class="col-md-1" style="padding-top: 10px !important;">
                <a href="?lang=en"><img class="flag flag-gb"/></a>
                <a href="?lang=ru"><img class="flag flag-ru"/></a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Link</a></li>
                <li class="dropdown" id="linkToUserPanel">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user" aria-hidden="true"></span><spring:message code="label.userPanel"/><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li id="linkToUserList"><a href="${pageContext.request.contextPath}/userpanel.html"><spring:message code="label.userPanel"/></a></li>
                        <li id="linkToUserRole"><a href="${pageContext.request.contextPath}/userrolelist.html"><spring:message code="label.userRole"/></a></li>
                        <li id="linkToActivity"><a href="${pageContext.request.contextPath}/activitylist.html"><spring:message code="label.activity"/></a></li>
                        <li><a href="${pageContext.request.contextPath}/logout"><spring:message code="label.logout"/></a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
