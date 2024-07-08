var editedData = [];
var addedData = [];
var deletedData = [];
var uploadedData = []; // CSV 업로드 데이터를 저장할 변수 (20240616 추가)
var existingData = []; // 기존 데이터 저장 변수 (20240616)
var addedRowIds = new Set(); // 추가된 행의 ID를 저장할 변수

// default 컬럼 설정 (20240616)
const defaultColumns = [
    {
        formatter: "rowSelection",
        titleFormatter: "rowSelection",
        headerSort: false,
        hozAlign: "center",
        width: 30,
        cellClick: function (e, cell) {
            e.preventDefault(); // 기본 동작 막기
            e.stopPropagation(); // 이벤트 전파 막기
            cell.getRow().toggleSelect();
        }
    },
    {
        formatter: "rownum",
        headerSort: false,
        hozAlign: "center",
        resizable: true,
        frozen: false,
        width: 3,
        headerFilter: "input"
    }
];

const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;

function createTable(selectableRange, columns, url) {

    const finalColumns = [
        ...defaultColumns,
        ...columns
    ];

    var table;

    table = new Tabulator("#example-table", {
        ajaxURL: url,
        height: "900px",
        layout: "fitColumns",
        placeholder: "No Data Set",
        pagination: "local",
        paginationSize: 30,
        paginationSizeSelector: [10, 20, 30, 50, 100],
        movableColumns: true,
        paginationCounter: "rows",

        //enable range selection
        selectableRange: selectableRange,
        selectableRangeColumns: true,
        selectableRangeRows: true,
        selectableRangeClearCells: true,

        clipboard: true,
        clipboardCopyStyled: false,
        clipboardCopyConfig: {
            rowHeaders: true,
            columnHeaders: true,
        },
        clipboardCopyRowRange: "range",
        clipboardPasteParser: "range",
        clipboardPasteAction: "range",

        headerSortClickElement: 'icon',

        editTriggerEvent: "click", //dblclick or click

        columnDefaults: {
            headerHozAlign: "center",
        },
        columns: finalColumns,
        sortOrderReverse: true,
        initialSort: [
            {column: "createdTime", dir: "desc"}
        ],
    });

    table.on("cellEdited", function (cell) {
        var row = cell.getRow().getData();
        if (!addedRowIds.has(row.id)) {
            console.log("Edited row:", row);
            editedData.push(row);
            cell.getElement().classList.add("highlight-cell");
        }
    });

    table.on("rowAdded", function (row) {
        var rowData = row.getData();
        console.log("Added row:", rowData);
        addedData.push(rowData);
        addedRowIds.add(rowData.id); // 추가된 행의 ID를 저장
        // Highlight all cells in the new row
        row.getCells().forEach(function (cell) {
            cell.getElement().classList.add("highlight-cell");
        });
    });

    table.on("rowDeleted", function (row) {
        var rowData = row.getData();
        console.log("Deleted row:", rowData);
        addedData = addedData.filter(item => item.id !== rowData.id);
        deletedData.push(rowData);
        addedRowIds.delete(rowData.id); // 삭제된 행의 ID를 제거
    });

    document.getElementById("add-row").addEventListener("click", function () {
        table.addData({}, true);
    });

    document.getElementById("del-row").addEventListener("click", function () {
        var selectedRows = table.getSelectedRows();
        selectedRows.forEach(function (row) {
            row.delete();
        });
    });

    document.getElementById("reload").addEventListener("click", function () {
        table.setData(url);
    });

    document.getElementById("save").addEventListener("click", function () {
        console.log('editedData:', editedData);
        console.log('addedData:', addedData);
        console.log('deletedData:', deletedData);

        var payload = {
            changed: editedData,
            added: addedData,
            deleted: deletedData
        };

        fetch(url + '/save', {
            method: 'POST',
            headers: {
                'header': header,
                'X-CSRF-Token': token,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data.message);
                alert(data.message);
                editedData = [];
                addedData = [];
                deletedData = [];
                addedRowIds.clear(); // 추가된 행의 ID 초기화
                // 새로고침 추가 (20240617)
                location.reload();
            })
            .catch((error) => {
                console.error('Error:', error.message);
                alert(error.message);
                editedData = [];
                addedData = [];
                deletedData = [];
                addedRowIds.clear(); // 추가된 행의 ID 초기화
            });
    });

    // 조회버튼 클릭 또는 엔터 시
    document.getElementById("search-button").addEventListener("click", performSearch);
    document.getElementById("search-input").addEventListener("keyup", function (event) {
        if (event.key === "Enter") {
            performSearch();
        }
    });

    function performSearch() {
        var searchValue = document.getElementById("search-input").value;
        if (searchValue) {
            // Get all columns from the table
            var columns = table.getColumns();
            // Create filter conditions for all columns
            var filters = columns.map(function (column) {
                return {field: column.getField(), type: "like", value: searchValue};
            });
            table.setFilter([filters]); // Nested array for multiple filters
        } else {
            table.clearFilter(); // Clear filter if search input is empty
        }
    }

    // 초기화버튼 엔터 또는 클릭 시
    document.getElementById("reset-button").addEventListener("click", function () {
        document.getElementById("search-input").value = "";
        table.clearFilter(); // Clear all filters
    });

    // 토글 버튼 이벤트 리스너 추가
    document.getElementById("toggle-selectable").addEventListener("click", function () {
        var currentSetting = table.options.selectableRange;
        table.destroy();
        createTable(!currentSetting, window.columns, window.url);
    });
}
