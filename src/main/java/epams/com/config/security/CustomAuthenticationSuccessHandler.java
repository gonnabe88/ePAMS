package epams.com.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import epams.com.login.repository.LoginRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 인증 성공 시 핸들러 (Custom)
 * @since 2024-06-20
 */
@Slf4j
@RequiredArgsConstructor
@Component("authenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * @author K140024
     * @implNote 로그인 기록 저장소 주입
     * @since 2024-06-11
     */
	private final LoginRepository loginRepository;

	/**
	 * @author K140024
	 * @implNote 인증 성공 시 수행할 내용
	 * @param HttpServletRequest, HttpServletResponse, Authentication
	 * @since 2024-06-20
	 */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	// 유저 성공 로직을 추가 해준다.
    	
    }

}