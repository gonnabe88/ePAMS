
//화면 로그 시 호출
document.addEventListener("DOMContentLoaded", function () {
	
	// 간편인증 or 2단계인증 checkbox 선택값
    const checkbox = document.getElementById('flexSwitchCheckChecked');
    // 간편인증 or 2단계인증 checkbox 텍스트
    const label = document.getElementById('switchLabel');
 	// 패스워드 입력폼
 	const passwordInput = document.getElementById('password');
 	// SMS, KAKAO, OTP, FIDO 라디오버튼
    const smsInput = document.getElementById('sms');
    const kakaoInput = document.getElementById('kakao');
    const otpInput = document.getElementById('otp');
    const fidoInput = document.getElementById('fido');
    
    //submit 버튼 클릭 시
    form.addEventListener("submit", function (e) {
        e.preventDefault();  // 폼의 기본 제출 동작을 방지
         // password 값을 가져오기
        const passwordField = document.getElementById('password');
        const password = passwordField.value;
        // Base64 인코딩
        const encodedPassword = btoa(encodeURIComponent(password));
        // 인코딩된 값을 다시 설정
        passwordField.value = encodedPassword;
        if (checkbox.checked) webauthn(e); // 간편인증
        else normal(e); // 일반인증(2단계)
    });        
        
    // 체크박스(토글) 세팅값에 따라 간편인증 <> 2단계인증 문구 변경
    function updateLabel() {
        label.textContent = checkbox.checked ? '간편인증' : '2단계인증';
    }
    checkbox.addEventListener('change', updateLabel);
    updateLabel(); // 초기 상태 설정
    
    // 체크박스(토글) 세팅값에 따라 2단계 인증 관련 elements disable 
    function toggleChange() {
        passwordInput.disabled = checkbox.checked;
        smsInput.disabled = checkbox.checked;
        kakaoInput.disabled = checkbox.checked;
        otpInput.disabled = checkbox.checked;
        fidoInput.disabled = checkbox.checked;
    }
    checkbox.addEventListener('change', toggleChange);  
    toggleChange(); // 초기 상태 설정
});

// 간편인증 WebAuthn 인증 로직
function webauthn(e) {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;
    e.preventDefault();
    //this.form = document.getElementById("form");
    const formData = new FormData(e.target);
    fetch('/webauthn/login', {
        method: 'POST',
        headers: {
	        'header': header,
	        'X-CSRF-Token': token,
    	},
        body: formData
    })
    .then(response => initialCheckStatus(response))
    .then(credentialGetJson => ({
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
    }))
    .then(credentialGetOptions =>
        navigator.credentials.get(credentialGetOptions))
    .then(publicKeyCredential => ({
        type: publicKeyCredential.type,
        id: publicKeyCredential.id,
        response: {
        authenticatorData: uint8arrayToBase64url(publicKeyCredential.response.authenticatorData),
        clientDataJSON: uint8arrayToBase64url(publicKeyCredential.response.clientDataJSON),
        signature: uint8arrayToBase64url(publicKeyCredential.response.signature),
        userHandle: publicKeyCredential.response.userHandle && uint8arrayToBase64url(publicKeyCredential.response.userHandle),
        },
        clientExtensionResults: publicKeyCredential.getClientExtensionResults(),
    }))
    .then((encodedResult) => {
	    const header = document.querySelector('meta[name="_csrf_header"]').content;
    	const token = document.querySelector('meta[name="_csrf"]').content;
        document.getElementById("credential").value = JSON.stringify(encodedResult);
        //this.form.submit();
        //const form = document.getElementById("form");
        //const formData = new FormData(form);
        //formData.append("credential", JSON.stringify(encodedResult));
        const formData = new FormData(form);
		console.log("formData : "+ JSON.stringify(formData));
        return fetch("/webauthn/welcome", {
            method: 'POST',
        	headers: {
	        'header': header,
	        'X-CSRF-Token': token,
    	},
            body: formData,
        })
    })
    .then((response) => {
		console.log(response)
        //followRedirect(response);
        if(response.status == 200)
            	window.location.href = '/index'
    	else {
    		errorAlert();
		}
    })
    .catch(error => 
    	//displayError(error)
    	//errorAlert()
    	popupMsg(error)    	
    )
}

// 일반인증 호출(2단계)
function normal(e) {
    
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
            login2()
        } else {
            console.log("패스워드 로그인 실패:", data);
            // 패스워드 인증 실패 시
            popupMsg(data.message);
        }
    })
    .catch(error => {
        console.error('로그인 중 오류 발생:', error);
        popupMsg(error); // 오류 처리 함수 호출
    });
}

// 로그인 처리
const login2 = () => {
    const username = document.getElementById("username").value.toUpperCase();
    //const password = btoa(encodeURIComponent(document.getElementById("password").value));
    const password = document.getElementById("password").value;
    const MFA = $('input[name="MFA"]:checked').val();
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');    
	console.log(password)
	switch (MFA) {
	    case 'OTP':     
	        otpAuthAlert(username, MFA, password, header, token); //OTP 인증
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
	console.log(password);
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

// OTP 유형(SMS, 카카오, mOTP) 인증화면
const otpAuthAlert = (username, MFA, password, header, token) => {
	
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
    //Swal(OTP 인증)


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
            if(data.status == 200)
            	window.location.href = '/index'
        	else
        		errorAlert();
        }
    }); //ajax    
}

// jquery
$(function(){
    let key = getCookie("MFAChk"); 
    if (key != "") 
		$("input:radio[name=MFA]:radio[value='"+key+"']").prop("checked", true);
        
    $('input[name="MFA"]').change(function() {
		setCookie("MFAChk", $('input[name="MFA"]:checked').val(), 30);
    });
});

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

const setCookie=(cookieName, value, exdays) => {
    let exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    let cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

const deleteCookie=(cookieName) => {
    let expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

const getCookie=(cookieName) => {
    cookieName = cookieName + '=';
    let cookieData = document.cookie;
    let start = cookieData.indexOf(cookieName);
    let cookieValue = '';
    if (start != -1) {
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if (end == -1) end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}

const popupMsg = (e) => {	
	Swal.fire({
            title: '인증 오류',
            text: e,
            icon: 'error',
            confirmButtonText: '확인'
        }).then(() => {
			window.location.href = '/login'
		});
}

const errorAlert = () => {	
	Swal.fire({
            title: '인증 오류',
            text: '인증을 다시 진행해주세요.',
            icon: 'error',
            confirmButtonText: '확인'
        })
}
