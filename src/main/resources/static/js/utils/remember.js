// 인증방식 기억하기
const rememberAuthType = (webauthnYn) => {

    let key = getCookie("MFA");
    if( webauthnYn === 'Y' ) {
        $("input:radio[name='MFA'][value='webauthn']").prop("checked", true);
    }
    else if (key !== "") {
        // 쿠키에 설정된 값으로 라디오 버튼 체크
        $("input:radio[name='MFA'][value='" + key + "']").prop("checked", true);
    } else {
        // 카카오톡으로 Webauthn 체크
        $("input:radio[name='MFA'][value='카카오톡']").prop("checked", true);
    }
}

// ID 기억하기
const rememberId = () => {
    let key = getCookie("USERNAME");

    if (key != "")
        $("#username").val(key);

    if ($("#username").val() != "")
        $("#flexCheckDefault").attr("checked", true);

    $("#flexCheckDefault").change(function() {
        if ($("#flexCheckDefault").is(":checked"))
            setCookie("USERNAME", $("#username").val(), 30);
        else
            deleteCookie("USERNAME");
    });

    $("#username").keyup(function() {
        if ($("#flexCheckDefault").is(":checked"))
            setCookie("USERNAME", $("#username").val(), 30);
    });
}
