$(document).ready(() => {
    console.log("click");
    // 'save' 버튼 클릭 시 save2() 함수 호출
    $('.appl-alert-button').on('click', function() {
        ApplAlert(this);
    });
});