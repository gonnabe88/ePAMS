window.tabulator = window.tabulator || {}; // 네임스페이스 생성
window.tabulator.url = "/api/admin/loginotp"
window.tabulator.columns = [
    { title: "생성시간", field: "createdTime", sorter: "string", minWidth: 100 },
    { title: "사용자ID", field: "username", sorter: "string", minWidth: 100 },
    { title: "OTP번호", field: "otp", sorter: "string", editor: "input" },
    { title: "인증방식", field: "mfa", sorter: "string" }
]

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.tabulator.columns, window.tabulator.url);
});