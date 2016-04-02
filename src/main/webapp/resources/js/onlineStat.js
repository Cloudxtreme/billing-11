$(document).ready(function() {
    console.log("start js onLoad");
    $('#authLogDiv').hide();
    renderOnlineTable();//for first time calling
    setInterval(renderOnlineTable, 5000); //for next repeating
    getlastAuthLogLines();
    setInterval(getlastAuthLogLines, 5000);

    $('#authLogShow').on('click', function(){
        $('#userOnlineTableDiv').hide();
        $('#authLogDiv').show();
    });

    $('#onlineUserCount').on('click', function(){
        $('#userOnlineTableDiv').show();
        $('#authLogDiv').hide();
    });


});


function renderOnlineTable(){
    $.ajax({
        url: '../getOnline',
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            $("#usersCount").text(data.length);
            drawTable(data);
        }
    });
}

function drawTable(data) {
    $("#userOnlineTable").find("tr:gt(0)").remove();

    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
}

function drawRow(rowData) {
    var row = $("<tr />");
    $("#userOnlineTable").append(row);
    row.append($("<td>" + rowData.username + "</td>"));
    row.append($("<td>" + rowData.user_fio + "</td>"));
    row.append($("<td>" + rowData.nasipaddress + "</td>"));
    row.append($("<td>" + rowData.nasportid + "</td>"));
    row.append($("<td>" + rowData.acctstarttime + "</td>"));
    row.append($("<td>" + rowData.acctsessiontime + "</td>"));
    row.append($("<td>" + rowData.framedipaddress + "</td>"));
    row.append($("<td>" + rowData.acctinputoctets + "</td>"));
    row.append($("<td>" + rowData.acctoutputoctets + "</td>"));

}

function getlastAuthLogLines(){
    $.ajax({
        url: '../getAuthLogLines',
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            $("#authLogDiv").text("");
            for (var i = 0; i < data.length; i++) {
                $("#authLogDiv").append("<h4>" + data[i] + "</h4>");
            }
        }
    });
}

