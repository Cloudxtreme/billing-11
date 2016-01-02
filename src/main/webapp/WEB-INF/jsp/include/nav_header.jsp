<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
            <a class="navbar-brand" href="${pageContext.request.contextPath}">ELS Telecom</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li id="linkToAccounts"><a href="${pageContext.request.contextPath}/accounts/accountHome.html">Accounts<span class="sr-only">(current)</span></a></li>
                <li id="linkToUserOnline"><a href="${pageContext.request.contextPath}/statistic/statonline.html">User Online</a></li>
                <li  id="linkToCatalogs" class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Catalogs <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li id="linkToServiceCatalog"><a href="${pageContext.request.contextPath}/serviceType/catalog/">Service Catalog</a></li>
                        <li id="linkToTransactionCatalog"><a href="${pageContext.request.contextPath}/transaction/0/catalog/">Transaction Catalog</a></li>
                        <li id="linkToExternalPayments"><a href="${pageContext.request.contextPath}/extpayments/listlast/">External Payments</a></li>
                    </ul>
                </li>
                <li id="linkToCallsList"><a href="${pageContext.request.contextPath}/callshome">Calls List</a></li>
                <li class="dropdown" id="linkToUtils">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li id="linkToDeviceList"><a href="${pageContext.request.contextPath}/device.html">Devices List</a></li>

                        <li class="dropdown-submenu" id="linkToFile">
                            <a tabindex="-1" href="#">Files uploading</a>
                            <ul class="dropdown-menu">
                                <li id="linkToFileUploading"><a href="${pageContext.request.contextPath}/uploadfile.html">KDF file processing</a></li>
                                <li id="linkToCSVFileUploading"><a href="${pageContext.request.contextPath}/uploadcsvfile.html">CSV file processing</a></li>
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
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search account">
                </div>
                <button type="submit" class="btn btn-default">Search</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Link</a></li>
                <li class="dropdown" id="linkToUserPanel">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> User Panel<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li id="linkToUserList"><a href="${pageContext.request.contextPath}/user_panel.html">Show User Panel</a></li>
                        <li id="linkToUserRole"><a href="${pageContext.request.contextPath}/user_role_list.html">User Role</a></li>
                        <li id="linkToActivity"><a href="${pageContext.request.contextPath}/activity_list.html">Activity</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
