$(document).ready(() => {

    // 'save' 버튼 클릭 시 save2() 함수 호출
    $('.appl-alert-button').on('click', function() {
        ApplAlertPopup(this);
    });

    // 'scrollToSearchDiv' 버튼 클릭 시 scrollToDiv() 함수 호출
    $('#scrollToSearchDiv').on('click', function() {
        scrollToDiv();
    });

    // 'searchMember' 인풋을 클릭했을 때 스크롤을 최상단으로 이동시키는 이벤트
    $('#searchMember').on('click', function() {

    });

});
