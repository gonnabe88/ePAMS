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

$("#loginForm").submit(function(){
	login();	
});


// 로그인 처리
const login= () => {
    const username = document.getElementById("username").value;
    const MFA = $('input[name="MFA"]:checked').val();
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    console.log("login "+username+MFA);
    $.ajax({
        type: "post",
        url: "/api/mfa",
        dataType: "json",
        data: {
            "username": username,
            "MFA": MFA
        },       
        //CSRF Token
        beforeSend: function (xhr) {
        	xhr.setRequestHeader(header, token);
        },                
        complete: function(data) {         
			const UUID = JSON.parse(data.responseText).UUID;
			console.log("MFA reply : " + JSON.parse(data.responseText).OTP)
		    switch (MFA) {
		        case 'OTP':     
		            console.log(MFA+UUID)
		            otpAuthAlert(username, MFA, UUID, header, token); //OTP 인증
		            break; 
		        case 'SMS':
		            otpAuthAlert(username, MFA, UUID, header, token); //SMS 인증
		            break; 
		        case '카카오톡':
		            otpAuthAlert(username, MFA, UUID, header, token); //카카오톡 인증
		            break; 
		        case 'FIDO':
		            fidoAuthAlert(username, MFA, UUID, header, token); //FIDO 인증
		            break; 
		    } //switch(MFA)
	    }
    });
}

// FIDO 인증화면
const fidoAuthAlert = (username, MFA, UUID, header, token) => {

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
			const password = MFA
			if(UUID == "false")
				pwlogin(username, MFA, OTP ,header, token);  
			else 
				authentication(username, password, MFA, OTP, header, token);
            	}
        	})
        }

// OTP 유형(SMS, 카카오, mOTP) 인증화면
const otpAuthAlert = (username, MFA, UUID, header, token) => {
	
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
            const password = result.value
            console.log(MFA+" 번호 :", OTP, " password :", password)
			if(UUID == "false")
				pwlogin(username, MFA, OTP ,header, token);  
			else 
				authentication(username, password, MFA, OTP, header, token);            
        }
    })
}
    //Swal(OTP 인증)


// 추가 인증
const pwlogin = (username, MFA, OTP, header, token) => {
 
     Swal.fire({
        title: "비밀번호 인증",
        html: "접속환경 변경으로 추가인증이 필요합니다.<br> <b><u>내부망 ESSO 패스워드</u></b>를 입력해주세요.",
        input: "password",
        inputPlaceholder: "ESSO 비밀번호",
        inputAttributes: {
            autocapitalize: "off",
            autofocus : "on"
        },
        showCancelButton: true,
        confirmButtonText: "제출",
        cancelButtonText: "취소",
        showLoaderOnConfirm: true,
        preConfirm: async () => {},
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.isConfirmed) {
            const password = result.value
            authentication(username, password, MFA, OTP, header, token);
        }
    }); //Swal(PW 인증)  
}

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

const errorAlert = () => {	
	Swal.fire({
            title: '인증 오류',
            text: '인증을 다시 진행해주세요.',
            icon: 'error',
            confirmButtonText: '확인'
        })
}
