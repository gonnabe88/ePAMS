// 인증방식 기억하기
const rememberAuthType = () => {

    let key = getCookie("MFA");
    if (key !== "") {
        // Check the radio button that matches the value stored in the cookie
        $("input:radio[name='MFA'][value='" + key + "']").prop("checked", true);
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
