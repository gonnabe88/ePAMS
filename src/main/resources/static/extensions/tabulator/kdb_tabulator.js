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
        ajaxResponse: function(url, params, response) {
            console.log("Server response:", response); // 서버 응답 로그
            return response; // 서버에서 받은 데이터를 그대로 반환
        },
        height: "900px",
        layout: "fitColumns",
        placeholder: "No Data Set",
        pagination: "local",
        paginationSize: 30,
        paginationSizeSelector: [10, 20, 30, 50, 100],
        movableColumns: true,
        paginationCounter: "rows",

        resizableRows: true,
        //resizableRowGuide: true,
        //resizableColumnGuid: true,

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
            resizable: true,
        },
        columns: finalColumns,
        sortOrderReverse: true,
        initialSort: [
            //{column: "createdTime", dir: "desc"}
        ],

    });

    // 셀이 편집될 때 호출되는 이벤트 핸들러
    table.on("cellEdited", function (cell) {
        var row = cell.getRow();
        var rowData = row.getData();
    
        if (addedRowIds.has(rowData.id)) {
            // 추가된 행인 경우, editedData에 추가하지 않고 addedData에 업데이트
            console.log("Added row edited id:", rowData.id);
            let existingAddedData = addedData.find(item => item.id === rowData.id);
            if (existingAddedData) {
                Object.assign(existingAddedData, rowData); // 데이터를 업데이트
            } else {
                addedData.push(rowData); // 만약 addedData에 없다면 추가
            }
        } else {
            // 기존 행의 경우 editedData에 추가
            console.log("Edited row id:", rowData.id);
            let existingEditedData = editedData.find(item => item.id === rowData.id);
            if (!existingEditedData) {
                editedData.push(rowData);
            } else {
                Object.assign(existingEditedData, rowData); // 데이터를 업데이트
            }
        }
    
        cell.getElement().classList.add("highlight-cell");
    });

    // 행이 추가될 때 호출되는 이벤트 핸들러
    table.on("rowAdded", function (row) {
        var rowData = row.getData();
        
        // ID가 없으면 고유한 ID 생성
        if (!rowData.id) {
            rowData.id = 'row-' + Math.random().toString(36).substr(2, 9);
            row.update(rowData);
        }
    
        console.log("Added row:", rowData);
    
        if (!addedRowIds.has(rowData.id)) {
            addedData.push(rowData);
            addedRowIds.add(rowData.id); // 추가된 행의 ID를 저장
        }
    });

    document.getElementById("add-row").addEventListener("click", function () {
        table.addRow({}, false).then(function(row) {
            // 고유한 ID 생성
            var uniqueId = 'row-' + Math.random().toString(36).substr(2, 9);
            row.update({ id: uniqueId }); // 행에 고유한 ID 할당
    
            // 새 행을 첫 번째 위치로 이동
            table.moveRow(row, table.getRows()[0], true);
    
            // 추가된 행에 스타일 적용
            row.getCells().forEach(function(cell) {
                cell.getElement().classList.add("highlight-cell");
            });
        });
    });

    // 행이 삭제될 때 호출되는 이벤트 핸들러
    table.on("rowDeleted", function (row) {
        var rowData = row.getData();
        console.log("Deleted row:", rowData);
        console.log("Deleted row id:", rowData.id);
    
        // 삭제된 행이 addedData에 있을 경우 제거
        addedData = addedData.filter(item => item.id !== rowData.id);
    
        // deletedData에 삭제된 행 추가
        deletedData.push(rowData);
    
        // addedRowIds에서 삭제된 행의 ID 제거
        addedRowIds.delete(rowData.id);
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

    // 데이터 저장 버튼 클릭 시 호출되는 함수
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
        createTable(!currentSetting, window.tabulator.columns, window.tabulator.url);
    });

    // CSV 다운로드 기능
    document.getElementById("download-csv").addEventListener("click", function () {
        table.download("csv", "data.csv", {bom: true}); // UTF-8 BOM을 추가하여 다운로드
    });

// CSV 업로드 기능
document.getElementById("upload-csv").addEventListener("click", function () {
    var fileInput = document.getElementById("upload-csv-input");
    if (fileInput) { // 파일 입력 요소가 존재하는지 확인
        var file = fileInput.files[0]; // 선택한 파일 가져오기
        if (file) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var csvData = e.target.result;
                try {
                    var parsedData = Papa.parse(csvData, {
                        header: true,
                        skipEmptyLines: true, // 빈 줄 건너뛰기
                        complete: function(results) {
                            // 컬럼 title을 field로 매핑하는 객체 생성
                            var columnMap = {};
                            var editableFields = new Set(); // 입력 가능한 필드를 저장할 집합

                            table.getColumns().forEach(function(column) {
                                var columnDef = column.getDefinition();
                                if (columnDef.title && column.getField()) {
                                    columnMap[columnDef.title] = column.getField();
                                    // editor가 'input'인 컬럼의 field 저장
                                    if (columnDef.editor === "input") {
                                        editableFields.add(column.getField());
                                    }
                                }
                            });

                            // title을 field명으로 변환하고, editor가 'input'이 아닌 경우 필터링
                            var mappedData = results.data.map(row => {
                                var newRow = {};
                                for (var key in row) {
                                    if (columnMap[key] && editableFields.has(columnMap[key])) {
                                        // editor가 'input'인 경우에만 데이터 추가
                                        newRow[columnMap[key]] = row[key];
                                    }
                                }
                                return newRow;
                            });

                            // 빈 행 필터링
                            var filteredData = mappedData.filter(row => Object.values(row).some(val => val));

                            // 기존 데이터 목록 맨 앞에 추가
                            table.addData(filteredData, true, "top").then(function() {
                                console.log("CSV Data Uploaded and Appended at the Top Successfully:", filteredData);
                                // 테이블의 모든 행을 가져와서 처리
                                table.getRows().slice(0, filteredData.length).forEach(function(row) {
                                    row.getCells().forEach(function(cell) {
                                        cell.getElement().classList.add("highlight-cell"); // 새로 추가된 셀에 배경색 클래스 추가
                                    });
                                });
                            }).catch(function(error) {
                                console.error("Error Adding Data:", error.message);
                                alert("Error Adding Data: " + error.message);
                            });
                        },
                        error: function(err) {
                            console.error("CSV Parsing Error:", err.message);
                            alert("CSV Parsing Error: " + err.message);
                        }
                    }); // CSV 파싱 (헤더 포함)
                } catch (error) {
                    console.error("Error during CSV parsing:", error.message);
                    alert("Error during CSV parsing: " + error.message);
                }
            };
            reader.onerror = function (e) {
                console.error("File Reading Error:", e.target.error.message);
                alert("File Reading Error: " + e.target.error.message);
            };
            reader.readAsText(file, 'UTF-8'); // 인코딩을 UTF-8로 지정
        } else {
            alert("No file selected.");
        }
    } else {
        alert("File input element not found.");
    }
});


}
