$(document).ready(() => {

    // 'save' 버튼 클릭 시 save2() 함수 호출
    $('.appl-alert-button').on('click', function () {
        const staDate = this.getAttribute('data-staDate');
        const staDateStr = this.getAttribute('data-staDateStr');
        const dtmCode = this.getAttribute('data-dtmCode');
        const dtmReasonCode = this.getAttribute('data-dtmReasonCode');
        const dtmReasonName = this.getAttribute('data-dtmReasonName');
        const dtmDispName = this.getAttribute('data-dtmDispName');
    
        // DtmHisDTO 객체 생성
        const dtmHisDTO = {
            dtmKindCd: dtmCode,
            dtmReasonCd: dtmReasonCode,
            dtmReasonNm: dtmReasonName,
            dtmDispName: dtmDispName,
            staYmd: new Date(staDate).toISOString(),
            endYmd: new Date(staDate).toISOString()
        };

        const dtmHisDTOList = [];
        dtmHisDTOList.push(dtmHisDTO);
        
        ApplCheck(dtmHisDTOList);
        
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
