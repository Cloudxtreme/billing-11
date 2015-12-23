$(document).ready(function () {
    //Listener to changing entries values
    $('#transactionListLimit').on('change', function() {
        var limit = $(this).find(':selected').val();
        if((typeof limit !='undefined') )
            ajaxBuildTransactionTable(limit);
    });

    $(".form-control").on('keydown', function (event) {
        if (event.keyCode == 13) {
            console.log("press enter");
            buildSearchResultTable();
        }
    });

    $('#searchBtn').on('click', function () {
        buildSearchResultTable();
    });

    $('#eraseSearch').on('click', function () {
        $('#searchAccountName').val('');
        $('#searchDate').val('');
        console.log("erase btn click");
        buildSearchResultTable();
    })

    //Date range picker settings
    var drp = $('input[name="daterange"]').data('daterangepicker');
    $('input[name="daterange"]').daterangepicker({
        timePicker: true,
        timePickerIncrement: 10,
        "timePicker24Hour": true,
        locale: {
            cancelLabel: 'Clear',
            format: 'YYYY.MM.DD'
        },
        autoUpdateInput: true
    }).val('');

    $('input[name="daterange"]').on('cancel.daterangepicker', function (ev, picker) {
        $('input[name="daterange"]').val('');
    });
});

function buildSearchResultTable(){
    $.ajax({
        url: '../../searchTransaction?account='+$('#searchAccountName').val()+'&searchDate='+$('#searchDate').val(),
        type: "get",
        dataType: "json",
        success: function(data) {
            drawTable(data);
        }
    });
}


function ajaxBuildTransactionTable(limit){
    var accountId = ($("#accountId").text() === "") ? 0 : $("#accountId").text();

    console.log(accountId);
    $.ajax({
        url: '/transaction/buildTransactionTable?accountId='+accountId+'&limit='+limit,
        type: "get",
        dataType: "json",
        success: function(data) {
            drawTable(data);
        }
    });
}

function drawTable(data){
    $("#transactionTable").find("tr:gt(0)").remove();
    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
}

function drawRow(data){
    var date = new Date(data.date);
    var tableRow = "<tr id=" + data.id + ">" +
        "<td>"+ data.account.accountName +"</td>" +
        "<td>"+ date.format('yyyy-mm-dd HH:MM') +"</td>" +
        "<td>"+ data.direction +"</td>" +
        "<td>"+ data.source +"</td>" +
        "<td>"+ data.price +"</td>" +
        "<td>"+ data.comment +"</td>" +
        "</tr>";
    $("#transactionTable").append(tableRow);
}
