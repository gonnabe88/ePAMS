// 화면 로드 시 수행
$(function() {
    $('#webauthnRevokeBtn').on('click', function() {
        // 해제 버튼 클릭 시 간편인증
       webauthnRevokePopup();
    });

    $('#webauthnRegBtn').on('click', function() {
        // 등록 버튼 클릭 시 간편인증
        webauthnPopup("REGIST");
    });
});
