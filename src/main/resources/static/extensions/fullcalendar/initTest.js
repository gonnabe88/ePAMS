document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('dtmRegCal');
    var selectedDate = null;
    var selectedDateStr = null;

    // div 태그에서 data-events 속성을 추출하여 JSON 데이터로 변환
    var eventsData = calendarEl.getAttribute('data-events');
    var events = JSON.parse(eventsData);

    console.log(events); // 변환된 이벤트 데이터 확인

    var calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'standard',
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'today',
            center: 'prev title next',
            right: 'add'
        },
        customButtons: {
            add: {
                text: '신청',
                click: function() {
                    var url = '/dtm/dtmRegDetail';
                    var date;

                    if (selectedDateStr) {
                        date = selectedDateStr;
                    } else {
                        // 오늘 날짜를 YYYY-MM-DD 형식으로 가져옵니다.
                        var today = new Date();
                        var dd = String(today.getDate()).padStart(2, '0');
                        var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
                        var yyyy = today.getFullYear();
                        date = yyyy + '-' + mm + '-' + dd;
                    }
                    url += '?date=' + date;
                    window.location.href = url;
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
            // 오늘 버튼에 클래스 추가
            var todayButton = document.querySelector('.fc-today-button');
            if (todayButton) {
                todayButton.className = 'btn btn-primary btn-sm';
            }

            var applButton = document.querySelector('.fc-add-button');
            if (applButton) {
                applButton.className = 'btn btn-primary btn-sm';
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
    console.log("Calendar initialized and rendered");
});