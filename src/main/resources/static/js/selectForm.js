/**
 * Select 드롭다운 폼 관련 스크립트(/js/selectForm.js)
 * @author K140024
 * @implements Select 드롭다운 폼 관련 스크립트
 *  - 페이지 로딩 시 전체 목록 가져오기
 *  - category 변화 감지
 *  - Select2 초기화
 * @since 2024-07-28
 */

$(document).ready(function () {

    // 페이지 로딩 시, th:attr에서 전달받은 값을 읽어옴
    const dtmReasonCd = $('#list').data('dtmreasoncd'); // data-dtmReasonCd 값 읽기

    // Select2 초기화
    $('#list').select2();

    // 전체 목록 가져오기
    $.ajax({
        url: "/api/commoncode/code/DTM_REASON_CD",
        method: 'GET',
        success: function (data) {
            const $select = $('#list');
            $select.empty(); // 기존 옵션 삭제
            //근태 신청화면에서는 전체선택 제외
            if(window.location.pathname != '/dtm/dtmReg'){
                $select.append(new Option("전체", "")); // 기본 옵션 추가
            }else {
                $select.append(new Option("","")) //선택 안됐을때는 null값
            }
            data.forEach(item => {
                $select.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
            });
            $select.trigger('change'); // 옵션 새로고침

            // dtmReasonCd 값이 있을 경우 해당 값을 선택
            if (dtmReasonCd) {
                $select.val(dtmReasonCd).trigger('change');
            } else {
                $select.val(null).trigger('change'); // 기본 옵션 선택
            }

        },
        error: function (xhr, status, error) {
            console.error('Error fetching data: ', error);
        }
    });

    // 카테고리 항목 선택 시 SELECT 요소 갱신
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
                $select.append(new Option("전체", "")); // 기본 옵션 추가
                data.forEach(item => {
                    $select.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
                });

                // dtmReasonCd 값이 있을 경우 해당 값을 선택
                if (dtmReasonCd) {
                    $select.val(dtmReasonCd).trigger('change');
                } else {
                    $select.val("").trigger('change'); // 기본 옵션 선택
                }
            },
            error: function (xhr, status, error) {
                console.error('Error fetching data: ', error);
            }
        });
    });
});