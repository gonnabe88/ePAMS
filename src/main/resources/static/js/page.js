window.url = "/api/admin/html"
window.columns = [ {
    title : "화면경로",
    field : "html",
    sorter : "string",
    editor : "input",
    minWidth : 100
}, {
    title : "화면이름",
    field : "htmlName",
    sorter : "string",
    editor : "input",
    minWidth : 100
}, {
    title : "갱신일자",
    field : "updatedTime",
    sorter : "date"
}, {
    title : "등록일자",
    field : "createdTime",
    sorter : "date"
} ];

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.columns, window.url);
});