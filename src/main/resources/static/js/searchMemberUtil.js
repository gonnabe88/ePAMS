
// awesomplete에서 선택된 항목 데이터를 저장할 전역 변수
let selectedItem = null; // 선택된 항목을 저장할 변수

// 직원 검색 조회 버튼 클릭 시 이벤트
const searchMember = () => {
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    if(!selectedItem) {
        popupMsg("입력 오류", "검색할 직원을 선택하여 입력해주세요.", "error");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/searchMember",
        data: {
            "text": selectedItem ? selectedItem.username : "" // 선택된 항목의 실제 값을 사용
        },
        // CSRF Token
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        complete: function(data) {
            // 서버로부터 받은 데이터를 memberList에 넣음
            $("#memberList").html(data.responseText);
            // AJAX 후에 이벤트 바인딩
            setupEventListeners();

            // 입력폼 값 및 X 버튼 삭제
            $('#searchMember').val('');
            $('#clearSearch').attr("hidden", true);

        },
        error: function(error) {
            popupMsg("입력 오류", 'Error fetching data : '+error, "error");
        }
    });
};

// 직원검색 결과 하단 버튼 이벤트 등록
const setupEventListeners = () => {
    $(document).ready(() => {
        // 다시검색 아이콘 클릭 시 searchMember 인풋에 포커스
        document.getElementById('search').addEventListener('click', function () {
            const searchInput = document.getElementById('searchMember');
            if (searchInput) {
                searchInput.focus();
            }
        });

        // 연락하기 아이콘 클릭 시 전화 걸기
        document.getElementById('contact').addEventListener('click', function () {
            const phoneLink = document.getElementById('inlineNumber');
            if (phoneLink && phoneLink.getAttribute('href') !== 'tel:') {
                // 타임리프에서 설정된 전화번호로 전화 걸기
                phoneLink.click();
            } else {
                popupMsg("입력 오류", "전화번호를 찾을 수 없습니다.", "error");
            }
        });
    });
}

// 직원 검색 폼으로 스크롤 이동
const scrollToDiv = () => {
    const target = $('#searchDiv').get(0);
    target.scrollIntoView({ behavior: 'smooth'});
};

// 직원 검색 폼으로 스크롤 이동
const scrollToSearchMemberCard = () => {
    const target = $('#searchMemberCard').get(0);
    target.scrollIntoView({ behavior: 'smooth'});
};

// 직원 검색 시 자동완성을 위한 데이터 가져오기
$(document).ready(() => {
    const inputElement = document.getElementById('searchMember');
    const placeholders = ["디지털금융 업무지원", "인사부 통할", "단신부임", "연수"];
    let placeholderIndex = 0;
    let charIndex = 0;
    let typingInterval;
    let isFocused = false; // input이 포커스되었는지 여부를 저장하는 변수
    let isTyping = false;  // 타이핑 애니메이션이 실행 중인지 여부를 저장하는 변수

    function typePlaceholder() {
        if (!isFocused && charIndex < placeholders[placeholderIndex].length) {
            inputElement.setAttribute("placeholder", placeholders[placeholderIndex].substring(0, charIndex + 1));
            charIndex++;
        } else {
            clearInterval(typingInterval);
            typingInterval = null; // 타이핑이 완료되면 interval 변수 초기화
            isTyping = false; // 타이핑 상태를 false로 설정
            if (!isFocused) { // focus 상태가 아닐 때만 타이핑 재개
                setTimeout(startTypingEffect, 2000); // 2초 동안 전체 텍스트를 유지한 후 다음 placeholder로 넘어감
            }
        }
    }

    function startTypingEffect() {
        if (!isFocused && !isTyping) { // isFocused가 false이고 isTyping이 false일 때만 시작
            isTyping = true; // 타이핑 상태를 true로 설정
            placeholderIndex = (placeholderIndex + 1) % placeholders.length;
            charIndex = 0;
            inputElement.setAttribute("placeholder", ""); // 타이핑 효과 전에 placeholder 초기화
            typingInterval = setInterval(typePlaceholder, 150); // 150ms 간격으로 타이핑 효과 적용
        }
    }

    startTypingEffect(); // 초기 타이핑 효과 시작

    // searchMember 입력 필드가 클릭되었을 때 placeholder 숨기고 애니메이션 중지
    inputElement.addEventListener('focus', () => {
        clearInterval(typingInterval);  // 애니메이션 중지
        typingInterval = null; // interval 변수 초기화
        inputElement.setAttribute("placeholder", "");
        isFocused = true;  // 포커스 상태를 true로 설정
        scrollToSearchMemberCard(); // 검색 폼으로 스크롤 이동
    });

    // searchMember 입력 필드가 포커스를 잃었을 때 애니메이션 재개
    inputElement.addEventListener('blur', () => {
        isFocused = false;  // 포커스 상태를 false로 설정
        if (!inputElement.value) {  // 필드가 비어 있을 때만 애니메이션 재개
            startTypingEffect();
        }
    });

    const awesomplete = new Awesomplete(inputElement, {
        minChars: 2,
        maxItems: 50, // 최대 50개까지 검색 결과 표시
        autoFirst: false, // 기본 지정 해제
        list: [],
        item: function(text, input) {
            let html = text.label;
            const terms = input.trim().toLowerCase().split(/\s+/);

            terms.forEach(term => {
                const regex = new RegExp(`(${term})`, 'gi');
                html = html.replace(regex, '<mark>$1</mark>');
            });

            const itemElement = document.createElement("li");
            itemElement.innerHTML = html;

            // 터치 이벤트 처리
            // let touchTimer;
            // let touchDuration = 500; // 터치를 길게 했을 때 인식하는 시간 (500ms)
            // let startX, startY;
            // itemElement.addEventListener('touchstart', function (event) {
            //     startX = event.touches[0].clientX;
            //     startY = event.touches[0].clientY;
            //     touchTimer = setTimeout(function() {
            //         event.preventDefault();
            //         event.stopPropagation();
            //         itemElement.click(); // 길게 터치했을 때 클릭 이벤트 발생
            //     }, touchDuration);
            // });
            // itemElement.addEventListener('touchmove', function (event) {
            //     let moveX = event.touches[0].clientX;
            //     let moveY = event.touches[0].clientY;
            //     if(Math.abs(moveX - startX) > 10 || Math.abs(moveY - startY) > 10) {
            //         clearTimeout(touchTimer);
            //     }
            // });
            // itemElement.addEventListener('touchend', function (event) {
            //     clearTimeout(touchTimer);
            // });
            // itemElement.addEventListener('touchcancel', function (event) {
            //     clearTimeout(touchTimer);
            // });

            return itemElement;
        },
        replace: function(text) {
            selectedItem = text.value;  // 전체 객체를 전역변수 selectedItem에 저장
            searchMember(); // 검색 함수 호출

            // 키패드를 숨김
            inputElement.blur();

            // id가 searchDiv인 요소를 화면 최상단에 위치시킴
            const searchDiv = document.getElementById('searchDiv');
            const offset = searchDiv.offsetTop;

            // 스크롤 애니메이션으로 searchDiv를 최상단에 위치시킴
            $('html, body').animate({
                scrollTop: offset
            }, 300); // 300ms의 애니메이션 지속 시간
        },
        filter: function(text, input) {
            const terms = input.trim().toLowerCase().split(/\s+/);
            return terms.every(term => text.label.toLowerCase().includes(term));
        }
    });

    // 검색 결과가 나오면 키패드를 숨기고 searchDiv(직원검색 입력 폼)를 최상단에 위치시킴
    inputElement.addEventListener('awesomplete-selectcomplete', function() {
        // 키패드를 숨김
        inputElement.blur();

        // id가 searchDiv인 요소를 화면 최상단에 위치시킴
        const searchDiv = document.getElementById('searchDiv');
        const offset = searchDiv.offsetTop;

        // 스크롤 애니메이션으로 searchDiv를 최상단에 위치시킴
        $('html, body').animate({
            scrollTop: offset
        }, 300); // 300ms의 애니메이션 지속 시간
    });

    // 드롭다운 내부 스크롤이 외부 스크롤로 전파되지 않도록 설정
    const dropdown = document.getElementById('awesomplete_list_2');

    // 드롭다운 내부 스크롤이 외부 스크롤로 전파되지 않도록 설정 (스크롤 이벤트)
    dropdown.addEventListener('scroll', function(event) {
        const scrollTop = this.scrollTop;
        const scrollHeight = this.scrollHeight;
        const clientHeight = this.clientHeight;

        //값 확인을 위한 로그 출력 (현재 시간 포함)
        //const currentTime = new Date().toLocaleTimeString();
        //console.log(`[${currentTime}] Scroll Event - scrollTop: ${scrollTop}, clientHeight: ${clientHeight}, scrollHeight: ${scrollHeight}`);

        // 상단에 도달한 경우
        if (scrollTop <= 1) {
            this.scrollTop += 1; // 상단에서 더 이상 스크롤되지 않도록 조정
            event.preventDefault(); // 외부로 스크롤 전파를 방지
        }

        // 하단에 도달한 경우
        if (scrollTop + clientHeight >= scrollHeight - 1) { // 오차를 약간 둠
            this.scrollTop -= 1; // 하단에서 더 이상 스크롤되지 않도록 조정
            event.preventDefault(); // 외부로 스크롤 전파를 방지
            //console.log("preventDefault#2");
        }

        // 스크롤이 내부에서만 발생하도록 설정
        event.stopPropagation();
    });

    // 터치 이벤트에서 스크롤이 외부로 전파되지 않도록 설정
    dropdown.addEventListener('touchmove', function(event) {
        event.stopPropagation();
    }, { passive: true });
    dropdown.addEventListener('touchstart', function(event) {
        event.stopPropagation();
    }, { passive: true });
    dropdown.addEventListener('touchend', function(event) {
        event.stopPropagation();
    }, { passive: true });

    $.ajax({
        url: "api/index/getDeptList",
        method: 'GET',
        dataType: "json",
        success: function(data) {
            const list = [];

            if (data.teamList) {
                data.teamList.forEach(item => {
                    const itemText = `
                        <span class="searchItemHead">${item.realname} ${item.positionName}</span><br> 
                        <span class="searchItemBody">${item.deptName} ${item.teamName}</span><br>
                        <span class="searchItemFooter">${item.jobDetail}</span>
                    `;
                    list.push({
                        label: itemText,
                        value: item
                    });
                });
            }
            awesomplete.list = list;
        },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    });
});

