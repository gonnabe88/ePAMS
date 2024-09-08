// 빠른 근태 신청 팝업
const ApplAlertPopup = (element) => {
    const staDate = element.getAttribute('data-staDate');
    const staDateStr = element.getAttribute('data-staDateStr');
    const dtmCode = element.getAttribute('data-dtmCode');
    const dtmReasonCode = element.getAttribute('data-dtmReasonCode');
    const dtmDispName = element.getAttribute('data-dtmDispName');

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

// 비동기로 대기하는 함수
const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

// 진행률 업데이트 함수 (1%당 0.1초 대기)
const updateProgressBar = async (startPercentage, targetPercentage) => {
    const progressBar = document.getElementById('progressBar');
    let currentPercentage = startPercentage;

    while (currentPercentage < targetPercentage) {
        currentPercentage++;
        progressBar.style.width = currentPercentage + '%';
        progressBar.innerHTML = currentPercentage + '%';
        await delay(100);  // 0.1초 대기
    }
};

// 근태 신청
const postDtmHisDTO = async (dtmHisDTO) => {
    const header = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더
    const token = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰

    // AJAX로 모달 HTML 로드
    $.get('/dtm/dtmApplProcessModal', async function(modalHtml) {


        $('body').append(modalHtml); // 모달을 페이지에 동적으로 삽입
        $('#processModal').modal({backdrop: 'static'}) // 모달 배경 클릭 시 닫히지 않도록 설정
        $('#processModal').modal('show');// 모달 표시
        await updateProgressBar(0, 40); // 진행률 0%에서 40%까지 증가

        $.ajax({
            url: '/api/dtm/appl', // 요청 URL
            method: 'POST', // 요청 방식
            headers: {
                'Content-Type': 'application/json',
                [header]: token // CSRF 토큰 헤더 설정
            },
            data: JSON.stringify(dtmHisDTO), // 전송 DATA
            beforeSend: async () => {
                // 진행률 40%에서 80%까지 증가
                await updateProgressBar(41, 80);
            },
            success: async (data) => { // 성공 (HTTP 상태코드 20X)
                const staYmd = data.staYmd; // 서버에서 반환된 staYmd 값 사용
                const dtmDispName = data.dtmDispName;

                const progressBar = document.getElementById('progressBar');
                progressBar.classList.add('bg-success'); // Danger 색상 변경
                await updateProgressBar(81, 100); // 진행률 80%에서 100%까지 증가 후 모달 닫기
                $('#processModal').modal('hide');
                $('#processModal').remove(); // 모달을 제거하여 DOM을 깨끗하게 유지

                // 신청 완료 팝업
                popupBtnReHtmlMsg('신청되었습니다.', `<span class="dtmApplSuccessPopup">${staYmd} ${dtmDispName}</span>`, 'success', '근태조회', '/dtm/dtmList');
                console.log('Success:', data);
            },
            error: async (error) => { // 실패 (HTTP 상태코드 40X, 50X)

                const progressBar = document.getElementById('progressBar');
                progressBar.classList.add('bg-danger'); // Danger 색상 변경
                await updateProgressBar(81, 90); // 진행률 80%에서 100%까지 증가 후 모달 닫기
                $('#processModal').modal('hide');
                $('#processModal').remove(); // 모달을 제거하여 DOM을 깨끗하게 유지

                // 에러 발생 팝업
                popupHtmlMsg('신청 불가', error.responseJSON.error, 'error');
                console.error('Error:', error.status, error.responseJSON.error);
            }
        });
    });
};
