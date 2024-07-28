

// 달력 생성 함수
const initializePicker = (staYmdInput, endYmdInput) => {
    const picker = new Litepicker({
        element: staYmdInput,
        elementEnd: endYmdInput,
        switchingMonths: true,
        singleMode: false,
        allowRepick: false,
        autoApply: false,
        autoRefresh: true,
        inlineMode: false,
        resetButton: true,
        lang: 'ko-KR',
        buttonText: { "apply": "적용", "cancel": "취소"},
        dropdowns: { "minYear": 2023, "maxYear": null, "months": false, "years": false },
        format: 'YYYY-MM-DD',
        setup: (picker) => {
            picker.on('selected', (date1, date2) => {
                staYmdInput.value = date1.format('YYYY-MM-DD');
                endYmdInput.value = date2.format('YYYY-MM-DD');
            })
        }
    });

    // 날짜 지정 시 Form에 값 설정
    const updatePicker = () => {
        const startDateValue = staYmdInput.value;
        const endDateValue = endYmdInput.value;
        if (startDateValue && endDateValue) {
            picker.setDateRange(startDateValue, endDateValue);
        }
    }

    $('#staYmdInput').on('change', updatePicker);
    $('#endYmdInput').on('change', updatePicker);

    // 1년 전 날짜 설정
    const oneYear = new Date();
    oneYear.setFullYear(oneYear.getFullYear() - 1);
    const lastYear = oneYear.toISOString().split('T')[0];
    staYmdInput.value = lastYear;

    // 오늘 날짜 설정
    const today = new Date().toISOString().split('T')[0];
    endYmdInput.value = today;

    // 달력 날짜 설정 (1년전 ~ 오늘)
    picker.setDateRange(lastYear, today);
}

// 화면 로드 시 달력 초기화
$(document).ready(function () {
    // Input Form 입력값을 가져와서 달력 초기화
    const staYmdInput = document.getElementById('start-input');
    const endYmdInput = document.getElementById('end-input');
    initializePicker(staYmdInput, endYmdInput);
    console.log('Litepicker initialized');
});