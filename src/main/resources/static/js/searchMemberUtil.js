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
            $("#memberList").html(data.responseText);
        },
        error: function(error) {
            popupMsg("입력 오류", 'Error fetching data : '+error, "error");
        }
    });
};

// 직원 검색 폼으로 스크롤 이동
const scrollToDiv = () => {
    const target = $('#searchDiv').get(0);
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
        autoFirst: false,
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

            return itemElement;
        },
        replace: function(text) {
            this.input.value = ''; // 입력폼을 비움
            selectedItem = text.value;  // 전체 객체를 전역변수 selectedItem에 저장
            searchMember(); // 검색 함수 호출
        },
        filter: function(text, input) {
            const terms = input.trim().toLowerCase().split(/\s+/);
            return terms.every(term => text.label.toLowerCase().includes(term));
        }
    });


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

