window.url = "/api/admin/htmlLangMap"
window.columns = [
    { title: "화면명", field: "html", sorter: "string", editor: function(cell, onRendered, success, cancel) { return searchEditor(cell, onRendered, success, cancel, '/api/admin/html', 'html'); },  minWidth: 100 },
    { title: "코드명", field: "lang", sorter: "string", editor: function(cell, onRendered, success, cancel) { return searchEditor(cell, onRendered, success, cancel, '/api/admin/lang', 'lang'); }, minWidth: 100 },
    { title: "변경일자", field: "updatedTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}},
    { title: "등록일자", field: "createdTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}}
]

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.columns, window.url);
});