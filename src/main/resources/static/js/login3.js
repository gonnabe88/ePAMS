$(document).ready(function() {
    var key = getCookie("idChk"); //user1
    if (key != "") { //idChk 쿠키에 값이 있다면
        $("#username").val(key);
    }

    if ($("#username").val() != "") { //username 폼에 값이 있다면
        $("#flexCheckDefault").attr("checked", true);
    }

    $("#flexCheckDefault").change(function() {
        if ($("#flexCheckDefault").is(":checked")) {
            setCookie("idChk", $("#username").val(), 30);
        } else {
            deleteCookie("idChk");
        }
    });

    $("#username").keyup(function() {
        if ($("#flexCheckDefault").is(":checked")) {
            setCookie("idChk", $("#username").val(), 30);
        }
    });
});

function setCookie(cookieName, value, exdays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName) {
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if (start != -1) {
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if (end == -1) end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}
// 로그인 처리
const pwlogin = () => {
    const username = document.getElementById("username").value;
    const password = document.getElementById("memberPassword").value;
    const twofactor = $('input[name="twofactor"]:checked').val();
    const UUID = getCookie("UUID");

    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

    $.ajax({
        type: "post",
        url: "/pwlogin",
        dataType: "json",
        data: {
            "username": username,
            "password": password
        },
        
        //CSRF Token
        beforeSend: function (xhr) {
        	xhr.setRequestHeader(header, token);
        },

        complete: function(data) {
            console.log("PW 검증 응답 성공", data.responseText)
            if (data.responseText == "success") {
                console.log("PW 검증 성공", data)
                console.log("인증방법", twofactor);
                switch (twofactor) {
                    case 'OTP':
                        Swal.fire({
                            title: "OTP 인증",
                            text: "OTP번호를 입력해주세요",
                            input: "text",
                            inputAttributes: {
                                autocapitalize: "off"
                            },
                            showCancelButton: true,
                            confirmButtonText: "Submit",
                            showLoaderOnConfirm: true,
                            preConfirm: async (pwlogin) => {},
                            allowOutsideClick: () => !Swal.isLoading()
                        }).then((result) => {
                            if (result.isConfirmed) {
                                const OTP = result.value
                                console.log("otp 번호 :", OTP)
                                $.ajax({
                                    type: "post",
                                    url: "/otplogin",
                                    dataType: "json",
                                    data: {
                                        "username": username,
                                        "OTP": OTP,
                                        "UUID" : UUID
                                    },
                                    
                                    //CSRF Token
                                    beforeSend: function (xhr) {
							        	xhr.setRequestHeader(header, token);
							        },
							        
                                    complete: function(data) {
                                        if (data.responseText == "success") {
                                            console.log("최종 로그인", )

                                            $.ajax({
                                                type: "post",
                                                url: "/authenticate",
                                                dataType: 'json',
                                                data: {
                                                    "username": username,
                                                    "password": password,
                                                    "OTP": OTP
                                                },
			                                    //CSRF Token
			                                    beforeSend: function (xhr) {
										        	xhr.setRequestHeader(header, token);
										        },

                                                //success로 진입이 안되는 이슈로 complete 사용
                                                complete: function(data) {
                                                    console.log("최종 로그인")
                                                    window.location.href = '/index'
                                                }
                                            }); //ajax

                                        } else {
                                            Swal.fire({
                                                title: '인증 오류',
                                                text: 'OTP를 다시 확인해주세요.',
                                                icon: 'error',
                                                confirmButtonText: '확인'
                                            })
                                        }
                                    }
                                })
                            }
                        }); //Swal(OTP 인증)
                        break; //case(OTP)
                    case 'SMS':
                        Swal.fire({
                            title: "SMS 인증",
                            text: "SMS번호를 입력해주세요",
                            input: "text",
                            inputAttributes: {
                                autocapitalize: "off"
                            },
                            showCancelButton: true,
                            confirmButtonText: "Submit",
                            showLoaderOnConfirm: true,
                            preConfirm: async (pwlogin) => {},
                            allowOutsideClick: () => !Swal.isLoading()
                        }).then((result) => {
                            if (result.isConfirmed) {
                                const SMS = result.value
                                console.log("SMS 번호 :", SMS)
                                $.ajax({
                                    type: "post",
                                    url: "/otplogin",
                                    dataType: "json",
                                    data: {
                                        "username": username,
                                        "OTP": SMS,
                                        "UUID" : UUID
                                    },
                                    
                                    //CSRF Token
                                    beforeSend: function (xhr) {
							        	xhr.setRequestHeader(header, token);
							        },
                                    
                                    complete: function(data) {
                                        if (data.responseText == "success") {
                                            console.log("최종 로그인", )

                                            $.ajax({
                                                type: "post",
                                                url: "/authenticate",
                                                dataType: 'json',
                                                data: {
                                                    "username": username,
                                                    "password": password,
                                                    "OTP": SMS
                                                },
                                                
			                                    //CSRF Token
			                                    beforeSend: function (xhr) {
										        	xhr.setRequestHeader(header, token);
										        },

                                                //success로 진입이 안되는 이슈로 complete 사용
                                                complete: function(data) {
                                                    console.log("최종 로그인")
                                                    window.location.href = '/index'
                                                }
                                            }); //ajax(authenticate)

                                        } else {
                                            Swal.fire({
                                                title: '인증 오류',
                                                text: 'OTP를 다시 확인해주세요.',
                                                icon: 'error',
                                                confirmButtonText: '확인'
                                            })
                                        }
                                    }
                                })
                            }
                        }); //Swal(SMS 인증)
                        break; //case(SMS)

                    case '카카오톡':
                        Swal.fire({
                            title: "카카오톡 인증",
                            text: "인증번호를 입력해주세요",
                            input: "text",
                            inputAttributes: {
                                autocapitalize: "off"
                            },
                            showCancelButton: true,
                            confirmButtonText: "Submit",
                            showLoaderOnConfirm: true,
                            preConfirm: async (pwlogin) => {},
                            allowOutsideClick: () => !Swal.isLoading()
                        }).then((result) => {
                            if (result.isConfirmed) {
                                const KAKAO = result.value
                                console.log("인증 번호 :", KAKAO)
                                $.ajax({
                                    type: "post",
                                    url: "/otplogin",
                                    dataType: "json",
                                    data: {
                                        "username": username,
                                        "OTP": KAKAO,
                                        "UUID" : UUID
                                    },
                                    
                                    //CSRF Token
                                    beforeSend: function (xhr) {
							        	xhr.setRequestHeader(header, token);
							        },
                                    
                                    complete: function(data) {
                                        if (data.responseText == "success") {
                                            console.log("최종 로그인", )

                                            $.ajax({
                                                type: "post",
                                                url: "/authenticate",
                                                dataType: 'json',
                                                data: {
                                                    "username": username,
                                                    "password": password,
                                                    "OTP": KAKAO
                                                },

			                                    //CSRF Token
			                                    beforeSend: function (xhr) {
										        	xhr.setRequestHeader(header, token);
										        },

                                                //success로 진입이 안되는 이슈로 complete 사용
                                                complete: function(data) {
                                                    console.log("최종 로그인")
                                                    window.location.href = '/index'
                                                }
                                            }); //ajax(authenticate)

                                        } else {
                                            Swal.fire({
                                                title: '인증 오류',
                                                text: '인증번호를 다시 확인해주세요.',
                                                icon: 'error',
                                                confirmButtonText: '확인'
                                            })
                                        }
                                    }
                                })
                            }
                        }); //Swal(카카오톡 인증)
                        break; //case(카카오톡)

                    case 'FIDO':
                        Swal.fire({
                            title: "FIDO 인증",
                            text: "휴대폰에서 FIDO 인증 후 제출을 눌러해주세요",
                            showCancelButton: true,
                            confirmButtonText: "Submit",
                            showLoaderOnConfirm: true,
                            preConfirm: async (pwlogin) => {},
                            allowOutsideClick: () => !Swal.isLoading()
                        }).then((result) => {
                            if (result.isConfirmed) {
                                const FIDO = result.value
                                $.ajax({
                                    type: "post",
                                    url: "/otplogin",
                                    dataType: "json",
                                    data: {
                                        "username": username,
                                        "OTP": FIDO,
                                        "UUID" : UUID
                                    },
                                    
                                    //CSRF Token
                                    beforeSend: function (xhr) {
							        	xhr.setRequestHeader(header, token);
							        },
                                    
                                    complete: function(data) {
                                        if (data.responseText == "success") {
                                            console.log("최종 로그인", )

                                            $.ajax({
                                                type: "post",
                                                url: "/authenticate",
                                                dataType: 'json',
                                                data: {
                                                    "username": username,
                                                    "password": password,
                                                    "OTP": FIDO
                                                },

			                                    //CSRF Token
			                                    beforeSend: function (xhr) {
										        	xhr.setRequestHeader(header, token);
										        },

                                                //success로 진입이 안되는 이슈로 complete 사용
                                                complete: function(data) {
                                                    console.log("최종 로그인")
                                                    window.location.href = '/index'
                                                }
                                            }); //ajax(authenticate)

                                        } else {
                                            Swal.fire({
                                                title: '인증 오류',
                                                text: '인증을 다시 진행해주세요.',
                                                icon: 'error',
                                                confirmButtonText: '확인'
                                            })
                                        }
                                    }
                                })
                            }
                        }); //Swal(FIDO 인증)
                        break; //case(FIDO)

                } //switch(twofactor)
            } else {
                console.log("PW 검증 오류")
                Swal.fire({
                    title: '인증 오류',
                    text: 'ID/비밀번호를 다시 확인해주세요.',
                    icon: 'error',
                    confirmButtonText: '확인'
                })
            }
        }
    }); //ajax			
}