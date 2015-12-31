$(document).ready(function() {

    $('#table tr #deleting').click(function () {
        var $tr = $(this).closest('tr');
        $('#deleteBtn').on('click', function () {
            $('#confirm-delete').modal('hide');
            $.ajax({
                type: "POST",
                url: 'device/delete.html',
                data: $tr.attr('id'),
                datatype: "JSON",
                contentType: "application/json",
                success: function (data) {
                    if (data == "SUCCESS") {
                        $tr.fadeOut('slow', function () {
                            $tr.remove()
                        });
                        document.getElementById('succesMessage').style.display = "block";
                        setTimeout(function () {
                            $("#succesMessage").fadeOut(2000);
                        });
                    } else {
                        $("#errorMessage").fadeOut(2000);
                    }
                }
            });
        });

        setTimeout(function () {
            $('.alert').fadeOut(5000);
        })
    })
});
