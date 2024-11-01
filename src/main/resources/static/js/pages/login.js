// 화면 전환 시 수행
$(document).on("visibilitychange", function() {
    if(document.visibilityState === "visible") {
        checkSessionValid(); // 세션 유효성 체크
    }
});

// ID 입력 시 수행
document.getElementById('username').addEventListener('input', function(event) {
    event.target.value = event.target.value.toUpperCase(); // 대문자 변환
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
        webauthnYn === 'Y' && MFA !== 'webauthn' ? loadToastHTML() : null; // 간편인증 사용자가 OTP 인증을 고르는 경우
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

const loadToastHTML = () => {
    // Toast 엘리먼트를 가져옴
    const toastEl = document.querySelector('.toast');
    if (toastEl) {
        // Bootstrap Toast 초기화
        const toast = new bootstrap.Toast(toastEl, {
            delay: 5000 // 5초 후 자동으로 사라지게 설정
        });
        // Toast 보여주기
        toast.show();
    }
}
