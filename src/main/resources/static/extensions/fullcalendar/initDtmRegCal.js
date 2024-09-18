document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calender');
    var selectedDates = [];
    var selectOneDate = new Date();
    var selectedDateStr = null;
    var currentPage = window.location.pathname;

    // 페이지 로드 시 날짜 입력 필드에 오늘 날짜 설정
    updateDates(new Date(), new Date());

    const holidays = ['2024-09-16', '2024-09-17', '2024-09-18']; // 추가할 휴일 날짜

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'today prev',
            center: 'title',
            right: 'next'
        },
        titleFormat: {year: 'numeric', month: '2-digit'},
        locale: 'ko',
        buttonText: {
            today: '오늘'
        },
        firstDay: 0,
        height: 'auto',
        fixedWeekCount: false,
        datesSet: function () {
            // 오늘 버튼 스타일 적용을 위한 클래스 추가
            const todayButton = document.querySelector('.fc-today-button');
            todayButton && (todayButton.className = 'btn btn-primary btn-sm fc-today-button');

            // 달력이 전환될 때 fadeIn
            const calendarContainer = document.querySelector('.fc-scrollgrid-sync-table');
            calendarContainer.classList.add('fc-fade');

            setTimeout(() => {
                calendarContainer.classList.remove('fc-fade'); // fadeIn 완료 후 클래스 제거
            }, 500); // fadeIn 애니메이션 지속 시간
        },
        dayCellDidMount: function (info) {
            const day = info.date.getDay(); // 0: 일요일, 6: 토요일
            const dateStr = info.date.toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit'
            }).replace(/\. /g, '-').replace('.', '');  // YYYY-MM-DD 형식으로 변환

            if (day === 0) {
                // 일요일에 대한 스타일 적용 (danger #dc3545)
                info.el.style.setProperty('color', '#dc3545');
            } else if (day === 6) {
                // 토요일에 대한 스타일 적용 (primary #435ebe)
                info.el.style.setProperty('color', '#435ebe');
            }
            // 휴일 목록에 있는 날짜에 대해 스타일 적용 (danger #dc3545)
            if (holidays.includes(dateStr)) {
                info.el.style.setProperty('color', '#dc3545'); // 휴일 글씨색 적용 (일요일처럼)
            }
        },
        dayCellContent: function (arg) {
            return {html: '<div class="fc-daygrid-day-number">' + arg.dayNumberText.replace('일', '') + '</div>'};
        },
        dateClick: function (info) {
            var selectedDate = new Date(info.dateStr); // 선택한 날짜
            console.log("Selected Date:", formatDate(selectedDate));
            selectedDates.push(selectedDate);
            if (selectedDates.length === 2) {
                setStartAndEnd(selectedDates[0], selectedDates[1]);
                selectedDates = []; // 다음 클릭을 위해 배열 초기화
            } else if (selectedDates.length === 1) {
                startDate = selectedDate;
                endDate = selectedDate;
                updateDates(startDate, endDate);
                addDateRangeHighlight(startDate, endDate);
            }
        },

    });

    function formatDate(date) {
        var year = date.getFullYear();
        var month = String(date.getMonth() + 1).padStart(2, '0');
        var day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`; // YYYY-MM-DD 형식으로 변환
    }

    function updateDates(start, end) {
        document.getElementById('startDate').value = formatDate(start);
        document.getElementById('endDate').value = formatDate(end);
    }

    function setStartAndEnd(date1, date2) {
        if (date1 < date2) {
            startDate = date1;
            endDate = date2;
        } else {
            startDate = date2;
            endDate = date1;
        }
        updateDates(startDate, endDate);
        addDateRangeHighlight(startDate, endDate);
    }

    function addOneDay(date) {
        var newDate = new Date(date);
        newDate.setDate(newDate.getDate() + 1); // 하루 더하기
        return newDate;
    }

    function addDateRangeHighlight(start, end) {
        // 모든 기존 이벤트 제거
        calendar.getEvents().forEach(event => event.remove());

        // 이전에 선택된 날짜 셀들의 스타일 초기화
        document.querySelectorAll('.fc-daygrid-day').forEach(dayEl => {
            dayEl.classList.remove('selected-range-start', 'selected-range-middle', 'selected-range-end', 'selected-single-day');
        });

        // 시작일과 종료일이 같은 경우 처리
        if (start.getTime() === end.getTime()) {
            let cell = document.querySelector(`[data-date='${start.toISOString().split('T')[0]}']`);
            if (cell) {
                cell.classList.add('selected-single-day'); // 단일 날짜에 클래스 추가
            }
        } else {
            // 시작일과 종료일의 날짜 범위 선택
            let currentDate = new Date(start);
            while (currentDate <= end) {
                let cell = document.querySelector(`[data-date='${currentDate.toISOString().split('T')[0]}']`);
                if (cell) {
                    if (currentDate.getTime() === start.getTime()) {
                        cell.classList.add('selected-range-start'); // 시작일에만 적용
                    } else if (currentDate.getTime() === end.getTime()) {
                        cell.classList.add('selected-range-end'); // 종료일에만 적용
                    } else {
                        cell.classList.add('selected-range-middle'); // 중간 날짜
                    }
                }
                currentDate.setDate(currentDate.getDate() + 1); // 다음 날짜로 이동
            }
        }
    }






    function resetAllDayStyles() {
        // 모든 날짜 셀에서 관련 클래스 제거
        const allDayCells = calendar.el.querySelectorAll('.fc-daygrid-day');
        allDayCells.forEach(cell => {
            cell.classList.remove('selected-date-range', 'range-start', 'range-end');
        });
    }
    function adjustDateBoxSize() {
        var dayCells = document.querySelectorAll('.fc-daygrid-day');
        dayCells.forEach(function (cell) {
            var width = cell.offsetWidth;
            cell.style.height = width + 'px';
        });
    }

    // 초기 호출 및 창 크기 변경 시 박스 크기 조정
    adjustDateBoxSize();

    calendar.render();

    let touchStartX, touchStartY;
    calendarEl.addEventListener('touchstart', function (e) {
        touchStartX = e.touches[0].clientX;
        touchStartY = e.touches[0].clientY;
    });

    calendarEl.addEventListener('touchmove', function (e) {
        if (!touchStartX || !touchStartY) {
            return;
        }

        let touchEndX = e.touches[0].clientX;
        let touchEndY = e.touches[0].clientY;

        let deltaX = touchStartX - touchEndX;
        let deltaY = touchStartY - touchEndY;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // 좌우 스와이프
            if (deltaX > 0) {
                calendar.next();
            } else {
                calendar.prev();
            }
        }

        touchStartX = null;
        touchStartY = null;
    });
});
