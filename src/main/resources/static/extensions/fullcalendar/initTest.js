$(document).ready(function () {

    const calendarEl = document.getElementById('dtmCalendar');
    let selectedDate = null;
    let selectedDateStr = null;

    // div 태그에서 data-events 속성을 추출하여 JSON 데이터로 변환
    const eventsData = calendarEl.getAttribute('data-events');
    const eventsRaw = JSON.parse(eventsData);
    console.log(eventsRaw);

    const events = eventsRaw.map(function(event) {
        return {
            title: event.title,
            start: event.start,
            end: event.end,
            allDay: event.allDay,
            extendedProps: {
                dtmHisId: event.dtmHisId // 커스텀 필드는 extendedProps로 추가
            }
        };
    });

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
        titleFormat: { year: 'numeric', month: '2-digit' },
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
            todayButton && (todayButton.className = 'btn btn-primary btn-sm fc-today-button');

            // 신청 버튼 스타일 적용을 위한 클래스 추가
            const applButton = document.querySelector('.fc-add-button');
            applButton && (applButton.className = 'btn btn-primary btn-sm fc-add-button');
            applButton && (applButton.style.visibility = 'hidden'); // 기본적으로 버튼을 숨김

            // 달력이 전환될 때 fadeIn
            const calendarContainer = document.querySelector('.fc-view-harness');
            calendarContainer.classList.add('fc-fade');

            setTimeout(() => {
                calendarContainer.classList.remove('fc-fade'); // fadeIn 완료 후 클래스 제거
            }, 500); // fadeIn 애니메이션 지속 시간
        },
        dayCellDidMount: function(info) {
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
        dayCellContent: function(arg) {
            // 날짜에서 '일'단어를 제거
            return { html: '<div class="fc-daygrid-day-number">' + arg.dayNumberText.replace('일', '') + '</div>' };
        },
        dateClick: function(info) {
            // 날짜를 클릭하면 선택된 날짜를 표시
            selectedDate && (selectedDate.classList.remove('selected-date'));
            info.dayEl.classList.add('selected-date');
            selectedDate = info.dayEl;
            selectedDateStr = info.dateStr;

            // 날짜를 클릭하면 신청 버튼 보이기
            const applButton = document.querySelector('.fc-add-button');
            applButton && (applButton.style.visibility = 'visible'); // 버튼 보이기

            // 해당 날짜의 이벤트 찾기
            const events = calendar.getEvents(); // 모든 이벤트 가져오기
            const selectedEvents = events.filter(event => {
                return event.startStr === info.dateStr; // 해당 날짜의 이벤트만 필터링
            });

            // 이벤트 출력 영역
            const eventContainer = document.getElementById('dtmEvent');
            if (selectedEvents.length > 0) {
                eventContainer.innerHTML = selectedEvents.map(event => {
                    // YYYY-MM-DD 형식으로 변환
                    const startDate = event.start.toISOString().split('T')[0]; // start 날짜 형식 변환
                    const endDate = event.end ? event.end.toISOString().split('T')[0] : ''; // end 날짜가 있으면 변환

                    return `
                        <div class="event-item">
                            <div class="d-flex align-items-end gap-2">
                                <span class="h6">${event.title}</span>
                                <span class="h7">${startDate}</span>
                                ${endDate ? `<span class="h7"> ~ ${endDate}</span>` : ''}
                                <span class="h7">${event.extendedProps.dtmHisId}(HIS_ID)</span>
                            </div>
                        </div>
                    `;
                }).join('');
            } else {
                eventContainer.innerHTML = '<span class="h7">해당일은 근태가 없습니다.</span>';
            }
        }

    });

    // 달력 렌더링
    calendar.render();

    // 터치 스와이프 이벤트 추가
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
        }
        touchStartX = null;
        touchStartY = null;
    });

});