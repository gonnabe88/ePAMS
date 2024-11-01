$(document).ready(() => { // 화면 로드 시 호출

    resetSearchMember(); // 직원검색 폼 초기화

    function formatDate(date) {
        const days = ['일', '월', '화', '수', '목', '금', '토'];  // 요일 배열

        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2);  // 월을 2자리로 맞춤
        const day = ('0' + date.getDate()).slice(-2);  // 일을 2자리로 맞춤
        const dayName = days[date.getDay()];  // 요일 이름

        return `${year}-${month}-${day}(${dayName})`;
    }

    function formatLocalDateTime(date) {
        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2);  // 월을 2자리로 맞춤
        const day = ('0' + date.getDate()).slice(-2);  // 일을 2자리로 맞춤

        // 시, 분, 초를 고정으로 00:00:00으로 설정
        const hours = '00';
        const minutes = '00';
        const seconds = '00';

        // LocalDateTime 형식: YYYY-MM-DDTHH:MM:SS
        return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
    }

    $.ajax({
        url: '/api/index/current-utc-time',
        method: 'GET',
        success: function(response) {

            // 서버에서 받은 시간을 로컬 시간대로 변환
            const now = new Date(response);

            // 내일의 날짜 계산
            const tomorrow = new Date(now);
            tomorrow.setDate(now.getDate() + 1);

            // jQuery를 사용하여 내일의 날짜를 'YYYY-MM-DD(요일)' 형식으로 출력하고, LocalDateTime 형식을 data 속성에 저장
            $('[id="today-date"]').each(function() {
                // 요소의 텍스트는 'YYYY-MM-DD(요일)' 형식으로 출력
                $(this).text(formatDate(now));
            });
            $('[id="today-appl-btn"]').each(function() {
                // LocalDateTime 형식은 data-localdatetime 속성에 저장
                $(this).attr('data-staDate', formatLocalDateTime(now));
            });
            $('[id="tomorrow-date"]').each(function() {
                // 요소의 텍스트는 'YYYY-MM-DD(요일)' 형식으로 출력
                $(this).text(formatDate(tomorrow));
            });
            $('[id="tomorrow-appl-btn"]').each(function() {
                // LocalDateTime 형식은 data-localdatetime 속성에 저장
                $(this).attr('data-staDate', formatLocalDateTime(tomorrow));
            });

            // 필요한 곳에 로컬 시간 적용
            //$('#today-date').text(now.toISOString().slice(0, 10));
            //$('#tomorrow-date').text(tomorrow.toISOString().slice(0, 10));
        },
        error: function() {
            console.error('서버 시간 요청 실패');
        }
    });

    // 신청(dtmRegistBtn) 버튼 클릭 시
    $('.dtmRegistBtn').on('click', function () {

        let revokeDTOList = []; // 근태 취소 List 객체 생성
        let registDTOList = []; // 근태 신청 List 객체 생성

        const staDate = this.getAttribute('data-staDate');
        const dtmKindCd = this.getAttribute('data-dtmKindCd');
        const dtmReasonCd = this.getAttribute('data-dtmReasonCd');
        const dtmReasonNm = this.getAttribute('data-dtmReasonNm');
        const dtmDispName = this.getAttribute('data-dtmDispName');
    
        // [신규] 근태 객체 세팅
        let registDtmHisDTO = {
            dtmKindCd: dtmKindCd, // 근태종류
            dtmReasonCd: dtmReasonCd, // 근태코드
            dtmReasonNm: dtmReasonNm, // 근태명
            dtmReason: dtmReasonNm, // 근태사유
            dtmDispName: dtmDispName, // 근태표시이름
            staYmd: staDate, // 근태시작일 (indexController에서 세팅한 날짜)
            endYmd: staDate // 근태종료일 (indexController에서 세팅한 날짜)
        };
        
        registDTOList.push(registDtmHisDTO); // 서버로 전달할 전체 리스트 객체 추가
        console.log(registDtmHisDTO);
        ApplCheck(revokeDTOList, registDTOList); // 신청 내역 체크        
        revokeDTOList = []; // 근태 취소 List 객체 초기화
        registDTOList = []; // 근태 신청 List 객체 초기화
    });

    // 'scrollToSearchDiv' 버튼 클릭 시 scrollToDiv() 함수 호출
    $('.scrollToSearchDiv').on('click', function () {
        scrollToDiv();
    });

    // 'scrollToSearchDiv' 버튼 클릭 시 scrollToDiv() 함수 호출
    $('#scrollToSearchDiv2').on('click', function () {
        scrollToDiv();
    });

    // 'scrollToSearchDiv' 버튼 클릭 시 scrollToDiv() 함수 호출
    $('.scrollToQuickapplDiv').on('click', function () {
        scrollToQuickapplDiv();
    });

    // 'scrollToSearchDiv' 버튼 클릭 시 scrollToDiv() 함수 호출
    $('#scrollToQuickapplDiv').on('click', function () {
        scrollToQuickapplDiv();
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
