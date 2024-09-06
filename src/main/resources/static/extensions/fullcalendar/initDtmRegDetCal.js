document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('dtmRegDetCal');
    var selectedDates = []; // 사용자가 선택한 날짜를 저장하는 배열
    var startDate = new Date(); // 오늘 날짜
    var endDate = new Date(); // 기본적으로 오늘로 설정

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

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'twoWeekView',
        headerToolbar: false,
        views: {
            twoWeekView: {
                type: 'dayGrid',
                duration: { weeks: 2 }
            }
        },
        height: 'auto',
        firstDay: 0,
        fixedWeekCount: false,
        scrollTime: '00:00:00',
        editable: true,
        selectable: true,
        nowIndicator: true,
        dayMaxEvents: true,
        eventLimit: true,
        events: [
            // 여기에 이벤트를 추가하세요
        ],
        dateClick: function(info) {
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
        viewDidMount: function(view) {
            if (view.type === 'twoWeekView') {
                var startDate = view.currentStart;
                var endDate = new Date(startDate.getTime() + 13 * 24 * 60 * 60 * 1000);
                calendar.gotoDate(startDate);
            }
        }
    });

    calendar.render();

    document.getElementById('startDate').addEventListener('input', function() {
        var dateString = this.value;
        var parsedDate = parseDate(dateString);
        if (parsedDate && !isNaN(parsedDate.getTime())) {
            startDate = parsedDate;
            if (endDate < startDate) {
                endDate = startDate;
            }
            updateDates(startDate, endDate);
            addDateRangeHighlight(startDate, endDate);
        }
    });

    document.getElementById('endDate').addEventListener('input', function() {
        var dateString = this.value;
        var parsedDate = parseDate(dateString);
        if (parsedDate && !isNaN(parsedDate.getTime())) {
            endDate = parsedDate;
            if (startDate > endDate) {
                startDate = endDate;
            }
            updateDates(startDate, endDate);
            addDateRangeHighlight(startDate, endDate);
        }
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
    updateDates(startDate, endDate);
});