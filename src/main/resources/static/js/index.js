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
        // 인풋 요소의 오프셋 위치를 가져옴
        var offset = $(this).offset().top;
        
        // 스크롤 애니메이션으로 인풋을 화면 최상단에 위치시킴
        $('html, body').animate({
            scrollTop: offset
        }, 300); // 300ms의 애니메이션 지속 시간
    });

});
