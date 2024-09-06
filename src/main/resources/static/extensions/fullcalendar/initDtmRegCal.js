document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calender');
    var selectedDates = [];
    var selectOneDate = null;
    var selectedDateStr = null;
    var currentPage = window.location.pathname;

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'today prev',
            center: 'title',
            right: 'next'
        },
        titleFormat: { year: 'numeric', month: '2-digit' },
        locale: 'ko',
        buttonText: {
            today: '오늘'
        },
        firstDay: 0,
        height: 'auto',
        fixedWeekCount: false,
        dayCellContent: function(arg) {
            return { html: '<div class="fc-daygrid-day-number">' + arg.dayNumberText.replace('일', '') + '</div>' };
        },
        dateClick: function(info) {
            var selectedDate = new Date(info.dateStr); // 선택한 날짜
            console.log("Selected Date:", formatDate(selectedDate));
            if (currentPage.includes('/dtm/dtmRegDetail')) {
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
            }
            else {
                if (selectOneDate) {
                    selectOneDate.classList.remove('selected-date');
                }
                info.dayEl.classList.add('selected-date');
                selectOneDate = info.dayEl;
                selectedDateStr = info.dateStr;
            }
        },
        datesSet: function() {
            adjustCellSize();
        }
    });

    calendar.render();

    function adjustCellSize() {
        const cells = document.querySelectorAll('.fc-daygrid-day');
        cells.forEach(cell => {
            cell.style.height = `${cell.offsetWidth}px`;
        });
    }

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
        calendar.getEvents().forEach(event => event.remove());

        if (start.getTime() === end.getTime()) {
            calendar.addEvent({
                start: start,
                end: addOneDay(start), // 선택한 날짜 하루 더하기
                allDay: true,
                display: 'background',
                classNames: ['selected-day']
            });
        } else {
            calendar.addEvent({
                start: start,
                end: addOneDay(end), // 끝나는 날짜의 다음 날
                allDay: true,
                display: 'background',
                backgroundColor: '#87CEFA',
                borderColor: '#1E90FF'
            });
        }
    }

    function adjustDateBoxSize() {
        var dayCells = document.querySelectorAll('.fc-daygrid-day');
        dayCells.forEach(function(cell) {
            var width = cell.offsetWidth;
            cell.style.height = width + 'px';
        });
    }

    // 초기 호출 및 창 크기 변경 시 박스 크기 조정
    adjustDateBoxSize();
    window.addEventListener('resize', function() {
        calendar.updateSize();
        adjustDateBoxSize();
    });

    let touchStartX, touchStartY;
    calendarEl.addEventListener('touchstart', function(e) {
        touchStartX = e.touches[0].clientX;
        touchStartY = e.touches[0].clientY;
    });

    calendarEl.addEventListener('touchmove', function(e) {
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
        } else {
            // 상하 스와이프
            if (deltaY > 0) {
                calendar.next();
            } else {
                calendar.prev();
            }
        }

        touchStartX = null;
        touchStartY = null;
    });

    // 마우스 휠 이벤트 처리
    calendarEl.addEventListener('wheel', function(e) {
        e.preventDefault();
        if (e.deltaY > 0) {
            calendar.next();
        } else {
            calendar.prev();
        }
    });
});
