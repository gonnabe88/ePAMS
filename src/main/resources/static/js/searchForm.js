/**
 * 검색 폼 관련 스크립트(/js/searchForm.js)
 * @author K140024
 * @since 2024-07-28
 * @implements 검색 폼 관련 스크립트
 *  - 검색 시 페이지네이션 링크를 업데이트
 *  - 페이지네이션 버튼 클릭 시 전체화면을 reload 하지 않도록 비동기 처리
 *  - 검색 폼의 조회 버튼 클릭 시 검색 조건에 맞는 데이터 조회
 *  - 검색 폼의 초기화 버튼 클릭 시 검색 폼 초기화 후 목록 갱신
 *  - 목록수 Select Box 선택 시 목록 갱신
 *  - 2024-08-11 CWE-79(XSS) 취약점 조치
 */

// 페이지네이션 링크 주소 업데이트
const updatePaginationLinks = () => {
    // 결재상태
    const statCdList = [];
    $('input[name="statCdList"]:checked').each(function() {
        statCdList.push($(this).attr('id'));
    });
    const dtmReasonCd = $('#list').data('dtmreasoncd') || ''; // 근태유형 (undefined 처리)
    const dtmKindCd = $('input[name="category"]:checked').val()|| ''; //근태종류 undefined 처리
    const staYmdInput = $('#start-input').val(); // 근태기간(시작일)
    const endYmdInput = $('#end-input').val(); // 근태기간(종료일)
    const itemsPerPage = $('#itemsPerPage').val(); // 페이지 아이템수
    const startPage = $('.first.page-link').length ? $('.first.page-link').data('startPage') : ''; 
    const endPage = $('.last.page-link').length ? $('.last.page-link').data('endPage') : ''; 
    let baseUrl = '/dtm/dtmList?page=';
    let searchParam = `&statCdList=${statCdList.join(',')}&dtmReasonCd=${dtmReasonCd}&dtmKindCd=${dtmKindCd}&staYmdInput=${staYmdInput}&endYmdInput=${endYmdInput}&itemsPerPage=${itemsPerPage}`;

    // 페이지네이션 링크 업데이트
    $('.page-link').each(function() {
        $(this).attr('href', baseUrl+`${$(this).text()}`+searchParam);
    });
    $('.first.page-link').length ? $('.first.page-link').attr('href') : baseUrl+startPage+searchParam;
    $('.last.page-link').length ? $('.last.page-link').attr('href') : baseUrl+endPage+searchParam;

}

// 조회 함수
const search = () => {
    // 조회 조건 가져오기
    const statCdList = [];
    $('input[name="statCdList"]:checked').each(function() {
        statCdList.push($(this).attr('id'));
    });
    const dtmReasonCd = $('#list option:selected').val() || ''; // undefined 처리
    const dtmKindCd = $('input[name="category"]:checked').val()|| ''; // undefined 처리
    const staYmdInput = $('#start-input').val();
    const endYmdInput = $('#end-input').val();
    const itemsPerPage = $('#itemsPerPage').val();

    console.log("검색폼");
    console.log(statCdList, dtmReasonCd, dtmKindCd, staYmdInput, endYmdInput, itemsPerPage);

    // 검색 조건에 맞는 데이터를 포함한 새로운 URL을 만듭니다.
    let baseUrl = '/dtm/dtmList';
    let newUrl = `${baseUrl}?&statCdList=${statCdList.join(',')}&dtmReasonCd=${dtmReasonCd}&dtmKindCd=${dtmKindCd}&staYmdInput=${staYmdInput}&endYmdInput=${endYmdInput}&itemsPerPage=${itemsPerPage}`;

    // 새로운 URL로 리다이렉트하여 페이지를 로드합니다.
    window.location.href = newUrl;

    // 검색 버튼 collapse 처리
    $('#collapseSearch').collapse('hide');
};

// 검색 폼에서 초기화를 눌렀을 때
const resetSelect = () => {
    $('input[name="statCdList"]').prop('checked', true); // 모든 체크박스 체크
    $('input[name="select"]').prop('checked', false); // 모든 라디오버튼 해제
    $('#allCategory').prop('checked', true); // '전체' 라디오버튼 체크
    $('#list').val(''); // 셀렉트박스 초기화
    $('#itemsPerPage').val('5'); // 페이지네이션 갯수 초기화
    window.resetPicker(); // Datepicker 초기화

    $.ajax({
        url: "/api/commoncode/code/DTM_REASON_CD",
        method: 'GET',
        success: function (data) {
            const $select = $('#list');
            $select.empty(); // 기존 옵션 삭제
            $select.append(new Option("전체", "")); // 기본 옵션 추가
            data.forEach(item => {
                $select.append(new Option(item.codeName, item.code)); // 새로운 옵션 추가
            });
            $select.val('').trigger('change'); // Select2 UI 갱신 및 초기화
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data: ', error);
        }
    });
}