$(document).ready(function () {

    const calendarEl = document.getElementById('dtmCalendar');
    let selectedDate = null;
    let selectedDateStr = null;

    // div 태그에서 data-events 속성을 추출하여 JSON 데이터로 변환
    const eventsData = calendarEl.getAttribute('data-events');
    const eventsRaw = JSON.parse(eventsData);
    console.log(eventsRaw);

    // 근태종류(이벤트)별 CSS 스타일 적용
    const getEventClass = (dtmKindCd) => {
        switch(dtmKindCd.substring(0, 1)) {
            case '1': return 'event-kind-1'; // 연차 (파랑)
            case '2': return 'event-kind-2'; // 출장 (빨강)
            case '4': return 'event-kind-4'; // 연수 (초록)
            default: return 'event-default'; // 그외 (보라)
        }
    }
    const events = eventsRaw.map(function(event) {
        return {
            title: event.title,
            start: event.start,
            end: new Date(new Date(event.end).setHours(24, 0, 0, 0)),    // 24:00 시간으로 설정
            allDay: event.allDay,
            extendedProps: {
                dtmHisId: event.dtmHisId, // 커스텀 필드는 extendedProps로 추가
                dtmKindCd: event.dtmKindCd
            },
            classNames: getEventClass(event.dtmKindCd)
        };
    });



    const holidays = calendarEl.getAttribute('data-holiDayList'); // 추가할 휴일 날짜

    const calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'standard',
        initialView: 'dayGridMonth',
        editable: false,
        selectable: false,
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
        dayMaxEvents: 1, // 1일에 표시할 이벤트 수 제한 (2개 이상 시 more)
        moreLinkText: function(num) { // more 링크 텍스트 변경
            return `+${num}개`;  // 원하는 형식으로 변경
        },
        moreLinkClick: function(info) {
            return 'none'; // 기본 동작 방지
        },
        datesSet: function() {
            // 오늘 버튼 스타일 적용을 위한 클래스 추가
            const todayButton = document.querySelector('.fc-today-button');
            todayButton && (todayButton.className = 'btn btn-primary btn-sm fc-today-button');

            // 신청 버튼 스타일 적용을 위한 클래스 추가
            const applButton = document.querySelector('.fc-add-button');
            applButton && (applButton.className = 'btn btn-primary btn-sm fc-add-button');
            applButton && (applButton.style.visibility = 'hidden'); // 기본적으로 버튼을 숨김

            // 달력이 전환될 때 fadeIn
            const calendarContainer = document.querySelector('.fc-scrollgrid-sync-table');
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
        // 날짜를 클릭했을 때 이벤트
        dateClick: function(info) {
            handleEventOrDateClick(info.dateStr, calendar.getEvents(), info.dateStr, '날짜');
        },
        // 이벤트 클릭했을 때 이벤트
        eventClick: function(info) {
            handleEventOrDateClick(info.event.startStr, [info.event], info.event.startStr, '이벤트');
        },

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
            if (deltaX > 3) {
                calendar.next();
            } else if (deltaX < -3) {
                calendar.prev();
            }
        }
        touchStartX = null;
        touchStartY = null;
    });

    // 공통 함수로 클릭 처리
    const handleEventOrDateClick = (selectedDateStr, events, infoDateStr, type) => {
        console.log(`${type} 클릭됨: ${selectedDateStr}`);

        selectedDate && selectedDate.classList.remove('selected-date');
        const selectedDateEl = document.querySelector(`[data-date="${infoDateStr}"]`);
        selectedDateEl && selectedDateEl.classList.add('selected-date');
        selectedDate = selectedDateEl;

        // 신청 버튼 보이기
        const applButton = document.querySelector('.fc-add-button');
        applButton && (applButton.style.visibility = 'visible');

        const selectedEvents = events.filter(event => {
            const start = event.start;
            const end = event.end || start;
            const selectedDate = new Date(selectedDateStr);
            return start <= selectedDate && selectedDate <= end;
        });

        // 이벤트 출력 영역
        const eventContainer = document.getElementById('dtmEvent');
        if (selectedEvents.length > 0) { // 이벤트가 있는 경우
            eventContainer.innerHTML = selectedEvents.map(event => {
                const startDate = event.start.toLocaleDateString('ko-KR', {
                    year: 'numeric',
                    month: '2-digit',
                    day: '2-digit'
                }).replace(/\.\s?/g, '-').replace(/-$/, '');

                const endDate = event.end ? (() => {
                    const adjustedEndDate = new Date(event.end);
                    adjustedEndDate.setDate(adjustedEndDate.getDate() - 1);
                    const formattedEndDate = adjustedEndDate.toLocaleDateString('ko-KR', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit'
                    }).replace(/\.\s?/g, '-').replace(/-$/, '');
                    return startDate === formattedEndDate ? '' : formattedEndDate;
                })() : '';

                return `
                <div class="event-item">
                    <div class="d-flex align-items-center justify-content-start">
                        <div class="registDtmNameTag ms-2 ps-2 pe-0">
                            <h6 class="m-0">${event.title}</h6>
                            <h7>${startDate}</h7>
                            ${endDate ? `<span class="h7"> ~ ${endDate}</span>` : ''}
                        </div>
                    </div>
                </div>
            `;
            }).join('');
        } else {
            eventContainer.innerHTML = '<span class="h7">해당일은 근태가 없습니다.</span>';
        }
    }

    

});