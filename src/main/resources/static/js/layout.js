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
        checkSession();
    }
});