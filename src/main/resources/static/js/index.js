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

        // 현재 스크롤 위치를 가져옴
        var currentScroll = $(window).scrollTop();

        // 이동해야 할 거리 계산 (오프셋 위치에서 현재 스크롤 위치를 뺀 값)
        var distanceToScroll = offset - currentScroll;

        // 스크롤 애니메이션으로 계산된 거리만큼 스크롤
        $('html, body').animate({
            scrollTop: currentScroll + distanceToScroll
        }, 300); // 300ms의 애니메이션 지속 시간
    });

});
