/**
 * 날짜 입력폼 유효성 검증
 * @author K140024
 * @implements 날짜 입력폼에 대한 유효성 검증 및 포맷팅
 * @since 2024-07-28
 */

// 날짜 입력폼 이벤트 발생 시
$(document).on('input', '.start-input, .end-input', function(event) {
    handleDateInput(event);
});

// 날짜 입력값 검증 및 포맷팅
const handleDateInput = (event) => {
    const input = event.target;
    const value = input.value.replace(/[^0-9]/g, ''); // 숫자만 입력 허용
    let formattedValue = '';

    if (value.length <= 4) {
        formattedValue = value;
    } else if (value.length <= 6) {
        formattedValue = `${value.slice(0, 4)}-${value.slice(4)}`;
    } else {
        formattedValue = `${value.slice(0, 4)}-${value.slice(4, 6)}-${value.slice(6, 8)}`;
    }

    input.value = formattedValue;

    if (formattedValue && !isValidDate(formattedValue)) {
        input.setCustomValidity('Please enter a valid date in YYYY-MM-DD format');
    } else {
        input.setCustomValidity('');
    }
}

// 날짜 입력 형식 검증
const isValidDate = (dateString) => {
    const regex = /^\d{4}-\d{2}-\d{2}$/;
    if (!dateString.match(regex)) {
        return false;
    }

    const date = new Date(dateString);
    const timestamp = date.getTime();

    if (typeof timestamp !== 'number' || Number.isNaN(timestamp)) return false;
    return dateString === date.toISOString().split('T')[0];
}