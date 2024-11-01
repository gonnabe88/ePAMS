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
    const dtmReasonCd = $('#dtmSearchReasonCdSelect').data('dtmreasoncd') || ''; // 근태유형 (undefined 처리)
    const dtmKindCd = $('input[name="dtmSearchKindCdSelect"]:checked').val() || ''; //근태종류 undefined 처리
    const staYmdInput = $('#start-input').val(); // 근태기간(시작일)
    const endYmdInput = $('#end-input').val(); // 근태기간(종료일)
    const itemsPerPage = $('#itemsPerPage').val(); // 페이지 아이템수

    // startPage 값 가져오기
    const firstPageLink = $('#first-page-link');
    const startPage = firstPageLink.length ? firstPageLink.data('startpage') : '';

    // endPage 값 가져오기
    const endPageLink = $('#end-page-link');
    const endPage = endPageLink.length ? endPageLink.data('endpage') : '';

    // Base URL + 검색조건
    let baseUrl = '/dtm/dtmList?page=';
    let searchParam = `&statCdList=${statCdList.join(',')}&dtmReasonCd=${dtmReasonCd}&dtmKindCd=${dtmKindCd}&staYmdInput=${staYmdInput}&endYmdInput=${endYmdInput}&itemsPerPage=${itemsPerPage}`;

    // 페이지네이션 링크 업데이트
    $('.page-link').each(function() {
        const pageNumber = $(this).text().trim(); // 페이지 번호 가져오기
        $(this).attr('href', `${baseUrl}${pageNumber}${searchParam}`);
    });

    // 첫 페이지 링크 설정
    if (firstPageLink.length) {
        firstPageLink.attr('href', `${baseUrl}${startPage}${searchParam}`);
    }

    // 마지막 페이지 링크 설정
    if (endPageLink.length) {
        endPageLink.attr('href', `${baseUrl}${endPage}${searchParam}`);
    }

}

// 조회 함수
const search = () => {
    // 조회 조건 가져오기
    const statCdList = [];
    $('input[name="statCdList"]:checked').each(function() {
        statCdList.push($(this).attr('id'));
    });
    const dtmReasonCd = $('#dtmSearchReasonCdSelect option:selected').val() || ''; // undefined 처리
    const dtmKindCd = $('input[name="dtmSearchKindCdSelect"]:checked').val()|| ''; // undefined 처리
    const staYmdInput = $('#start-input').val();
    const endYmdInput = $('#end-input').val();
    const itemsPerPage = $('#itemsPerPage').val();

    // 검색 조건에 맞는 데이터를 포함한 새로운 URL을 만듭니다.
    let baseUrl = '/dtm/dtmList';
    let newUrl = `${baseUrl}?&statCdList=${statCdList.join(',')}&dtmReasonCd=${dtmReasonCd}&dtmKindCd=${dtmKindCd}&staYmdInput=${staYmdInput}&endYmdInput=${endYmdInput}&itemsPerPage=${itemsPerPage}`;

    // 새로운 URL로 리다이렉트하여 페이지를 로드합니다.
    window.location.href = newUrl;

    // 검색 버튼 collapse 처리
    $('#collapseSearch').collapse('hide');
};