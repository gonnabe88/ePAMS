window.tabulator = window.tabulator || {}; // 네임스페이스 생성
window.tabulator.url = "/api/admin/lang"
window.tabulator.columns = [
    { title: "코드", field: "lang", sorter: "string", editor: "input"},
    { title: "코드명", field: "langName", sorter: "string", editor: "textarea", formatter:"textarea", minWidth: 100, widthGrow: 2},
    { title: "코드타입", field: "langType", sorter: "string", editor: "input" },
    { title: "변경일자", field: "updatedTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}},
    { title: "등록일자", field: "createdTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}}
]

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.tabulator.columns, window.tabulator.url);
});