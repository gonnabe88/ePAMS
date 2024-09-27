window.url = "/api/admin/viewlog"
window.columns = [
    { title: "호출시간", field: "createdTime", sorter: "string", minWidth: 100 },
    { title: "요청URL", field: "requestUrl", sorter: "string", minWidth: 100 },
    { title: "사용자IP", field: "clientIp", sorter: "string" },
    { title: "View", field: "controllerName", sorter: "string" },
    { title: "Method", field: "methodName", sorter: "string" },
    { title: "단말정보", field: "userAgent", sorter: "string"},
]

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.columns, window.url);
});