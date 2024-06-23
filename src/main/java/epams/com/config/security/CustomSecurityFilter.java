package epams.com.config.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import epams.com.member.service.MemberDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 사용자 인증을 처리하는 커스텀 시큐리티 필터
 * @since 2024-06-11
 */
@Slf4j
@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {

    /**
     * @author K140024
     * @implNote 사용자 세부 정보 서비스 주입
     * @since 2024-06-11
     */
    private final MemberDetailsService userDetailsService;

    /**
     * @author K140024
     * @implNote 비밀번호 인코더 주입
     * @since 2024-06-11
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * @author K140024
     * @implNote 내부 필터링 메서드, 인증을 처리하고 요청을 다음 필터로 전달
     * @since 2024-06-11
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {

        final String username = request.getParameter("username");
        final String password = request.getParameter("password");

        if (username != null && password != null && (request.getRequestURI().equals("/authenticate"))) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            log.warn("CustomSecurityFilter : {} {}", username, password);

            // 비밀번호 확인
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new IllegalAccessError("비밀번호가 일치하지 않습니다.");
            }

            // 인증 객체 생성 및 등록
            final SecurityContext context = SecurityContextHolder.createEmptyContext();
            final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request, response);
    }
}
