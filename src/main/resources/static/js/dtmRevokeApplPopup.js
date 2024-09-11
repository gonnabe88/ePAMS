
// 근태 취소 팝업
const dtmRevokeApplPopup = (element) => {
    const applId = element.getAttribute('data-applid');
    const dtmHisId = element.getAttribute('data-dtmhisid');
    const staYmd = element.getAttribute('data-staymd');
    const endYmd = element.getAttribute('data-endymd');
    const dtmKindCd = element.getAttribute('data-dtmkindcd');
    const dtmReasonCd = element.getAttribute('data-dtmreasoncd');
    const dtmReasonNm = element.getAttribute('data-dtmreasonnm');
    const dtmDispName = element.getAttribute('data-dtmdispname');

    // DtmHisDTO 객체 생성
    const dtmHisDTO = {
        applId: applId,
        dtmHisId: dtmHisId,
        staYmd: staYmd, //new Date(staYmd).toISOString(),
        endYmd: endYmd, //new Date(endYmd).toISOString(),
        dtmKindCd: dtmKindCd,
        dtmReasonCd: dtmReasonCd,
        dtmReasonNm: dtmReasonNm,
        dtmDispName: dtmDispName,
    };

    // 객체를 쿼리 스트링으로 변환
    const queryString = $.param(dtmHisDTO);
    console.log(queryString);

    // HTML 파일을 불러오기 (쿼리 스트링과 함께 GET 요청)
    $.get(`/dtm/dtmApplPopup?${queryString}`, function(htmlContent) {
        Swal.fire({
            title: "취소하시겠습니까?",
            html: htmlContent,
            icon: "info",
            showCancelButton: true,
            scrollbarPadding: false,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "네, 취소할게요!",
            cancelButtonText: "아니요",
        }).then((result) => {
            if (result.isConfirmed) {
                // POST 요청 함수 호출
                dtmRevokeApplPost(dtmHisDTO);
            }
        });
    });
};

// 근태 취소 신청
const dtmRevokeApplPost = async (dtmHisDTO) => {
    const header = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더
    const token = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰


    $.ajax({
        url: '/api/dtm/dtmRevoke', // 요청 URL
        method: 'POST', // 요청 방식
        headers: {
            'Content-Type': 'application/json',
            [header]: token // CSRF 토큰 헤더 설정
        },
        data: JSON.stringify(dtmHisDTO), // 전송 DATA
        success: async (data) => { // 성공 (HTTP 상태코드 20X)
            const staYmd = data.staYmd; // 서버에서 반환된 staYmd 값 사용
            const endYmd = data.endYmd; // 서버에서 반환된 endYmd 값 사용
            const dtmReasonNm = data.dtmReasonNm; // 서버에서 반환된 staYmd 값 사용

            // 신청 완료 팝업
            popupBtnReHtmlMsg('취소되었습니다.', `<span class="dtmApplSuccessPopup">${staYmd} ${dtmReasonNm}</span>`, 'success', '근태조회', '/dtm/dtmList');
            console.log('Success:', data);
        },
        error: async (error) => { // 실패 (HTTP 상태코드 40X, 50X)

            popupHtmlMsg('신청 불가', error.responseJSON.error, 'error');
            console.error('Error:', error.status, error.responseJSON.error);
        }
    });

}