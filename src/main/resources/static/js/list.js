/**
 * list.js 전용 자바스크립트(/js/list.js)
 * @author K140024
 * @implements 
 *  - 근태 현황 보기 Modal
 * @since 2024-07-28
 */

// 근태 현황 보기 Modal
$(document).ready(function () {
    // Open the modal when the button is clicked
    $('#openDtmStatusModal').on('click', function () {
        $('#dtmStatusModal').modal('show');
    });
});