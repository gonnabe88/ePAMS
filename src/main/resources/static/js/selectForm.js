$(document).ready(function () {
    // 모달 내에서 Select2 초기화
    $('#list').select2({
        dropdownParent: $('#selectModal'), // 모달 내에서 Select2를 렌더링하도록 설정
        placeholder: "옵션을 선택하세요", // 기본 텍스트
        allowClear: true // 기본 옵션 제거 기능
    });

    // 모달 열기 버튼 클릭 시 모달 표시
    $('#openSelectModal').on('click', function () {
        $('#selectModal').modal('show');
    });

    // 페이지 로딩 시 전체 목록 가져오기
    $.ajax({
        url: "/api/commoncode/code/DTM_REASON_CD",
        method: 'GET',
        success: function (data) {
            const $select = $('#list');
            $select.empty(); // 기존 옵션 삭제
            $select.append(new Option("전체", "")); // 기본 옵션 추가
            data.forEach(item => {
                $select.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
            });
            $select.trigger('change'); // 옵션 새로고침
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data: ', error);
        }
    });

    // category 변화 감지
    $('input[name="category"]').on('change', function () {
        const selectedValue = $(this).attr('id');
        let url = "/api/commoncode/code/DTM_REASON_CD";

        if (selectedValue !== 'allCategory') {
            url += "/" + selectedValue;
        }

        // AJAX 요청
        $.ajax({
            url: url,
            method: 'GET',
            success: function (data) {
                console.log(data); // 받아온 데이터 출력
                const $select = $('#list');
                $select.empty(); // 기존 옵션 삭제
                if (selectedValue === 'allCategory') {
                    $select.append(new Option("전체", "")); // 기본 옵션 추가
                }
                data.forEach(item => {
                    $select.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
                });
                $select.trigger('change'); // 옵션 새로고침
            },
            error: function (xhr, status, error) {
                console.error('Error fetching data: ', error);
            }
        });
    });

    // 선택 저장 버튼 클릭 시
    $('#saveSelection').on('click', function () {
        const selectedValue = $('#list').val();
        console.log("선택된 값: ", selectedValue);
        $('#selectModal').modal('hide'); // 모달 닫기
    });
});
