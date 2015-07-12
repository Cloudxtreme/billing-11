$(function() {
    // set active navigation tab "Accounts"
    console.log("start js onLoad");
    $("li").removeClass('active');
    $("#linkToAccounts").addClass('active');
});


$(document).ready(function() {

    $('#crtAccount').click(function(e) {
        console.log("button pushed");
        var frm = $('#crtAccountForm');
        e.preventDefault();


        var account = {
            accountName: $("#accountName").val(),
            accountType:$("#accountType").val()
        }

        var formData = $('#crtAccountForm').serializeArray();
        var data = {};
        $(formData ).each(function(index, obj){
            data[obj.name] = obj.value;
        });


        var jsonData = JSON.stringify(data);

        $.ajax({
            contentType : 'application/json; charset=utf-8',
            type: frm.attr('method'),
            url: frm.attr('action'),
            dataType : 'json',
            data : jsonData,
            success : function(callback){
                /*alert("Response: Name: "+callback.name+"  DOB: "+callback.dob+"  Email: "+callback.email+"  Phone: "+callback.phone);*/
                /*alert("Response here");*/
                console.log("in success of Ajax call ADD ACCOUNT");
                hideModalAddAccount();
                /*$(this).html("Success!");*/
            },
            error : function(){
                $(this).html("Error!");
            }
        });
    });
});


function hideModalAddAccount(){
    $("#accAccountModal").modal('hide');
};


$(document).ready(function () {
    $("input#submit").click(function(){
        console.log("button pushed");


        var formdata = $("#myform").serializeArray();
        var data = {};
        $(formdata).each(function(index, obj){
            data[obj.name] = obj.value;
        });


        /*$.ajax({
         type: "POST",
         url: "process.php", //
         data: $('form.contact').serialize(),
         success: function(msg){
         $("#thanks").html(msg)
         $("#form-content").modal('hide');
         },
         error: function(){
         alert("failure");
         }
         });*/
    });
});
