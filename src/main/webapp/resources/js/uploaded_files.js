$(function() {
    $("li").removeClass('active');
    $("#linkToUtils").addClass('selected');
    $("#linkToFileUploading").addClass('active');
});

$(document).ready(function(){

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
            url: '${pageContext.request.contextPath}/uploadedfiles/handle',
            data: JSON.stringify(values),
            datatype: "JSON",
            contentType: "application/json",
            success: function (data) {}
        });
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
            url: "${pageContext.request.contextPath}/uploadedfiles/handle/getprogress",
            success : function(data){
                var width = (data);
                if(data >0 && data < 100){
                    document.getElementById('progress').style.display = "block";
                    $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
                    setTimeout(getProgress,2000);
                }if (data == 100){
                    $('.progress-bar').css('width', data+'%').attr('aria-valuenow', data);
                    clearInterval(interval);
                    document.getElementById('succesMessageReload').style.display="block";
                    setTimeout(function() {
                        $("#succesMessageReload").fadeOut(3000);
                        $("#progress-bar").fadeOut(1500);
                        location.reload();
                    },3000);

                }
            }
        })
    }
    var interval = setTimeout(getProgress,2000);

    $('#table tr #deleting').click(function () {
        console.log(this);
        var $tr = $(this).closest('tr');
        var conf = confirm("Are you sure?");
        if (conf == true){
            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/uploadedfiles/delete.html',
                data: $tr.attr('id'),
                datatype: "JSON",
                contentType: "application/json",
                success: function (data) {
                    if(data == "success") {
                        $tr.fadeOut('slow',function(){
                            $tr.remove()
                        });
                        document.getElementById('succesMessage').style.display="block";
                        setTimeout(function() {
                            $("#succesMessage").fadeOut(2000);
                        });
                    } else{
                        alert("Delete is not finished");
                    }
                }
            });
        } else {
            alert("Thats right decision");
        }
    });


})

