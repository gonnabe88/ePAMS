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
    // Awesomplete 초기화
    const inputElement = document.getElementById('searchMember');
    const awesomplete = new Awesomplete(inputElement, {
        minChars: 2,
        autoFirst: true,
        list: [], // 초기 리스트 비워두기
        //maxItems: 30, // 최대 표시할 항목 수를 설정하여 스크롤을 유도
        item: function(text, input) {
            // 검색어에 일치하는 부분을 하이라이트하기 위해 item 함수 수정
            const html = text.label.replace(
                new RegExp(input.trim(), "gi"),
                (match) => `<mark>${match}</mark>`
            );

            const itemElement = document.createElement("li");
            itemElement.innerHTML = html; // HTML 포함
            return itemElement;
        },
        replace: function(text) {
            // 선택된 항목을 input에 입력하는 방식 수정
            this.input.value = text.value;
        }
    });

    $.ajax({
        url: "api/index/getDeptList",
        method: 'GET',
        dataType: "json",
        success: function(data) {
            // 자동 완성 리스트를 비워두기
            const list = [];

            // 팀 목록 추가
            if (data.teamList) {
                data.teamList.forEach(item => {
                    // 팀명, 사용자명 등을 포함한 데이터 추가
                    const itemText = `
                        <span class="searchItemHead">${item.userName} ${item.positionName}(${item.inlineNumber})</span><br> 
                        <span class="searchItemBody">${item.deptName} ${item.teamName}</span><br>
                        <span class="searchItemFooter">${item.jobDetail}</span>
                    `;
                    list.push({ label: itemText, value: item.userName });
                });
            }

            // Awesomplete 리스트 업데이트
            awesomplete.list = list;
        },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    });
});
