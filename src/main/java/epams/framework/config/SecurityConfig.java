package epams.framework.config;

import epams.domain.com.login.repository.LoginRepository;
import epams.framework.exception.CustomGeneralException;
import epams.framework.security.CustomAuthenticationDetailsSource;
import epams.framework.security.CustomAuthenticationFailureHandler;
import epams.framework.security.CustomAuthenticationSuccessHandler;
import epams.framework.security.CustomPasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author K140024
 * @implNote Spring Security 설정
 * @since 2024-06-11
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig{

	@Value("${authn.origin}")
	private String cookieDomain; // 주입 받을 값

	/**
	 * @author K140024
	 * @implNote 의존성 주입: 로그인 리포지토리
	 * @since 2024-06-11
	 */
	private final LoginRepository loginRepository;

	/**
	 * 사용자 인증 정보 소스 빈 생성
	 * 
	 * @return CustomAuthenticationDetailsSource 인스턴스
	 */
	@Bean
	AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource() {
		return new CustomAuthenticationDetailsSource();
	}

	/**
	 * 인증 성공 핸들러 빈 생성
	 * 
	 * @return CustomAuthenticationSuccessHandler 인스턴스
	 */
	@Bean
	AuthenticationSuccessHandler successHandler() {
		return new CustomAuthenticationSuccessHandler(loginRepository);
	}

	/**
	 * 인증 실패 핸들러 빈 생성
	 * 
	 * @return CustomAuthenticationFailureHandler 인스턴스
	 */
	@Bean
	AuthenticationFailureHandler failureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	/**
	 * 비밀번호 인코더 빈 생성
	 * 
	 * @return CustomPasswordEncoder 인스턴스
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}

    /**
     * 보안 필터 체인 설정
     * @param http HttpSecurity 인스턴스
     * @return SecurityFilterChain 인스턴스
     * @throws CustomGeneralException
     * @throws Exception 설정 중 예외 발생 시
     */
    @SuppressWarnings("removal")
    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		// Content-Security-Policy 설정에 application.yml의 cookieDomain 값 사용
		final String cspPolicy = String.format(
				"default-src 'self'; " + // 기본 출처를 동일 출처로 제한
				"font-src 'self' data:;" + // 폰트는 동일 출처와 data:만 허용
				"img-src 'self' data:; " + // 이미지는 동일 출처와 data:만 허용
				"style-src 'self' 'nonce-kdb'; " + // 스타일은 동일 출처와 nonce-kdb만 허용
				"script-src 'self' %s; " + // 스크립트는 동일 출처와 nonce-kdb만 허용
				"object-src 'none'; " + // 'none'으로 설정하여 객체 삽입을 차단
				"connect-src 'self'; " + // 연결은 동일 출처만 허용
				"frame-ancestors 'self'; " + // 프레임 조상은 동일 출처만 허용
				"form-action 'self';", // 폼 작업은 동일 출처만 허용
				cookieDomain
		);

		http
		    // 세션 관리 설정
		    .sessionManagement((auth) -> auth
		        .sessionFixation().changeSessionId() // 세션 고정 보호를 위해 세션 ID를 변경
		        .maximumSessions(1) // 한 계정당 최대 세션 수를 1로 제한
		        .maxSessionsPreventsLogin(false)  // 세션 초과 시 새로운 세션을 허용
		    )

		    // URL별 접근 권한 설정
		    .authorizeHttpRequests((authorizeRequests) ->
		        authorizeRequests
		            .requestMatchers(
		                "/h2-console/**",
		                "/actuator/prometheus", "/metrics/**",
		                "/manifest.webmanifest", "/login/**", "/logout", "/register",
		                "/api/**", 
		                "/css/**",
		                "/js/**",
		                "/extensions/**",
		                "/images/**",
		                "/error/**"
		            ).permitAll()  // 특정 URL은 모든 사용자에게 허용
		            .requestMatchers("/admin/**").hasRole("ADMIN")  // ADMIN 역할만 접근 허용
		            .anyRequest().authenticated()  // 나머지 요청은 인증 필요
		    )


		    // 폼 로그인 설정
		    .formLogin((formLogin) ->
		        formLogin
		            .loginPage("/login")  // 로그인 페이지 경로
		            .loginProcessingUrl("/authenticate")  // 로그인 처리 경로
		            .authenticationDetailsSource(authenticationDetailsSource())
		            .usernameParameter("username")  // 사용자 이름 파라미터
		            .passwordParameter("password")  // 비밀번호 파라미터
		            .defaultSuccessUrl("/index", true)  // 로그인 성공 시 리디렉션 경로
		            .successHandler(successHandler())  // 인증 성공 핸들러
		            .failureHandler(failureHandler())  // 인증 실패 핸들러
		    )

		    // 로그아웃 설정
		    .logout((logout) -> logout
		        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // 로그아웃 처리 경로
		        .logoutSuccessUrl("/login")  // 로그아웃 성공 시 리디렉션 경로
		        .invalidateHttpSession(true)  // 세션 무효화
		        .deleteCookies("JSESSIONID")  // 쿠키 삭제
		    )

		    // 예외 처리 설정
		    .exceptionHandling((exceptionHandling) -> exceptionHandling
				.accessDeniedHandler(
					(request, response, accessDeniedException) -> response.sendRedirect("/error/403")) // 접근 거부 시 리디렉션 경로 설정
		        .authenticationEntryPoint(
		            (request, response, authException) -> response.sendRedirect("/login"))  // 인증 필요 시 리디렉션 경로
		        .defaultAuthenticationEntryPointFor(
		            (request, response, authException) -> response.sendRedirect("/error/404"),
		            new AntPathRequestMatcher("/**"))  // 기본 인증 엔트리 포인트 설정
		    )

			// X-Frame-Options 설정
			.headers((headers) -> headers
					.frameOptions().sameOrigin()
					.contentSecurityPolicy(cspPolicy)
			);

        return http.build();
    }
}
