$(document).ready(function() {
    hideServiceForm();
    if((typeof $('#getServiceType').data("parameter") !='undefined') )
        showServiceForm($('#getServiceType').data("parameter"));
 });

function hideServiceForm(){
    $('#internetService').hide();
    $('#phoneService').hide();
    $('#sharedForm').hide();
    $('#submitBtn').hide();
    $('#sharedServiceForm').hide();
}

function showServiceForm(type){
    $('#sharedForm').show();
    $('#submitBtn').show();
    $('#sharedServiceForm').show();
    type = type.toLowerCase();
    switch(type){
        case 'internet':{
            $('#internetService').show();
            $('#phoneService').hide();
            break;
        }
        case 'phone':{
            $('#internetService').hide();
            $('#phoneService').show();
            break;
        }
        case 'marker':{
            $('#internetService').hide();
            $('#phoneService').hide();
            break;
        }
    }
}