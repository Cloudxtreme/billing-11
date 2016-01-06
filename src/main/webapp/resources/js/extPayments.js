$(document).on("click", ".pushHandle", function () {
    console.log("pushHandle push");
    var paymentId = $(this).data('id');
    console.log("pushHandle " + paymentId);

    $.ajax({
        url: '../setPaymentHandled?id='+paymentId,
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            var obj = $('[data-id='+paymentId+']');
            obj.attr('disabled','disabled');
        },
        error : function(){
            console.log("error in ajax query setPaymentHandled");
        }
    });

});