$(function() {
    // highlighting the selected menu
    var s = window.location.href;
    // --- For SERVICE ---
    if(s.indexOf("service") > -1) {
        $("#linkToServices").addClass('selected');
        if(s.indexOf("user") > -1)
            $("#linkToUserService").addClass('active');
        else
            $("#linkToServiceCatalog").addClass('active');
    }
    //  --- For ACCONT Page ---
    if(s.indexOf("accounts") > -1) {
        $("#linkToAccounts").addClass('selected');
    }
    // --- For USER PANEL ---
    if(s.indexOf("user") > -1) {
        $("#linkToUserPanel").addClass('selected');
        if(s.indexOf("role") > -1)
            $("#linkToUserRole").addClass('active');
        else
            $("#linkToUserList").addClass('active');
    }
    // --- For ACTIVITY Page ---
    if(s.indexOf("activity") > -1) {
        $("#linkToUserPanel").addClass('selected');
        $("#linkToActivity").addClass('active');
    }
    // --- For DEVICE Page ---
    if(s.indexOf("device") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');
    }
    // --- For DEVICE TYPE LISTPage ---
    if(s.indexOf("devicetypeslist") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');

    }
    // --- For File Uploading LISTPage ---
    if(s.indexOf("uploadfile") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToFileUploading").addClass('active');
    }
    // --- For Calls List LISTPage ---
    if(s.indexOf("callshome") > -1) {
        $("#linkToCallsList").addClass('selected');
    }
    // --- For Uploaded Files List LISTPage ---
    if(s.indexOf("uploadedfiles") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToFileUploading").addClass('active');
    }
    // --- For Add Devices LISTPage ---
    if(s.indexOf("adddevice") > -1) {
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');
    }

});

function confirmOperation(){
    if (confirm('Are you sure you want to save this thing into the database?')) {
        // Save it!
    } else {
        // Do nothing!
    }
}
