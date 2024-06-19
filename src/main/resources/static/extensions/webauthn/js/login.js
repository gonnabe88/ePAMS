document.addEventListener("submit", (e) => {
	const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;
    e.preventDefault();
    //this.form = document.getElementById("form");
    const formData = new FormData(e.target);
    fetch('/api/webauthn/login', {
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
        document.getElementById("credential").value = JSON.stringify(encodedResult);
        //this.form.submit();
        //const form = document.getElementById("form");
        //const formData = new FormData(form);
        //formData.append("credential", JSON.stringify(encodedResult));
        const formData = new FormData(form);
		console.log("formData : "+ JSON.stringify(formData));
        return fetch("/api/webauthn/welcome", {
            method: 'POST',
        	headers: {
	        'header': header,
	        'X-CSRF-Token': token,
    	},
            body: formData,
        })
    })
    .then((response) => {
        followRedirect(response);
    })
    .catch(error => displayError(error))
})