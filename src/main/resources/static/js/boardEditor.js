$(document).ready(() => {

    const boardType = $('#save').attr('data-boardType');

    // 'save' 버튼 클릭 시 () 함수 호출
    $('#save').on('click', () => save(boardType));
});