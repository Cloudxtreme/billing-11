function parseDateTOStringWithFormat(dateToParse) {
    if (dateToParse != 0) {
        var now = new Date(dateToParse);
        var day = ("0" + now.getDate()).slice(-2);
        var month = ("0" + (now.getMonth() + 1)).slice(-2);
        return now.getFullYear() + "-" + (month) + "-" + (day);
    } else {
        return "";
    }
}

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

function scrollToElement(ele) {
    var elOffset = ele.offset().top;
    var elHeight = ele.height();
    var windowHeight = $(window).height();
    var offset;

    if (elHeight < windowHeight) {
        offset = elOffset - ((windowHeight / 2) - (elHeight / 2));
    }
    else {
        offset = elOffset;
    }

    $.smoothScroll({ speed: 700 }, offset);
    return false;
}

function testDate(str) {
    if (str.length > 0) {
        var t = str.match(/^(\d{4})\-(\d{2})\-(\d{2})$/);
        if (t === null)
            return false;
        var d = +t[3], m = +t[2], y = +t[1];
        if (m >= 1 && m <= 12 && d >= 1 && d <= 31) {
            return true;
        }
        return false;
    }
    return true;
}

jQuery.fn.shake = function(intShakes, intDistance, intDuration) {
    this.each(function() {
        for (var x=1; x<=intShakes; x++) {
            $(this).animate({left:(intDistance*-1)}, (((intDuration/intShakes)/4)))
                .animate({left:intDistance}, ((intDuration/intShakes)/2))
                .animate({left:0}, (((intDuration/intShakes)/4)));
        }
    });
    return this;
};
