$(document).ready(() => {

    // 'save' 버튼 클릭 시 save2() 함수 호출
    $('.appl-alert-button').on('click', function() {
        ApplAlert(this);
    });

    // 'searchMemberBtn' 버튼 클릭 시 search() 함수 호출
    $('#searchMemberBtn').on('click', function() {
        search();
    });

    // 'scrollToSearchDiv' 버튼 클릭 시 scrollToDiv() 함수 호출
    $('#scrollToSearchDiv').on('click', function() {
        scrollToDiv();
    });

});
