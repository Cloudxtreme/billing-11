

$(document).ready(function() {

    document.getElementById('chkNet').onchange = function() {
        if ( document.getElementById('chkNet').checked ) {
            document.getElementById('ipNetDiv').style.visibility = 'visible';
        } else {
            document.getElementById('ipNetDiv').style.visibility = 'hidden';

            console.log(this);
            $.ajax({
                type: "get",
                url: "returniplist",
                //data: $('#ipNet').val(),
                datatype: "JSON",
                contentType: "application/json",
                success: function (data) {
                    $('#ip').html('');
                    var option_html = '';
                    $.each(data, function(key, value) {
                        $('#ip').append('<option value="'+key+'">'+value+'</option>');
                    });
                }
            });
        }
    };

    $('#streets').typeahead({
        onSelect: function(item) {
            console.log(item);
            $('#streetId').val(item.value);
        },
        ajax: {
            url: ".././getListOfStreets.html",
            timeout: 500,
            displayField: "name",
            triggerLength: 1,
            method: "get",
            loadingClass: "loading-circle"
        }
    });

    $(function() {
        $("li").removeClass('active');
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');
    });

    $('#submitForm').on('click', function(){
        redirectToDevicePage();
    });
    $('#declineForm').on('click', function(){
        redirectToDevicePage();
    });

    function redirectToDevicePage(){
        window.location="device.html";
    }


    $('#ipNet').on('change', function() {
        console.log(this);
        $.ajax({
            type: "POST",
            url: "getValidIps",
            data: $('#ipNet').val(),
            datatype: "JSON",
            contentType: "application/json",
            success: function (data) {
                $('#ip').html('');
                var option_html = '';
                $.each(data, function(key, value) {
                    $('#ip').append('<option value="'+key+'">'+value+'</option>');
                });
            }
        });
    });

});