// [1] 사전검증
const ApplCheck = (revokeDTOList, registDTOList) => {
    const header = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더
    const token = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰

    $.ajax({
        url: '/api/dtm/check', // 요청 URL
        method: 'POST', // 요청 방식
        headers: {
            'Content-Type': 'application/json',
            [header]: token // CSRF 토큰 헤더 설정
        },
        data: JSON.stringify({
            revoke: revokeDTOList,
            regist: registDTOList
        }), // 전송 DATA
        success: (data) => { // 성공 (HTTP 상태코드 20X)

            if(data.adUseYn){
                Swal.fire({
                    title: "선연차 사용 동의",
                    html: `<p>올해 잔여연차 부족으로 선연차를 사용합니다. 신청하시겠습니까?</p>
                           <h6 class=\"text-left\">총 보유연차 : ${data.annualTotalCnt}일</h6>
                           <h6 class=\"text-left\">기 사용연차 : ${data.annualUsedCnt}일</h6>
                           <h6 class=\"text-left\">신청내용 : ${data.annualSum}일</h6>`,
                    icon: "info",
                    showCancelButton: true,
                    cancelButtonColor: "#6c757d",
                    cancelButtonText: "취소",
                    confirmButtonColor: "#3085d6",
                    confirmButtonText: "신청하겠습니다.",
                }).then((result) => {
                    if(result.isConfirmed) {
                        ApplListAlertPopup(data.revokeDTOList ,data.registDTOList);
                    }
                });
            } else {
                ApplListAlertPopup(data.revokeDTOList, data.registDTOList);
            }
           
        },
        error: (error) => { // 실패 (HTTP 상태코드 40X, 50X)
            // 에러 발생 팝업
            popupHtmlMsg('신청 불가', error.responseJSON.error, 'error');
            console.error('Error:', error.status, error.responseJSON.error);
        }
    });   
}

// [2] 근태 신청 팝업
const ApplListAlertPopup = (revokeDTOList, registDTOList) => {
    const header = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더
    const token = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰
    $.ajax({
        url: '/dtm/dtmApplListPopup', // 요청 URL
        method: 'POST', // 요청 방식
        headers: {
            'Content-Type': 'application/json',
            [header]: token // CSRF 토큰 헤더 설정
        },
        data: JSON.stringify({
            revoke: revokeDTOList,
            regist: registDTOList
        }), // 전송 DATA
        success: (data) => { // 성공 (HTTP 상태코드 20X)

            Swal.fire({
                title: "신청하시겠습니까?",
                html: data,
                icon: "info",
                showCancelButton: true,
                scrollbarPadding: false,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "네, 신청할게요!",
                cancelButtonText: "아니요",
                didOpen: () => {
                    const confirmButton = Swal.getConfirmButton();
                    const agreeCheck = $('#agreeCheck');

                    confirmButton.disabled = true;

                    agreeCheck.on('change', function () {
                        confirmButton.disabled = !this.checked;
                    });
                },
                preConfirm: () => {
                    const isChecked = $('#agreeCheck').is(':checked');
                    if(!isChecked) {
                        Swal.showValidationMessage('동의 후 진행 가능합니다.');
                        return false;
                    }
                    return true;
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    // POST 요청 함수 호출
                    postDtmHisDTO(revokeDTOList, registDTOList);
                }
            });
        },
        error: (error) => { // 실패 (HTTP 상태코드 40X, 50X)
            // 에러 발생 팝업
            popupHtmlMsg('신청 불가', error.responseJSON.error, 'error');
            console.error('Error:', error.status, error.responseJSON.error);
        }
    });

};

// [3] 근태 신청
const postDtmHisDTO = async (revokeDTOList, registDTOList) => {
    const header = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더
    const token = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰

    // AJAX로 모달 HTML 로드
    $.get('/dtm/dtmApplProcessModal', async function(modalHtml) {

        if ($('#dynamicModal').length) {
            // 모달이 존재하면 종료
            $('#dynamicModal').modal('hide');
        }

        $('body').append(modalHtml); // 모달을 페이지에 동적으로 삽입
        $('#processModal').modal({backdrop: 'static'}) // 모달 배경 클릭 시 닫히지 않도록 설정
        $('#processModal').modal('show');// 모달 표시

        $.ajax({
            url: '/api/dtm/appl', // 요청 URL
            method: 'POST', // 요청 방식
            headers: {
                'Content-Type': 'application/json',
                [header]: token // CSRF 토큰 헤더 설정
            },
            data: JSON.stringify({
                revoke: revokeDTOList,
                regist: registDTOList
            }), // 전송 DATA
            beforeSend: async () => {
                // 진행률 40%에서 80%까지 증가
                await controlProgressBar(0, 80);
            },
            success: async (data) => { // 성공 (HTTP 상태코드 20X)

                const progressBar = document.getElementById('progressBar');
                progressBar.classList.add('bg-success'); // 색상 변경

                await controlProgressBar(81, 100); // 진행률 80%에서 100%까지 증가 후 모달 닫기
                $('#processModal').modal('hide');
                $('#processModal').remove(); // 모달을 제거하여 DOM을 깨끗하게 유지

                // 신청 완료 팝업
                popupBtnReHtmlMsg('신청되었습니다.','', 'success', '근태조회', '/dtm/dtmList');
                
            },
            error: async (error) => { // 실패 (HTTP 상태코드 40X, 50X)
                const progressBar = document.getElementById('progressBar');
                progressBar.classList.add('bg-danger'); // 색상 변경

                await controlProgressBar(81, 90); // 진행률 80%에서 90%까지 증가 후 모달 닫기
                $('#processModal').modal('hide');
                $('#processModal').remove(); // 모달을 제거하여 DOM을 깨끗하게 유지

                // 에러 발생 팝업
                popupHtmlMsg('신청 불가', error.responseJSON.error, 'error');
                console.error('Error:', error.status, error.responseJSON.error);
            }
        });
    });
};