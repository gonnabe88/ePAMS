// 근태 변경 팝업
const dtmModifyApplPopup = (element) => {
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
    $.get(`/dtm/dtmModifyPopup?${queryString}`, function(htmlContent) {
        // 모달 바디에 동적으로 불러온 HTML을 삽입
        $('#dynamicModal .modal-body').html(sanitizeHTML(htmlContent)); //취약점 조치
        $('#dynamicModal').modal('show');

        // 동적으로 필요한 스크립트 로드
        loadScript('/extensions/fullcalendar/index.global.js');
        loadScript('/extensions/fullcalendar/initDtmRegCal.js');
        loadScript('/extensions/fullcalendar/main.min.js');
        loadScript('/extensions/select2/select2.full.js');

        loadScript('/js/dateFormat.js');
        loadScript('/js/pastValid.js');
    });
};

// JavaScript 파일을 동적으로 로드하는 함수
function loadScript(src) {
    const script = document.createElement('script');
    script.src = src;
    script.async = false; // 순서를 보장하기 위해 비동기 로드하지 않음
    document.head.appendChild(script);
}

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

    // AJAX로 모달 HTML 로드
    $.get('/dtm/dtmApplProcessModal', async function(modalHtml) {
        $('body').append(modalHtml); // 모달을 페이지에 동적으로 삽입
        $('#processModal').modal({backdrop: 'static'}) // 모달 배경 클릭 시 닫히지 않도록 설정
        $('#processModal').modal('show');// 모달 표시

        $.ajax({
            url: '/api/dtm/dtmRevoke', // 요청 URL
            method: 'POST', // 요청 방식
            headers: {
                'Content-Type': 'application/json',
                [header]: token // CSRF 토큰 헤더 설정
            },
            data: JSON.stringify(dtmHisDTO), // 전송 DATA
            beforeSend: async () => {
                // 진행률 40%에서 80%까지 증가
                await controlProgressBar(0, 80);
            },
            success: async (data) => { // 성공 (HTTP 상태코드 20X)
                const staYmd = data.staYmd; // 서버에서 반환된 staYmd 값 사용
                const endYmd = data.endYmd; // 서버에서 반환된 endYmd 값 사용
                const dtmReasonNm = data.dtmReasonNm; // 서버에서 반환된 staYmd 값 사용

                const progressBar = document.getElementById('progressBar');
                progressBar.classList.add('bg-success'); // 색상 변경
                await controlProgressBar(81, 100); // 진행률 80%에서 100%까지 증가 후 모달 닫기
                $('#processModal').modal('hide');
                $('#processModal').remove(); // 모달을 제거하여 DOM을 깨끗하게 유지

                // 신청 완료 팝업
                popupBtnReHtmlMsg('취소되었습니다.', `<span class="dtmApplSuccessPopup">${staYmd} ${dtmReasonNm}</span>`, 'success', '근태조회', '/dtm/dtmList');
                console.log('Success:', data);
            },
            error: async (error) => { // 실패 (HTTP 상태코드 40X, 50X)
                const progressBar = document.getElementById('progressBar');
                progressBar.classList.add('bg-danger'); // 색상 변경

                await controlProgressBar(81, 90); // 진행률 80%에서 90%까지 증가 후 모달 닫기
                $('#processModal').modal('hide');
                $('#processModal').remove(); // 모달을 제거하여 DOM을 깨끗하게 유지

                popupHtmlMsg('신청 불가', error.responseJSON.error, 'error');
                console.error('Error:', error.status, error.responseJSON.error);
            }
        });
    });
}