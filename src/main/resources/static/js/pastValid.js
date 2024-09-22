// 오늘 날짜를 가져와서 시간 정보를 제거
const today = new Date();
today.setHours(0, 0, 0, 0);

const dateWarning = document.getElementById('dateWarning');

// 경고 문구를 표시할 함수
function checkDateValidity(startDateInput, endDateInput) {
    if ((startDateInput && startDateInput < today) || (endDateInput && startDateInput < today)) {
        dateWarning.style.display = 'block'; // 경고 문구 표시
        dateWarning.style.animation = 'none';
        void dateWarning.offsetWidth; // 리플로우 강제 실행
        dateWarning.style.animation = 'blink 0.5s ease-in-out';
    } else {
        dateWarning.style.display = 'none'; // 경고 문구 숨기기
    }
}
