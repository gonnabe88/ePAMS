 package com.kdb.common.config;


//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.kdb.common.repository.LoginRepository;
import com.kdb.common.service.MemberDetailsService;
import com.kdb.security.CustomAuthenticationDetailsSource;
import com.kdb.security.CustomAuthenticationFailureHandler;
import com.kdb.security.CustomAuthenticationSuccessHandler;
import com.kdb.security.CustomPasswordEncoder;
import com.kdb.security.CustomSecurityFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {		
	
	private final MemberDetailsService memberDetailsService;
	private final LoginRepository loginRepository;
	
	@Bean
	public AuthenticationDetailsSource authenticationDetailsSource() {
		return new CustomAuthenticationDetailsSource();
	}
	   
	@Bean
    public AuthenticationSuccessHandler SuccessHandler() {
        return new CustomAuthenticationSuccessHandler(loginRepository);
    }
	
	@Bean
    public AuthenticationFailureHandler FailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }
    
    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        http
        	.addFilterBefore(new CustomSecurityFilter(memberDetailsService, new CustomPasswordEncoder()), AuthorizationFilter.class)
        	
        	.sessionManagement((auth) -> auth
        			.sessionFixation().changeSessionId()
        			.maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    )
        	
        	// CSRF 토큰 검증을 비활성화하는 코드 테스트 시 필요하다면 풀어도 됨
            // 단, 푸는 경우 html, javascript에 있는 토큰 검증 코드 모두 제외 필요
            //.csrf(CsrfConfigurer::disable)   
        
            .authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests                
                    .requestMatchers(
            		/*"/index", "index2", "list", "save",*/
                    "/webauthn/**",
                    "/actuator/prometheus",
            		"/manifest.webmanifest", "/login", "/logout", "/register",
            		"/api/**", 
                    "/css/**",
                    "/js/**",
                    "/extensions/**",
                    "/fonts/**",
                    "/images/**",
                    "/svg/**", 
                    "/forbidden",
                    "/admin/**", // Spring Boot Admin UI
                    "/actuator/**" // Actuator endpoints
                    ).permitAll() // 위 URI에 매칭되는 주소는 인증을 하지 않아도 허용
                    
                    .requestMatchers("/manager/**").hasAnyRole("KDB", "ADMIN") // ADMIN, KDB 복수 허용 예시
                    .requestMatchers("/admin2/**").hasRole("ADMIN") // ADMIN 단독 허용 예시
                    .anyRequest().authenticated() // 그 외 전체 허용
                    
                    
                   // .exceptionHandling()
                   // .accessDeniedPage("/forbidden")
                    
            )
            
            .formLogin((formLogin) ->
                formLogin
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .authenticationDetailsSource(authenticationDetailsSource())
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/index", true)     
                    .successHandler(SuccessHandler())
                    .failureHandler(FailureHandler())
            )
            
            .logout((logout) -> logout.logoutSuccessUrl("/login"))

            
            .exceptionHandling((exceptionHandling) ->
            	exceptionHandling
                    .accessDeniedPage("/common/error")
			);
		/*
		 * .logout((logout) -> logout .logoutUrl("/logout") .logoutSuccessUrl("/login")
		 * .invalidateHttpSession(true) .deleteCookies("JSESSIONID")
		 * 
		 * )
		 */  

        
        return http.build();
        
    }
    
    
	/*
	 * @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration
	 * authenticationConfiguration) throws Exception { return
	 * authenticationConfiguration.getAuthenticationManager(); }
	 */
}


/*
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                
                .csrf(CsrfConfigurer::disable)
                //.httpBasic(HttpBasicConfigurer::disable)
                //.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests                                
                                .requestMatchers(
                                    "/pwlogin/**", 
                                    "/auth-register/**",
                                    "/auth_register/**",                               
                                    "/login/**",
                                    "/id-check/**",
                                    "/otplogin/**",
                                    "/css/**",
                                    "/js/**",
                                    "/extensions/**",
                                    "/fonts/**",
                                    "/images/**",
                                    "/svg/**"
                                    ).permitAll()
                                .anyRequest().authenticated()
                            
                                //.anyRequest().permitAll()
                )
                
                //.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login") // 1踰�
                                .usernameParameter("userId") // 2踰�
                                .passwordParameter("memberPassword") // 3踰�
                                .defaultSuccessUrl("/",true)
                                .successHandler(successHandler())
                                //.failureHandler(failureHandler())
                                //.loginProcessingUrl("/pwlogin") // 4踰�
                                //.permitAll() // 5踰�
                )

                .logout((logout) -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true))
                ;
                
            

        return http.build();
    }
 // JWT
    @Bean
    PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/");//default濡� �씠�룞�븷 url
    }
}
 */