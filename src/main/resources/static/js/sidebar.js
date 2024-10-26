// (페이지 로딩 시) 관리자 페이지와 일반 사용자 페이지의 sidebar를 동적으로 변경하는 스크립트
$(document).ready(function() {

    // 사용자 정보
    getUserInfo(function(ret){
        const isAdmin = ret;
        const isChecked = getCookie("adminView");

        // flexSwitchCheckAdmin의 체크 상태 설정
        if(isAdmin) {
            if(isChecked.match("true")) {
                $('#flexSwitchCheckAdmin').prop('checked', true);
                url = '/common/layout/renderSidebarAdmin';
            } else {
                url = '/common/layout/renderSidebarNormal';
            }
        } else {
            console.log("normal : " + isAdmin + " " + isChecked);
            url = '/common/layout/renderSidebarNormal';
        }

        // 서버에 요청을 보내어 사용자 또는 관리자 sidebar 템플릿을 받아옴
        $.get(url, function(html) {
            // 2024-08-17 CWE-79(Cross-site Scripting (XSS)) 취약점 조치
            const safeHTML = DOMPurify.sanitize(html, {
                SAFE_FOR_TEMPLATES: true,
                ALLOWED_TAGS: ['h7', 'h6', 'h5', 'h4', 'h3', 'h2', 'h1', 'div', 'span', 'section', 'i', 'ul', 'li', 'a'], // 필요시 추가
                FORBID_ATTR: ['style']
            });
            $('#menu').html(safeHTML);
            highlightActiveMenuItem(); // 사이드바가 로드된 후 .sidebar-item 요소 탐색
        }).fail(function(error) {
            console.error('Error loading sidebar:', error);
        });
    });

    // 현재 페이지 경로와 일치하는 사이드바 항목에 'active' 클래스를 추가하는 함수
    function highlightActiveMenuItem() {
        const currentPath = window.location.pathname; // 현재 페이지의 경로를 가져옴
        const currentHash = window.location.hash; // 현재 URL의 해시(#searchDiv 등) 가져오기

        // 모든 .submenu-item 요소를 순회하며 현재 페이지와 일치하는 항목에 'active' 클래스 추가
        $(".submenu-item").each((_, submenuItem) => {
            const submenuLink = $(submenuItem).find("a");
            const submenuLinkPath = submenuLink.attr("href");

            // 현재 URL 경로와 서브메뉴의 링크가 일치하는 경우
            if (submenuLinkPath === currentPath) {
                $(submenuItem).addClass("active"); // 일치하는 submenu-item에 'active' 클래스 추가

                // 상위의 sidebar-item에도 'active' 클래스 추가
                $(submenuItem).closest(".sidebar-item").addClass("active");
            }
        });

        // 모든 .sidebar-item 요소를 순회하며 현재 페이지와 일치하는 항목에 'active' 클래스 추가
        $(".sidebar-item").each((_, item) => {
            const link = $(item).find(".sidebar-link");
            const fullLinkPath = link.attr("href");

            // 링크 경로에서 해시를 분리
            const linkUrl = fullLinkPath.split('#')[0];  // 경로만 추출
            const linkHash = fullLinkPath.includes('#') ? '#' + fullLinkPath.split('#')[1] : ''; // #을 포함한 해시 추출

            // 직원검색 처리: 경로는 동일하고 해시(#searchDiv)까지 일치할 경우 'active' 클래스 추가
            if (linkUrl === currentPath && linkHash === currentHash) {
                // 직원검색에만 'active' 추가
                $(item).addClass("active");
            } else if (linkUrl === currentPath && linkHash === undefined) {
                // 직원검색에만 'active' 추가
                $(item).addClass("active");
            }
        });
    }
});

// (Switch 변경 시) 관리자 페이지와 일반 사용자 페이지의 sidebar를 동적으로 변경하는 스크립트
$('#flexSwitchCheckAdmin').on('change', function () {
    let isChecked = $(this).prop('checked');
    let url = isChecked ? '/common/layout/renderSidebarAdmin' : '/common/layout/renderSidebarNormal';
    setCookie("adminView", isChecked, 30);

    // 서버에 요청을 보내어 해당 템플릿을 받아옴
    $.get(url, function(html) {
        // 2024-08-17 CWE-79(Cross-site Scripting (XSS)) 취약점 조치
        const safeHTML = DOMPurify.sanitize(html, {
            SAFE_FOR_TEMPLATES: true,
            ALLOWED_TAGS: ['h7', 'h6', 'h5', 'h4', 'h3', 'h2', 'h1', 'div', 'span', 'section', 'i', 'ul', 'li', 'a'], // 필요시 추가
            FORBID_ATTR: ['style']
        });
        $('#menu').html(safeHTML);
    }).fail(function(error) {
        console.error('Error loading sidebar:', error);
    });
});

// Ajax 요청을 사용하여 사용자 데이터를 가져오는 함수 정의
const getUserInfo = (callback) => {
    $.ajax({
        url: '/api/sidebar/getUserInfo', // 요청할 URL
        method: 'GET', // HTTP 메서드
        dataType: 'json', // 서버에서 반환될 데이터 형식
        success: function(data) { // 요청이 성공했을 때 실행되는 함수
            const userInfo = data.userInfo;
            // JSON 응답에서 사용자 이름과 부서를 가져와서 HTML에 삽입
            $('#realname').text(userInfo.realname);
            $('#deptName').text(userInfo.deptName);
            if(userInfo.staTime === "휴무") {
                $('#todayWorkTime').text(`${userInfo.staTime}`);
            } else {
                $('#todayWorkTime').html(`${userInfo.staTime}<br>${userInfo.endTime}`);
            }
            if(userInfo.staTime2 === "휴무") {
                $('#tomorrowWorkTime').text(`${userInfo.staTime2}`);
            } else {
                $('#tomorrowWorkTime').html(`${userInfo.staTime2}<br>${userInfo.endTime2}`);
            }
            callback(userInfo.isAdmin);
        },
        error: function(jqXHR, textStatus, errorThrown) { // 요청이 실패했을 때 실행되는 함수
            console.error('Error fetching user info:', textStatus, errorThrown);
        }
    });
};

// Sidebar 기본 동작을 위한 상수 설정
const DESKTOP_SIZE = 1200; // 데스크톱 화면 크기 기준 상수

// Sidebar 클래스를 정의하여 사이드바 동작을 제어합니다
class Sidebar {
    constructor(element, options = {}) {
        // 사이드바 요소를 설정합니다
        this.sidebarElement = element instanceof HTMLElement ? element : document.querySelector(element);
        this.options = options; // 사용자 옵션을 설정합니다
        this.init(); // 초기화 메서드를 호출합니다
    }

    init() {
        // 드래그(스와이프) 이벤트를 위한 변수 초기화
        let startX = 0; // 드래그 시작 위치
        let endX = 0; // 드래그 종료 위치
        const noDragElement = document.querySelector('.no-drag');

        // 모바일 터치 시작 이벤트 (스와이프)
        $(document).on("touchstart", (event) => {
            if (noDragElement && noDragElement.contains(event.target)) return;
            startX = event.touches[0].pageX; // 터치가 시작되는 위치 저장
            endX = 0; // 터치 시작 시 endX 초기화
        });

        // 모바일 터치 종료 이벤트 (스와이프)
        $(document).on("touchend", (event) => {
            if (noDragElement && noDragElement.contains(event.target)) return;
            endX = event.changedTouches[0].pageX; // 터치가 끝나는 위치 저장
            if ((window.innerWidth < DESKTOP_SIZE) && (startX + 100 < endX)) {
                this.show(); // 오른쪽으로 스와이프된 경우
            } else if ((window.innerWidth < DESKTOP_SIZE) && (startX > endX + 100)) {
                this.hide(); // 왼쪽으로 스와이프된 경우
            }
        });

        // 햄버거 버튼 및 사이드바 숨김 버튼 클릭 이벤트 등록
        $(".burger-btn, .sidebar-hide").on("click", this.toggle.bind(this));


        // 창 크기 변경 이벤트 등록
        $(window).on("resize", this.onResize.bind(this));

        // 서브메뉴를 토글하는 함수 정의
        const toggleSubmenu = submenu => {
            if ($(submenu).hasClass("submenu-open")) {
                $(submenu).removeClass("submenu-open").addClass("submenu-closed");
            } else {
                $(submenu).removeClass("submenu-closed").addClass("submenu-open");
            }
        };

        // 서브메뉴가 있는 사이드바 항목을 클릭했을 때 이벤트 설정
        $(document).on("click", ".sidebar-item.has-sub .sidebar-link", function(event) {
            if (event.cancelable) event.preventDefault(); // 기본 동작 방지
            const submenu = $(this).closest(".sidebar-item").find(".submenu");
            toggleSubmenu(submenu); // 서브메뉴 토글
        });

        // 서브 서브메뉴가 있는 항목을 클릭했을 때 이벤트 설정
        $(document).on("click", ".submenu-item.has-sub", function() {
            const submenuItem = $(this);
            const parentSubmenu = submenuItem.parent();  // 현재 서브메뉴의 부모
            toggleSubmenu(submenuItem.find(".submenu")); // 서브 서브메뉴 토글
            ensureSubmenuVisibility(parentSubmenu); // 서브메뉴 가시성 보장
        });

        // PerfectScrollbar 플러그인 사용 설정
        if (typeof PerfectScrollbar === "function") {
            const sidebarWrapper = document.querySelector(".sidebar-wrapper");
            new PerfectScrollbar(sidebarWrapper, {
                wheelPropagation: true
            });
        }

        // 일정 시간 후에 활성화된 사이드바 항목을 보이도록 설정
        setTimeout(() => {
            const activeItem = document.querySelector(".sidebar-item.active");
            activeItem && this.forceElementVisibility(activeItem);
        }, 300);

        // 옵션에 따라 사이드바 높이를 재계산합니다
        if (this.options.recalculateHeight) {
            recalculateSidebarHeight(this.sidebarElement);
        }

        // 직원검색 클릭 시 사이드바를 닫고 이동
        $(document).on('click', '#memberSearch', (e) => { 
            e.preventDefault();
            e.stopPropagation();
            if(window.innerWidth < DESKTOP_SIZE) {
                this.hide();
            }
            window.location.href = $('#memberSearch').attr('href');
        });
    }

    // 창 크기 변경 시 처리할 동작 정의
    onResize() {
        this.removeBackdrop(); // 백드롭을 제거합니다
        this.toggleBodyOverflow(true); // 본문 스크롤 설정
    }

    // 사이드바 토글 함수 정의
    toggle() {
        this.sidebarElement.classList.contains("active") ? this.hide() : this.show();
    }

    // 사이드바 표시 함수 정의
    show() {
        this.sidebarElement.classList.add("active");
        this.sidebarElement.classList.remove("inactive");
        this.createBackdrop(); // 백드롭을 생성합니다
        this.toggleBodyOverflow(); // 본문 스크롤 설정
    }

    // 사이드바 숨김 함수 정의
    hide() {
        this.sidebarElement.classList.remove("active");
        this.sidebarElement.classList.add("inactive");
        this.removeBackdrop(); // 백드롭을 제거합니다
        this.toggleBodyOverflow(); // 본문 스크롤 설정
    }

    // 백드롭을 생성하는 함수 정의
    createBackdrop() {
        if (window.innerWidth > DESKTOP_SIZE) return; // 데스크톱 크기 이상일 때 백드롭을 생성하지 않음
        this.removeBackdrop();
        const backdrop = document.createElement("div");
        backdrop.classList.add("sidebar-backdrop");
        backdrop.addEventListener("click", this.hide.bind(this));
        document.body.appendChild(backdrop); // 백드롭을 문서에 추가
    }

    // 백드롭을 제거하는 함수 정의
    removeBackdrop() {
        const backdrop = document.querySelector(".sidebar-backdrop");
        if (backdrop) backdrop.remove(); // 백드롭 요소가 존재할 경우 제거
    }

    // 본문 스크롤 설정을 토글하는 함수 정의
    toggleBodyOverflow(forceAuto) {
        if (window.innerWidth > DESKTOP_SIZE) return; // 데스크톱 크기 이상일 때 설정하지 않음
        const isActive = this.sidebarElement.classList.contains("active");
        document.documentElement.style.overflowY = forceAuto ? "auto" : (isActive ? "hidden" : "auto"); // 스크롤 설정
    }

    // 요소가 화면에 있는지 확인하는 함수 정의
    isElementInViewport(element) {
        const rect = element.getBoundingClientRect();
        return rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth);
    }

    // 요소를 강제로 화면에 보이게 하는 함수 정의
    forceElementVisibility(element) {
        if (!this.isElementInViewport(element)) {
            element.scrollIntoView(false); // 요소를 화면에 보이도록 스크롤
        }
    }

    // 서브메뉴의 가시성을 보장하는 함수 정의 (필요시)
    ensureSubmenuVisibility(element) {
        // 서브메뉴 가시성을 위한 플레이스홀더 함수
    }
}

// 사이드바 요소를 선택
const sidebarElement = document.getElementById("sidebar");

// 사이드바를 초기화하는 함수 정의
const initializeSidebar = (element) => {
    if (!element) return; // 요소가 존재하지 않으면 종료

    if (window.innerWidth > DESKTOP_SIZE) {
        // 데스크톱에서도 기본적으로 사이드바를 보이게 처리
        element.classList.add("sidebar-desktop"); // 데스크톱 모드 설정
        // 사이드바를 항상 보이도록 설정
        element.classList.add("active");
    }
    // 추가적인 초기화 코드 필요시 추가
};

// 사이드바 높이를 재계산하는 함수 정의
const recalculateSidebarHeight = (element) => {
    if (!element) return; // 요소가 존재하지 않으면 종료

    // 사이드바 높이를 재계산 및 조정
};

// 문서가 로딩된 후 사이드바를 초기화
document.readyState !== "loading" ? initializeSidebar(sidebarElement) : $(document).ready(() => initializeSidebar(sidebarElement));

// Sidebar 클래스를 전역으로 등록하고 인스턴스 생성
window.Sidebar = Sidebar;
sidebarElement && new window.Sidebar(sidebarElement);