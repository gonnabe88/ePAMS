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

// 'searchMember' 인풋을 클릭했을 때 스크롤을 최상단으로 이동시키는 이벤트
$('#searchMember').on('click', function() {
    // 인풋 요소의 오프셋 위치를 가져옴
    var offset = $(this).offset().top;

    // 모바일 환경에서는 추가적인 여유 공간을 설정합니다 (키패드에 가리지 않도록)
    var additionalOffset = 0;

    // 모바일 기기인지 확인 (간단한 userAgent 검사)
    if (/Mobi|Android|iPhone|iPad|iPod/.test(navigator.userAgent)) {
        additionalOffset = 50; // 필요에 따라 조정 가능, 키패드 높이 고려
    }

    // 스크롤 애니메이션으로 인풋을 화면 최상단에 위치시킴
    $('html, body').animate({
        scrollTop: offset - additionalOffset // 인풋 요소에 여유 공간 추가
    }, 300); // 300ms의 애니메이션 지속 시간
});

});
