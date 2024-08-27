document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var selectedDate = null;
    var selectedDateStr = null;

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'add'
        },
        customButtons: {
            add: {
                text: '신청하기',
                click: function() {
                    var url = '/dtm/dtmRegDetail';
                    if (selectedDateStr) {
                        url += '?date=' + selectedDateStr;
                    }
                    window.location.href = url;
                }
            }
        },
        titleFormat: { year: 'numeric', month: 'long' },
        locale: 'ko',
        buttonText: {
            today: '오늘'
        },
        firstDay: 0,
        height: 'auto',
        aspectRatio: 1,
        fixedWeekCount: false,
        dayCellContent: function(arg) {
            return { html: '<div class="fc-daygrid-day-number">' + arg.dayNumberText.replace('일', '') + '</div>' };
        },
        dateClick: function(info) {
            if (selectedDate) {
                selectedDate.classList.remove('selected-date');
            }
            info.dayEl.classList.add('selected-date');
            selectedDate = info.dayEl;
            selectedDateStr = info.dateStr;
        }
    });

    calendar.render();

    // 날짜 박스의 높이를 넓이와 맞추어 정사각형으로 설정하는 함수
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
});
