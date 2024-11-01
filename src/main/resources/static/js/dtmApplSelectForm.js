const dtmApplSelectForm = (dtmReasonCd, pageType, stdName, selectId) => {

    let selectedValue = $(`input[name=${stdName}]`).val();
    let url = "/api/commoncode/code/DTM_REASON_CD/" + selectedValue;

    // 전체 목록 가져오기
    $.ajax({
        url: url,
        method: 'GET',
        success: function (data) {
            const $dtmReasonCdSelect = $(selectId);

            $dtmReasonCdSelect.empty(); // 기존 옵션 삭제

            if (window.innerWidth >= DESKTOP_SIZE) { // 데스크탑 (Select2 사용)
                console.log('Select2 사용');
                $(selectId).select2({
                    dropdownParent: pageType === "modal" ? $('#dynamicModal') : $(document.body), // 모달 내에서 렌더링 설정
                    dropdownAutoWidth: true, // 드롭다운이 콘텐츠에 맞게 자동으로 너비 조정
                });
            }

            // 새로운 옵션 추가
            data.forEach(item => {
                $dtmReasonCdSelect.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
            });

            $dtmReasonCdSelect.trigger('change'); // 옵션 새로고침

            // dtmReasonCd 값이 있을 경우 해당 값을 선택
            if (dtmReasonCd) {
                $dtmReasonCdSelect.val(dtmReasonCd);
            } else {
                $dtmReasonCdSelect.val(null); // 기본 옵션 선택
            }

        },
        error: function (xhr, status, error) {
            console.error('Error fetching data: ', error);
        }
    });

    // 카테고리 항목 선택 시 SELECT 요소 갱신
    $(`input[name=${stdName}]`).on('change', function () {
        let selectedValue = $(this).val();
        let url = "/api/commoncode/code/DTM_REASON_CD/" + selectedValue;

        // AJAX 요청
        $.ajax({
            url: url,
            method: 'GET',
            success: function (data) {
                const $dtmReasonCdSelect = $(selectId);

                $dtmReasonCdSelect.empty(); // 기존 옵션 삭제

                // 새로운 옵션 추가
                data.forEach(item => {
                    $dtmReasonCdSelect.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
                });

                $dtmReasonCdSelect.trigger('change'); // 옵션 새로고침

            },
            error: function (xhr, status, error) {
                console.error('Error fetching data: ', error);
            }
        });
    });
}
