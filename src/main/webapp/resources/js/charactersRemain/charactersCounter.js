function updateCountdown() {
    // 140 is the max message length
    var remaining = 16 - jQuery('#username').val().length;
    jQuery('.countdown').text(remaining + ' characters remaining.');
}
jQuery(document).ready(function($) {
    updateCountdown();
    $('#username').change(updateCountdown);
    $('#username').keyup(updateCountdown);
});