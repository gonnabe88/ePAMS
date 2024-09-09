$(document).ready(function() {
    // 현재 URL 경로를 가져옵니다.
    var currentPath = window.location.pathname;
    
    // 현재 페이지가 '/index'인지 확인합니다.
    if (currentPath === '/index') {
        // /index 페이지일 경우
        $('#scrollToSearchDiv').removeAttr("hidden"); // scrollToSearchDiv를 보여줌
    } else {
        // /index 페이지가 아닐 경우
        $('#goToHome').removeAttr("hidden");         // goToHome을 보여줌
    }

});

$(document).on("visibilitychange", function() {
    if(document.visibilityState === "visible") {
        console.log("Visible");
        checkSession();
    }
});

const checkSession = () => {
    $.ajax({
        url:'/check-session',
        method: 'GET',
        success: function(data, testStatus, xhr) {
            if(!data.sessionValid) {
                popupCustBtnReHtmlMsg('인증 만료', '15분 이상 요청이 없어 정보보호를 위해 인증을 종료하였습니다. 다시 로그인해주시기 바랍니다.', 'info', '/login', '로그인');
            }
        },
        error: function(xhr, textStatus, errorThrown){
            popupCustBtnReHtmlMsg('알 수 없는 오류', '사용자 정보 확인 과정에서 알 수 없는 오류가 발생하여 인증을 종료하였습니다. 다시 로그인해주시기 바랍니다.', 'error', '/login', '로그인');
        }
    })
}