
$(function() {
    $("input[name='softblock']").bootstrapSwitch();

    $('.softBlock').on('switchChange.bootstrapSwitch', function() {
        $.ajax({
            url: 'changeSoftBlockStatus?tariffzoneid='+this.id,
            type: "get",
            dataType: "json"
        });
    });

});

$(document).ready(function(){
    if(window.location.href.indexOf('#modal') > -1 ){
        $('#tariffZoneModal').modal('show');
    }
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
        },
        error : function(){
            console.log("error in ajax query edit");
        }
    });

});

$(document).ready(function () {

    var frm = $('#crtTariffZoneForm');

    $('#dismissAction').on('click', function(){
        $(frm).each(function () {
            this.reset();
        });
    });

    $('#addZone').on('click', function () {
        $('#zoneName').removeClass("invalidVal");
        $('#zoneNameWarn').hide();
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

        var jsonData = $(frm).serialize();
        console.log(jsonData);

        $.ajax({
            type: frm.attr('method'),
            url: action,
            dataType: 'json',
            data: jsonData,
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
                $(frm).each(function () {
                    this.reset();
                });
                setTimeout(function () {
                    location.reload();
                }, 3000)
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

jQuery.fn.shake = function(intShakes, intDistance, intDuration) {
    this.each(function() {
        for (var x=1; x<=intShakes; x++) {
            $(this).animate({left:(intDistance*-1)}, (((intDuration/intShakes)/4)))
                .animate({left:intDistance}, ((intDuration/intShakes)/2))
                .animate({left:0}, (((intDuration/intShakes)/4)));
        }
    });
    return this;
};