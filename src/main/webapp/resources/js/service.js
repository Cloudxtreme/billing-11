$(document).ready(function () {
    hideServiceForm();
    if ((typeof $('#getServiceType').data("parameter") != 'undefined')) {
        showServiceForm($('#getServiceType').data("parameter"));
        $('#changeServiceType').show();
    }
});

$(document).ready(function () {
    callAjaxGetPortList();
    callAjaxGetValidIp();
    $('#ipAddress').hide();
    $('#ipAddressCurrent').show();
});

$(document).ready(function () {
    $('#serviceType input[type=radio]').change(function () {
        var type = $('#serviceType input[type=radio]:checked').val();
        ajaxBuildServiceTypeSelectList(type);
        showServiceForm(type);
    })
});

$(document).ready(function () {
    $('#changeIp').on('change', function () {
        if (document.getElementById('changeIp').checked) {
            $('#ipAddress').show();
            $('#ipAddressCurrent').hide();
        } else {
            $('#ipAddress').hide();
            $('#ipAddressCurrent').show();
            $('#ipAddressCurrent').html($('#ipAddress').find(":selected").text());
        }
    });
});

$(document).ready(function () {
    $('#device').on('change', function () {
        callAjaxGetPortList();
    });
});
$(document).ready(function () {
    $('#ipNet').on('change', function () {
        callAjaxGetValidIp();
    });
});
$(document).ready(function () {
    $('#changeServiceType').click(function (e) {
        if ($(this).text() == "Change") {
            disabledAllFields();
            allowChangeServiceType();
            $(this).text("Cancel");
            $(this).removeClass('btn-success').addClass('btn-primary');
        }
        else {
            allowAllFields();
            disableChangeServiceType();
            $(this).text("Change");
            $(this).removeClass('btn-primary').addClass('btn-success');
        }
        ajaxBuildServiceTypeSelectList($('#getServiceType').data("parameter"));
    });
});

function ajaxBuildServiceTypeSelectList(type) {
    var selectedService = $('#getServiceType').val();
    var pathArray = window.location.pathname.split( '/' );

    $.ajax({
        url: '../../../../serviceTypeList?type=' + type + '&accountid=' + pathArray[3] ,
        type: "get",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            $('#serviceTypeList').html('');
            $.each(data, function (key, value) {
                selected = (selectedService === key) ? 'selected' : '';
                $('#serviceTypeList').append('<option value="' + key + '" ' + selected + '>' + value + '</option>');
            });
        }
    });
}

function disabledAllFields() {
    $('#serviceForm input').attr('disabled', 'disabled');
    $('#serviceForm select').attr('disabled', 'disabled');
}

function allowAllFields() {
    $('#serviceForm input').removeAttr('disabled');
    $('#serviceForm select').removeAttr('disabled');
}

function allowChangeServiceType() {
    $('#serviceForm #serviceTypeList').removeAttr('disabled');
    $('#serviceForm #dateStart').removeAttr('disabled');
    $('#serviceForm #id').removeAttr('disabled');
    $('#serviceForm #accountId').removeAttr('disabled');
}

function disableChangeServiceType() {
    $('#serviceForm #serviceTypeList').attr('disabled', 'disabled');
}

function hideServiceForm() {
    $('#changeServiceType').hide();
    $('#internetService').hide();
    $('#phoneService').hide();
    $('#sharedForm').hide();
    $('#submitBtn').hide();
    $('#sharedServiceForm').hide();
}

function showServiceForm(type) {
    if (typeof type != undefined) {
        $('#sharedForm').show();
        $('#submitBtn').show();
        $('#sharedServiceForm').show();
        type = type.toLowerCase();
        switch (type) {
            case 'internet':
            {
                $('#internetService').show();
                $('#phoneService').hide();
                break;
            }
            case 'phone':
            {
                $('#internetService').hide();
                $('#phoneService').show();
                break;
            }
            case 'marker':
            {
                $('#internetService').hide();
                $('#phoneService').hide();
                break;
            }
        }
    }
}

function callAjaxGetPortList() {
    var idService = 0;
    var sendData = $('#device').val();
    if ($('#id').val() != "") {
        idService = $('#id').val();
    }
    $.ajax({
        type: "POST",
        url: "../../../../getDeviceFreePortList/" + idService,
        data: sendData,
        datatype: "json",
        contentType: "application/json",
        success: function (data) {
            $('#devicePorts').html('');
            $.each(data, function (key, value) {
                $('#devicePorts').append('<option value="' + value + '">' + value + '</option>');
            });
        }
    });
}
function callAjaxGetValidIp() {
    serviceId = 0;
    if ($('#id').val() != "") {
        serviceId = $('#id').val();
    }
    var currentIpAddress = callAjaxGetCurrentIpAddress(serviceId);
    $.ajax({
        type: "POST",
        url: "../../../../getIpAddressList/" + serviceId,
        data: $('#ipNet').val(),
        datatype: "JSON",
        contentType: "application/json",
        success: function (data) {
            $('#ipAddress').html('');
            $.each(data, function (key, value) {
                var selected = "";

                if (currentIpAddress == key) {
                    selected = "selected";
                }
                $('#ipAddress').append('<option value="' + key + '" ' + selected + '>' + value + '</option>');
            });
            $('#ipAddressCurrent').html($('#ipAddress').find(":selected").text());
        }
    });
}
function callAjaxGetCurrentIpAddress(serviceId) {
    var value = 0;
    $.ajax({
        url: '../../../../getCurrentIpAddress?serviceId=' + serviceId,
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            value = data;
        }
    });
    return value;
}
