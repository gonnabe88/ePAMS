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

    $('#searchMember').select2({
        placeholder: 'Select an option',
        minimumInputLength: 2,
        allowClear: true
    });

    $.ajax({
        url: "api/index/getDeptList",
        method: 'GET',
        dataType: "json",
        success: function(data) {
            const $select = $('#searchMember');
            $select.empty(); // 기존 옵션 삭제

            // 부서 목록 그룹 생성
            const deptGroupLabel = "부서/지점명";
            const $deptOptgroup = $('<optgroup/>', { label: deptGroupLabel });

            // 팀 목록 그룹 생성
            const teamGroupLabel = "팀명";
            const $teamOptgroup = $('<optgroup/>', { label: teamGroupLabel });

            // 부서 목록 추가
            if (data.deptList) {
                data.deptList.forEach(item => {
                    $deptOptgroup.append(new Option(item.deptName, item.deptCode)); // 새로운 옵션 추가
                });
            }

            // 팀 목록 추가
            if (data.teamList) {
                data.teamList.forEach(item => {
                    $teamOptgroup.append(new Option(item.teamName, item.teamCode)); // 새로운 옵션 추가
                });
            }

            // Select 요소에 그룹 추가
            $select.append($deptOptgroup);
            $select.append($teamOptgroup);

            $select.trigger('change'); // 옵션 새로고침
        },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    });


    // 직원 검색 조회 버튼 클릭 이벤트 핸들러 추가
    $('#button-addon2').on('click', search);
});
