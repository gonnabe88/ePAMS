$(document).ready( () => {

    const submitButton = document.querySelector('.btn-submit');
    const reasonSelect = document.getElementById('list');
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');

    dtmApplSelectForm("1A1", "normal");  // select form 세팅 (기본세팅 : 연차)

    //신청하기 버튼 클릭시
    submitButton.addEventListener('click', function () {

        let dtmHisDTOList = []; // 근태 List 객체 생성
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
                dtmKindCd: dtmKindCd,
                dtmReasonCd: dtmReasonCd,
                dtmReasonNm: dtmReasonNm,
                dtmDispName: dtmReasonNm,
                staYmd: new Date(startDate).toISOString(),
                endYmd: new Date(endDate).toISOString()
            };

            console.log(registDtmHisDTO);
            dtmHisDTOList.push(registDtmHisDTO); // 서버로 전달할 전체 리스트 객체 추가
            ApplCheck(dtmHisDTOList); // 신청 내역 체크
        }
    });

    // 휴가 보유현황 클릭 시 Modal 실행 이벤트 등록
    $('#openDtmStatusModal').on('click', function () {
        $('#dtmStatusModal').modal('show');
    });

});