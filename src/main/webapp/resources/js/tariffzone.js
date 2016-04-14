$(function() {
    $("input[name='softblock']").bootstrapSwitch();
    $("input[name='zonelist']").bootstrapSwitch();
});

$(document).ready(function(){
    if(window.location.href.indexOf('fromdirection') > -1 ){
        var zoneId = getUrlParameter('id');
        $("#table tr").filter(function() {
            return $(this).attr('id') == zoneId;
        }).addClass("bg-danger");
        scrollToElement($('.bg-danger'));
    }

    if(window.location.href.indexOf('home') > -1 ){
        $('#getAll').bootstrapSwitch('state', false);
    }
    if(window.location.href.indexOf('all') > -1 ){
        $('#getAll').bootstrapSwitch('state', true);
    }

    $('#getAll').on('switchChange.bootstrapSwitch', function () {
        if ($(this).is(':checked')) {
            window.location.href = 'all';
        } else {
            window.location.href = 'home';
        }
    });
});

$(document).on("click", ".pushEdit", function () {
    console.log("pushEdit push");
    var tarZoneId = $(this).data('id');
    $('#tariffZoneModal').modal('show');
    $("#id").val(tarZoneId);

    $.ajax({
        url: 'edit?id='+tarZoneId,
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            $("#id").val(data.id);
            $("#zoneId").val(data.zoneId);
            $("#zoneName").val(data.zoneName);
            $("#additionalKode").val(data.additionalKode);
            $('#dollarPath').bootstrapSwitch('state', (data.dollar));
            $("#dollar").val(data.dollar);
            $("#tarif").val(data.tarif);
            $("#tarifPref").val(data.tarifPref);
            $('#prefProfile').val(data.prefProfile);
            $('#validFrom').val(parseDateTOStringWithFormat(data.validFrom));
            $('#validTo').val(parseDateTOStringWithFormat(data.validTo));
        },
        error : function(){
            console.log("error in ajax query edit");
        }
    });

});


$(document).ready(function () {

    var frm = $('#crtTariffZoneForm');

    $('#tariffZoneModal').on('hidden.bs.modal', function () {
        $(this).removeClass("invalidVal");
        $('#zoneNameWarn').hide();
        $(frm).each(function () {
            this.reset();
        });
    });

    $('#addZone').on('click', function () {
        $('#zoneName').removeClass("invalidVal");
        $('#zoneNameWarn').hide();
        $('#id').val(0);
    });

    $('#zoneName').keypress(function(){
        $(this).removeClass("invalidVal");
        $('#zoneNameWarn').hide();
    });

    $('#crtTariffZone').click(function (e) {

        var zoneNameVal = $('#zoneName').val();
        if (zoneNameVal.length < 1) {
            $('#zoneName').addClass("invalidVal");
            $('#zoneNameWarn').show();
            $("#tariffZoneModal").shake(3,7,800);
            return false;
        }

        if (navigator.userAgent.search("Chrome") == -1) {
            $('#validFrom').keypress(function () {
                $(this).removeClass("invalidVal");
                $('#dateWarn').hide();
            });

            $('#validTo').keypress(function () {
                $(this).removeClass("invalidVal");
                $('#dateWarn').hide();
            });

            var validTo = $('#validTo');
            var validFrom = $('#validFrom');
            if (!testDate(validFrom.val()) || !testDate(validTo.val())) {
                $('#dateWarn').show();
                if (!testDate(validFrom.val())) {
                    validFrom.addClass("invalidVal");
                }
                if (!testDate(validTo.val())) {
                    validTo.addClass("invalidVal");
                }
                return false;
            }

        }

        e.preventDefault();

        var id = $("#id").val();

        var action = frm.attr('action');
        if (id > 0) {
            action = 'edit';
        }

        var dollarState;
        if($('#dollarPath').is(":checked")){
            dollarState = 'true';
        }else{
            dollarState = 'false';
        }
        $('#dollar').val(dollarState);

        var formData = $(frm).serializeArray();
        var data = {};
        $(formData).each(function (index, obj) {
            data[obj.name] = obj.value;
        });

        var validFromFieldValue = $('#validFrom').val();
        var validFrom;
        if (validFromFieldValue.length > 0) {
            validFrom = new Date(validFromFieldValue);
        } else {
            validFrom = 0;
        }

        var validToFieldValue = $('#validTo').val();
        var validTo;
        if (validToFieldValue.length > 0) {
            validTo = new Date(validToFieldValue);
        } else {
            validTo = 0;
        }

        var jsonData = $(frm).serialize();

        var nameValuePairs = jsonData.split(/&/);
        var newSerializedString = "";

        for (var i = 0; i < nameValuePairs.length; i++) {
            var nameValuePair = nameValuePairs[i];
            var entry = nameValuePair.split(/=/);
            var key = entry[0];

            if (key == "validFrom") {
                entry = "validFrom=" + validFrom.valueOf();
            }
            else if (key == "validTo") {
                entry = "validTo=" + validTo.valueOf();
            } else {
                entry = key + "=" + entry[1];
            }
            newSerializedString += entry + "&";
        }
        newSerializedString = newSerializedString.replace(/&$/, "");

        $.ajax({
            type: frm.attr('method'),
            url: action,
            dataType: 'json',
            data: newSerializedString,
            success: function (callback) {
                if (id > 0) {
                    document.getElementById('successMessageEdit').style.display = 'block';
                    setTimeout(function () {
                        $("#successMessageEdit").fadeOut(2000);
                        if(window.location.href.indexOf('#modal') > -1 ){
                            window.location = "home";
                        }
                    }, 2000);
                } else {
                    document.getElementById('successMessageADD').style.display = 'block';
                    setTimeout(function () {
                        $("#successMessageADD").fadeOut(3000);
                    });
                }
                $("#tariffZoneModal").modal('hide');
            },
            error: function () {
                document.getElementById('errorMessage').style.display = 'block';
                setTimeout(function () {
                    $("#errorMessage").fadeOut(15000);
                });
            }
        });
    });
});



