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
    if(s.indexOf("account") > -1) {
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

});

function confirmOperation(){
    if (confirm('Are you sure you want to save this thing into the database?')) {
        // Save it!
    } else {
        // Do nothing!
    }
}
