window.tabulator = window.tabulator || {}; // 네임스페이스 생성
window.tabulator.url = "/api/admin/role";
window.tabulator.columns = [
    { title: "행번", field: "username", sorter: "string", editor: userSearchEditor, minWidth: 100 },
    { title: "역할", field: "roleId", sorter: "string", minWidth: 100, editor: "list", editorParams: { values: { ROLE_ADMIN: "최고관리자", ROLE_NORMAL: "일반사용자" } } },
    { title: "변경일자", field: "updatedTime", sorter: "date", minWidth: 100 },
    { title: "생성일자", field: "createdTime", sorter: "date", minWidth: 100 },
];

document.addEventListener("DOMContentLoaded", function () {
    createTable(false, window.tabulator.columns, window.tabulator.url, "admin");
});

// JavaScript 코드
document.addEventListener("DOMContentLoaded", function () {
    const hasSubItems = document.querySelectorAll(".org-item.has-sub > a");

    hasSubItems.forEach((item) => {
        item.addEventListener("click", function (event) {
            event.preventDefault();
            const parentItem = this.parentElement;
            const subMenu = parentItem.querySelector(".org");

            // 현재 클릭한 항목의 모든 자식 항목을 찾기
            const allSubMenus = parentItem.parentElement.querySelectorAll(".org-item.has-sub .org");

            // 다른 열린 하위 메뉴를 닫기
            allSubMenus.forEach((menu) => {
                if (menu !== subMenu) {
                    menu.style.display = "none";
                    menu.parentElement.classList.remove("open");
                }
            });

            // 현재 클릭한 항목을 열거나 닫기
            if (parentItem.classList.contains("open")) {
                subMenu.style.display = "none";
                parentItem.classList.remove("open");
            } else {
                subMenu.style.display = "block";
                parentItem.classList.add("open");
            }
        });
    });
});
