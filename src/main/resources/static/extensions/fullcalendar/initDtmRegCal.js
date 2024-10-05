let calendar;

$(document).ready( () => {

    var calendarEl = document.getElementById('calender');
    var selectedDates = [];

    // 오늘 날짜를 가져오기
    const todayYmd = new Date().toISOString().slice(0, 10); // YYYY-MM-DD 형식

    // 휴일 날짜를 가져오기
    const holidays = calendarEl.getAttribute('data-holiDayList');

    // 근태(이벤트) 가져오기 후 JSON 변환
    const eventsData = calendarEl.getAttribute('data-dtmHisEvents');
    const eventsRaw = JSON.parse(eventsData);

    // 근태(이벤트) CSS 스타일 적용
    const getEventClass = (dtmKindCd) => {
        switch(dtmKindCd.substring(0, 1)) {
            case '1': return 'event-kind-1'; // 연차 (파랑)
            case '2': return 'event-kind-2'; // 출장 (빨강)
            case '4': return 'event-kind-4'; // 연수 (초록)
            default: return 'event-default'; // 그외 (보라)
        }
    }

    const splitEventByDay = (event) => {
        const events = [];
        const startDate = new Date(event.start);
        const endDate = new Date(event.end);

        for(let d = startDate; d<endDate; d.setDate(d.getDate() + 1)) {
            const eventClone = {
                title: event.title,
                start: new Date(d),
                allDay: event.allDay,
                extendedProps: {
                    dtmHisId: event.extendedProps.dtmHisId,
                    dtmKindCd: event.extendedProps.dtmKindCd
                },
                classNames: getEventClass(event.extendedProps.dtmKindCd)
            };
            events.push(eventClone);
        }
        console.log(events);
        return events;
    }

    // DB에서 가져온 근태(이벤트) 객체를 FullCalendar 객체에 할당
    const events = eventsRaw.flatMap( (event) => {
        const mappedEvents = {
            title: event.title,
            start: event.start,
            end: new Date(new Date(event.end).setHours(24, 0, 0, 0)), // 24:00 시간으로 설정
            allDay: event.allDay,
            extendedProps: { // 커스텀 필드는 extendedProps로 추가
                dtmHisId: event.dtmHisId,
                dtmKindCd: event.dtmKindCd
            },
            classNames: getEventClass(event.dtmKindCd)
        };
        return splitEventByDay(mappedEvents);
    });

    // 페이지 로드 시 날짜 입력 필드에 오늘 날짜 설정
    var today=new Date()
    updateDates(today, today);

    calendar = new FullCalendar.Calendar(calendarEl, {
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
        events: events,  // 이벤트 리스트 추가
        datesSet: function () {
            // 오늘 버튼 스타일 적용을 위한 클래스 추가
            const todayButton = document.querySelector('.fc-today-button');
            todayButton && (todayButton.className = 'btn btn-primary btn-sm fc-today-button');

            // 달력이 전환될 때 fadeIn
            const calendarContainer = document.querySelector('.fc-scrollgrid-sync-table');
            calendarContainer.classList.add('fc-fade');
            setTimeout(() => {
                calendarContainer.classList.remove('fc-fade'); // fadeIn 완료 후 클래스 제거
                let cell = document.querySelector(`[data-date='${today.toISOString().split('T')[0]}']`);
                if (cell) {
                    cell.classList.add('selected-single-day'); // 오늘 날짜에 클래스 추가
                }
            }, 500); // fadeIn 애니메이션 지속 시간
        },
        dayCellDidMount: function(info) { // 날짜 셀이 렌더링된 후 이벤트
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
            // 오늘 날짜가 달력에 표시될 때 자동으로 선택 스타일 추가
            if (info.dateStr === todayYmd) {
                info.el.classList.add('selected-date');
            }
        },
        dayCellContent: function (arg) {
            return {html: '<div class="fc-daygrid-day-number">' + arg.dayNumberText.replace('일', '') + '</div>'};
        },
        dateClick: function (info) {
            var selectedDate = new Date(info.dateStr); // 선택한 날짜
            // 과거일자 선택 시 경고표시 및 비활성화 -- 추후 과거일자 선택가능해지면 아래 if문(disablePastDates.js)만 제거하면 됨
            if (selectedDate < today.setHours(0, 0, 0, 0)) {
                disablePastDates();
                return; // 함수 종료
            }
            selectedDates.push(selectedDate);

            if (selectedDates.length === 2) { //날짜 2개 클릭한 경우
                setStartAndEnd(selectedDates[0], selectedDates[1]);
                selectedDates = []; // 다음 클릭을 위해 배열 초기화
            } else if (selectedDates.length === 1) { //날짜 1개만 클릭한 경우
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

    //날짜 2개 선택시 시작 종료일
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

    //캘린더 강조 표시 함수
    function addDateRangeHighlight(start, end) {
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


    //text로 직접 입력하는 경우
    document.getElementById('startDate').addEventListener('change', updateCalendar);
    document.getElementById('endDate').addEventListener('change', updateCalendar);

    function updateCalendar() {
        var startDate = document.getElementById('startDate').value;
        var endDate = document.getElementById('endDate').value;

        if (new Date(startDate) < today.setHours(0, 0, 0, 0) || new Date(endDate) < today.setHours(0, 0, 0, 0)) {
            //텍스트로 과거일자 선택하는 경우에도 못하게 제한
            disablePastDates();
            updateDates(today, today);
            addDateRangeHighlight(today, today);
            return; // 함수 종료
        }
        
        if (startDate && endDate) {
            var end = new Date(endDate);
            end.setDate(end.getDate());

            addDateRangeHighlight(new Date(startDate), end);
        }
    }

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
