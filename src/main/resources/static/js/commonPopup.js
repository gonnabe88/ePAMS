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