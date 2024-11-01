//삼성인터넷 브라우저인지 체크
function isSamsungBrowser(){
    return navigator.userAgent.includes("SamsungBrowser");
}

function isChrome(){
    const userAgent = navigator.userAgent;
    console.log(userAgent);
    return /Chrome/.test(userAgent);
}

if(isChrome()){
    console.log("크롬이다!");
}else {
    console.log("크롬 아니다!")
}

//다크모드인지 체크
function isDarkMode(){
    return window.matchMedia('(prefers-color-scheme : dark)').matches;
}

if(isSamsungBrowser() && isDarkMode()){
    const message = document.createElement("div");
    message.textContent = "해당 페이지는 '밝은 웹페이지'에 최적화 되어있습니다. 하단에 ≡을 눌러 밝은 웹페이지로 만들어 보세요"
    message.style.position="fixed";
    message.style.bottom="20px";
    message.style.left="50%";
    message.style.transform="translateX(-50%)";
    message.style.backgroundColor="#000";
    message.style.color="#fff";
    message.style.padding="10px 20px";
    message.style.borderRadius="5px";
    message.style.zIndex="1000"

    document.body.appendChild(message);

    setTimeout(() => {
        message.remove();
    },2000);
}