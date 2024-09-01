/**
 * list.js 전용 자바스크립트(/js/list.js)
 * @author K140024
 * @implements 
 *  - 근태 현황 보기 Modal
 * @since 2024-07-28
 */

// 화면 로드 시 함수
$(document).ready(function () {

    // Input Form 입력값을 가져와서 달력 초기화
    const staYmdInput = document.getElementById('start-input');
    const endYmdInput = document.getElementById('end-input');
    const startDate = document.getElementById('start-date');
    const endDate = document.getElementById('end-date');
    initializePicker(staYmdInput, endYmdInput, startDate, endDate);

    // 페이지네이션 링크 업데이트
    updatePaginationLinks();

    // 휴가 보유현황 클릭 시 Modal 실행 이벤트 등록
    $('#openDtmStatusModal').on('click', function () {
        $('#dtmStatusModal').modal('show');
    });   

});