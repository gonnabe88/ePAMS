$(document).ready(function () {

    const calendarEl = document.getElementById('dtmCalendar');
    let selectedDate = null;
    let selectedDateStr = null;

    // div 태그에서 data-events 속성을 추출하여 JSON 데이터로 변환
    const eventsData = calendarEl.getAttribute('data-events');
    const events = JSON.parse(eventsData);

    // 휴일 목록 추가 (YYYY-MM-DD 형식)
    const holidays = ['2024-09-16', '2024-09-17', '2024-09-18']; // 추가할 휴일 날짜



    console.log(events); // 변환된 이벤트 데이터 확인

    const calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'standard',
        initialView: 'dayGridMonth',
        editable: true,
        selectable: true,
        scrollable: true,
        headerToolbar: {
            left: 'today',
            center: 'prev title next',
            right: 'add'
        },
        customButtons: {
            add: {
                text: '신청',
                click: function() {
                    window.location.href = '/dtm/dtmRegDetail';
                }
            }
        },
        titleFormat: { month: 'long' },
        locale: 'ko',
        buttonText: {
            today: '오늘'
        },
        firstDay: 0,
        height: 'auto',
        aspectRatio: 1,
        fixedWeekCount: false,
        events: events,  // 이벤트 리스트 추가
        datesSet: function() {
            // 오늘 버튼 스타일 적용을 위한 클래스 추가
            const todayButton = document.querySelector('.fc-today-button');
            todayButton && (todayButton.className = 'btn btn-primary btn-sm');

            // 신청 버튼 스타일 적용을 위한 클래스 추가
            const applButton = document.querySelector('.fc-add-button');
            applButton && (applButton.className = 'btn btn-primary btn-sm');
        },
        dayCellDidMount: function(info) {
            const day = info.date.getDay(); // 0: 일요일, 6: 토요일
            const dateStr = info.date.toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit'
            }).replace(/\. /g, '-').replace('.', '');  // YYYY-MM-DD 형식으로 변환

            if (day === 0) {
                // 일요일에 대한 스타일 적용
                info.el.style.setProperty('color', '#FF0000');
            } else if (day === 6) {
                // 토요일에 대한 스타일 적용
                info.el.style.setProperty('color', '#0000FF');
            }
            // 휴일 목록에 있는 날짜에 대해 스타일 적용
            if (holidays.includes(dateStr)) {
                info.el.style.setProperty('color', '#FF0000'); // 휴일 글씨색 적용 (일요일처럼)
            }
        },
        dayCellContent: function(arg) {
            // 날짜에서 '일'단어를 제거
            return { html: '<div class="fc-daygrid-day-number">' + arg.dayNumberText.replace('일', '') + '</div>' };
        },
        dateClick: function(info) {
            // 날짜를 클릭하면 선택된 날짜를 표시
            if (selectedDate) {
                selectedDate.classList.remove('selected-date');
            }
            info.dayEl.classList.add('selected-date');
            selectedDate = info.dayEl;
            selectedDateStr = info.dateStr;
        }
    });

    calendar.render();
});