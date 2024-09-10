// 달력 생성 함수
const initializePicker = (staYmdInput, endYmdInput, startDate, endDate) => {
    const picker = new Litepicker({
        element: startDate, // 기본 달력 시작일 매핑 위치
        elementEnd: endDate, // 기본 달력 종료일 매핑 위치
        switchingMonths: true,
        singleMode: false,
        allowRepick: true,
        autoApply: true,
        autoRefresh: true,
        inlineMode: true,
        resetButton: true,
        lang: 'ko-KR',
        buttonText: { "apply": "적용", "cancel": "취소"},
        dropdowns: { "minYear": 2023, "maxYear": null, "months": false, "years": false },
        format: 'YYYY-MM-DD',
        plugins: ['mobilefriendly'],
        setup: (picker) => {
            picker.on('selected', (date1, date2) => {
                // 날짜가 유효하지 않을 때 빈 문자열 설정
                staYmdInput.value = date1 ? date1.format('YYYY-MM-DD') : ''; // 시작일
                endYmdInput.value = date2 && !isNaN(date2.getTime()) ? date2.format('YYYY-MM-DD') : ''; // 종료일
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


    // 초기 날짜 설정
    const setInitialDates = () => {
        // 1년 전 날짜 설정
        const oneYear = new Date();
        oneYear.setFullYear(oneYear.getFullYear() - 1);
        const lastYear = oneYear.toISOString().split('T')[0];
        staYmdInput.value = lastYear;

        // 종료일을 오늘 날짜로 설정
        //const today = new Date().toISOString().split('T')[0];
        //endYmdInput.value = today;

        // 달력 날짜 설정 (1년전 ~ 오늘)
        //picker.setDateRange(lastYear, today);

        // 종료일을 공백으로 설정
        endYmdInput.value = '';

        // 달력 날짜 설정 (1년전 ~ )   
        picker.setDateRange(lastYear, '');
    }

    //setInitialDates(); // 초기화 함수 호출

    // Reset function for external calls
    //window.resetPicker = setInitialDates; // 글로벌 함수로 설정하여 외부 접근 가능
}