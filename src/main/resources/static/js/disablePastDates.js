// 경고 문구를 보여주는 함수
function disablePastDates() {
    const dateWarning = document.getElementById('dateWarning');

    // 경고 문구 표시 및 투명도 초기화
    dateWarning.style.display = 'flex'; // flex로 설정해서 가운데 정렬
    dateWarning.style.opacity = '1'; // 처음에는 완전히 보이게

    // 2초 후에 서서히 사라지게 함
    setTimeout(function() {
        dateWarning.style.opacity = '0'; // 서서히 사라짐

        // 완전히 사라진 후 display를 none으로 설정 (1초 후)
        setTimeout(function() {
            dateWarning.style.display = 'none';
        }, 1000); // 1000ms = 1초 (opacity transition 시간과 동일)
    }, 1000); // 경고 문구가 보여지는 시간
}
//
// // 경고 문구를 클릭하면 즉시 숨기기
// document.getElementById('dateWarning').addEventListener('click', function() {
//     const dateWarning = document.getElementById('dateWarning');
//     dateWarning.style.opacity = '0'; // 클릭 시 서서히 사라지게
//
//     // 1초 후에 display를 none으로 설정
//     setTimeout(function() {
//         dateWarning.style.display = 'none';
//     }, 1000); // opacity 애니메이션 시간과 맞춤
// });?