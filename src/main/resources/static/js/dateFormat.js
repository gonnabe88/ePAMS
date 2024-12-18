document.addEventListener('DOMContentLoaded', function() {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');

    function formatDate(input) {
        // 숫자만 추출
        let numbers = input.value.replace(/\D/g, '');

        // 8자리까지만 유지 (YYYYMMDD)
        numbers = numbers.slice(0, 8);

        // YYYY-MM-DD 형식으로 변환
        if (numbers.length >= 4) {
            numbers = numbers.slice(0, 4) + '-' + numbers.slice(4);
        }
        if (numbers.length >= 7) {
            numbers = numbers.slice(0, 7) + '-' + numbers.slice(7);
        }

        input.value = numbers;
    }

    function validateDate(dateString) {
        // YYYY-MM-DD 형식인지 확인
        if (!/^\d{4}-\d{2}-\d{2}$/.test(dateString)) return false;

        // Date 객체로 변환하여 유효한 날짜인지 확인
        const parts = dateString.split('-');
        const year = parseInt(parts[0], 10);
        const month = parseInt(parts[1], 10) - 1; // 0-11
        const day = parseInt(parts[2], 10);
        const date = new Date(year, month, day);

        return date.getFullYear() === year &&
            date.getMonth() === month &&
            date.getDate() === day;
    }

    function handleInput(event) {
        formatDate(event.target);
    }

    function handleBlur(event) {
        const input = event.target;
        if (input.value.length === 10) { // YYYY-MM-DD
            if (!validateDate(input.value)) {
                alert('유효하지 않은 날짜입니다. YYYY-MM-DD 형식으로 올바른 날짜를 입력해주세요.');
                input.value = ''; // 잘못된 입력 초기화
            }
        } else if (input.value.length > 0) {
            alert('날짜를 완전히 입력해주세요 (YYYYMMDD).');
            input.value = ''; // 불완전한 입력 초기화
        }
    }

    startDateInput.addEventListener('input', handleInput);
    endDateInput.addEventListener('input', handleInput);
    startDateInput.addEventListener('blur', handleBlur);
    endDateInput.addEventListener('blur', handleBlur);
});