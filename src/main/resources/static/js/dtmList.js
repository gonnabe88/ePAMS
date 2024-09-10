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

    // 휴가 보유현황 클릭 시 Modal 실행 이벤트 등록
    $('#openDtmStatusModal').on('click', function () {
        $('#dtmStatusModal').modal('show');
    });

    // 취소 클릭 시 Modal 실행 이벤트 등록
    $('.cancelBtn').on('click', function () {
        dtmRevokeApplPopup(this);
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