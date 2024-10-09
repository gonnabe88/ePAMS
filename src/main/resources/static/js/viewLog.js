window.tabulator = window.tabulator || {}; // 네임스페이스 생성
window.tabulator.url = "/api/admin/viewlog"
window.tabulator.columns = [
    { title: "호출시간", field: "createdTime", sorter: "string", minWidth: 100 },
    { title: "요청URL", field: "requestUrl", sorter: "string", minWidth: 100 },
    { title: "사용자IP", field: "clientIp", sorter: "string" },
    { title: "View", field: "controllerName", sorter: "string" },
    { title: "Method", field: "methodName", sorter: "string" },
    { title: "단말정보", field: "userAgent", sorter: "string"},
]

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.tabulator.columns, window.tabulator.url);
});