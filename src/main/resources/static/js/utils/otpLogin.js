// 패스워드 인증
const passwordLogin = (e, encodedPassword) => {

    // CSRF 토큰 가져오기
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    e.preventDefault();
    const formData = new FormData(e.target);
    formData.set("password", encodedPassword); // 인코딩된 패스워드를 폼 데이터에 추가

    fetch('/login', {
        method: 'POST',
        headers: {
            'header': header,
            'X-CSRF-Token': token,
        },
        body: formData
    })
        .then(response => {
            // 서버에서 보내준 exception 처리 메시지 출력
            if (!response.ok) {
                return response.json().then(errorData => {
                    const errorMessage = errorData.error;
                    throw new Error(errorMessage);
                });
            }
            return response.json();
        })
        .then(data => {
            if (data.result) {
                // 패스워드 인증 성공 시 OTP 인증화면 호출
                otpLogin(encodedPassword, data.maskedPhoneNo);
            } else {
                // 패스워드 인증 실패 시 팝업
                popupMsg("인증 실패", data.message, "error");
            }
            hideSpinnerButton(); // 스피너 제거 및 버튼 복구
        })
        .catch(error => {
            // 패스워드 인증 실패 시 팝업
            popupReHtmlMsg("인증 오류", error.message, "error","/login");
            hideSpinnerButton(); // 스피너 제거 및 버튼 복구
        });
}

// OTP 인증 (카카오, SMS)
const otpLogin = (encodedPassword, maskedPhoneNo) => {
    const username = document.getElementById("username").value.toUpperCase();
    const password = encodedPassword;
    const MFA = $('input[name="MFA"]:checked').val();
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    Swal.fire({
        title: MFA+" 인증",
        html: `
        <p class="text-center">인증번호가 발송되었습니다.</p>
        <p class="text-center">${MFA} 인증번호 6자리 입력 후<br>제출을 눌러주세요(<b></b>초)</p>
        <p class="h7 mt-2 text-center"><i class="bi bi-phone-vibrate me-1"></i>${maskedPhoneNo}</p>`,
        input: "number",
        inputPlaceholder: "인증번호(6자리)",
        inputAttributes: {
            autocapitalize: "off",
            autofocus : "on",
            autocomplete: "one-time-code"
        },
        timer: 300000,
        timerProgressBar: true,
        didOpen: () => {
            const timer = Swal.getPopup().querySelector("b");
            timerInterval = setInterval(() => {timer.textContent = (`${Swal.getTimerLeft()}`/1000).toFixed();}, 10);},
        willClose: () => {
            clearInterval(timerInterval);
        },
        showCancelButton: true,
        confirmButtonText: "제출",
        cancelButtonText: "취소",
        showLoaderOnConfirm: true,
        preConfirm: async () => {},
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.isConfirmed) {
            const OTP = result.value
            authentication(username, password, MFA, OTP, header, token);
        }
    })
}

// 최종 인증
const authentication = (username, password, MFA, OTP, header, token) => {
    $.ajax({
        type: "POST",
        url: "/authenticate",
        dataType: 'json',
        data: {
            "username" : username,
            "password" : password,
            "MFA" : MFA,
            "OTP" : OTP
        },
        //CSRF Token
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        //success로 진입이 안되는 이슈로 complete 사용
        complete: function(data) {
            if(data.status === 200)
                window.location.href = '/index'
            else
                popupMsg("인증 오류", "인증을 다시 진행해주세요.", "error");
        }
    });
}