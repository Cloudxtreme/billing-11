var pageResults = 25;

//Function which starts first
$(function () {
    // set active navigation tab "Calls"
    console.log("start js onLoad");
    $("li").removeClass('active');
    renderCallsTable(pageResults, 1);
});

//Go to the previous page by click on the button BACK
function goToPrevPage() {
    var currentPageNum = $("#pageNumber").text();
    console.log("goPrev push with " + currentPageNum);
    var nextPage = Number(currentPageNum) - 1;
    if (nextPage != 0) {
        renderCallsTable(pageResults, nextPage);
    }
};

//Go to the next page by click to the button Forward
function goToNextPage() {
    var currentPageNum = $("#pageNumber").text();
    var totalPages = $("#totalPages").text();
    console.log("goNext push with " + currentPageNum);
    var nextPage = Number(currentPageNum) + 1;
    if (nextPage <= totalPages) {
        renderCallsTable(pageResults, nextPage);
    }
};

//Setting the current page number, uses by renderCallsTable function
function setCurrentPageNumber(number) {
    $("#pageNumber").html(number);
};

function renderCallsTable(rows, page) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        dataType: 'json',
        url: 'list?rows=' + rows + '&page=' + page,
        success: function (data, textStatus, jqXHR) {
            drawTable(data);
            setCurrentPageNumber(page);
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
    var tarif = [];
    var tarifPref = [];
    for (var i = 0; i < rowData.tariffZoneList.length; i++) {
        zoneName.push(rowData.tariffZoneList[i].zoneName);
        tarif.push(rowData.tariffZoneList[i].tarif);
        tarifPref.push(rowData.tariffZoneList[i].tarifPref);
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
    row.append($("<td>" + rowData.additionalKode + "</td>"));
    row.append($("<td>" + rowData.trunkgroup + "</td>"));
    row.append($("<td>" + zoneName[0] + "</td>"));
    row.append($("<td>" + tarif[0] + "</td>"));
    row.append($("<td>" + tarifPref[0] + "</td>"));
}

function getPageCounts() {
    pageResults = $("#selectEntries option:selected").val();
    $.ajax({
        url: 'count?pageResults=' + pageResults,
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#totalPages').text(data);
        }
    });
}

$(document).on("click", ".pushEdit", function () {
    console.log("pushEdit push");
    var directionId = $(this).data('id');
    $('#directionCreateModal').modal('show');
    $("#id").val(directionId);

    $.ajax({
        url: 'edit?id='+directionId,
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            console.log(data);
            $("#id").val(data.id);
            $("#description").val(data.description);
            $("#prefix").val(data.prefix);
            $("#additionalKode").val(data.additionalKode);
            $("#trunkgroup").val(data.trunkgroup);
            $('#tarriffZoneId option[value=' + data.tariffZoneList[0].id + ']').prop("selected", "selected");
        },
        error : function(){
            console.log("error in ajax query edit");
        }
    });

});

$(document).ready(function () {

    $('#selectEntries').on('change', function () {
        getPageCounts();
        renderCallsTable(pageResults, 1);
    });

    $('#addDirection').on('click', function () {
        $('#prefix').removeClass("invalidVal");
        $('#description').removeClass("invalidVal");
        $('#prefixWarn').hide();
        $('#descriptionWarn').hide();
    });
    $('#prefix').keypress(function(){
        $(this).removeClass("invalidVal");
        $('#prefixWarn').hide();
    });

    $('#description').keypress(function(){
        $(this).removeClass("invalidVal");
        $('#descriptionWarn').hide();
    });

    $('#crtDirection').click(function (e) {
        console.log("button pushed");
        var frm = $('#crtDirectionForm');

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
            $("#directionCreateModal").shake(3,7,800);
            return false;
        }

        e.preventDefault();

        var id = $("#id").val();

        var action = frm.attr('action');
        if (id > 0) {
            action = 'edit';
        }

        var formData = $(frm).serializeArray();
        var data = {};
        $(formData).each(function (index, obj) {
            data[obj.name] = obj.value;
        });

        var currentPageNum = $("#pageNumber").text();
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