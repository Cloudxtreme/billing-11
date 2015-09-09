$(function() {
    // set active navigation tab
    var s = window.location.href;
    if(s.indexOf("service") > -1) {
        $("#linkToServices").addClass('active');
    }
    if(s.indexOf("account") > -1) {
        $("#linkToAccounts").addClass('active');
    }
});

function confirmOperation(){
    if (confirm('Are you sure you want to save this thing into the database?')) {
        // Save it!
    } else {
        // Do nothing!
    }
}
