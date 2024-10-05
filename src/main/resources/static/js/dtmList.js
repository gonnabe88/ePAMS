$(document).ready(() => { // 화면 로드 시 호출

    // Input Form Element를 가져와서 달력 초기화
    const staYmdInputEl = document.getElementById('start-input');
    const endYmdInputEl = document.getElementById('end-input');
    const startDateEl = document.getElementById('start-date');
    const endDateEl = document.getElementById('end-date');
    initializePicker(staYmdInputEl, endYmdInputEl, startDateEl, endDateEl);

    // 화면 로드 시 항상 페이지네이션 링크 업데이트
    updatePaginationLinks();

    // 검색 드롭다운 펼쳐지는 경우
    $('#collapseSearch').on('show.bs.collapse', function() {
        $('#toggleBtn').removeClass('btn-secondary').addClass('btn-warning').html('<i class="bi bi-capslock me-1"></i>닫기');
        $('#collapseCard').addClass('card-warning');
    });

    // 검색 드롭다운 접히는 경우
    $('#collapseSearch').on('hidden.bs.collapse', function() {
        $('#toggleBtn').removeClass('btn-warning').addClass('btn-secondary').html('<i class="bi bi-search me-1"></i>검색');
        $('#collapseCard').removeClass('card-warning');
    });

    // 검색 드롭다운 내부 닫기 클릭 시
    $('#close-button').on('click', function() {
        $('#collapseSearch').collapse('hide'); // 검색 버튼 collapse 처리
    });

    // 휴가 보유현황 클릭 시 Modal 실행 이벤트 등록
    $('#openDtmStatusModal').on('click', function () {
        $('#dtmStatusModal').modal('show');
    });

    // 취소 클릭 시 Modal 실행 이벤트 등록
    $('.cancelBtn').on('click', function () {
        dtmRevokeApplPopup(this);
    });

    // 변경(modifyBtn) 버튼 클릭 시
    $('.modifyBtn').on('click', function () {
        
        let dtmHisDTOList = []; // 근태 List 객체 생성
        const button = $('#dtmModifyBtn');
        const applId = button.data('applid');
        const dtmHisId = button.data('dtmhisid');
        const modiType = "D";
        const dtmKindCd = button.data('dtmkindcd');
        const dtmReasonCd = button.data('dtmreasoncd');
        const dtmReasonNm = button.data('dtmreasonnm');
        const beforeStaYmd = button.data('staymd');
        const beforeEndYmd = button.data('endymd');     
        const dtmRange = button.data('dtmrange'); 

        // [취소] 근태 객체 세팅
        let revokeDtmHisDTO = {
            applId: applId, // 신청서ID
            dtmHisId: dtmHisId, // 근태ID
            modiType: modiType, // 변경타입

            dtmKindCd: dtmKindCd, // 근태종류
            dtmReasonCd: dtmReasonCd, // 근태사유코드
            dtmReasonNm: dtmReasonNm, // 근태사유
            dtmDispName: dtmReasonNm, // 근태표시이름
            staYmd: beforeStaYmd, // 근태시작일
            endYmd: beforeEndYmd // 근태종료일
        };

        dtmHisDTOList.push(revokeDtmHisDTO); // 서버로 전달할 전체 리스트 객체 추가

        $('#dynamicModal').modal('show'); // Modal에 변경 대상(취소) 객체 보여주기
        $('#dtmResonNm').text(dtmReasonNm); // 근태사유(유형) 표시
        $('#dtmRange').text(dtmRange); // 근태기간 표시
        dtmApplSelectForm(dtmReasonCd, "modal"); // select form 세팅

        $('#add').on('click', function() {
            // 현재 세팅된 날짜 가져오기
            let afterStaYmd = $('#startDate').val();
            let afterEndYmd = $('#endDate').val();
            let afterDtmReasonCd = $('#dtmReasonCdSelect option:selected').val();
            let afterDtmReasonNm = $('#dtmReasonCdSelect option:selected').text();
            let afterdtmKindCd = $('input[name="dtmKindCdSelect"]:checked').val();

            // 화면에 보여줄 html sub page
            let html = `
            <div class="swiper-slide">
                <div class="card mb-1 animate__animated animate__fadeIn animate__slow">
                    <div class="card-body pt-2 pb-1">
                    <!-- X 아이콘을 우측 상단에 추가 -->
                <button class="close-btn">&times;</button>
                        <div class="row">
                            <div class="col">
                                <div class="d-flex justify-content-start mb-4 gap-1">
                                    <div class="registDtmNameTag">
                                        <h6 class="mb-0 ms-3">
                                            <span class="badge badge-sm rounded-pill bg-primary m-0">신규</span>
                                            <span>${afterDtmReasonNm}</span>
                                        </h6>
                                        <h7 class="mb-0 ms-3">${afterStaYmd}~${afterEndYmd}</h7>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`

            // [신규] 근태 객체 세팅
            let registDtmHisDTO = {
                dtmKindCd: afterdtmKindCd, // 근태종류
                dtmReasonCd: afterDtmReasonCd, // 근태사유코드
                dtmReasonNm: afterDtmReasonNm, // 근태사유
                dtmDispName: afterDtmReasonNm, // 근태표시이름
                staYmd: new Date(afterStaYmd), // 근태시작일 (fullcalendar에서 세팅한 날짜)
                endYmd: new Date(afterEndYmd) // 근태종료일 (fullcalendar에서 세팅한 날짜)
            };
            
            dtmHisDTOList.push(registDtmHisDTO); // 서버로 전달할 전체 리스트 객체 추가

            if(swiper.slides.length == 0) { // 첫 아이템 추가 시 
                $('#comment').remove(); // 기존 안내 코멘트 삭제
            }
            swiper.appendSlide(html); // 아이템 추가
            swiper.slideTo(swiper.slides.length - 1); // 마지막 아이템 이동
            swiper.update(); // swiper 업데이트

            $(document).on('click', '.close-btn', function() { // X 버튼 클릭 시 해당 슬라이드를 삭제하는 이벤트 등록
                const slideIndex = $(this).closest('.swiper-slide').index(); // 버튼이 포함된 swiper-slide 요소를 찾음
                swiper.removeSlide(slideIndex); // 해당 인덱스의 슬라이드를 삭제
            });
        });

        // Submit 버튼 클릭 시 기존에 등록된 이벤트 핸들러가 있다면 제거 후 등록 (중복 호출 방지)
        $('#submit').off('click').on('click', function() {
            ApplCheck(dtmHisDTOList); // 신청 내역 체크
            dtmHisDTOList = []; // 근태 List 객체 초기화
            $('#dynamicModal').modal('hide'); // 모달 종료
        });
    });

    // (검색폼) 조회 버튼 클릭 시
    $('#search-button').on('click', function() {
        search();
    });

    // (목록수) itemsPerPage Select Box 선택 시 목록 갱신
    $('#itemsPerPage').on('change', function() {
        search();
    });

    // (검색폼) 초기화 버튼 클릭 시 폼 초기화 후 목록 갱신
    $('#reset-button').on('click', function() {
        resetSelect(); // Select2 초기화
        search(); // 목록 갱신
    });

});