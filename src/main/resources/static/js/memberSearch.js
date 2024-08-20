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
    const input = $("#searchMember").get(0);
    const awesomplete = new Awesomplete(input, {
        minChars: 1,
        //maxItems: 5,
        autoFirst: true,
        tabSelect: true,
    });
    $.ajax({
        type: "get",
        url: "api/deptlist",
        dataType: "json",
        success: function(data) {
            // Assuming data is an object with a 'realname' property
            awesomplete.list = data.response.map(item => item.realname);
        },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    });

    // 직원 검색 조회 버튼 클릭 이벤트 핸들러 추가
    $('#button-addon2').on('click', search);
});
