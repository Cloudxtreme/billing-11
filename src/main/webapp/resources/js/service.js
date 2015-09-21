$(document).ready(function() {

    hideServiceForm();

    if($("#internetBtn").prop("checked")){
        showInternetService()
    }
    if($("#phoneBtn").prop("checked")){
        showPhoneService()
    }


    $('#internetBtn').click(function(e) {
        showInternetService()
    });
    $('#phoneBtn').click(function(e) {
        showPhoneService()
    });
});

function hideServiceForm(){
    $('#formServiceHead').hide();
    $('#formServiceBody').hide();
}
function showServiceForm(){
    $('#formServiceHead').show();
    $('#formServiceBody').show();
}
function hideAllServices(){
    $('#internetService').hide();
    $('#phoneService').hide();
};

function showInternetService(){
    showServiceForm();
    hideAllServices();
    $('#internetService').show();
};
function showPhoneService(){
    showServiceForm();
    hideAllServices();
    $('#phoneService').show();
};
