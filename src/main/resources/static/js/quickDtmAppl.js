// 빠른 근태 신청 팝업
const ApplAlert = (element) => {
    const staDate = element.getAttribute('data-staDate');
    const staDateStr = element.getAttribute('data-staDateStr');
    const dtmCode = element.getAttribute('data-dtmCode');
    const dtmReasonCode = element.getAttribute('data-dtmReasonCode');

    Swal.fire({
        title: "신청하시겠습니까?",
        html: `
            <p style="text-align:center">${staDateStr} 연차 1일</p>
            <div class="accordion" id="accordionExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" style="--bs-accordion-btn-focus-box-shadow:;">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                            <div class="badge text-bg-warning text-wrap me-2" style="width: 3rem;">필독</div><h6 class="mb-0">근태 신청전 유의사항</h6>
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                        <div class="accordion-body" style="text-align:left;">
                            <div class="row">
                                <div class="col-1"><i class="bi bi-check-square-fill" style="color:#ffc107;"></i> </div>
                                <div class="col-11">근태관련 제반 규정을 준수하고, <br><strong>목적외 사용시 어떠한 처벌도 <br>감수</strong>하겠음을 확인합니다. <br><br></div>
                            </div>
                            <div class="row">
                                <div class="col-1"><i class="bi bi-check-square-fill" style="color:#ffc107;"></i> </div>
                                <div class="col-11"><strong>인장(부/임시 보관책임자), 보안</strong><br>업무에 대해, 업무인수인계를 완료하였음을 확인합니다.<br><br></div>
                            </div>
                            <div class="row">
                                <div class="col-1"><i class="bi bi-check-square-fill" style="color:#ffc107;"></i> </div>
                                <div class="col-11"></i> 위 담당업무외 <strong>중요서류 및 기타<br>사항</strong>에 대하여 업무인수인계를<br>완료하였음을 확인합니다. <br></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `,
        icon: "info",
        showCancelButton: true,
        scrollbarPadding: false,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "네, 신청할게요!",
        cancelButtonText: "아니요",
    }).then((result) => {
        if (result.isConfirmed) {
            console.log(staDate);
            // DtmHisDTO 객체 생성
            const dtmHisDTO = {
                dtmKindCd: dtmCode,
                dtmReasonCd: dtmReasonCode,
                staYmd: new Date(staDate).toISOString(),
                endYmd: new Date(staDate).toISOString()
            };

            // POST 요청 함수 호출
            postDtmHisDTO(dtmHisDTO);
        }
    });
};

// 근태 신청
const postDtmHisDTO = (dtmHisDTO) => {
    const header = $('meta[name="_csrf_header"]').attr('content');
    const token = $('meta[name="_csrf"]').attr('content');

    $.ajax({
        url: '/api/dtm/appl',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        data: JSON.stringify(dtmHisDTO),
        success: (data) => {
            popupHtmlMsg('신청되었습니다.', `<p style="text-align:center">${dtmHisDTO.staYmd} 연차 1일</p>`, 'success');
            console.log('Success:', data);
        },
        error: (error) => {
            popupHtmlMsg('신청 불가', error, 'error');
            console.error('Error:', error);
        }
    });
};
