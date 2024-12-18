// 공통 팝업 기본
const popupMsg = (title, text, icon) => {
    Swal.fire({
        title: title,
        text: text,
        icon: icon,
        confirmButtonText: '확인'
    });
}

// Html 팝업
const popupHtmlMsg = (title, html, icon) => {
    Swal.fire({
        title: title,
        html: html,
        icon: icon,
        confirmButtonColor: "#3085d6",
        confirmButtonText: '확인'
    });
}

// 공통 심플 팝업 URL Redirect
const popupReHtmlMsg = (title, html, icon, url) => {
    Swal.fire({
        title: title,
        html: html,
        icon: icon,
        showCancelButton: false,
        confirmButtonColor: "#6c757d",
        confirmButtonText: '닫기',
    }).then((result) => {
        window.location.href = url
    });
}

// 공통 심플 팝업 URL Redirect
const popupReloadHtmlMsg = (title, html, icon) => {
    Swal.fire({
        title: title,
        html: html,
        icon: icon,
        showCancelButton: false,
        confirmButtonColor: "#6c757d",
        confirmButtonText: '닫기',
    }).then((result) => {
        location.reload(); // 화면 새로고침
    });
}


// 공통 팝업 URL Redirect
const popupBtnReHtmlMsg = (title, html, icon, button_text, url) => {
    Swal.fire({
        title: title,
        html: html,
        icon: icon,
        showCancelButton: true,
        cancelButtonColor: "#6c757d",
        cancelButtonText: '닫기',
        confirmButtonColor: "#3085d6",
        confirmButtonText: button_text,
    }).then((result) => {
        if(result.isConfirmed) {
            window.location.href = url
        }
    });
}

// 공통 버튼이름 지정 팝업 URL Redirect
const popupCustBtnReHtmlMsg = (title, html, icon, url, btn) => {
    Swal.fire({
        title: title,
        html: html,
        icon: icon,
        showCancelButton: false,
        confirmButtonColor: "#6c757d",
        confirmButtonText: btn,
    }).then((result) => {
        window.location.href = url
    });
}
