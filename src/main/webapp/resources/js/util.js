$(function() {
    // highlighting the selected menu
    var s = window.location.href;
    // --- For SERVICE ---
    if(s.indexOf("service") > -1) {
        $("#linkToCatalogs").addClass('selected');
        if(s.indexOf("user") > -1)
            $("#linkToUserService").addClass('active');
        else
            $("#linkToServiceCatalog").addClass('active');
    }
    // --- For TRANSACTION ---
    else if(s.indexOf("transaction") > -1) {
        $("#linkToCatalogs").addClass('selected');
        $("#linkToTransactionCatalog").addClass('active');
    }

    else if(s.indexOf("saldoReport") > -1) {
        $("#linkToCatalogs").addClass('selected');
        $("#linkToSaldoReport").addClass('active');
    }

    //  --- For ACCONT Page ---
    else if(s.indexOf("accounts") > -1) {
        $("#linkToAccounts").addClass('selected');
    }
    // --- For USER PANEL ---
    else if(s.indexOf("user") > -1) {
        $("#linkToUserPanel").addClass('selected');
        if(s.indexOf("role") > -1)
            $("#linkToUserRole").addClass('active');
        else
            $("#linkToUserList").addClass('active');
    }
    // --- For ACTIVITY Page ---
    else if(s.indexOf("activity") > -1) {
        $("#linkToUserPanel").addClass('selected');
        $("#linkToActivity").addClass('active');
    }
    // --- For DEVICE Page ---
    else if(s.indexOf("device") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');
    }
    // --- For DEVICE TYPE LISTPage ---
    else if(s.indexOf("devicetypeslist") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');

    }
    // --- For File Uploading LISTPage ---
    else if(s.indexOf("uploadfile") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToFile").addClass('active');
    }
    // --- For CSV FILE Uploading LISTPage ---
    if(s.indexOf("uploadCSVFile") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToFile").addClass('active');
    }
    // --- For Calls List LISTPage ---
    else if(s.indexOf("callshome") > -1) {
        $("#linkToCallsList").addClass('selected');
    }
    // --- For Uploaded Files List LISTPage ---
    else if(s.indexOf("uploadedfiles") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToFileUploading").addClass('active');
    }
    // --- For Add Devices LISTPage ---
    else if(s.indexOf("adddevice") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');
    }
    //linkToUserOnline
    else if(s.indexOf("statonline") > -1) {
        $("#linkToUserOnline").addClass('selected');
    }
    else if(s.indexOf("extpayments") > -1){
        $("#linkToCatalogs").addClass('selected');
        $("#linkToExternalPayments").addClass('active');
    }
    else if(s.indexOf("statistic") > -1){
        $("#linkToAccounts").addClass('selected');
        $('#eng').attr('href',"?lang=en" + "&login=" +getUrlParameter('login'));
        $('#ru').attr('href', "?lang=ru" +"&login="+ getUrlParameter('login'));
    }else if(s.indexOf("extpayments/listlast/") > -1){
        $("#linkToCatalogs").addClass('selected');
        $("#linkToTransactionCatalog").addClass('active');
    }else if(s.indexOf("objectinfo") > -1){
        $('#eng').attr('href',"?lang=en" + "&type=" +getUrlParameter('type'));
        $('#ru').attr('href', "?lang=ru" +"&type="+ getUrlParameter('type'));
    }

});

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

function confirmOperation(){
    if (confirm('Are you sure you want to save this thing into the database?')) {
        // Save it!
    } else {
        // Do nothing!
    }
}
