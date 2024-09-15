
const webauthnLogin = (e) => {
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
                    }
                } catch (e) {
                    console.error('Invalid URL:', redirectUrl);
                    popupReHtmlMsg("인증 오류", "인증을 다시 시도해주시기 바랍니다.", "error","/login");
                }
            } else {
                popupReHtmlMsg("인증 오류", "인증을 다시 시도해주시기 바랍니다.", "error","/login");
            }
        })
        .catch(error => {
            popupReHtmlMsg("인증 오류", "인증을 다시 시도해주시기 바랍니다.", "error","/login");
        });
}

// 간편인증 WebAuthn 인증 로직
const showSpinnerButton = () => {
    const loginBtn = document.getElementById('login');
    loginBtn.innerHTML = `
            <span class="spinner-border spinner-border-sm" aria-hidden="true"></span>
            <span role="status">Loading...</span>
        `;
    loginBtn.setAttribute('disabled', 'true');
}
