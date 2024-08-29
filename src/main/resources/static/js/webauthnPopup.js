// 간편인증 자동 팝업
$(document).ready(() => {
    const authData = $('#authData');
    const simpleauth = authData.data('simpleauth');
    const username = authData.data('username');
    const simpleAuthPopup = getCookie("SIMPLE_AUTH_POPUP_YN");
    console.log(`WebAuthn: ${simpleauth} ${username}`);

    if (!simpleauth && simpleAuthPopup !== "FALSE") {
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
                webauthnReg(username);
            }
            else {
                // 1일간 팝업을 보여주지 않기 위해 쿠키 설정
                setCookie("SIMPLE_AUTH_POPUP_YN", "FALSE", 1);
            }
        });
    }
});

// 간편인증 등록
const webauthnReg = (username) => {
    console.log("In webauthnReg");
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');
    const formData = new FormData();
    formData.append("username", username);

    fetch('/api/webauthn/register', {
        method: 'POST',
        headers: {
            'header': header,
            'X-CSRF-Token': token,
        },
        body: formData,
    })
        .then(response => initialCheckStatus(response))
        .then(credentialCreateJson => ({
            publicKey: {
                ...credentialCreateJson.publicKey,
                challenge: base64urlToUint8array(credentialCreateJson.publicKey.challenge),
                user: {
                    ...credentialCreateJson.publicKey.user,
                    id: base64urlToUint8array(credentialCreateJson.publicKey.user.id),
                },
                excludeCredentials: credentialCreateJson.publicKey.excludeCredentials.map(credential => ({
                    ...credential,
                    id: base64urlToUint8array(credential.id),
                })),
                extensions: credentialCreateJson.publicKey.extensions,
            },
        }))
        .then(credentialCreateOptions => navigator.credentials.create(credentialCreateOptions))
        .then(publicKeyCredential => ({
            type: publicKeyCredential.type,
            id: publicKeyCredential.id,
            response: {
                attestationObject: uint8arrayToBase64url(publicKeyCredential.response.attestationObject),
                clientDataJSON: uint8arrayToBase64url(publicKeyCredential.response.clientDataJSON),
                transports: publicKeyCredential.response.getTransports ? publicKeyCredential.response.getTransports() : [],
            },
            clientExtensionResults: publicKeyCredential.getClientExtensionResults(),
        }))
        .then((encodedResult) => {
            console.log("encodedResult:", encodedResult);
            formData.append("credential", JSON.stringify(encodedResult));
            return fetch("/api/webauthn/finishauth", {
                method: 'POST',
                headers: {
                    'header': header,
                    'X-CSRF-Token': token,
                },
                body: formData,
            });
        })
        .then((response) => {
            if (response.status === 200) {
                popupMsg("등록 성공", "간편인증 등록을 성공하였습니다.", "success");
            } else {
                popupMsg("등록 오류", "등록 중 오류가 발생했습니다.", "error");
            }
        })
        .catch((error) => {
            popupMsg("등록 취소", "간편인증 등록을 취소하였습니다.", "info");
        });
};
