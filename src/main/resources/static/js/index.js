// Scroll to top 버튼
// Get the button
var mybutton = document.getElementById("scrollToTopBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
    var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    var scrollHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    var scrollPercent = (scrollTop / scrollHeight) * 100;

    if (scrollTop > 20) {
        mybutton.style.display = "flex";
        // Calculate the conic gradient percentage
        var gradientValue = `conic-gradient(#80ABFE ${scrollPercent}%, #DECFFF ${scrollPercent}%)`;
        mybutton.style.background = gradientValue;
    } else {
        mybutton.style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
mybutton.onclick = function() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}


// 직원 검색 스크롤 이동
function scrollToDiv() {
    const target = document.getElementById('searchDiv');
    target.scrollIntoView({ behavior: 'smooth' });
}

// 직원 검색 조회
const search = () => {
    const text = $("#searchMember").val();
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    console.log("searchMember :"+text);
    $.ajax({
        type: "post",
        url: "/search",
        dataType: "json",
        data: {
            "text": text
        },
        //CSRF Token
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        complete: function(data) {
            console.log("search reply : " + data.responseText)
            $("#memberList").replaceWith(data.responseText);
        }
    });
}

// 간편인증 자동 팝업
$(document).ready(function() {
    const authData = $('#authData');
    const simpleauth = authData.data('simpleauth');
    const username = authData.data('username');
    console.log("WebAuthn : "+simpleauth + username);

    if(simpleauth == false) {
        Swal.fire({
            title: "간편인증",
            html: "편리한 로그인을 위해 휴대폰 간편인증을 등록합니다.",
            inputAttributes: {
                autocomplete: 'new-password',
                autocapitalize: "off",
                autofocus : "on"
            },
            showCancelButton: true,
            confirmButtonText: "등록",
            cancelButtonText: "나중에 할게요",
            showLoaderOnConfirm: true,
            preConfirm: async () => {},
            allowOutsideClick: () => !Swal.isLoading()
        }).then((result) => {
            if (result.isConfirmed) {
                webauthn_reg(username);
            }
        }); //Swal(PW 인증)
    }
});

// 간편인증 등록
function webauthn_reg(username) {
    console.log("In webauthn_reg");
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;
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
            //const form = document.getElementById("form");
            //const formData = new FormData(form);
            console.log("encodedResult : ")
            formData.append("credential", JSON.stringify(encodedResult));
            return fetch("/api/webauthn/finishauth", {
                method: 'POST',
                headers: {
                    'header': header,
                    'X-CSRF-Token': token,
                },
                body: formData,
            })
        })
        .then((response) => {
            //followRedirect(response);
            if(response.status == 200)
                popupMsg("등록 성공", "간편인증 등록을 성공하였습니다.", "success");
            else {
                popupMsg("등록 오류", error, "error", "");
            }
        })
        .catch((error) => {
            popupMsg("등록 취소", "간편인증 등록을 취소하였습니다.", "info");
        });
}

// 공통 팝업 기본
const popupMsg = (title, text, icon) => {
    Swal.fire({
        title: title,
        text: text,
        icon: icon,
        confirmButtonText: '확인'
    });
}

// 공통 팝업 URL Redirect
const popupRedirectMsg = (title, text, icon, url) => {
    Swal.fire({
        title: title,
        text: text,
        icon: icon,
        confirmButtonText: '확인'
    }).then(() => {
        window.location.href = url
    });
}

// 빠른 근태 신청 팝업
const ApplAlert = (date) => {
    Swal.fire({
        title: "신청하시겠습니까?",
        html: `
            <p style=\"text-align:center\">${date} 연차 1일</p>
            <div class="accordion" id="accordionExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" style="--bs-accordion-btn-focus-box-shadow:;">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                            <div class="badge text-bg-warning text-wrap me-2" style="width: 3rem;">필독</div><h6 class="mb-0">근태 신청전 유의사항</h6>
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                        <div class="accordion-body" style="text-align:left;">
                            <div class="row">
                                <div class="col-1"><i class="bi bi-check-square-fill" style="color:#ffc107;"></i> </div>
                                <div class="col-11">근태관련 제반 규정을 준수하고, <br><strong>목적외 사용시 어떠한 처벌도 <br>감수</strong>하겠음을 확인합니다. <br><br></div>
                            </div>
                            <div class="row">
                                <div class="col-1"><i class="bi bi-check-square-fill" style="color:#ffc107;"></i> </div>
                                <div class="col-11"><strong>인장(부/임시 보관책임자), 보안</strong><br>업무에 대해, 업무인수인계를 완료하였음을 확인합니다.<br><br></div>
                            </div>
                            <div class="row">
                                <div class="col-1"><i class="bi bi-check-square-fill" style="color:#ffc107;"></i> </div>
                                <div class="col-11"></i> 위 담당업무외 <strong>중요서류 및 기타<br>사항</strong>에 대하여 업무인수인계를<br>완료하였음을 확인합니다. <br></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `,
        icon: "info",
        showCancelButton: true,
        scrollbarPadding: false,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "네, 신청할게요!",
        cancelButtonText: "아니요",
    }).then((result) => {
        if (result.isConfirmed) {
            // DtmHisDTO 객체 생성
            const dtmHisDTO = {
                dtmKindCd: 'DTM01',
                dtmReasonCd: 'RES01',
                staYmd: new Date(date).toISOString(),
                endYmd: new Date(date).toISOString()
            };

            // POST 요청 함수 호출
            postDtmHisDTO(dtmHisDTO);

            Swal.fire({
                title: "신청되었습니다.",
                html: `<p style=\"text-align:center\">${date} 연차 1일</p>`,
                icon: "success",
                confirmButtonColor: "#3085d6",
                confirmButtonText: "확인"
            });
        }
    });
};

const postDtmHisDTO = (dtmHisDTO) => {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;
    fetch('/api/dtm/appl', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'header': header,
            'X-CSRF-Token': token,
        },
        body: JSON.stringify(dtmHisDTO)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
};

