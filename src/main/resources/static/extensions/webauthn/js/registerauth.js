document.addEventListener("submit2", (e) => {
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    e.preventDefault();
    const formData = new FormData(e.target);
    fetch('/webauthn/register', {
        method: 'POST',
        body: formData,       
        //CSRF Token
        beforeSend: function (xhr) {
        	xhr.setRequestHeader(header, token);
        }, 
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
    .then(credentialCreateOptions =>
        navigator.credentials.create(credentialCreateOptions))
    .then(publicKeyCredential => ({
        type: publicKeyCredential.type,
        id: publicKeyCredential.id,
        response: {
        attestationObject: uint8arrayToBase64url(publicKeyCredential.response.attestationObject),
        clientDataJSON: uint8arrayToBase64url(publicKeyCredential.response.clientDataJSON),
        transports: publicKeyCredential.response.getTransports && publicKeyCredential.response.getTransports() || [],
        },
        clientExtensionResults: publicKeyCredential.getClientExtensionResults(),
    }))
    .then((encodedResult) => {
        const form = document.getElementById("form");
        const formData = new FormData(form);
        formData.append("credential", JSON.stringify(encodedResult));
        return fetch("/webauthn/finishauth", {
            method: 'POST',
   		    headers: {
                'header': document.querySelector('meta[name="_csrf"]').content,
                'Content-Type': "application/json",
                'X-CSRF-Token': document.querySelector('meta[name="_csrf_header"]').content
            },  
            body: formData,
        })
    })
    .then((response) => {
        followRedirect(response);
    })
    .catch((error) => {
        displayError(error);
    });
})