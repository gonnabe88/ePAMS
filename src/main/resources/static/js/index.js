$(document).ready(() => {

    // 'save' 버튼 클릭 시 save2() 함수 호출
    $('.appl-alert-button').on('click', function () {
        const header = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더
        const token = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰
        const staDate = this.getAttribute('data-staDate');
        const staDateStr = this.getAttribute('data-staDateStr');
        const dtmCode = this.getAttribute('data-dtmCode');
        const dtmReasonCode = this.getAttribute('data-dtmReasonCode');
        const dtmDispName = this.getAttribute('data-dtmDispName');
    
        // DtmHisDTO 객체 생성
        const dtmHisDTO = {
            dtmKindCd: dtmCode,
            dtmReasonCd: dtmReasonCode,
            dtmDispName: dtmDispName,
            staYmd: new Date(staDate).toISOString(),
            endYmd: new Date(staDate).toISOString()
        };

        $.ajax({
            url: '/api/dtm/check', // 요청 URL
            method: 'POST', // 요청 방식
            headers: {
                'Content-Type': 'application/json',
                [header]: token // CSRF 토큰 헤더 설정
            },
            data: JSON.stringify(dtmHisDTO), // 전송 DATA
            success: (data) => { // 성공 (HTTP 상태코드 20X)
                const staYmd = data.staYmd; // 서버에서 반환된 staYmd 값 사용
                const dtmDispName = data.dtmDispName;

                if(data.adUseYn){
                    Swal.fire({
                        title: "선연차 사용",
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
                            console.log("선연차 신청 : ", data.dtmHisDTOList);
                            ApplListAlertPopup(this, data.dtmHisDTOList);
                        }
                    });
                } else {
                    console.log("일반연차 신청 : ", data.dtmHisDTOList);
                    ApplListAlertPopup(this, data.dtmHisDTOList);
                }
               
            },
            error: (error) => { // 실패 (HTTP 상태코드 40X, 50X)
                // 에러 발생 팝업
                popupHtmlMsg('신청 불가', error.responseJSON.error, 'error');
                console.error('Error:', error.status, error.responseJSON.error);
            }
        });        
        
    });

    // 'scrollToSearchDiv' 버튼 클릭 시 scrollToDiv() 함수 호출
    $('#scrollToSearchDiv').on('click', function () {
        scrollToDiv();
    });

    // 직원검색 입력폼에 입력값이 있는 경우 X 버튼 표시 (값 없는 경우 X 미표시)
    $('#searchMember').on('input', function () {
        if(this.value.length > 0) {
            $('#clearSearch').removeAttr("hidden");
        }
        else {
            $('#clearSearch').attr("hidden", true);
        }
    });

    // 직원검색 입력폼 X 버튼 클릭 시 값 삭제
    $('#clearSearch').on('click', function() {
        $('#searchMember').val('');
        $('#clearSearch').attr("hidden", true);
        $('#searchMember').focus();
    });

    // 간편인증 등록 팝업
    webauthnPopup("REMIND");

});
