$(document).ready(() => {
    // id : delete 버튼 클릭 시 del() 함수 호출
    $('#delete').on('click', function() {
        del(this);
    });
});