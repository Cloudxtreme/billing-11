function clearWarn() {
    $('#profileId').removeClass("invalidVal");
    $('#profileIdWarn').hide();
    $('#rulePriority').removeClass("invalidVal");
    $('#rulePriorityWarn').hide();
}

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

$(document).on("click", ".pushEdit", function () {
    var ruleId = $(this).data('id');
    $('#ruleModal').modal('show');
    $("#id").val(ruleId);
    $.ajax({
        url: 'edit?id=' + ruleId,
        type: "get",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            $("#id").val(data.id);
            $("#profileId").val(data.profileId);
            $("#rulePriority").val(data.rulePriority);
            $("#dayOfMonth").val(data.dayOfMonth);
            $('#month').val(data.month);
            $("#dayOfWeek").val(data.dayOfWeek);
            $("#starttime").val(data.starttime);
            $("#finishtime").val(data.finishtime);
            $("#tarif").val(data.tarif);
        },
        error: function () {
            console.log("error in ajax query edit");
        }
    });

});

$(document).ready(function () {
    var frm = $('#crtRuleForm');

    if (window.location.href.indexOf('preferencerule/list') > -1) {
        var profileIdFromZones = getUrlParameter('prefProfileId');
        $("#table tr td:nth-child(2)").filter(function() {
            return $.text([this]) == profileIdFromZones;
        }).parent('tr').addClass("bg-danger");
    }

    function clearForm() {
        $(frm).each(function () {
            this.reset();
        });
    }

    $('#ruleModal').on('hidden.bs.modal', function () {
        clearWarn();
        clearForm();
    });

    $('#rulePriority').on('change', function () {
        $(this).removeClass("invalidVal");
        $('#rulePriorityWarn').hide();
    });
    $('#rulePriority').keypress(function () {
        $(this).removeClass("invalidVal");
        $('#rulePriorityWarn').hide();
    });
    $('#profileId').on('change', function () {
        $(this).removeClass("invalidVal");
        $('#profileIdWarn').hide();
    });
    $('#profileId').keypress(function () {
        $(this).removeClass("invalidVal");
        $('#profileIdWarn').hide();
    });

    $('#crtRule').click(function (e) {
        var rulePriority = $('#rulePriority').val();
        var profileId = $('#profileId').val();

        if ((profileId.length < 1 || profileId === "0" ) || (rulePriority.length < 1 || rulePriority === "0")) {
            if (profileId.length < 1 || profileId === "0") {
                $('#profileId').addClass("invalidVal");
                $('#profileIdWarn').show();
            }
            if (rulePriority.length < 1 || rulePriority === "0") {
                $('#rulePriority').addClass("invalidVal");
                $('#rulePriorityWarn').show();
            }
            $("#ruleModal").shake(3, 7, 800);
            return false;
        }

        e.preventDefault();
        var id = $("#id").val();
        var action = frm.attr('action');
        if (id > 0) {
            action = 'edit';
        }

        $.ajax({
            type: 'get',
            url: 'checkfree?id=' + id + '&profileId=' + profileId + '&rulePriority=' + rulePriority,
            dataType: 'json',
            success: function (data) {
                if (data == "BUSY") {
                    $('#busyModal').modal('show');
                    return false;
                }
                var jsonData = $(frm).serialize();
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
                            });
                        } else {
                            document.getElementById('successMessageADD').style.display = 'block';
                            setTimeout(function () {
                                $("#successMessageADD").fadeOut(2000);
                            });
                        }
                        $("#ruleModal").modal('hide');
                        setTimeout(function () {
                            location.reload();
                        }, 2000)
                    },
                    error: function () {
                        $("#ruleModal").modal('hide');
                        document.getElementById('errorMessage').style.display = 'block';
                        setTimeout(function () {
                            $("#errorMessage").fadeOut(15000);
                        });
                    }
                });
            }
        });


    });
});

jQuery.fn.shake = function (intShakes, intDistance, intDuration) {
    this.each(function () {
        for (var x = 1; x <= intShakes; x++) {
            $(this).animate({left: (intDistance * -1)}, (((intDuration / intShakes) / 4)))
                .animate({left: intDistance}, ((intDuration / intShakes) / 2))
                .animate({left: 0}, (((intDuration / intShakes) / 4)));
        }
    });
    return this;
};