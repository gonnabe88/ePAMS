$(document).ready( () => {

    const submitButton = document.querySelector('.btn-submit');
    const reasonSelect = document.getElementById('list');
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    dtmApplSelectForm("1A1", "normal","dtmKindCdSelect", "#dtmReasonCdSelect");  // select form 세팅 (기본세팅 : 연차)

    //신청하기 버튼 클릭시
    submitButton.addEventListener('click', function () {

        let revokeDTOList = []; // 근태 취소 List 객체 생성
        let registDTOList = []; // 근태 신청 List 객체 생성

        const startDate = startDateInput.value;
        const endDate = endDateInput.value;
        //const dtmReasonCd = reasonSelect.value;
        let dtmReasonCd = $('#dtmReasonCdSelect option:selected').val();
        let dtmReasonNm = $('#dtmReasonCdSelect option:selected').text();
        let dtmKindCd = $('input[name="dtmKindCdSelect"]:checked').val();

        // dtmReasonCd가 없을 경우 경고 메시지 표시
        if (!dtmReasonCd) {
            alert("근태종류를 선택 바랍니다.");
            return;
        } else if (startDate > endDate) {
            alert("시작일이 종료일보다 큽니다.");
            return;
        } else {
            // DtmHisDTO 객체 생성
            const registDtmHisDTO = {
                dtmKindCd: dtmKindCd, // 근태종류
                dtmReasonCd: dtmReasonCd, // 근태코드
                dtmReasonNm: dtmReasonNm, // 근태명
                dtmReason: dtmReasonNm, // 근태사유
                dtmDispName: dtmReasonNm,
                staYmd: new Date(startDate).toISOString(),
                endYmd: new Date(endDate).toISOString()
            };

            registDTOList.push(registDtmHisDTO); // 서버로 전달할 전체 리스트 객체 추가
            ApplCheck(revokeDTOList, registDTOList); // 신청 내역 체크
            revokeDTOList = []; // 근태 취소 List 객체 초기화
            registDTOList = []; // 근태 신청 List 객체 초기화
        }
    });

    // 휴가 보유현황 클릭 시 Modal 실행 이벤트 등록
    $('#openDtmStatusModal').on('click', function () {
        $('#dtmStatusModal').modal('show');
    });



});