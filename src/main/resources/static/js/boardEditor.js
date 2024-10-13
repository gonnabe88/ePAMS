$(document).ready(() => {

    const boardType = $('#save').attr('data-boardType');
    // 'save' 버튼 클릭 시 () 함수 호출
    $('#save').on('click', () => save(boardType));

    // jQuery로 4자리 숫자 입력 제한
    $('#guidPrgSno').on('input', function() {
        if ($(this).val().length > 4) {
            $(this).val($(this).val().slice(0, 4));  // 최대 4자리까지만 입력 가능
        }
    });
});