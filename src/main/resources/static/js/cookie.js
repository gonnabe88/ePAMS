
const setCookie = (cookieName, value, exdays) => {
    let exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    let cookieValue = encodeURIComponent(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
    document.cookie = cookieName + "=" + cookieValue + "; path=/"; // 경로를 추가하여 루트에서 사용할 수 있도록 설정
}

const deleteCookie = (cookieName) => {
    let expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "=; expires=" + expireDate.toUTCString() + "; path=/"; // 경로 추가
}

const getCookie = (cookieName) => {
    cookieName = cookieName + '=';
    let cookieData = document.cookie;
    let start = cookieData.indexOf(cookieName);
    let cookieValue = '';
    if (start !== -1) {
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if (end === -1) end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return decodeURIComponent(cookieValue);
}