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

    $('#generateReport').on('click', function getNameValue() {
        var values = [];
        var year= $('#yearSelect option:selected').val();
        var month = $('#monthSelect option:selected').val();
        values.push(year);
        values.push(month);
        $.each($(".check-box-table-cell:checked"),
            function () {
                var tr = $(this).closest("tr");
                values.push(tr.attr('id'));
                tr.removeClass("info");
                tr.addClass("success");
                $(this).attr('checked', false);
            });
        reportCreatingRequest(values);
    });

    $('.unDefaultTDStyle').on('click', function () {
        var checked = $(this).parents('tr').find('input[type="checkbox"]').prop('checked');
        if (checked) {
            $(this).parents('tr').find('input[type="checkbox"]').prop('checked', false);
            $(this).closest("tr").removeClass("info");

        }
        else {
            $(this).parents('tr').find('input[type="checkbox"]').prop('checked', true);
            $(this).closest("tr").addClass("info")
        }
    });

    function reportCreatingRequest(reportNames) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify(reportNames),
            url: 'reportCreating',
            success: function (data) {
                if (data == "SUCCESS") {
                    document.getElementById('successMessage').style.display = "block";
                    $('#successMessage').append('<strong>Report created successfully</strong>');
                    $("#successMessage").fadeOut(2500, function () {
                        $("#successMessage strong").remove();
                        location.reload();
                    });
                } else {
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong>Failed to generate report. Please try again later</strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(2500, function () {
                            $("#errorMessage strong").remove();
                        });
                    });
                }
            }
        })
    }

    $('.check-box-table-cell').click(function () {
        var checked = $(this).attr('checked');
        if (checked) {
            $(this).attr('checked', false);
            $(this).closest("tr").removeClass("info");

        }
        else {
            $(this).attr('checked', true);
            $(this).closest("tr").addClass("info")
        }
    });

    $('#selectAllBtn').on('click', function () {
        var $checkboxesAll = $('.check-box-table-cell');
        var $checkboxesChecked = $('.check-box-table-cell:checked');
        var boolCondition = !$checkboxesAll.length == $checkboxesChecked.length;
        $checkboxesAll.prop('checked', boolCondition);
        $($checkboxesAll).closest('tr')[boolCondition ? 'addClass' : 'removeClass']('info');
    });
    $.ajax({
        type: "Post",
            url: 'uploadCSVFile/generateFileTree',
        success: function (data) {
            console.log(data);
            $('#fileTree').fileTree({
                data: data,
                sortable: false,
                selectable: false
            });
        }
    });

    $('#fileTree').on('click', '.file', function () {
        var selected = $(this).hasClass('selected');
        if (selected) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
            var id = $(this).attr('data-id');
            window.location.href = 'downloadFile?fileId=' + id;
            $.ajax({
                type: "GET",
                url: 'downloadFile?fileId=' + id,
                contentType: "application/json",
                dataType: 'json'
            });
            setTimeout(function () {
                $('.file').removeClass("selected");
            }, 1500);
        }
    });

    $('#fileTree').on('click', '.glyphicon-download-alt', function () {
        var directoryName = $(this).attr('name');
        window.location.href = 'downloadZIP?directoryName=' + directoryName;
        $.ajax({
            type: "GET",
            url: 'downloadZIP?directoryName=' + directoryName,
            contentType: "application/json",
            dataType: 'json'
        });
    })
});
