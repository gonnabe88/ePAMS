// 화면 전환 시 수행
$(document).on("visibilitychange", function() {
    if(document.visibilityState === "visible") {
        checkSessionValid(); // 세션 유효성 체크
    }
});

const usernameField = document.getElementById('username');
let isComposing = false;

// IME 입력 시작 시 설정
usernameField.addEventListener('compositionstart', function() {
    isComposing = true;
});

// IME 입력 완료 시 설정
usernameField.addEventListener('compositionend', function(event) {
    isComposing = false;
    filterUsernameInput(event);  // IME 입력이 끝난 후 필터링 수행
});

// 일반 입력 시 필터링
usernameField.addEventListener('input', function(event) {
    if (!isComposing) {  // IME 입력 중이 아닐 때만 필터링 수행
        filterUsernameInput(event);
    }
});

// ID 입력 값 필터링
function filterUsernameInput(event) {
    const inputField = event.target;
    const originalValue = inputField.value;
    
    // 한글 및 공백만 제거
    inputField.value = originalValue.replace(/[\u3131-\u318E\uAC00-\uD7A3\s]/g, '');

    // 입력값이 변경되었으면 (허용되지 않는 값이 있었음을 의미) 토스트 표시
    if (inputField.value !== originalValue) {
        loadToastHTML("공백과 한글은 입력할 수 없습니다.");
    }
}

// 패스워드 값 필터링
document.getElementById('password').addEventListener('input', function(event) {
    const inputField = event.target;
    const originalValue = inputField.value;

    // 한글 및 공백을 제외한 값으로 필터링
    event.target.value = event.target.value.replace(/[\u3131-\u318E\uAC00-\uD7A3\s]/g, '')

    // 입력값이 변경되었으면 (허용되지 않는 값이 있었음을 의미) 토스트 표시
    if (inputField.value !== originalValue) {
        loadToastHTML("공백은 입력할 수 없습니다.");
    }
});

// 화면 로드 시 수행
$(function() {

    checkBrowser();

    const webauthnData = $('#webauthnData');
    const webauthnYn = webauthnData.data('webauthn'); // 간편인증 사용 여부

    const loginLockData = $('#loginLockData');
    const loginLockYn = loginLockData.data('loginlock'); // 계정 잠김 여부

    const setWebauthnRadio = () => { // Webauthn 등록 사용자 인증방식 자동 체크
        if (loginLockYn) {
            $('#passwordDiv').addClass('hidden'); // 패스워드 입력창 hidden
        } else if (webauthnYn === 'Y') { // Webauthn 등록 사용자인 경우
            $('#webauthn').prop('checked', true); // Webauthn 인증방식 자동 체크
            $('#passwordDiv').addClass('hidden'); // 패스워드 입력창 hidden
            setCookie("MFA", 'webauthn', 30); // 쿠키 갱신
        } 
    }

    const setPasswordInput = () => {// 패스워드 입력창을 숨기거나 보여주는 함수
        let MFA = $('input[name="MFA"]:checked').val(); // MFA 라디오 버튼 값 확인
        MFA === 'webauthn' || loginLockYn ? $('#passwordDiv').addClass('hidden') : $('#passwordDiv').removeClass('hidden'); // 계정잠김 또는 webauthn이면 패스워드 입력창 hidden
    }

    $('input[name="MFA"]').change(function() { // MFA 라디오 버튼 변경 시
        let MFA = $('input[name="MFA"]:checked').val(); // MFA 라디오 버튼 값 확인
        const username = $('#username').val();

        setCookie("MFA", MFA, 30); // 쿠키 갱신
        setPasswordInput(); // 패스워드 입력창을 숨기거나 보여줌

        webauthnYn === 'Y' && MFA !== 'webauthn' ? loadToastHTML("<p>간편인증이 등록된 사용자는 자동으로 간편인증이 선택됩니다.</p> <p>간편인증 사용을 원하지 않으시면 로그인 후 간편인증 등록을 해제해주세요.</p>") : null; // 간편인증 사용자가 OTP 인증을 고르는 경우
        if(MFA === 'webauthn') { // 간편인증을 선택한 경우
            $.get(`/api/login/isWebauthn?username=${username}`, function(data) { // 서버에서 간편인증 사용자인지 체크
                if(data.isWebauthn === 'N') { // 간편인증 미등록 사용자인 경우
                    $('input[name="MFA"]').prop('checked', false);
                    $('#webauthn').blur(); // focus 해제 (해제 안하면 잔상 남음)
                    rememberAuthType(data.isWebauthn);
                    setPasswordInput(); // 인증방식에 따른 패스워드 입력창 세팅
                    popupHtmlMsg("미등록 사용자", 
                        "<p class='text-left'>간편인증을 등록하지 않은 계정입니다. <span class='h6'>카카오톡</span> 또는 <span class='h6'>SMS</span> 로그인 후 간편인증을 등록해주세요.</p>" +
                        "<p class='text-left h6'>로그인 계정 : " + username + "</p>",
                        "info");
                }                    
            }).fail(function() {
                console.log('webauth 사용자 확인 중 에러 발생');
            });

        }
    });

    $('#loginFaqLink').on('click', function(event) {
        event.preventDefault();
        $.get('/login/faq', function(html) {
            $('#loginFaq .modal-body').html(sanitizeHTML(html));
            $('#loginFaq').modal('show');
        }).fail(function() {
            console.log('FAQ 호출 중 에러 발생')
        });
    });

    $('#simpleAuthQ').on('click', function() { // 간편인증 등록 사용자 안내사항 클릭 시
        popupHtmlMsg(
            "간편인증",
            "<p class='text-left'>간편인증은 휴대기기의 잠금화면 해제 인증(지문, 안면, 패턴 등)으로 로그인하는 방식입니다.</p> " +
            "<p class='text-left'>간편인증 사용을 위해서는 최초 1회 패스워드 + SMS/카카오 로그인 후 간편인증 등록이 필요합니다.</p>" +
            "<p class='h6'>(간편인증은 로그인 시 패스워드 미사용)</p>",
            "question");
    });

    $('#loginForm').on('submit', function(e) { // 로그인 버튼 클릭 시
        console.log("클릭");
        e.preventDefault(); // Prevent form's default submission
        let MFA = $('input[name="MFA"]:checked').val(); // 선택된 MFA 값 가져오기

        if (!loginLockYn) {
            const password = $('#password').val(); // jQuery의 .val()을 사용해 값 가져오기
            const encodedPassword = btoa(encodeURIComponent(password)); // Base64 인코딩           

            showSpinnerButton(); // 로그인 버튼 스피너 설정

            switch (MFA) {
                case 'webauthn':
                    webauthnLogin(e); // 간편인증
                    break;
                case 'SMS':
                    passwordLogin(e, encodedPassword); // OTP 인증 (SMS)
                    break;
                case '카카오톡':
                    passwordLogin(e, encodedPassword); // OTP 인증 (카카오톡)
                    break;
            }
        } else {
            switch (MFA) {
                case 'webauthn':
                    webauthnLogin(e); // 간편인증
                    break;
                case 'SMS':
                    releaseLock(e); // OTP 인증 (SMS)
                    break;
                case '카카오톡':
                    releaseLock(e); // OTP 인증 (카카오톡)
                    break;
            }
        }
    });

    rememberAuthType(webauthnYn); // 인증방식 기억하기 세팅
    rememberId(); // ID 기억하기 세팅
    setPasswordInput(); // 인증방식에 따른 패스워드 입력창 세팅
    setWebauthnRadio(); // Webauthn 인증 등록 사용자 Webauthn 라디오 버튼 자동 세팅
});

const loadToastHTML = (message) => {
    // 기존 토스트가 있다면 삭제
    const existingToast = document.querySelector('.toast-container');
    if (existingToast) {
        existingToast.parentNode.removeChild(existingToast);
    }

    // 토스트 HTML을 변수로 정의, 메시지를 동적으로 삽입
    const toastHtml = `
        <div aria-live="polite" aria-atomic="true" class="toast-container p-3" style="position: fixed; bottom: 1rem; right: 1rem; z-index: 1050;">
            <div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header d-flex align-items-center gap-2">
                    <img src="/images/kdb/epams_r_192.png" class="rounded me-2" width="20" height="20" alt="logo">
                    <span class="h6 m-0">ePAMS 안내사항</span>
                    <small>방금</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    ${message}
                </div>
            </div>
        </div>
    `;

    // 새 토스트 HTML을 body에 추가
    document.body.insertAdjacentHTML('beforeend', toastHtml);

    // 추가된 토스트 요소를 가져와 부트스트랩 토스트로 초기화 및 표시
    const toastElement = document.querySelector('.toast:last-child');
    const toast = new bootstrap.Toast(toastElement, { delay: 0 , autohide: false });
    toast.show();

    // 토스트가 숨겨지면 자동으로 제거
    toastElement.addEventListener('hidden.bs.toast', () => {
        if (toastElement.parentNode) {  // parentNode가 존재할 때만 제거
            toastElement.parentNode.removeChild(toastElement);
        }
    });
};
