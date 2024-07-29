/**
 * 검색 폼 관련 스크립트
 * @author K140024
 * @implements 검색 폼 관련 스크립트
 *  - 검색 시 페이지네이션 링크를 업데이트
 *  - 페이지네이션 버튼 클릭 시 전체화면을 reload 하지 않도록 비동기 처리
 *  - 검색 폼의 조회 버튼 클릭 시 검색 조건에 맞는 데이터 조회
 *  - 검색 폼의 초기화 버튼 클릭 시 검색 폼 초기화 후 목록 갱신
 *  - 목록수 Select Box 선택 시 목록 갱신
 * @since 2024-07-28
 */

// 페이지네이션 링크 주소 업데이트
const updatePaginationLinks = () => {
    // 폼의 값 가져오기
    let statCdList = [];
    $('input[name="statCdList"]:checked').each(function() {
        statCdList.push($(this).attr('id'));
    });
    let dtmReasonCd = $('#list option:selected').val();
    let staYmdInput = $('#start-input').val();
    let endYmdInput = $('#end-input').val();
    let itemsPerPage = $('#itemsPerPage').val();

    // 페이지네이션 링크 업데이트
    $('.page-link').each(function() {
        let baseUrl = $(this).attr('href').split('?')[0];
        let newUrl = `${baseUrl}?page=${$(this).text()}&statCdList=${statCdList.join(',')}&dtmReasonCd=${dtmReasonCd}&staYmdInput=${staYmdInput}&endYmdInput=${endYmdInput}&itemsPerPage=${itemsPerPage}`;
        $(this).attr('href', newUrl);
    });
}

// 페이지네이션 버튼 클릭 시 전체화면을 reload 하지 않도록 비동기 처리
$(document).on('click', '.page-link', function(event) {
    event.preventDefault();
    let url = $(this).attr('href');
    $.get(url, function(data) {
        $('#dtmListContainer').html($(data).find('#dtmListContainer').html());
        updatePaginationLinks(); // 페이지 업데이트 후 다시 링크 업데이트
    });
});

// (검색폼) 조회 버튼 클릭 시
$('#search-button').on('click', function() {
    search();
});

// (검색폼) 초기화 버튼 클릭 시 폼 초기화 후 목록 갱신
$('#reset-button').on('click', function() {
    $('input[name="statCdList"]').prop('checked', true); // 모든 체크박스 체크
    $('input[name="select"]').prop('checked', false); // 모든 라디오버튼 해제
    $('#allCategory').prop('checked', true); // '전체' 라디오버튼 체크
    $('#list').val(''); // 셀렉트박스 초기화
    $('#itemsPerPage').val('5'); // 페이지네이션 갯수 초기화

    // 시작 날짜 초기화
    const oneYear = new Date();
    oneYear.setFullYear(oneYear.getFullYear() - 1);
    const lastYear = oneYear.toISOString().split('T')[0];
    $('#start-input').val(lastYear); // 시작 날짜 초기화

    // 종료 날짜 초기화
    const today = new Date().toISOString().split('T')[0];
    $('#end-input').val(today);

    search(); // 목록 갱신
});

// (목록수) itemsPerPage Select Box 선택 시 목록 갱신
$('#itemsPerPage').on('change', function() {
    search();
});

// 목록 갱신 함수
const search = () => {
    let statCdList = [];
    $('input[name="statCdList"]:checked').each(function() {
        statCdList.push($(this).attr('id'));
    });
    let dtmReasonCd = $('#list option:selected').val();
    let staYmdInput = $('#start-input').val();
    let endYmdInput = $('#end-input').val();
    let itemsPerPage = $('#itemsPerPage').val();

    // 검색 조건에 맞는 데이터 조회
    $.get(`/dtm/list?statCdList=${statCdList.join(',')}&dtmReasonCd=${dtmReasonCd}&staYmdInput=${staYmdInput}&endYmdInput=${endYmdInput}&itemsPerPage=${itemsPerPage}`, function(data) {
        $('#dtmListContainer').html($(data).find('#dtmListContainer').html());
        updatePaginationLinks(); // 페이지 업데이트 후 다시 링크 업데이트
        $('#collapseSearch').collapse('hide'); // 검색 폼 접기
    });
}