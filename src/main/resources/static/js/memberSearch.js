// 직원 검색 조회 버튼 클릭 시 이벤트
const search = () => {
    const text = $("#searchMember").val();
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');
    console.log("searchMember :" + text);
    $.ajax({
        type: "post",
        url: "/search",
        dataType: "json",
        data: {
            "text": text
        },
        // CSRF Token
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        complete: function(data) {
            //console.log("search reply: " + data.responseText);
            $("#memberList").html(data.responseText);
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

    function typePlaceholder() {
        if (charIndex < placeholders[placeholderIndex].length) {
            inputElement.setAttribute("placeholder", placeholders[placeholderIndex].substring(0, charIndex + 1));
            charIndex++;
        } else {
            clearInterval(typingInterval);
            setTimeout(startTypingEffect, 2000); // 2초 동안 전체 텍스트를 유지한 후 다음 placeholder로 넘어감
        }
    }

    function startTypingEffect() {
        placeholderIndex = (placeholderIndex + 1) % placeholders.length;
        charIndex = 0;
        inputElement.setAttribute("placeholder", ""); // 타이핑 효과 전에 placeholder 초기화
        typingInterval = setInterval(typePlaceholder, 150); // 150ms 간격으로 타이핑 효과 적용
    }

    startTypingEffect(); // 초기 타이핑 효과 시작

    const awesomplete = new Awesomplete(inputElement, {
        minChars: 2,
        autoFirst: true,
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
            this.input.value = text.value;
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
                        <span class="searchItemHead">${item.userName} ${item.positionName}</span><br> 
                        <span class="searchItemBody">${item.deptName} ${item.teamName}</span><br>
                        <span class="searchItemFooter">${item.jobDetail}</span>
                    `;
                    list.push({ label: itemText, value: item.userName });
                });
            }

            awesomplete.list = list;
        },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    });
});
