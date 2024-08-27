window.url = "/api/admin/login"
window.columns = [
    { title: "행번", field: "empNo", headerHozAlign: "center", sorter: "string", minWidth: 100},
    { title: "인증방식", field: "loginType", sorter: "string", minWidth: 100 },
    { title: "인증결과", field: "loginResult", sorter: "number" , formatter:"tickCross"},
    { title: "인증시간", field: "createdTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}}
];
document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.columns, window.url);

});