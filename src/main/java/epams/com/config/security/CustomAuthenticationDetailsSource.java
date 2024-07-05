package epams.com.config.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

/**
 * CustomAuthenticationDetailsSource는 인증 세부 정보를 생성하는 데 사용되는 클래스입니다.
 * HttpServletRequest를 기반으로 CustomWebAuthenticationDetails를 생성합니다.
 */
@NoArgsConstructor
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    /**
     * 주어진 HttpServletRequest에서 CustomWebAuthenticationDetails 인스턴스를 생성합니다.
     * 
     * @param context 현재 HTTP 요청
     * @return 생성된 CustomWebAuthenticationDetails 인스턴스
     */
    @Override
    public WebAuthenticationDetails buildDetails(final HttpServletRequest context) {
        return new CustomWebAuthenticationDetails(context);
    }
}
