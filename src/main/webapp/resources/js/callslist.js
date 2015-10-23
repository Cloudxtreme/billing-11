var pageResults = 25;

//Function which starts first
$(function () {
    // set active navigation tab "Calls"
    console.log("start js onLoad");
    $("li").removeClass('active');
    $("#linkToCallsList").addClass('active');
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

//Main Function for getting and searching CallForms from server-side
function renderCallsTable(rows, page) {
    var numberA = $('#searchNumberA').val();
    var numberB = $('#searchNumberB').val();
    var timeRange = $('#searchDate').val();

    if(isNaN(numberA) || isNaN(numberB)){
        document.getElementById('errorMessage').style.display="block";
        setTimeout(function() {
            $("#errorMessage").fadeOut(2000);
        });
    }else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            dataType: 'json',
            url: 'callsList?rows=' + rows + '&page=' + page + '&numberA=' + numberA + '&numberB=' + numberB + '&timeRange=' + timeRange,
            success: function (data, textStatus, jqXHR) {
                drawTable(data);
                setCurrentPageNumber(page);
            }
        });
        getPageCounts();

    }
}

//Function for drawing the table
function drawTable(data) {
    $("#callsTable").find("tr:gt(0)").remove();

    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
}

//Function for drawing the rows of the table
function drawRow(rowData) {
    var row = $("<tr />");
    $("#callsTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
    var date = new Date(rowData.startTime);
    row.append($("<td>" + rowData.numberA + "</td>"));
    row.append($("<td>" + rowData.numberB + "</td>"));
    row.append($("<td>" + date.format('yyyy/mm/dd HH:MM') + "</td>"));
    row.append($("<td>" + rowData.duration + "</td>"));
    row.append($("<td>" + rowData.aonKat + "</td>"));
    row.append($("<td>" + rowData.dvoCodeA + "</td>"));
    row.append($("<td>" + rowData.dvoCodeB + "</td>"));
    row.append($("<td>" + rowData.costTotal + "</td>"));
}

//Helps us to get the page counts while we use search function
function getPageCounts(){
    var numberA = $('#searchNumberA').val();
    var numberB = $('#searchNumberB').val();
    var timeRange = $('#searchDate').val();
    pageResults = $("#selectEntries option:selected").val();
    $.ajax({
        url: 'callsPages?pageResults=' + pageResults + '&numberA=' + numberA + '&numberB=' + numberB + '&timeRange=' + timeRange,
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#totalPages').text(data);
        }
    });
}

$(document).ready(function () {
    //Listener to changing entries values
    $('#selectEntries').on('change', function () {
        getPageCounts();
        renderCallsTable(pageResults, 1);

    });

    //Call the search function by ENTER button pressing
    $(".form-control").on('keydown', function (event) {
        if (event.keyCode == 13) {
            renderCallsTable(pageResults,1);
        }
    });

    //Call the search function by pressing SEARCH button
    $('#searchBtn').on('click', function () {
        renderCallsTable(pageResults,1);
    });

    //Clear field's values and come back to the initial statement
    $('#eraseSearch').on('click', function () {
        $('#searchNumberA').val('');
        $('#searchNumberB').val('');
        $('#searchDate').val('');
        renderCallsTable(pageResults, 1);
    })



    //Date range picker settings
    var drp = $('input[name="daterange"]').data('daterangepicker');
    $('input[name="daterange"]').daterangepicker({
        timePicker: true,
        timePickerIncrement: 10,
        "timePicker24Hour": true,
        locale: {
            cancelLabel: 'Clear',
            format: 'YYYY/MM/DD HH:mm'
        },
        autoUpdateInput: true
    }).val('');


    //Date range picker. Helps us to erase data from field when Clear button was pressed
    $('input[name="daterange"]').on('cancel.daterangepicker', function (ev, picker) {
        $('input[name="daterange"]').val('');
    });




});

