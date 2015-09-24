var pageResults = 20;

$(function() {
    // set active navigation tab "Calls"
    console.log("start js onLoad");
    $("li").removeClass('active');
    $("#linkToCallsList").addClass('active');
    renderCallsTable(pageResults, 1);
});

function goToPrevPage() {
    var currentPageNum = $("#pageNumber").text();
    console.log("goPrev push with " + currentPageNum);
    var nextPage = Number(currentPageNum)-1;
    if (nextPage != 0){
        renderCallsTable(pageResults, nextPage);
    }
};

function goToNextPage() {
    var currentPageNum = $("#pageNumber").text();
    var totalPages = $("#totalPages").text();
    console.log("goNext push with " + currentPageNum);
    var nextPage = Number(currentPageNum)+1;
    if (nextPage <= totalPages){
        renderCallsTable(pageResults, nextPage);
    }
};

function setCurrentPageNumber(number){
    $("#pageNumber").html(number);
};

function renderCallsTable(rows, page){
    console.log("page="+page+"row="+rows);
    $.ajax({
        url: 'callsList?rows='+rows+'&page='+page,
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            // since we are using jQuery, you don't need to parse response
            drawTable(data);
            setCurrentPageNumber(page);
        }
    });
};

function drawTable(data) {
    $("#callsTable").find("tr:gt(0)").remove();

    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
}

function drawRow(rowData) {
    var row = $("<tr />");
    $("#callsTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
    var date = new Date(rowData.startTime);
    row.append($("<td>" + rowData.numberA + "</td>"));
    row.append($("<td>" + rowData.numberB + "</td>"));
    row.append($("<td>" + date + "</td>"));
    row.append($("<td>" + rowData.duration + "</td>"));
    row.append($("<td>" + rowData.aonKat + "</td>"));
    row.append($("<td>" + rowData.dvoCodeA + "</td>"));
    row.append($("<td>" + rowData.dvoCodeB + "</td>"));

}



