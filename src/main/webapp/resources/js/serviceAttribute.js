$(document).ready(function() {
    showServiceAttributeFormIfNessesery($('#serviceType input[type=radio]:checked').val());
});

$(function () {
    $('#serviceType input[type=radio]').change(function(){
        showServiceAttributeFormIfNessesery($(this).val());
    })
})

function showServiceAttributeFormIfNessesery(serviceType){
    $('#serviceInternetAttribute').hide();
    if(typeof serviceType != undefined) {
        if ((serviceType.toUpperCase() == 'INTERNET') && ($('#id').val())) {
            $('#serviceInternetAttribute').show();
        }
    }
}