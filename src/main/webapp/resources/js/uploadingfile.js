
if (typeof jQuery === 'undefined') {
    throw new Error('Bootstrap\'s JavaScript requires jQuery')
}

+function ($) {
    var uniqFiles = [];
    $('input:file').on('change', function (evt) {
        var files = evt.target.files;
        for (var i = 0, f; f = files[i]; i++) {
            if ($.inArray(f, uniqFiles) == -1) {
                uniqFiles.push(f);
            }
            else {
                console.log('blalbla');
                $('#errorMessage').css('display', 'block');
                setTimeout(function () {
                    $("#errorMessage").fadeOut(10000);
                });
            }


        }

        $('#list').html('');
        for (var h = 0, q; q = uniqFiles[h]; h++) {
            var sub = (q.name).substring(0, 20);
            $('#list').append('<li class="list-group-item" value="' + q.size + '"' + '><a class="deleting"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a><strong> ' + sub + '...</strong> <b>File type:</b> ' + (q.type || 'n/a') + ' - ' +
                q.size + ' bytes, last modified: ' +
                (q.lastModifiedDate ? q.lastModifiedDate.toLocaleDateString() : 'n/a') +
                '</li>');
        }


    });

    $('html').on('click', '.glyphicon', function () {
        var $li = $(this).closest('li');
        var ident = $li.attr('value');
        for (var i = 0, p; p = uniqFiles[i]; i++) {
            if (p.size != ident) {
            } else {
                uniqFiles.splice(i, 1);
            }
        }
        var conf = confirm("Are you sure?");
        if (!conf) {
            console.log('decline');
        } else {
            $li.fadeOut('slow', function () {
                $li.remove();
            });
        }
    });


    $('.btn-toolbar').on('click', function () {
        var reader = new FileReader();
        var data = new FormData();
        for (var i = 0; i < uniqFiles.length; i++) {
            data.append(i, uniqFiles[i]);
        }
        $('#spinner').show();

        $.ajax({
            dataType: 'json',
            url: "${pageContext.request.contextPath}/uploadfile",
            data: data,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (result) {
                if (result == "1") {
                    $('#spinner').hide();
                    document.getElementById('successMessage').style.display = "block";
                    $('#successMessage').append('<strong>Your file is succesfully uploaded to the server</strong>');
                    uniqFiles = [];
                    setTimeout(function () {
                        $("#successMessage").fadeOut(3000, function () {
                            $("#successMessage strong").remove();
                            $("#list li").remove();
                        });
                    });
                    $('body').scrollTop(0);

                } else if (result == "2") {
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong>You tried to add file with incorrect type. Please delete it and try again</strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(10000, function () {
                            $("#errorMessage strong").remove();
                        });
                    });


                } else if (result == "3") {
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong>Failed to file upload</strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(10000, function () {
                            $("#errorMessage strong").remove();
                        });
                    });

                } else {
                    $('#spinner').hide();
                    document.getElementById('errorMessage').style.display = "block";
                    $('#errorMessage').append('<strong>It is not available now please try again later</strong>');
                    setTimeout(function () {
                        $("#errorMessage").fadeOut(10000, function () {
                            $("#errorMessage strong").remove();
                        });
                    });

                }
            }
        });

    });
}(jQuery);