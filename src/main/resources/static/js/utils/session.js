const checkSessionAuth = () => {
    $.ajax({
        url:'/api/session/check-session-auth',
        method: 'GET',
        success: function(data, testStatus, xhr) { // 서버 응답 성공
            
            if(!data.sessionValid) { // 세션이 유효하지 않으면 로그인 페이지로 이동
                console.log("session not valid : "+ data.testStatus);
                if(window.location.pathname === '/login') { // 현재 페이지가 로그인 페이지면 물어보지 않고 새로고침
                    window.location.href = "/login";
                } else { // 로그인 페이지가 아니면 팝업창 띄워서 로그인 페이지로 이동
                    popupCustBtnReHtmlMsg('인증 만료', '15분 이상 요청이 없어 인증을 종료하였습니다. 다시 로그인해주시기 바랍니다.', 'info', '/login', '확인');
                }
            }
            // 세션이 유효하면 아무것도 하지 않음
        },
        error: function(xhr, textStatus, errorThrown){ // 서버 응답 실패
            console.log("error");
            if(window.location.pathname === '/login') { // 현재 페이지가 로그인 페이지면 물어보지 않고 새로고침
                window.location.href = "/login";
            } else { // 로그인 페이지가 아니면 팝업창 띄워서 로그인 페이지로 이동
                popupCustBtnReHtmlMsg('인증 만료', '15분 이상 요청이 없어 인증을 종료하였습니다. 다시 로그인해주시기 바랍니다.', 'info', '/login', '확인');
            }
        }
    })
}

const checkSessionValid = () => {
    $.ajax({
        url:'/api/session/check-session-valid',
        method: 'GET',
        success: function(data, testStatus, xhr) { // 서버 응답 성공
            
            if(!data.sessionValid) { // 세션이 유효하지 않으면 로그인 페이지로 이동
                console.log("session not valid : "+ data.testStatus);
                if(window.location.pathname === '/login') { // 현재 페이지가 로그인 페이지면 물어보지 않고 새로고침
                    window.location.href = "/login";
                } else { // 로그인 페이지가 아니면 팝업창 띄워서 로그인 페이지로 이동
                    popupCustBtnReHtmlMsg('인증 만료', '15분 이상 요청이 없어 인증을 종료하였습니다. 다시 로그인해주시기 바랍니다.', 'info', '/login', '확인');
                }
            }
            // 세션이 유효하면 아무것도 하지 않음
        },
        error: function(xhr, textStatus, errorThrown){ // 서버 응답 실패
            console.log("error");
            if(window.location.pathname === '/login') { // 현재 페이지가 로그인 페이지면 물어보지 않고 새로고침
                window.location.href = "/login";
            } else { // 로그인 페이지가 아니면 팝업창 띄워서 로그인 페이지로 이동
                popupCustBtnReHtmlMsg('인증 만료', '15분 이상 요청이 없어 인증을 종료하였습니다. 다시 로그인해주시기 바랍니다.', 'info', '/login', '확인');
            }
        }
    })
}