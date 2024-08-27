// 근태 신청 팝업
const ApplAlert = () => {
    const startDateInput = document.getElementById('startDate');
    const staDate = startDateInput.value;
    const dtmCode = document.querySelector('.btn.btn-submit').getAttribute('data-dtmCode');
    const dtmReasonCode = document.querySelector('.btn.btn-submit').getAttribute('data-dtmReasonCode');
    const dtmDispName = document.querySelector('.btn.btn-submit').getAttribute('data-dtmDispName');

    // DtmHisDTO 객체 생성
    const dtmHisDTO = {
        dtmKindCd: dtmCode,
        dtmReasonCd: dtmReasonCode,
        dtmDispName: dtmDispName,
        staYmd: new Date(staDate).toISOString(),
        endYmd: new Date(staDate).toISOString()
    };

    // 객체를 쿼리 스트링으로 변환
    const queryString = $.param(dtmHisDTO);

    checkSession(); // 세션 체크

    // HTML 파일을 불러오기 (쿼리 스트링과 함께 GET 요청)
    $.get(`/dtm/dtmApplPopup?${queryString}`, function(htmlContent) {
        Swal.fire({
            title: "신청하시겠습니까?",
            html: htmlContent,
            icon: "info",
            showCancelButton: true,
            scrollbarPadding: false,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "네, 신청할게요!",
            cancelButtonText: "아니요",
        }).then((result) => {
            if (result.isConfirmed) {
                // POST 요청 함수 호출
                postDtmHisDTO(dtmHisDTO);
            }
        });
    });
};

// 근태 신청
const postDtmHisDTO = (dtmHisDTO) => {
    const header = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더
    const token = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰

    $.ajax({
        url: '/api/dtm/appl', // 요청 URL
        method: 'POST', // 요청 방식
        headers: {
            'Content-Type': 'application/json',
            [header]: token // CSRF 토큰 헤더 설정
        },
        data: JSON.stringify(dtmHisDTO), // 전송 DATA
        success: (data) => { // 성공 (HTTP 상태코드 20X)
            const staYmd = data.staYmd; // 서버에서 반환된 staYmd 값 사용
            const dtmDispName = data.dtmDispName;
            popupReHtmlMsg('신청되었습니다.', `<span class="dtmApplSuccessPopup">${staYmd} ${dtmDispName}</span>`, 'success', '근태조회', '/dtm/list');
            console.log('Success:', data);
        },
        error: (error) => { // 실패 (HTTP 상태코드 40X, 50X)
            popupHtmlMsg('신청 불가', error.responseJSON.error, 'error');
            console.error('Error:', error.status, error.responseJSON.error);
        }
    });
};

// 버튼 클릭 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', () => {
    const submitButton = document.querySelector('.btn.btn-submit');
    if (submitButton) {
        submitButton.addEventListener('click', ApplAlert);
    }
});