$(function() {
    // set active navigation tab "Accounts"
    console.log("start js onLoad");
    $("li").removeClass('active');
    $("#linkToAccounts").addClass('active');
    renderAccountsTable();
});


$(document).on("click", ".pushEdit", function () {
    console.log("pushEdit push");
    var accountId = $(this).data('id');
    $('#accAccountModal').modal('show');
    $("#accountName").val(accountId);

    $.ajax({
        url: 'getAccount?id='+accountId,
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            // since we are using jQuery, you don't need to parse response
            $("#id").val(data.id);
            $("#status").val(data.status);
            $("#currentBalance").val(data.currentBalance);
            $("#accountName").val(data.accountName);
            $('#accountType option[value=' + data.accountType + ']').prop("selected", "selected");

        },
        error : function(){
            console.log("error in ajax query getAccount");
            /*$(this).html("Error!");*/
        }
    });

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

        var id = $("#id").val();
        var action = frm.attr('action');
        if (id > 0){
            action = 'editAccount.html';
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
            url: action,
            dataType : 'json',
            data : jsonData,
            success : function(callback){
                /*alert("Response: Name: "+callback.name+"  DOB: "+callback.dob+"  Email: "+callback.email+"  Phone: "+callback.phone);*/
                /*alert("Response here");*/
                console.log("in success of Ajax call ADD ACCOUNT");
                hideModalAddAccount();
                clearForm();
                /*$(this).html("Success!");*/
                renderAccountsTable();
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


function renderAccountsTable(){
    $.ajax({
        url: 'accountsList?rows=0&page=0',
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            // since we are using jQuery, you don't need to parse response
            drawTable(data);
        }
    });
};

function drawTable(data) {
    $("#accountsTable").find("tr:gt(0)").remove();

    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
}

function drawRow(rowData) {
    var row = $("<tr />")
    $("#accountsTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
    row.append($("<td><a href=\"\" class=\"pushEdit\" data-toggle=\"modal\" data-id=\"" + rowData.id + "\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></a>" +
            "&nbsp&nbsp <a href=\"\" class=\"pushDelete\" data-toggle=\"modal\" data-id=\"" + rowData.id + "\"><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></a></td>"
    ));
    row.append($("<td><a href=\"editFull/" + rowData.id + "\" > " + rowData.accountName + " </a></td>"));
    row.append($("<td>" + rowData.accountType + "</td>"));
    row.append($("<td>" + rowData.currentBalance + "</td>"));
    row.append($("<td>" + rowData.status + "</td>"));

}


function clearForm(){
    $( '#crtAccountForm' ).each(function(){
        this.reset();
    });
};

/*
$(document).ready(function () {
    $("input#submit").click(function(){
        console.log("button pushed");


        var formdata = $("#myform").serializeArray();
        var data = {};
        $(formdata).each(function(index, obj){
            data[obj.name] = obj.value;
        });


        */
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
         });*//*

    });
});
*/
