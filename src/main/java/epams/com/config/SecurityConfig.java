package epams.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import epams.com.config.security.CustomAuthenticationDetailsSource;
import epams.com.config.security.CustomAuthenticationFailureHandler;
import epams.com.config.security.CustomAuthenticationSuccessHandler;
import epams.com.config.security.CustomGeneralEncryptionException;
import epams.com.config.security.CustomPasswordEncoder;
import epams.com.login.repository.LoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

/**
 * @author K140024
 * @implNote Spring Security 설정
 * @since 2024-06-11
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {


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
     * @throws CustomGeneralEncryptionException 
     * @throws Exception 설정 중 예외 발생 시
     */
    @SuppressWarnings("removal")
    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

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
		                "/webauthn/**",
		                "/h2-console/**",
		                "/actuator/prometheus",
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
		        .accessDeniedPage("/error/403")  // 접근 거부 시 리디렉션 경로
		        .authenticationEntryPoint(
		            (request, response, authException) -> response.sendRedirect("/login"))  // 인증 필요 시 리디렉션 경로
		        .defaultAuthenticationEntryPointFor(
		            (request, response, authException) -> response.sendRedirect("/error/404"),
		            new AntPathRequestMatcher("/**"))  // 기본 인증 엔트리 포인트 설정
		    )

		    // CSRF 설정
		    .csrf((csrf) -> csrf
		        .ignoringRequestMatchers("/h2-console/**")  // H2 콘솔에 대한 CSRF 보호 비활성화
		    )            
		    
		    // X-Frame-Options 설정
		    .headers((headers) -> headers
		        .frameOptions().sameOrigin()  // H2 콘솔이 iframe 내에서 제대로 작동하도록 설정
		    );

        return http.build();
    }
}
