/**
 * dtmList.js 전용 자바스크립트(/js/dtmList.js)
 * @author K140024
 * @implements 
 *  - 근태 현황 보기 Modal
 * @since 2024-07-28
 */

// 화면 로드 시 함수
$(document).ready(function () {

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
            // 검색 버튼 collapse 처리
        $('#collapseSearch').collapse('hide');
    });

    // 휴가 보유현황 클릭 시 Modal 실행 이벤트 등록
    $('#openDtmStatusModal').on('click', function () {
        $('#dtmStatusModal').modal('show');
    });

    // 취소 클릭 시 Modal 실행 이벤트 등록
    $('.cancelBtn').on('click', function () {
        dtmRevokeApplPopup(this);
    });

    // 변경 클릭 시 Modal 실행 이벤트 등록
    $('.modifyBtn').on('click', function () {

        let dtmHisDTOList = [];
        const button = $('#dtmModifyBtn');
        const applId = button.data('applid');
        const dtmHisId = button.data('dtmhisid');
        const modiType = "D";
        const dtmKindCd = button.data('dtmkindcd');
        const dtmReasonCd = button.data('dtmreasoncd');
        const dtmReasonNm = button.data('dtmreasonnm');
        const beforeStaYmd = button.data('staymd');
        const beforeEndYmd = button.data('endymd');        
                
        // 변경 대상(취소) 객체 세팅
        let revokeDtmHisDTO = {
            applId: applId,
            dtmHisId: dtmHisId,
            modiType: modiType,
            dtmKindCd: dtmKindCd,
            dtmReasonCd: dtmReasonCd,
            dtmReasonNm: dtmReasonNm,
            staYmd: new Date(beforeStaYmd),
            endYmd: new Date(beforeEndYmd)
        };

        // 서버로 전달할 전체 리스트 객체 추가
        dtmHisDTOList.push(revokeDtmHisDTO);

        // Modal에 변경 대상(취소) 객체 보여주기
        $('#dynamicModal').modal('show');
        $('#dtmResonNm').text(dtmReasonNm);
        $('#beforeStaYmd').text(beforeStaYmd.split('T')[0]);
        $('#beforeEndYmd').text(beforeEndYmd.split('T')[0]);

        // Submit 버튼 클릭 시 기존에 등록된 이벤트 핸들러가 있다면 제거 후 등록 (중복 호출 방지)
        $('#submit').off('click').on('click', function() {

            // 현재 세팅된 날짜 가져오기
            const afterStaYmd = $('#startDate').val();
            const afterEndYmd = $('#endDate').val();

            // dtoHisDTO 객체 세팅
            let registDtmHisDTO = {
                dtmKindCd: dtmKindCd,
                dtmReasonCd: dtmReasonCd,
                dtmReasonNm: dtmReasonNm,
                staYmd: new Date(afterStaYmd),
                endYmd: new Date(afterEndYmd)
            };
            // 서버로 전달할 전체 리스트 객체 추가
            dtmHisDTOList.push(registDtmHisDTO);
            
            // 신청 팝업 실행 후
            ApplListAlertPopup(this, dtmHisDTOList);

            // 모달 종료
            $('#dynamicModal').modal('hide');
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