$(document).ready(function() {

    $("td.dport").keydown(function(event) {

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
});


$('html').on('click', '.glyphicon-edit', function () {
    var $tr = $(this).closest('tr');
    $tr.children('td:not(#updating)').attr('contenteditable','true').css('background-color','#d9edf7');
    $(this).closest('#updatingBtn').remove();
    $tr.children('#updating').append('<a><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span></a>&nbsp;&nbsp;');
    $tr.children('#updating').append('<a><span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span></a>');
});

$('html').on('click', '.glyphicon-ban-circle', function () {
    var $tr = $(this).closest('tr');
    $tr.children('td:not(#updating)').attr('contenteditable','false').css('background-color','#fff');
    $tr.children('#updating').empty();
    $tr.children('#updating').append('<a id="updatingBtn"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>');
});

$('html').on('click', '.glyphicon-ok-circle', function () {
    var $tr = $(this).closest('tr');
    var deviceTypesForm = {
        id: $tr.attr('id'),
        description: $tr.find('td:nth-child(3)').text(),
        portsNumber: $tr.find('td:nth-child(4)').text(),
        deviceType: $tr.find('td:nth-child(2)').text()
    };
    $.ajax({
        dataType: 'json',
        url: "/editdevicetype",
        data: JSON.stringify(deviceTypesForm),
        type: "POST",
        contentType: "application/json",
        success: function (data) {
            if(data == 'SUCCESS') {
                document.getElementById('succesMessage').style.display="block";
                setTimeout(function() {
                    $("#succesMessage").fadeOut(3000);
                });
            } else{
                document.getElementById('errorMessage').style.display="block";
                setTimeout(function() {
                    $("#errorMessage").fadeOut(3000);
                });
            }
        }
    });
    $('body').scrollTop(0);
    $tr.children('td:not(#updating)').attr('contenteditable','false').css('background-color','#fff');
    $tr.children('#updating').empty();
    $tr.children('#updating').append('<a id="updatingBtn"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>');
});

$(function() {
    $("li").removeClass('active');
    $("#linkToUtils").addClass('selected');
    $("#linkToDeviceList").addClass('active');
});
