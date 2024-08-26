document.getElementById('username').addEventListener('input', function(event) {
    event.target.value = event.target.value.toUpperCase();
});

document.addEventListener("DOMContentLoaded", () => {
    const checkbox = document.getElementById('flexSwitchCheckChecked');
    const label = document.getElementById('switchLabel');
    const passwordInput = document.getElementById('password');
    const smsInput = document.getElementById('sms');
    const kakaoInput = document.getElementById('kakao');
    const otpInput = document.getElementById('otp');
    const fidoInput = document.getElementById('fido');
    const form = document.querySelector('form'); // Assuming the form element
	const twostepDiv = document.getElementById('twostepDiv');
	const twostepDiv2 = document.getElementById('twostepDiv2');
	const twostepDiv3 = document.getElementById('twostepDiv3');

    const updateLabel = () => {
        label.textContent = checkbox.checked ? '간편인증' : '2단계인증';
    };

    const toggleChange = () => {
        const isChecked = checkbox.checked;
        passwordInput.disabled = isChecked;
        smsInput.disabled = isChecked;
        kakaoInput.disabled = isChecked;
        otpInput.disabled = isChecked;
        fidoInput.disabled = isChecked;
    };
    
     const togglePasswordVisibility = () => {
        if (checkbox.checked) {
            twostepDiv.style.display = 'none';
            twostepDiv2.style.display = 'none';
            twostepDiv3.style.display = 'block';
        } else {
            twostepDiv.style.display = 'block';
            twostepDiv2.style.display = 'block';
            twostepDiv3.style.display = 'none';
        }
    }

    form.addEventListener("submit", (e) => {
        e.preventDefault(); // Prevent form's default submission
        const password = passwordInput.value;
        const encodedPassword = btoa(encodeURIComponent(password));
        passwordInput.value = encodedPassword;

        if (checkbox.checked) {
            webauthn(e); // Call webauthn function
        } else {
            normal(e); // Call normal function
        }
    });


    checkbox.addEventListener('change', () => {
        updateLabel();
        toggleChange();
        togglePasswordVisibility();
    });

	// Initialize label
    updateLabel();
    toggleChange();
    togglePasswordVisibility();
});


// 간편인증 WebAuthn 인증 로직

const showSpinnerButton = () => {
    const loginBtn = document.getElementById('login');
    loginBtn.innerHTML = `
            <span class="spinner-border spinner-border-sm" aria-hidden="true"></span>
            <span role="status">Loading...</span>
        `;
    loginBtn.setAttribute('disabled', 'true');
}

const webauthn = (e) => {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    let token = document.querySelector('meta[name="_csrf"]').content;
    e.preventDefault();
    const formData = new FormData(e.target);

    showSpinnerButton();

    console.time("total");
    console.time("fetch /api/webauthn/login");
    fetch('/api/webauthn/login', {
        method: 'POST',
        headers: {
            'X-CSRF-Token' : token
        },
        body: formData
    })
    .then(response => {
        console.timeEnd("fetch /api/webauthn/login");
        return initialCheckStatus(response);
    })
    .then(credentialGetJson => {
        console.time("parse credentialGetJson");
        const result = {
            publicKey: {
                ...credentialGetJson.publicKey,
                allowCredentials: credentialGetJson.publicKey.allowCredentials
                    && credentialGetJson.publicKey.allowCredentials.map(credential => ({
                    ...credential,
                    id: base64urlToUint8array(credential.id),
                    })),
                challenge: base64urlToUint8array(credentialGetJson.publicKey.challenge),
                extensions: credentialGetJson.publicKey.extensions,
            },
        };
        console.timeEnd("parse credentialGetJson");
        return result;
    })
    .then(credentialGetOptions => {
        console.time("navigator.credentials.get");
        return navigator.credentials.get(credentialGetOptions);
    })
    .then(publicKeyCredential => {
        console.timeEnd("navigator.credentials.get");
        console.time("process publicKeyCredential");
        const result = {
            type: publicKeyCredential.type,
            id: publicKeyCredential.id,
            response: {
                authenticatorData: uint8arrayToBase64url(publicKeyCredential.response.authenticatorData),
                clientDataJSON: uint8arrayToBase64url(publicKeyCredential.response.clientDataJSON),
                signature: uint8arrayToBase64url(publicKeyCredential.response.signature),
                userHandle: publicKeyCredential.response.userHandle && uint8arrayToBase64url(publicKeyCredential.response.userHandle),
            },
            clientExtensionResults: publicKeyCredential.getClientExtensionResults(),
        };
        console.timeEnd("process publicKeyCredential");
        return result;
    })
    .then((encodedResult) => {
        console.time("fetch /api/webauthn/welcome");
        const formData = new FormData();
        formData.append("credential", JSON.stringify(encodedResult));
        formData.append("username", document.querySelector('input[name="username"]').value);
        return fetch("/api/webauthn/welcome", {
            method: 'POST',
            headers: {
				'header': header,
				'X-CSRF-Token': token,
            },
            body: formData,
        });
    })
    .then(response => {
        console.timeEnd("fetch /api/webauthn/welcome");
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        console.time("response.json");
        return response.json();
    })
    .then(data => {
        console.timeEnd("response.json");
        if (data.status === 'OK') {
            console.timeEnd("total");

            // URL 검증 로직 추가 (2024-06-22 CWE-601 Open Redirect)
            const allowedUrls = ['/index', '/login'];
            const redirectUrl = data.redirectUrl;

            try {
                // URL 객체를 생성하여 유효성을 검증
                const url = new URL(redirectUrl, window.location.origin);

                // URL이 허용된 목록에 있는지 확인
                if (allowedUrls.includes(url.pathname)) {
                    // Sanitize the URL by encoding potential unsafe characters
                    const sanitizedPath = encodeURI(url.pathname);
                    window.location.href = sanitizedPath;
                } else {
                    console.error('Redirect URL is not allowed:', redirectUrl);
                    errorAlert('Invalid redirect URL.');
                }
            } catch (e) {
                console.error('Invalid URL:', redirectUrl);
                errorAlert('Invalid URL format.');
            }
        } else {
            errorAlert();
        }

    })
    .catch(error => {
        popupReHtmlMsg("인증 오류", "인증을 다시 시도해주시기 바랍니다.", "error", "돌아가기", "/login");
    });
}



// 일반인증 호출(2단계)
const normal = (e) => {
    
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;
    e.preventDefault();
    const formData = new FormData(e.target);
    fetch('/login', {
        method: 'POST',
        headers: {
			'header': header,
			'X-CSRF-Token': token,
    	},
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        if (data.result) {
            console.log("패스워드 로그인 성공:", data);
            // 패스워드 인증 성공 시
            login();
        } else {
            console.log("패스워드 로그인 실패:", data);
            // 패스워드 인증 실패 시
            popupMsg("인증 실패", data.message, "error");
        }
    })
    .catch(error => {
        console.error('로그인 중 오류 발생:', error);
        popupMsg("인증 오류", "인증을 다시 시도해주시기 바랍니다.", "error"); // 오류 처리 함수 호출
    });
}

// 로그인 처리
const login = () => {

    const username = document.getElementById("username").value.toUpperCase();
    //const password = btoa(encodeURIComponent(document.getElementById("password").value));
    const password = document.getElementById("password").value;
    const MFA = $('input[name="MFA"]:checked').val();
    console.log("2단계 로그인 함수 : ", MFA);
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');    
	switch (MFA) {
	    case 'OTP':     
	        otpAuthAlert(username, MFA, password, header, token); //otp 인증
	        break; 
	    case 'SMS':
	        otpAuthAlert(username, MFA, password, header, token); //SMS 인증
	        break; 
	    case '카카오톡':
	        otpAuthAlert(username, MFA, password, header, token); //카카오톡 인증
	        break; 
	    case 'FIDO':
	        fidoAuthAlert(username, MFA, password, header, token); //FIDO 인증
	        break; 
    }
}

// FIDO 인증화면
const fidoAuthAlert = (username, MFA, password, header, token) => {
	Swal.fire({
	        title: MFA+ " 인증",
	        html: "휴대폰 원가드 앱에서 "+MFA+" 인증 후 <br>제출을 눌러해주세요",
	        showCancelButton: true,
	        confirmButtonText: "제출",
	        cancelButtonText: "취소",
	        showLoaderOnConfirm: true,
	        preConfirm: async () => {},
	        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.isConfirmed) {       
			const OTP = "N/A"
			authentication(username, password, MFA, OTP, header, token);
    	}
	})
}

// otp 유형(SMS, 카카오, mOTP) 인증화면
const otpAuthAlert = (username, MFA, password, header, token) => {
    console.log("otpAuthAlert");
    Swal.fire({
        title: MFA+" 인증",
        html: MFA+" 인증번호 6자리 입력 후<br>제출을 눌러주세요(<b></b>초)",
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
            console.log(MFA+" 번호 :", OTP, " password :", password)
			authentication(username, password, MFA, OTP, header, token);           
        }
    })
}
    //Swal(otp 인증)


// 최종 인증
const authentication = (username, password, MFA, OTP, header, token) => {		
    $.ajax({
        type: "post",
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
        		errorAlert();
        }
    }); //ajax    
}

// 2단계 인증방식 기억하기
$(function() {
    let key = getCookie("MFAChk");
    if (key !== "") {
        // Check the radio button that matches the value stored in the cookie
        $("input:radio[name='MFA'][value='" + key + "']").prop("checked", true);
    }

    // When the radio button changes, update the cookie
    $('input[name="MFA"]').change(function() {
        setCookie("MFAChk", $('input[name="MFA"]:checked').val(), 30);
    });
});

// ID 기억하기
$(function(){
    let key = getCookie("idChk");

    if (key != "")
        $("#username").val(key);

    if ($("#username").val() != "")
        $("#flexCheckDefault").attr("checked", true);

    $("#flexCheckDefault").change(function() {
        if ($("#flexCheckDefault").is(":checked"))
            setCookie("idChk", $("#username").val(), 30);
        else
            deleteCookie("idChk");
    });

    $("#username").keyup(function() {
        if ($("#flexCheckDefault").is(":checked"))
            setCookie("idChk", $("#username").val(), 30);
    });
});


const errorAlert = () => {	
	Swal.fire({
            title: '인증 오류',
            text: '인증을 다시 진행해주세요.',
            icon: 'error',
            confirmButtonText: '확인'
        })
}
