
const checkBrowser = () => {
    const useragent = navigator.userAgent.toLowerCase();
    const target_url = location.href;
    const message = "<p class=\"text-left\">현재 브라우저에서는 동작하지 않는 기능이 있을 수 있습니다.</p>" +
        "<p class=\"text-left\">가급적 아래의 권장 브라우저를 사용해주시기 바랍니다.</p>" +
        "<p class=\"h6\"><i class=\"bi bi-info-circle ms-2 me-1\"></i>권장 브라우저</p>" +
        "<p class=\"h7 ms-3\"><i class=\"bi bi-browser-chrome me-1\"></i>Chrome(크롬)</p>" +
        "<p class=\"h7 ms-3\"><i class=\"bi bi-browser-edge me-1\"></i>Edge(엣지)</p>" +
        "<p class=\"h7 ms-3\"><i class=\"bi bi-browser-firefox me-1\"></i>Firefox(파이어폭스)</p>" +
        "<p class=\"h7 ms-3\"><i class=\"bi bi-browser-safari me-1\"></i>Safari(사파리)</p>" +
        "<p class=\"h7 ms-3\"><i class=\"bi bi-android2 me-1\"></i>Samsung(삼성)</p>"

	// open redirect 취약점 조치
	const isValidUrl = (url, allowedUrl) => {
		try {
			let parsedUrl = new URL(url);
			console.log("url : " + url);
			console.log("parsedUrl.origin : " + parsedUrl.origin);
			return allowedUrl.includes(parsedUrl.origin);
		} catch (e) {
			return false;
		}
	}

    if(useragent.match(/kakaotalk/i)){
    	const allowedUrl = ['http://localhost:8080/login', 'https://epams.kdb.co.kr/login', 'https://depams.kdb.co.kr/login'];
    	const kakaoUrl = 'kakaotalk://web/openExternal?url=';
    	
        //카카오톡 외부브라우저로 호출
        if(isValidUrl(target_url, allowedUrl)){
        	location.href = kakaoUrl+encodeURIComponent(target_url);
        }
        
        popupHtmlMsg("미호환 브라우저",message,"warning");
    } else if (useragent.match(/line/i)) {
        if(target_url.indexOf('?') !== -1){
            location.href = target_url+'&openExternalBrowser=1';
            popupHtmlMsg("미호환 브라우저",message,"warning");
        }else{
            location.href = target_url+'?openExternalBrowser=1';
            popupHtmlMsg("미호환 브라우저",message,"warning");
        }
    } else if (useragent.match(/inapp|naver|snapchat|wirtschaftswoche|thunderbird|instagram|everytimeapp|whatsApp|electron|wadiz|aliapp|zumapp|iphone(.*)whale|android(.*)whale|kakaostory|band|twitter|DaumApps|DaumDevice\/mobile|FB_IAB|FB4A|FBAN|FBIOS|FBSS|trill\/[^1]/i)){
        popupHtmlMsg("미호환 브라우저",message,"warning");
    }

}