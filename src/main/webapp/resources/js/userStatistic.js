$(document).ready(function () {
    ajaxBuildCustomCalendar();
});

$(document).ready(function () {
    $(function() {
        $('.date-picker').datepicker( {
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            dateFormat: 'yy-mm',
            onClose: function(dateText, inst) {
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(year, month, 1));
            }
        });
    });

    $('body').on('click','.btn-toolbar',function(){
        ajaxBuildDayStatistic(this.id);//alert(this.id);
    });

    $('#searchBtn').on('click', function () {
        ajaxBuildCustomCalendar();
    });
});

function ajaxBuildCustomCalendar(){
    $.ajax({
        url: 'getUserStatisticForPeriod?login='+$('#login').text()+'&startDate='+$('#startDate').val()+'&endDate='+$('#endDate').val(),
        type: "get",
        dataType: "json",
        success: function(data) {
            drawCustomCalendar(data);
        }
    });
}

function ajaxBuildDayStatistic(userDate){
    $.ajax({
        url: 'getUserDailyStatistic?login='+$('#login').text()+'&date='+userDate,
        type: "get",
        dataType: "json",
        success: function(data) {
            drawTable(data);
        }
    });
}
function drawTable(data){
    $("#statisticTable").html('');
    var table = "<table id=\"statisticTable\" class=\"table table-striped table-hover\">";
    table += drawTableHead();
    for (var i = 0; i < data.length; i++) {
        table += drawTableRow(data[i]);
    }
    table += "</table>";
    $("#statisticTable").append(table);
}
function drawTableHead(){
    return "<tr>" +
        "<th>username</th>" +
        "<th>nasipaddress</th>" +
        "<th>nasportid</th>" +
        "<th>acctstarttime</th>" +
        "<th>acctsessiontime</th>" +
        "<th>acctstoptime</th>" +
        "<th>framedipaddress</th>" +
        "<th>acctinputoctets</th>" +
        "<th>acctoutputoctets</th>" +
        "<th>acctterminatecause</th>" +
    "</tr>";
}
function drawTableRow(data) {
    var acctstarttime = new Date(data.acctstarttime);
    var acctstoptime = new Date(data.acctstoptime);
    return "<label for=\""+data.radacctid+"\">" +
        "<tr id=\""+data.radacctid+"\">" +
        "<td>"+data.username+"</td>" +
        "<td>"+data.nasipaddress+"</td>" +
        "<td>"+data.nasportid+"</td>" +
        "<td>"+acctstarttime.format('yyyy-mm-dd HH:MM')+"</td>" +
        "<td>"+data.acctsessiontime+"</td>" +
        "<td>"+acctstoptime.format('yyyy-mm-dd HH:MM')+"</td>" +
        "<td>"+data.framedipaddress+"</td>" +
        "<td>"+data.acctinputoctets+"</td>" +
        "<td>"+data.acctoutputoctets+"</td>" +
        "<td>"+data.acctterminatecause+"</td>" +
        "</tr>" +
    "</label>";
}


function drawCustomCalendar(data){
    $("#calendarGroup").html('');
    for (var i = 0; i < data.length; i++) {
        drawCustomCalendarRow(data[i]);
    }
}

function drawCustomCalendarRow(data){
    var rows = "<div class=\"btn-group\" role=\"group\" aria-label=\""+data.monthName+"\">" +
        "<button class=\"btn-toolbar\" style=\"width:45px;\" disabled>"+data.monthName+"</button>";
    for (var i = 0; i < data.dayList.length; i++) {
        rows += "<button class=\"btn-toolbar\" "+data.dayList[i].disabled+" " +
            "id=\""+data.year+"-"+data.monthNumber+"-"+data.dayList[i].dayNumber+"\">"+
                data.dayList[i].dayNumber+
            "</button>";
    }
    rows += "</div>";
    $("#calendarGroup").append(rows);
}


