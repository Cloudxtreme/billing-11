var pageResults = 25;
//Function which starts first
$(function () {
    // set active navigation tab "Calls"
    console.log("start js onLoad");
    $("li").removeClass('active');
    $.fn.bootstrapSwitch.defaults.onText = 'ACTUAL';
    $.fn.bootstrapSwitch.defaults.offText = 'GET ALL';
    $("input[name='zonelist']").bootstrapSwitch();
    getActualZonesList();
    renderDirectionsTable(pageResults, 1);
});

//Go to the previous page by click on the button BACK
function goToPrevPage() {
    var currentPageNum = $("#pageNumber").text();
    console.log("goPrev push with " + currentPageNum);
    var nextPage = Number(currentPageNum) - 1;
    if (nextPage != 0) {
        renderDirectionsTable(pageResults, nextPage);
    }
};

//Go to the next page by click to the button Forward
function goToNextPage() {
    var currentPageNum = $("#pageNumber").text();
    var totalPages = $("#totalPages").text();
    console.log("goNext push with " + currentPageNum);
    var nextPage = Number(currentPageNum) + 1;
    if (nextPage <= totalPages) {
        renderDirectionsTable(pageResults, nextPage);
    }
};

function setCurrentPageNumber(number) {
    $("#pageNumber").html(number);
};


function renderDirectionsTable(rows, page) {

    var searchValues = $('#searchPrefInput');
    if (isNaN(searchValues.val())) {
        $('#errorMessageNAN').show().fadeOut(15000);
        return false;
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        dataType: 'json',
        url: 'list?rows=' + rows + '&page=' + page + '&value=' + searchValues.val(),
        success: function (data, textStatus, jqXHR) {
            drawTable(data);
            setCurrentPageNumber(page);
            if (data.length < 1) {
                $('#nothingFound').show();
            }
            if ($('#nothingFound').attr("display", 'block') && data.length > 0) {
                $('#nothingFound').hide();
            }
        }
    });
    getPageCounts();
}

function drawTable(data) {
    $("#table").find("tr:gt(0)").remove();

    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
}

function drawRow(rowData) {
    var zoneName = [];
    var zoneId = [];
    for (var i = 0; i < rowData.tariffZoneList.length; i++) {
        zoneName.push(rowData.tariffZoneList[i].zoneName);
        zoneId.push(rowData.tariffZoneList[i].id);
    }

    var row = $("<tr id=" + rowData.id + "/>");
    $("#table").append(row);

    row.append($("<td>" +
        "<a href=\"\" class=\"pushEdit\" data-toggle=\"modal\" data-id=\"" + rowData.id + "\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></a>" +
        "&nbsp&nbsp " +
        "<a data-href=\"delete/" + rowData.id + "\" class=\"pushDelete\" data-toggle=\"modal\" data-id=\"" + rowData.id + "\" data-toggle='modal' data-target='#confirm-delete' \">" +
        "<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></a>" +
        "&nbsp&nbsp " +
        "<a id=\"info\" href=\"objectinfo/" + rowData.id + '?type=Direction' + "\"><span class=\"glyphicon glyphicon-info-sign\" aria-hidden=\"true\"></span></a></td>"
    ));

    row.append($("<td>" + rowData.description + "</td>"));
    row.append($("<td>" + rowData.prefix + "</td>"));
    if (typeof  zoneId[0] === "undefined") {
        row.append($("<td></td>"));
    } else {
        row.append($("<td><a href=../tariffzone/fromdirection?id=" + zoneId[0] + "\>" + zoneName[0] + "</a></td>"));
    }
    row.append($("<td>" + parseDateTOStringWithFormat(rowData.validFrom) + "</td>"));
    row.append($("<td>" + parseDateTOStringWithFormat(rowData.validTo) + "</td>"));
}

function getPageCounts() {
    pageResults = $("#selectEntries option:selected").val();
    $.ajax({
        url: 'count?pageResults=' + pageResults + '&value=' + $('#searchPrefInput').val(),
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#totalPages').text(data);
        }
    });
}

$(document).on("click", ".pushEdit", function () {
    var directionId = $(this).data('id');
    $('#directionCreateModal').modal('show');
    $("#id").val(directionId);

    $.ajax({
        url: 'edit?id=' + directionId,
        type: "get",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            $("#id").val(data.id);
            $("#description").val(data.description);
            $("#prefix").val(data.prefix);
            $("#additionalKode").val(data.additionalKode);
            $("#trunkgroup").val(data.trunkgroup);
            $('#validFrom').val(parseDateTOStringWithFormat(data.validFrom));
            $('#validTo').val(parseDateTOStringWithFormat(data.validTo));
            var opt = $('#tarriffZoneId option[value=' + data.tariffZoneList[0].id + ']');
            if(opt.length < 1){
                $('#getAll').bootstrapSwitch('state', true);
                getAllZonesList(function(){
                    opt = $('#tarriffZoneId option[value=' + data.tariffZoneList[0].id + ']');
                    opt.prop("selected", true);
                });
            }else {
                opt.prop("selected", true);
            }
        }
    });
});

function getActualZonesList(callback) {
    $.ajax({
        type: 'get',
        url: 'getActualZones',
        dataType: 'json',
        success: function (actualZonesList) {
            appendOptionsZonesList(actualZonesList);
            callback ? callback() : false;
        }
    })
}

function getAllZonesList(callback) {
    $.ajax({
        type: 'get',
        url: 'getAllZones',
        dataType: 'json',
        success: function (zoneList) {
            appendOptionsZonesList(zoneList);
            callback ? callback() : false;
        }
    })
}

function appendOptionsZonesList(zonesList) {
    $('#tarriffZoneId').find('option').remove().end();
    for (var i = 0; i < zonesList.length; i++) {
        $('#tarriffZoneId').append("<option value='" + zonesList[i].id + "'>" + zonesList[i].zoneName + '&nbsp;&nbsp;' + parseDateTOStringWithFormat(zonesList[i].validFrom) + "</option>");
    }
}

$(document).ready(function () {

    var frm = $('#crtDirectionForm');

    function clearWarn() {
        $('#prefix').removeClass("invalidVal");
        $('#description').removeClass("invalidVal");
        $('#prefixWarn').hide();
        $('#descriptionWarn').hide();
    }

    function clearForm() {
        $(frm).each(function () {
            this.reset();
        });
    }

    $('#directionCreateModal').on('hidden.bs.modal', function () {
        clearWarn();
        clearForm();
    });

    $('#selectEntries').on('change', function () {
        getPageCounts();
        renderDirectionsTable(pageResults, 1);
    });

    $('#addDirection').on('click', function () {
        $('#id').val(0);
        clearWarn();
    });
    $('#prefix').keypress(function () {
        $(this).removeClass("invalidVal");
        $('#prefixWarn').hide();
    });

    $('#description').keypress(function () {
        $(this).removeClass("invalidVal");
        $('#descriptionWarn').hide();
    });

    $('#crtDirection').click(function (e) {
        var prefixVal = $('#prefix').val();
        var descriptionVal = $('#description').val();
        if (prefixVal.length < 1 || descriptionVal.length < 1) {
            if (prefixVal.length < 1) {
                $('#prefix').addClass("invalidVal");
                $('#prefixWarn').show();
            }
            if (descriptionVal.length < 1) {
                $('#description').addClass("invalidVal");
                $('#descriptionWarn').show();
            }
            $("#directionCreateModal").shake(3, 7, 800);
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

        $.ajax({
            type: 'get',
            url: 'checkfree?id=' + id + '&prefix=' + $('#prefix').val() + '&validFrom=' + validFrom.valueOf(),
            dataType: 'json',
            success: function (data) {
                if (data == "BUSY") {
                    $('#busyModal').modal('show');
                    return false;
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
                                $("#successMessageEdit").fadeOut(3000);
                            });
                        } else {
                            document.getElementById('successMessageADD').style.display = 'block';
                            setTimeout(function () {
                                $("#successMessageADD").fadeOut(3000);
                            });
                        }
                        $("#directionCreateModal").modal('hide');
                        $(frm).each(function () {
                            this.reset();
                        });
                        renderDirectionsTable(pageResults, 1)
                    },
                    error: function () {
                        document.getElementById('errorMessage').style.display = 'block';
                        setTimeout(function () {
                            $("#errorMessage").fadeOut(15000);
                        });
                    }
                });
            }
        });
    });


    $('#searchPref').on('click', function () {
        renderDirectionsTable(pageResults, 1);
    });

    $("#searchPrefInput").keypress(function (e) {
        if (e.keyCode == 13) {
            renderDirectionsTable(pageResults, 1);
        }
    });

    $('#eraseField').on('click', function () {
        $('#searchPrefInput').val("");
        renderDirectionsTable(pageResults, 1);
    });

    $('#getAll').on('switchChange.bootstrapSwitch', function () {
        if ($(this).is(':checked')) {
            getAllZonesList();
        } else {
            getActualZonesList();
        }
    });

});
