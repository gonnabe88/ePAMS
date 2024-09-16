// 간편인증 자동 팝업
const webauthnPopup = (popupType) => {
    const authData = $('#authData');
    const simpleauth = authData.data('simpleauth');
    const username = authData.data('username');
    const simpleAuthPopup = getCookie("SIMPLE_AUTH_POPUP_YN");

    if ((popupType === "REGIST") || (!simpleauth && simpleAuthPopup !== "FALSE")) {
        Swal.fire({
            title: "간편인증",
            html: "편리한 로그인을 위해 휴대폰 간편인증을 등록합니다.",
            inputAttributes: {
                autocomplete: 'new-password',
                autocapitalize: "off",
                autofocus: "on"
            },
            showCancelButton: true,
            confirmButtonText: "등록",
            cancelButtonText: "나중에 할게요",
            showLoaderOnConfirm: true,
            preConfirm: async () => {},
            allowOutsideClick: () => !Swal.isLoading()
        }).then((result) => {
            if (result.isConfirmed) {
                webauthnReg(username, popupType);
            }

            if(popupType === "REMIND") {
                // 1일간 팝업을 보여주지 않기 위해 쿠키 설정
                setCookie("SIMPLE_AUTH_POPUP_YN", "FALSE", 1);
            }
        });
    }
}

const webauthnRevokePopup = () => {

        Swal.fire({
            title: "간편인증 해제",
            html: "간편인증 등록 내역을 해제하시겠습니까?",
            inputAttributes: {
                autocomplete: 'new-password',
                autocapitalize:"off",
                autofocus: "on"
            },
            showCancelButton: true,
            confirmButtonText: "해제",
            cancelButtonText: "다시 생각해볼래요.",
            showLoaderOnConfirm: true,
            preConfirm: async () => {},
            allowOutsideClick: () => !Swal.isLoading()
        }).then((result) => {
            if (result.isConfirmed) {
                webauthnRevoke();
            }
            else {
                // 1일간 팝업을 보여주지 않기 위해 쿠키 설정
                setCookie("SIMPLE_AUTH_POPUP_YN", "FALSE", 1);
            }
        });
}


