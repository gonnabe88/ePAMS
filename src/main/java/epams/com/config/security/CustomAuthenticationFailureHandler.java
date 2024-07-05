package epams.com.config.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CustomAuthenticationFailureHandler는 인증 실패 시 실행되는 핸들러입니다.
 */
@Slf4j
@NoArgsConstructor
@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    /**
     * 인증 실패 시 호출되는 메소드입니다.
     * 
     * @param request  현재 HTTP 요청
     * @param response 현재 HTTP 응답
     * @param exception 인증 예외
     */
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        // 인증 실패 시 응답 상태를 500으로 설정합니다.
        response.setStatus(500);
    }    
}
