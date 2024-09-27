window.url = "/api/admin/member"
window.columns = [
    { title: "행번", field: "username", sorter: "string", editor: "input", minWidth: 100 },
    { title: "이름", field: "realname", sorter: "string", editor: "input", minWidth: 100 },
    { title: "변경일자", field: "createdDate", sorter: "datetime", sorterParams:{format:"yyyy-MM-dd HH:mm:ss"}}
]

document.addEventListener("DOMContentLoaded", function() {
    createTable(false, window.columns, window.url);
});