$(function(){
    $('#totopscroller').totopscroller({
        showToBottom: true,
        showToPrev: true,
        link: false,
        linkTarget: '_self',
        toTopHtml: '<a href="#"></a>',
        toBottomHtml: '<a href="#"></a>',
        toPrevHtml: '<a href="#"></a>',
        linkHtml: '<a href="#"></a>',
        toTopClass: 'totopscroller-top',
        toBottomClass: 'totopscroller-bottom',
        toPrevClass: 'totopscroller-prev',
        linkClass: 'totopscroller-lnk'
    });
})