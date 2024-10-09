$(document).ready(() => { // 화면 로드 시 호출

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
            staYmd: new Date(staDate).toISOString(), // 근태시작일 (indexController에서 세팅한 날짜)
            endYmd: new Date(staDate).toISOString() // 근태종료일 (indexController에서 세팅한 날짜)
        };
        
        registDTOList.push(registDtmHisDTO); // 서버로 전달할 전체 리스트 객체 추가        
        ApplCheck(revokeDTOList, registDTOList); // 신청 내역 체크        
        revokeDTOList = []; // 근태 취소 List 객체 초기화
        registDTOList = []; // 근태 신청 List 객체 초기화
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
