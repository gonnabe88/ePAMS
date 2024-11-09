window.tabulator = window.tabulator || {}; // 네임스페이스 생성
window.tabulator.url = "/api/admin/member";
window.tabulator.columns = [
    { title: "행번", field: "username", sorter: "string", minWidth: 100 },
    { title: "이름", field: "realname", sorter: "string", minWidth: 100 },
    { title: "부서", field: "deptName", sorter: "string", minWidth: 100 },
    { title: "팀", field: "teamName", sorter: "string", minWidth: 100 },
    { title: "상세업무", field: "jobDetail", sorter: "string", minWidth: 100 },
    { title: "내선번호", field: "inlineNumber", sorter: "string", minWidth: 100 },
    { title: "변경일자", field: "createdDate", sorter: "datetime", sorterParams: { format: "yyyy-MM-dd HH:mm:ss" } },
];

document.addEventListener("DOMContentLoaded", function () {
    createTable(false, window.tabulator.columns, window.tabulator.url, "admin");
});
