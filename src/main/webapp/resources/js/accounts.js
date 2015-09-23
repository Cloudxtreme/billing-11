var pageResults = 10;

$(function() {
    // set active navigation tab "Accounts"
    console.log("start js onLoad");
    $("li").removeClass('active');
    $("#linkToAccounts").addClass('active');
    renderAccountsTable(pageResults, 1);
    hideShowLegalAddress();
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

function goToPrevPage() {
    var currentPageNum = $("#pageNumber").text();
    console.log("goPrev push with " + currentPageNum);
    var nextPage = Number(currentPageNum)-1;
    if (nextPage != 0){
        renderAccountsTable(pageResults, nextPage);
    }
};

function goToNextPage() {
    var currentPageNum = $("#pageNumber").text();
    var totalPages = $("#totalPages").text();
    console.log("goNext push with " + currentPageNum);
    var nextPage = Number(currentPageNum)+1;
    if (nextPage <= totalPages){
        renderAccountsTable(pageResults, nextPage);
    }
};

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

        var currentPageNum = $("#pageNumber").text();

        //var jsonData = JSON.stringify(data);
        var jsonData = $('#crtAccountForm').serialize();

        $.ajax({
            type: frm.attr('method'),
            url: action,
            async: false,
            dataType : 'json',
            data : jsonData,
            success : function(callback){
                console.log("in success of Ajax call ADD ACCOUNT");
                hideModalAddAccount();
                clearForm();
                /*$(this).html("Success!");*/
                renderAccountsTable(pageResults, currentPageNum);
            },
            error : function(){
                $(this).html("Error!");
            }
        });
    });
});


$(document).ready(function() {
    $('#accauntSaveBut').click(function(e) {
        e.preventDefault();
        $('#fullAccountForm').submit();
    });
});

$(document).ready(function() {
    $('#accountType').on('change', function (e) {
        var valueSelected = this.value;
        if (valueSelected == "PRIVATE"){
            $("#legAddrBlock").hide();
        }
        if (valueSelected == "LEGAL"){
            $("#legAddrBlock").show();
        }
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


function renderAccountsTable(rows, page){
    console.log("page="+page+"row="+rows);
    $.ajax({
        url: 'accountsList?rows='+rows+'&page='+page,
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            // since we are using jQuery, you don't need to parse response
            drawTable(data);
            setCurrentPageNumber(page);
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
            "&nbsp&nbsp " +
            "<a href=\"delete/" + rowData.id +"\" class=\"pushDelete\" data-toggle=\"modal\" data-id=\"" + rowData.id + "\" onclick=\"return confirm('Do you really want to delete account?')\"><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></a></td>"
    ));
    row.append($("<td><a href=\"editFull/" + rowData.id + "\" > " + rowData.accountName + " </a></td>"));
    row.append($("<td>" + rowData.accountType + "</td>"));
    row.append($("<td>" + rowData.currentBalance + "</td>"));
    row.append($("<td>" + rowData.status + "</td>"));

}

function setCurrentPageNumber(number){
    $("#pageNumber").html(number);
};

function clearForm(){
    $( '#crtAccountForm' ).each(function(){
        this.reset();
    });
};

function hideShowLegalAddress(){
    var valueSelected = $("#accountType").val();;
    if (valueSelected == "PRIVATE"){
        $("#legAddrBlock").hide();
    }
    if (valueSelected == "LEGAL"){
        $("#legAddrBlock").show();
    }
}


