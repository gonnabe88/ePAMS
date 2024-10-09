window.tabulator = window.tabulator || {}; // 네임스페이스 생성
window.tabulator.url = "/api/admin/htmlLangMap"
window.tabulator.columns = [
    { title: "화면명", field: "html", sorter: "string", minWidth: 100,
        editor: function(cell, onRendered, success) {
        return searchEditor(cell, onRendered, success, '/api/admin/html', 'html', 'htmlName');} },
    { title: "코드명", field: "lang", sorter: "string", minWidth: 100,
        editor: function(cell, onRendered, success) {
        return searchEditor(cell, onRendered, success, '/api/admin/lang', 'lang', 'langName'); } },
    { title: "변경일자", field: "updatedTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}},
    { title: "등록일자", field: "createdTime", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}}
]

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.tabulator.columns, window.tabulator.url);
});