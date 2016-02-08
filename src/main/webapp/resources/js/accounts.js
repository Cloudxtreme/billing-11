var pageResults = 20;

$(document).ready(function () {
    $('#confirm-delete').on('show.bs.modal', function(e) {
        $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
    });

    $(".alert-success").fadeOut(2500);
});

$(function() {
    // set active navigation tab "Accounts"
    console.log("start js onLoad");
    $("li").removeClass('active');
    renderAccountsTable(pageResults, 1);
    hideShowLegalAddress();
    $("[name='softblock']").bootstrapSwitch();


    $('input[name="softblock"]').on('switchChange.bootstrapSwitch', function() {
        $.ajax({
            url: '../../changeSoftBlockStatus?serviceId='+this.id,
            type: "get",
            dataType: "json"
        });
    });

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
        };

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
            dataType : 'json',
            data : jsonData,
            success : function(callback){
                console.log("in success of Ajax call ADD ACCOUNT");
                if(id > 0){
                    document.getElementById('successMessageEditAccount').style.display = 'block';
                    setTimeout(function () {
                        $("#successMessageEditAccount").fadeOut(3000);
                    });
                }else{
                    document.getElementById('successMessageADD').style.display = 'block';
                    setTimeout(function () {
                        $("#successMessageADD").fadeOut(3000);
                    });
                }
                hideModalAddAccount();
                clearForm();
                /*$(this).html("Success!");*/
                renderAccountsTable(pageResults, currentPageNum);
            },
            error : function(){
                document.getElementById('errorMessageACC').style.display = 'block';
                setTimeout(function () {
                    $("#errorMessageACC").fadeOut(15000);
                });
                $(this).html("Error!");
            }
        });
    });
});

$(document).ready(function(){
    setTimeout(function(){
        $('.alert').fadeOut(5000);
    })
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

String.prototype.capitalize = function() {
    return this.replace(/(?:^|\s)\S/g, function(a) { return a.toUpperCase(); });
};

$(document).ready(function() {
    var obj = {
        value: $('#phyAddressStreetId').val(),
        text: $('#phyAddressStreet').val()
    };
    $('#phyAddressStreet').typeahead({
        onSelect: function(item) {
            console.log(item);
            $('#phyAddressStreetId').val(item.value);
            obj = item;
        },
        ajax: {
            url: ".././getListOfStreets.html",
            timeout: 500,
            displayField: "name",
            triggerLength: 1,
            method: "post",
            loadingClass: "loading-circle"

        }
    });

    $('#fullAccountForm').submit(function(){
        var valueId = $('#phyAddressStreetId').val();
        console.log(valueId);
        var valueName = $('#phyAddressStreet').val();
        console.log(valueName);
        if(obj.value == valueId && valueName != obj.text){
            $('#phyAddressStreetId').val('');
            var capitalizeVal = $('#phyAddressStreet').val().capitalize();
            $('#phyAddressStreet').val(capitalizeVal);
        }
    });
});


$(document).ready(function() {
    var obj1 = {
        value: $('#legalAddressStreetId').val(),
        text: $('#legalAddressStreet').val()
    };
    $('#legalAddressStreet').typeahead({
        onSelect: function(item) {
            console.log(item);
            $('#legalAddressStreetId').val(item.value);
            obj1 = item;
        },
        ajax: {
            url: ".././getListOfStreets.html",
            timeout: 500,
            displayField: "name",
            triggerLength: 1,
            method: "post",
            loadingClass: "loading-circle"

        }
    });

    $('#fullAccountForm').submit(function(){
        var valueId = $('#legalAddressStreetId').val();
        console.log(valueId);
        var valueName = $('#legalAddressStreet').val();
        console.log(valueName);
        if(obj1.value == valueId && valueName != obj1.text){
            $('#legalAddressStreetId').val('');
            var capitalizeVal = $('#legalAddressStreet').val().capitalize();
            $('#legalAddressStreet').val(capitalizeVal);
        }
    });


})



function hideModalAddAccount(){
    $("#accAccountModal").modal('hide');
}


function renderAccountsTable(){
    $.ajax({
        url: 'accountsShortList?rows=0&page=0',
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
        url: 'accountsShortList?rows='+rows+'&page='+page,
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
            "<a data-href=\"delete/" + rowData.id +"\" class=\"pushDelete\" data-toggle=\"modal\" data-id=\"" + rowData.id + "\" data-toggle='modal' data-target='#confirm-delete' \"><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></a></td>"
    ));
    row.append($("<td><a href=\"editFull/" + rowData.id + "\" > " + rowData.accountName + " </a></td>"));
    row.append($("<td><a href=\"editFull/" + rowData.id + "\" > " + rowData.fio + " </a></td>"));
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
    var valueSelected = $("#accountType").val();
    if (valueSelected == "PRIVATE"){
        $("#legAddrBlock").hide();
    }
    if (valueSelected == "LEGAL"){
        $("#legAddrBlock").show();
    }
}

$(document).ready(function () {
    $('#transactionListLimit').on('change', function () {
        ajaxBuildTransactionTable();
    });

});

function ajaxBuildTransactionTable() {
    var accountId = ($("#accountId").text() === "") ? 0 : $("#accountId").text();
    var limit = $('#transactionListLimit').find(':selected').val();

    $.ajax({
        url: '../../../transaction/buildTransactionTable?accountId='+accountId+'&limit='+limit,
        type: "get",
        dataType: "json",
        success: function(data) {
            drawTransTable(data);
        }
    });
}

function drawTransTable(data){
    $("#transactionTable").find("tr:gt(0)").remove();
    for (var i = 0; i < data.length; i++) {
        drawTransRow(data[i]);
    }
}

function drawTransRow(data){
    var date = new Date(data.date);
    var tableRow = "<tr id=" + data.id + ">" +
        "<td>"+ data.account.accountName +"</td>" +
        "<td>"+ date.format('yyyy-mm-dd HH:MM') +"</td>" +
        "<td>"+ data.direction +"</td>" +
        "<td>"+ data.source +"</td>" +
        "<td>"+ data.price +"</td>" +
        "<td>"+ data.comment +"</td>" +
        "</tr>";
    $("#transactionTable").append(tableRow);
}

