/**
 * Select 드롭다운 폼 관련 스크립트(/js/selectForm.js)
 * @author K140024
 * @implements Select 드롭다운 폼 관련 스크립트
 *  - 페이지 로딩 시 전체 목록 가져오기
 *  - category 변화 감지
 *  - Select2 초기화
 * @since 2024-07-28
 */

const dtmApplSelectForm = (dtmReasonCd, pageType) => {
    console.log("dtmApplSelectForm");
    if(pageType === "modal"){
        // Select2 초기화
        $('#dtmReasonCdSelect').select2({
            dropdownParent: $('#dynamicModal') // 모달 내부에서 동작하도록 설정
        });
    } else {
        $('#dtmReasonCdSelect').select2();
    }
    let selectedValue = $('input[name="dtmKindCdSelect"]').val();
    let url = "/api/commoncode/code/DTM_REASON_CD/" + selectedValue;

    // 전체 목록 가져오기
    $.ajax({
        url: url,
        method: 'GET',
        success: function (data) {
            console.log(data);
            const $dtmApplSelect = $('#dtmReasonCdSelect');
            $dtmApplSelect.empty(); // 기존 옵션 삭제
            //근태 신청화면에서는 전체선택 제외
            data.forEach(item => {
                $dtmApplSelect.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
            });
            $dtmApplSelect.trigger('change'); // 옵션 새로고침

            // dtmReasonCd 값이 있을 경우 해당 값을 선택
            if (dtmReasonCd) {
                $dtmApplSelect.val(dtmReasonCd).trigger('change');
            } else {
                $dtmApplSelect.val(null).trigger('change'); // 기본 옵션 선택
            }

        },
        error: function (xhr, status, error) {
            console.error('Error fetching data: ', error);
        }
    });

    // 카테고리 항목 선택 시 SELECT 요소 갱신
    $('input[name="dtmKindCdSelect"]').on('change', function () {
        let selectedValue = $(this).val();
        let url = "/api/commoncode/code/DTM_REASON_CD/" + selectedValue;

        // AJAX 요청
        $.ajax({
            url: url,
            method: 'GET',
            success: function (data) {
                console.log(data); // 받아온 데이터 출력
                const $dtmApplSelect = $('#dtmReasonCdSelect');
                $dtmApplSelect.empty(); // 기존 옵션 삭제
                data.forEach(item => {
                    $dtmApplSelect.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
                });
                $dtmApplSelect.trigger('change'); // 옵션 새로고침
            },
            error: function (xhr, status, error) {
                console.error('Error fetching data: ', error);
            }
        });
    });
}