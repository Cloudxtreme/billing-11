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
            url: 'directionlist?rows=' + rows + '&page=' + page,
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
    var row = $("<tr id="+ rowData.id +"/>");
    $("#table").append(row);
    row.append($("<td>" + rowData.description + "</td>"));
    row.append($("<td>" + rowData.prefix + "</td>"));
    row.append($("<td>" + rowData.tariffZone.zoneName + "</td>"));
    row.append($("<td>" + rowData.tariffZone.tarif + "</td>"));
    row.append($("<td>" + rowData.tariffZone.tarifPref + "</td>"));
}

function getPageCounts(){
    pageResults = $("#selectEntries option:selected").val();
    $.ajax({
        url: 'directionCount?pageResults=' + pageResults,
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#totalPages').text(data);
        }
    });
}

$(document).ready(function () {
    $('#selectEntries').on('change', function () {
        getPageCounts();
        renderCallsTable(pageResults, 1);
    });
});