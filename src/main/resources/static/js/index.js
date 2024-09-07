$(document).ready(() => {

    // 'save' 버튼 클릭 시 save2() 함수 호출
    $('.appl-alert-button').on('click', function () {
        ApplAlertPopup(this);
    });

    // 'scrollToSearchDiv' 버튼 클릭 시 scrollToDiv() 함수 호출
    $('#scrollToSearchDiv').on('click', function () {
        scrollToDiv();
    });

    // 로그인 페이지에서 뒤로가기 시 처리
    document.addEventListener("DOMContentLoaded", function () {
        // 현재 페이지가 로그인 화면인지 확인
        const isLoginPage = window.location.pathname.includes("/login");  // 로그인 페이지의 경로에 맞게 수정

        if (isLoginPage) {
            // 뒤로가기 이벤트 리스너 추가
            window.addEventListener('popstate', function (event) {
                // 뒤로가기로 로그인 페이지에 도달했을 때 처리
                //window.location.replace("/index");  // index 페이지로 리다이렉트
                console.log("뒤로가기");
            });
        }
    });
});
