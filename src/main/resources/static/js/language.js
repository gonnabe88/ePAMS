window.url = "/api/admin/lang"
window.columns = [
    { title: "코드", field: "lang", sorter: "string", editor: "input"},
    { title: "코드명", field: "langName", sorter: "string", editor: "textarea", formatter:"textarea", minWidth: 100, widthGrow: 2},
    { title: "코드타입", field: "langType", sorter: "string", editor: "input" },
    { title: "변경일자", field: "updatedTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}},
    { title: "등록일자", field: "createdTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}}
]

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.columns, window.url);
});