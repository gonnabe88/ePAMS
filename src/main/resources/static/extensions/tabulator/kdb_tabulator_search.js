const searchEditor = (cell, onRendered, success, apiUrl, key, keyName) => {
    // 입력 요소 생성 및 스타일 적용
    const cellValue = cell.getValue() || ""; // 값이 없을 경우 빈 문자열로 처리
    const $input = $("<input>").css({
        width: "100%",
        height: "100%",
        boxSizing: "border-box"
    }).val(cellValue);

    // 컨테이너 요소 생성 및 입력 필드 추가
    const $container = $("<div>").addClass("d-flex align-items-center"); // 수직 가운데 정렬
    $container.append($input);

    // 렌더링 후 Awesomplete 초기화
    onRendered(() => {
        $input.focus(); // 입력 필드에 포커스
        $input.css("height", "100%"); // 높이 조정

        // Awesomplete 초기화
        const awesomplete = new Awesomplete($input[0], {
            minChars: 1, // 최소 입력 문자 수
            maxItems: 10, // 최대 표시 항목 수
        });

        // 부모 셀과 행 가져오기
        const $parentCell = $(cell.getElement()); // 셀 요소 가져오기
        const $parentRow = $parentCell.closest('.tabulator-row'); // 해당 셀이 포함된 행 가져오기

        // 부모 요소의 overflow 속성을 visible로 설정하여 드롭다운이 잘리거나 숨겨지지 않도록 처리
        $parentCell.css("overflow", "visible");
        $parentRow.css("overflow", "visible");

        // 입력 시 API로부터 자동 완성 데이터를 가져옴
        $input.on("input", () => {
            const query = $input.val();
            if (query.length > 0) {
                $.ajax({
                    url: apiUrl + '?query=' + query,
                    method: 'GET',
                    dataType: 'json',
                    success: (data) => {
                        // Awesomplete에 사용할 목록을 설정
                        const suggestions = data.map((item) => `${item[key]}(${item[keyName]})`); // API에서 받은 데이터를 사용하여 자동 완성 리스트 구성
                        awesomplete.list = suggestions; // Awesomplete 리스트에 적용
                    },
                    error: (error) => {
                        console.error('데이터 로드 오류:', error);
                    }
                });
            }
        });

        // Awesomplete에서 선택된 항목 처리
        $input.on('awesomplete-selectcomplete', (event) => {
            const selectedCode = event.text.value; // 선택된 값 가져오기
            success(selectedCode); // 선택된 값을 Tabulator로 넘김
        });

        // Awesomplete 드롭다운 스타일 조정
        const $dropdown = $(".awesomplete ul");
        $dropdown.css({
            zIndex: "10000", // 다른 요소보다 위에 표시
            position: "absolute" // 입력 필드에 상대적인 위치 설정
        });
    });

    return $container[0]; // jQuery 객체에서 DOM 요소를 반환
};
