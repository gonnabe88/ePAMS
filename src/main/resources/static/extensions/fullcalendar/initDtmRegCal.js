document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('dtmRegCal');
    var selectedDate = null;
    var selectedDateStr = null;

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'today prev',
            center: 'title',
            right: 'next add'
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
    });djwp
});
