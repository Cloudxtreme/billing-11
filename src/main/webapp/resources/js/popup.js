$(document).ready(function(){
    $('#confirm-delete').on('show.bs.modal', function(e) {
        $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
    });

    $('.alert-success').fadeOut(2500);
    $('.alert-danger').fadeOut(2500);

});
