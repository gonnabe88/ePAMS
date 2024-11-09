window.tabulator = window.tabulator || {}; // 네임스페이스 생성
window.tabulator.url = "/api/admin/html";
window.tabulator.columns = [
    { title: "화면경로", field: "html", sorter: "string", minWidth: 100, editor: "input" },
    { title: "화면이름", field: "htmlName", sorter: "string", minWidth: 100, editor: "input" },
    { title: "갱신일자", field: "updatedTime", sorter: "date" },
    { title: "등록일자", field: "createdTime", sorter: "date" },
];

document.addEventListener("DOMContentLoaded", function () {
    createTable(false, window.tabulator.columns, window.tabulator.url, "admin");
});
