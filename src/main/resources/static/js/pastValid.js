// 오늘 날짜를 가져와서 시간 정보를 제거
const today = new Date();
today.setHours(0, 0, 0, 0);

const dateWarning = document.getElementById('dateWarning');

// 경고 문구를 표시할 함수
function checkDateValidity(startDateInput, endDateInput) {
    console.log("시작일",startDateInput.value)
    console.log("오늘",today)

    if ((startDateInput && startDateInput < today) || (endDateInput && startDateInput < today)) {
        console.log("과거!")
        dateWarning.style.display = 'block'; // 경고 문구 표시
    } else {
        console.log("끄엥!")
        dateWarning.style.display = 'none'; // 경고 문구 숨기기
    }
}
