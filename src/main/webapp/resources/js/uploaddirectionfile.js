var uniqFiles = [];

$(function(){
    var tableCount = $('#table tr').length;
    if(tableCount > 20){
        $('#table tr:gt(20)').hide();
        $('#showAllBtn').show();
    }
});

$(document).ready(function () {
    function centerModal() {
        $(this).css('display', 'block');
        var $dialog = $(this).find(".modal-dialog");
        var offset = ($(window).height() - $dialog.height()) / 2;
        // Center modal vertically in window
        $dialog.css("margin-top", offset);
    }

    $('#showAllBtn').on('click', function(){
        if($(this).hasClass("glyphicon-triangle-bottom")){
            $('#table tr:gt(20)').fadeIn("slow");
            $(this).removeClass('glyphicon-triangle-bottom');
            $(this).addClass('glyphicon-triangle-top');
        }else{
            $('#table tr:gt(20)').fadeOut("slow");
            $(this).removeClass('glyphicon-triangle-top');
            $(this).addClass('glyphicon-triangle-bottom');
        }
    });

    $('.modal').on('show.bs.modal', centerModal);
    $(window).on("resize", function () {
        $('.modal:visible').each(centerModal);
    });

    $('input:file').on('change', function (evt) {
        var files = evt.target.files;

        $('#list').html('');
        var q = files[0];
        var sub = (q.name).substring(0, 20);
        var ext = (q.name).split('.').pop();
        if (ext != 'docx') {
            $('#errorMessageIncType').show();
            $('#errorMessageIncType').fadeOut(5000);
            return false;
        }
        uniqFiles.push(files[0]);
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
        var interval =  setInterval(getProgress, 2000);

        if (uniqFiles.length == 0) {
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
                var $errorMessage =  $('#errorMessage');
                var $errorBusy = $('#errorMessageBUSY');
                if (result == "BUSY") {
                    $errorBusy.show();
                    uniqFiles = [];
                    $errorBusy.fadeOut(3000);
                    $('body').scrollTop(0);
                } else if (result == "ERROR") {
                    $errorMessage.show();
                    $errorMessage.fadeOut(15000);
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
                    clearInterval(interval);
                }
                if (data > 0 && data < 100) {
                    document.getElementById('progress').style.display = "block";
                    $('.progress-bar').css('width', data + '%').attr('aria-valuenow', data);
                }
                if (data == 100) {
                    $('.progress-bar').css('width', data + '%').attr('aria-valuenow', data);
                    document.getElementById('successMessage').style.display = "block";
                    setTimeout(function () {
                        $("#successMessage").fadeOut(5000);
                        $("#progress-bar").fadeOut(5000);
                        location.reload();
                    }, 6000);
                }
            }
        })
    }

    var interval =  setInterval(getProgress, 2000);
});