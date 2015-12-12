

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

    var obj = {};
    $('#streets').typeahead({
        onSelect: function(item) {
            console.log(item);
            $('#streetId').val(item.value);
            obj = item;
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

    $('#addDeviceForm').submit(function(){
        var valueId = $('#streetId').val();
        console.log(valueId);
        var valueName = $('#streets').val();
        console.log(valueName);
        if(obj.value === valueId && valueName !== obj.text){
            console.log("AAKFKASKFKASF");
            $('#streetId').val('');
        }
    });

    $(function() {
        $("li").removeClass('active');
        $("#linkToUtils").addClass('selected');
        $("#linkToDeviceList").addClass('active');
    });

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