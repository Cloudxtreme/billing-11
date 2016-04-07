
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

    $('.table-striped tr').click(function(event) {
        if (event.target.type !== 'checkbox') {
            $(':checkbox', this).trigger('click');
        }
    });

    $('#handleBtn').click(function(){
        var values = [];
        $.each($(".check-box-table-cell:checked").closest("tr"),
            function(){
                values.push($(this).attr('id'));
            });
        $.ajax({
            type:"post",
            url: './uploadedfiles/handle',
            data: JSON.stringify(values),
            datatype: "JSON",
            contentType: "application/json",
            success: function (data) {
                if(data == "BUSY"){
                    console.log("BUSY");
                    document.getElementById('errorMessageBUSY').style.display="block";
                    setTimeout(function() {
                        $("#errorMessageBUSY").fadeOut(15000);
                    });
                }else if(data == "ERROR"){
                    document.getElementById('errorMessage').style.display="block";
                    setTimeout(function() {
                        $("#errorMessage").fadeOut(15000);
                    });
                }
            }
        });
        var intervalKDF = setInterval(getProgressKDFFile,2000);
    });

    $('#table tr #deleting').click(function () {
        var $tr = $(this).closest('tr');
        var idsArray = [$($tr).attr('id')];
        $('#deleteBtn').on('click', function () {
            $('#confirm-delete').modal('hide');
            $.ajax({
                type: "POST",
                url: './uploadedfiles/delete.html',
                data: JSON.stringify(idsArray),
                datatype: "JSON",
                contentType: "application/json",
                success: function (data) {
                    if(data == "success") {
                        $tr.fadeOut('slow',function(){
                            $tr.remove()
                        });
                        document.getElementById('successMessage').style.display="block";
                        setTimeout(function() {
                            $("#successMessage").fadeOut(3000);
                        });
                    } else{
                        document.getElementById('errorMessage').style.display="block";
                        setTimeout(function() {
                            $("#errorMessage").fadeOut(15000);
                        });
                    }
                }
            });
        })
    });

    $('#deleteSelected').on('click', function(){
        var idsArray = [];
        $($tr).find('tr:has(.check-box-table-cell:checked)').each(function(){
            idsArray.push($(this).attr('id'));
        });
        $('#deleteBtn').on('click', function () {
            $('#confirm-delete').modal('hide');
            $.ajax({
                type: "POST",
                url: './uploadedfiles/delete.html',
                data: JSON.stringify(idsArray),
                datatype: "JSON",
                contentType: "application/json",
                success: function (data) {
                    if(data == "success") {
                        $($tr).find('tr:has(.check-box-table-cell:checked)').each(function(){
                            $(this).remove();
                        });
                        document.getElementById('deleteSelected').style.display="none";
                        document.getElementById('successMessage').style.display="block";
                        setTimeout(function() {
                            $("#successMessage").fadeOut(3000);
                        });
                    } else{
                        document.getElementById('errorMessage').style.display="block";
                        setTimeout(function() {
                            $("#errorMessage").fadeOut(15000);
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

    $('#selectNew').on('click', function(){
        var checkedTRs = $($tr).find('tr:has(td:nth-child(3):contains("NEW")):has(.check-box-table-cell:checked)');
        if(checkedTRs.length>0){
            $($tr).find('tr').has('td:nth-child(3):contains("NEW")').each(function(){
                $(this).find('.check-box-table-cell').prop("checked", false);
                $(this).removeClass("info");
            });
            document.getElementById('deleteSelected').style.display="none";
        }else{
            $($tr).find('tr').has('td:nth-child(3):contains("NEW")').each(function(){
                $(this).find('.check-box-table-cell').prop("checked", true);
                $(this).addClass("info");
            });
            if($($tr).find('tr').has('td:nth-child(3):contains("NEW")').length>0){
                document.getElementById('deleteSelected').style.display="block";
            }
        }
    });

    $('#selectProcessed').on('click', function(){
        var checkedTRs = $($tr).find('tr:has(td:nth-child(3):contains("PROCESSED")):has(.check-box-table-cell:checked)');
        if(checkedTRs.length>0){
            $($tr).find('tr').has('td:nth-child(3):contains("PROCESSED")').each(function(){
                $(this).find('.check-box-table-cell').prop("checked", false);
                $(this).removeClass("danger");
            });
            document.getElementById('deleteSelected').style.display="none";
        }else{
            $($tr).find('tr').has('td:nth-child(3):contains("PROCESSED")').each(function(){
                $(this).find('.check-box-table-cell').prop("checked", true);
                $(this).addClass("danger");
            });
            if($($tr).find('tr').has('td:nth-child(3):contains("PROCESSED")').length>0){
                document.getElementById('deleteSelected').style.display="block";
            }
        }
    });

    $('#handleCostTotal').on('click', function(){
        $.ajax({
            url: "./worker/billCall",
            type: "Post",
            success: function(data){
                if(data == "ERROR") {
                    document.getElementById('errorMessage').style.display="block";
                    setTimeout(function() {
                        $("#errorMessage").fadeOut(15000);
                    });
                }
            }
        });
        var intervalBillCall = setInterval(getProgressCallCost, 2000);
    });


    function progressDone(data){
        $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
        document.getElementById('successMessageReload').style.display="block";
        setTimeout(function() {
            $("#successMessageReload").fadeOut(7000);
            $("#progress-bar").fadeOut(5000);
            location.reload();
        },8000);
    }

    function getProgressKDFFile(){
        $.ajax({
            url: "./uploadedfiles/handle/getprogress",
            success : function(data){
                var width = (data);
                if(data == 0){
                    clearInterval(intervalKDF);
                }
                if(data >0 && data < 100){
                    document.getElementById('progress').style.display = "block";
                    $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
                }if (data == 100){
                    progressDone(data);
                }
            }
        })
    }

    function getProgressCallCost(){
        $.ajax({
            url: "./uploadedfiles/billCall/getprogress",
            success : function(data){
                var width = (data);
                if(data == 0){
                    clearInterval(intervalBillCall);
                }
                if(data >0 && data < 100){
                    document.getElementById('progress').style.display = "block";
                    $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
                }if (data == 100){
                    progressDone(data);
                }
            }
        })
    }

    var intervalKDF = setInterval(getProgressKDFFile,2000);
    var intervalBillCall = setInterval(getProgressCallCost, 2000);
});



