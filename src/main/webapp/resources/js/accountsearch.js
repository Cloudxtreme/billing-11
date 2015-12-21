$(document).ready(function(){
    $('#searchButton').on('click', function () {
        var data = $('#searchInput').val();
        $.ajax({
            url:'../accounts/accountsearch?searchValues='+data,
            type: "POST"
        })
    })
});
