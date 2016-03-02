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


    $('input:file').on('change', function (evt) {
        var files = evt.target.files;
        uniqFiles.push(files[0]);

        $('#list').html('');
        var q = files[0];
        var sub = (q.name).substring(0, 20);
        var ext = (q.name).split('.').pop();
        if (ext != 'docx') {
            $('#errorMessageIncType').show();
            $('#errorMessageIncType').fadeOut(5000);
            return false;
        }
        $('#list').append('<li class="list-group-item" value="' + q.size + '"' + '><a class="deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true">' +
            '</span></a><strong> ' + sub + '...</strong> <b>File type:</b> ' + ext + ' - ' +
            q.size + ' bytes, last modified: ' +
            (q.lastModifiedDate ? q.lastModifiedDate.toLocaleDateString() : 'n/a') +
            '</li>');
    });

    $('html').on('click', '.glyphicon-remove', function () {
        var $li = $(this).closest('li');
        var conf = confirm("Are u sure ?");
        if (!conf) {
            console.log('decline');
        } else {
            $li.fadeOut('slow', function () {
                $li.remove();
            });
        }
    });

    function isEmpty(myObject) {
        for (var key in myObject) {
            if (myObject.hasOwnProperty(key)) {
                return false;
            }
        }
        return true;
    }

    $('#uploadFile').on('click', function () {
        var data = new FormData();

        if (uniqFiles.length == 0) {
            $('#myModal').modal('hide');
            $('#errorMessageEmpty').show();
            $('#errorMessageEmpty').fadeOut(3000);
            return false;
        }

        for (var i = 0; i < uniqFiles.length; i++) {
            data.append(i, uniqFiles[i]);
        }

        $.ajax({
            dataType: 'json',
            url: "uploaddirectionfile",
            data: data,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (result) {
                if (result == "SUCCESS") {
                    $('#myModal').modal('hide');
                    document.getElementById('successMessage').style.display = "block";
                    $('#successMessage').show();
                    uniqFiles = [];
                    setTimeout(function () {
                        $("#successMessage").fadeOut(2500, function () {
                            $("#successMessage").hide();
                            $("#list li").remove();
                        });
                    });
                    $('body').scrollTop(0);

                } else if (result == "INCORRECTTYPE") {
                    $('#myModal').modal('hide');
                    $('#errorMessageIncType').show();
                    $('#errorMessageIncType').fadeOut(3000);
                } else if (result == "ERROR") {
                    $('#myModal').modal('hide');
                    $('#errorMessage').show();
                    $('#errorMessage').fadeOut(3000);

                }
            }
        });
    });

    function getProgress() {
        $.ajax({
            url: "uploaddirectionfile/reportCreatingProgress",
            type: "GET",
            success: function (data) {
                var width = (data);
                if (data == 0) {
                    setTimeout(getProgress, 2000);
                }
                if (data > 0 && data < 100) {
                    document.getElementById('progress').style.display = "block";
                    $('.progress-bar').css('width', data + '%').attr('aria-valuenow', data);
                    setTimeout(getProgress, 2000);
                }
                if (data == 100) {
                    $('.progress-bar').css('width', data + '%').attr('aria-valuenow', data);
                    document.getElementById('successMessageReport').style.display = "block";
                    setTimeout(function () {
                        $("#successMessageReport").fadeOut(5000);
                        $("#progress-bar").fadeOut(5000);
                        location.reload();
                    }, 6000);
                }
            }
        })
    }

    /*var interval =  setInterval(getProgress, 2000);*/
});