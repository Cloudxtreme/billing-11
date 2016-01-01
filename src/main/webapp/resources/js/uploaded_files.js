
$(document).ready(function(){

    $(function() {
        $("li").removeClass('active');
        $("#linkToUtils").addClass('selected');
        $("#linkToFileUploading").addClass('active');
        $("#linkToFile").addClass('active');

    });

    $('.check-box-table-cell').click(function() {
        var checked = $(this).attr('checked');
        if(checked){
            $(this).attr('checked', false);
            $(this).closest("tr").removeClass("info");

        }
        else{
            $(this).attr('checked', true);
            $(this).closest("tr").addClass("info")
        }
    });

    $('#handleBtn').click(function(){
        var values = new Array();
        $.each($(".check-box-table-cell:checked").closest("tr"),
            function(){
                values.push($(this).attr('id'));
            });
        $.ajax({
            type:"post",
            url: '/uploadedfiles/handle',
            data: JSON.stringify(values),
            datatype: "JSON",
            contentType: "application/json",
            success: function (data) {}
        });
    });

    $('#table tr #deleting').click(function () {
        var $tr = $(this).closest('tr');
        $('#deleteBtn').on('click', function () {
            $('#confirm-delete').modal('hide');
            $.ajax({
                type: "POST",
                url: '/uploadedfiles/delete.html',
                data: $tr.attr('id'),
                datatype: "JSON",
                contentType: "application/json",
                success: function (data) {
                    if(data == "success") {
                        $tr.fadeOut('slow',function(){
                            $tr.remove()
                        });
                        document.getElementById('successMessage').style.display="block";
                        setTimeout(function() {
                            $("#successMessage").fadeOut(2500);
                        });
                    } else{
                        document.getElementById('errorMessage').style.display="block";
                        setTimeout(function() {
                            $("#errorMessage").fadeOut(2500);
                        });
                    }
                }
            });
        })
    });

    var $tr = $('#table');
    $($tr).find('td:nth-child(3)').each(function(){
        $($tr).find('td:nth-child(3):contains("NEW")').css({
            'color': '#4da309',
            'font-weight': 'bold'});
        $($tr).find('td:nth-child(3):contains("PROCESSED")').css({
            'color': '#e72510',
            'font-weight': 'bold'});
    });

    function getProgress(){
        $.ajax({
            url: "/uploadedfiles/handle/getprogress",
            success : function(data){
                var width = (data);
                if(data >0 && data < 100){
                    document.getElementById('progress').style.display = "block";
                    $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
                    setTimeout(getProgress,2000);
                }if (data == 100){
                    $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
                    clearInterval(interval);
                    document.getElementById('successMessageReload').style.display="block";
                    setTimeout(function() {
                        $("#successMessageReload").fadeOut(3000);
                        $("#progress-bar").fadeOut(1500);
                        location.reload();
                    },3000);

                }
            }
        })
    }

    var interval = setTimeout(getProgress,2000);

    $('#handleCostTotal').on('click', function(){
        $.ajax({
            url: "/worker/billCall",
            type: "Post",
            success: function(data){
                if(data == "success") {
                    $tr.fadeOut('slow',function(){
                        $tr.remove()
                    });
                    document.getElementById('successMessage').style.display="block";
                    setTimeout(function() {
                        $("#successMessage").fadeOut(2000);
                    });
                } else{
                    document.getElementById('errorMessage').style.display="block";
                    setTimeout(function() {
                        $("#errorMessage").fadeOut(2000);
                    });
                }
            }
        })
    });
});



