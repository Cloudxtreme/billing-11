var pageResults = 10;

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
    row.append($("<td>" + date.format('yyyy/mm/dd HH:MM:ss') + "</td>"));
    row.append($("<td>" + rowData.duration + "</td>"));
    row.append($("<td>" + rowData.aonKat + "</td>"));
    row.append($("<td>" + rowData.dvoCodeA + "</td>"));
    row.append($("<td>" + rowData.dvoCodeB + "</td>"));
}

$(document).ready(function(){
    $('#selectEntries').on('change',function(){
        pageResults = $("#selectEntries option:selected").val();

        $.ajax({
            url: 'callsPages'+pageResults,
            type: "get",
            dataType: "json",
            success: function() {
            }
        });

        renderCallsTable(pageResults, 1);

    });

    $(".form-control").on('keydown',function(event) {
        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 ||
            (event.keyCode == 65 && event.ctrlKey === true) ||
            (event.keyCode >= 35 && event.keyCode <= 39)) {
            return;
        }
        else {
            if ((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                event.preventDefault();
            }
        }
    });

    /*Date Range picker settings*/
    var drp = $('input[name="daterange"]').data('daterangepicker');
    $('input[name="daterange"]').daterangepicker({
        timePicker: true,
        timePickerIncrement: 10,
        "timePicker24Hour": true,
        locale: {
            cancelLabel: 'Clear',
            format: 'DD/MM/YYYY HH:mm'
        },
        autoUpdateInput: true
    }).val('');


    $('input[name="daterange"]').on('cancel.daterangepicker', function(ev, picker) {
        $('input[name="daterange"]').val('');
    });


    /*Search function logic*/
    function searchValues(rows, page){
        var numberA = $('#searchNumberA').val();
        var numberB = $('#searchNumberB').val();
        var startTime = $('#searchDate').val();
        var callForm = {
            "numberA" : numberA,
            "numberB": numberB
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType : 'json',
            url: 'searchCalls?rows='+rows+'&page='+page,
            data: JSON.stringify(callForm), // Note it is important
            success :function(data, textStatus, jqXHR) {
                drawTable(data);
                setCurrentPageNumber(page);
            }
        });
    }

    $('#searchBtn').on('click',function(){
        console.log("Btn search was pressed");
        searchValues(pageResults, 1);
    });





});

