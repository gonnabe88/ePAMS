// 화면 로드 시 수행
$(function () {
    $("#webauthnRevokeBtn").on("click", function () {
        // 해제 버튼 클릭 시 간편인증
        webauthnRevokePopup();
    });

    $("#webauthnRegBtn").on("click", function () {
        // 등록 버튼 클릭 시 간편인증
        webauthnPopup("REGIST");
    });
});

// 데이터 테이블
window.tabulator = window.tabulator || {}; // 네임스페이스 생성
window.tabulator.url = "/api/loginManager/myLoginLog";
window.tabulator.columns = [
    { title: "인증시간", field: "createdTime", sorter: "datetime", sorterParams: { format: "yyyy-MM-dd HH:mm:ss" }, widthGrow: 3 },
    { title: "결과", field: "loginResult", sorter: "number", hozAlign: "center", formatter: "tickCross", widthGrow: 1 },
    { title: "인증방식", field: "loginType", sorter: "string", widthGrow: 2 },
];
document.addEventListener("DOMContentLoaded", function () {
    createTable(false, window.tabulator.columns, window.tabulator.url);
});
