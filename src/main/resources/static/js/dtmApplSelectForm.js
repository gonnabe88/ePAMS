const dtmApplSelectForm = (dtmReasonCd, pageType, stdName, selectId) => {
    const header = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더
    const token = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰
    let selectedValue = $(`input[name=${stdName}]`).val();
    let url = "/api/commoncode/code"
    
    let commonCodeDTO = {
        codeKind: 'DTM_REASON_CD',
        code: selectedValue,
        etcCode3: 'Y%', // 사용자 신청 가능 대상
        etcCode4: '',
    }
    console.log(commonCodeDTO);

    // 전체 목록 가져오기
    $.ajax({
        url: url,
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token // CSRF 토큰 헤더 설정
        },
        data: JSON.stringify(commonCodeDTO), // 전송 DATA
        success: function (data) {
            const $dtmReasonCdSelect = $(selectId);

            $dtmReasonCdSelect.empty(); // 기존 옵션 삭제

            if (window.innerWidth >= DESKTOP_SIZE) { // 데스크탑 (Select2 사용)
                $(selectId).select2({
                    dropdownParent: pageType === "modal" ? $('#dynamicModal') : $(document.body), // 모달 내에서 렌더링 설정
                    dropdownAutoWidth: true, // 드롭다운이 콘텐츠에 맞게 자동으로 너비 조정
                });
            }

            // 새로운 옵션 추가
            data.forEach(item => {
                let newOption = new Option(item.codeName, item.code);
                newOption.setAttribute('data-etcCode4', item.etcCode4);
                $dtmReasonCdSelect.append(newOption); // 새로운 옵션 추가
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

    const periodFilter = (period) => {
        const $dtmReasonCdSelect = $(`${selectId} option`);
        $dtmReasonCdSelect.each(function() {
            const $options = $(this);
            if(period === 'Y') {
                if($options.data('etccode4') !== 'Y') {
                    $options.hide();
                }
            }
            if(period !== 'Y') {
                $options.show();
            }
        });
    }

    $('#endDate').on('change', function () {
        console.log("end ch");
        let staYmd = $('#startDate').val();
        let endYmd = $('#endDate').val();
        if(staYmd !== endYmd) {
            periodFilter('Y');
        } else {
            periodFilter('');
        }
    });


    // 카테고리 항목 선택 시 SELECT 요소 갱신
    $(`input[name=${stdName}]`).on('change', function () {
        let selectedValue = $(this).val();
        let staYmd = $('#startDate').val();
        let endYmd = $('#endDate').val();
        let url = "/api/commoncode/code"
        
        let commonCodeDTO = {
            codeKind: 'DTM_REASON_CD',
            code: selectedValue,
            etcCode3: 'Y%', // 사용자 신청 가능 대상
            etcCode4: ''
        }

        // AJAX 요청
        $.ajax({
            url: url,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token // CSRF 토큰 헤더 설정
            },
            data: JSON.stringify(commonCodeDTO), // 전송 DATA
            success: function (data) {
                const $dtmReasonCdSelect = $(selectId);

                $dtmReasonCdSelect.empty(); // 기존 옵션 삭제
                console.log(data);
                // 새로운 옵션 추가
                data.forEach(item => {
                    let newOption = new Option(item.codeName, item.code);
                    newOption.setAttribute('data-etcCode4', item.etcCode4);
                    $dtmReasonCdSelect.append(newOption); // 새로운 옵션 추가

                });

                $dtmReasonCdSelect.trigger('change'); // 옵션 새로고침

            },
            error: function (xhr, status, error) {
                console.error('Error fetching data: ', error);
            }
        });



    });
}
