var uniqFiles = [];

$(document).ready(function () {


    function centerModal() {
        $(this).css('display', 'block');
        var $dialog = $(this).find(".modal-dialog");
        var offset = ($(window).height() - $dialog.height()) / 2;
        // Center modal vertically in window
        $dialog.css("margin-top", offset);
    }

    $('.modal').on('show.bs.modal', centerModal);
    $(window).on("resize", function () {
        $('.modal:visible').each(centerModal);
    });


    $(function () {
        $("li").removeClass('active');
        $("#linkToUtils").addClass('selected');
        $("#linkToCSVFileUploading").addClass('active');
        $("#linkToFile").addClass('active');
    });

    $('input:file').on('change', function (evt) {
        var files = evt.target.files;
        uniqFiles = files;
        $('#list').html('');
        for (var h = 0, q; q = uniqFiles[h]; h++) {
            var sub = (q.name).substring(0, 20);
            $('#list').append('<li class="list-group-item" value="' + q.size + '"' + '><a class="deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a><strong> ' + sub + '...</strong> <b>File type:</b> ' + (q.type || 'n/a') + ' - ' +
                q.size + ' bytes, last modified: ' +
                (q.lastModifiedDate ? q.lastModifiedDate.toLocaleDateString() : 'n/a') +
                '</li>');
        }


    });

    $('html').on('click', '.glyphicon-remove', function () {
        var $li = $(this).closest('li');
        var conf = confirm("Are you sure?");
        if (!conf) {
            console.log('decline');
        } else {
            $li.fadeOut('slow', function () {
                $li.remove();
            });
            uniqFiles = [];
        }
    });


    $('.btn-toolbar').on('click', function () {
        var data = new FormData();

        if (uniqFiles.length == 0) {
            $('#spinner').hide();
            document.getElementById('errorMessage').style.display = "block";
            $('#errorMessage').append('<strong>Please select files to upload</strong>');
            setTimeout(function () {
                $("#errorMessage").fadeOut(3000, function () {
                    $("#errorMessage strong").remove();
                });
            });
            return false;
        }
        for (var i = 0; i < uniqFiles.length; i++) {
            data.append(i, uniqFiles[i]);
        }


        $('#spinner').show();

        $.ajax({
            dataType: 'json',
            url: "uploadCSVFile",
            data: data,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (result) {
                if (result == "SUCCESS") {
                    $('#myModal').modal('hide');
                    $('#spinner').hide();
                    document.getElementById('successMessage').style.display = "block";
                    $('#successMessage').append('<strong>Your file is succesfully uploaded to the server</strong>');
                    uniqFiles = [];
                    setTimeout(function () {
                        $("#successMessage").fadeOut(2500, function () {
                            $("#successMessage strong").remove();
                            $("#list li").remove();
                        });
                    });
                    $('body').scrollTop(0);

                } else if (result == "INCORRECTTYPE") {
                    $('#myModal').modal('hide');
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong>You tried to add file with incorrect type. Please delete it and try again</strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(4000, function () {
                            $("#errorMessage strong").remove();
                        });
                    });


                } else if (result == "ERROR") {
                    $('#myModal').modal('hide');
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong>Failed to file upload</strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(2500, function () {
                            $("#errorMessage strong").remove();
                        });
                    });

                } else {
                    $('#myModal').modal('hide');
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong>It is not available now please try again later</strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(2500, function () {
                            $("#errorMessage strong").remove();
                        });
                    });

                }
            }
        });

    });

    $('.undefaultStyleA').on('click', function getNameValue(){
        var reportName = this.attr('name');
        reportCreatingRequest(reportName);
    }

    function reportCreatingRequest(reportName){
        $.ajax({
            type: "GET",
            contentType: "application/json",
            dataType: 'json',
            url: 'reportCreating?reportName=' + reportName,
            success: function (data) {
            }
        })
    }





});
