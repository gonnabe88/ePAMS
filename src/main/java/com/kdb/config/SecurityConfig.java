package com.kdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kdb.service.ajax.AjaxLoginProcessingFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/*
	@Bean
	public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
		AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
		ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        // 2줄 추가!
        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
		ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
		return ajaxLoginProcessingFilter;
	}
	*/
    /*@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*///
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests                                
                    .requestMatchers("/", "/login", 
                    "/registry", "/register", // 사용자 등록용
                    "/css/**",
                    "/js/**",
                    "/extensions/**",
                    "/fonts/**",
                    "/images/**",
                    "/svg/**", 
                    "/forbidden").permitAll() // 모두 접속 허용
                    
                    .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // 매니저, 관리자 접근 가능
                    .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                    .anyRequest().authenticated() // 그외 모두 일반사용자 접근 가능
                    
                   // .exceptionHandling()
                   // .accessDeniedPage("/forbidden")
            )
            //.addFilterBefore(AjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            
            .formLogin((formLogin) ->
                formLogin
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/",true)
                    
            )

            .logout((logout) -> 
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    
            )
        ;
        return http.build();
    }
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
                                .loginPage("/login") // 1번
                                .usernameParameter("userId") // 2번
                                .passwordParameter("memberPassword") // 3번
                                .defaultSuccessUrl("/",true)
                                .successHandler(successHandler())
                                //.failureHandler(failureHandler())
                                //.loginProcessingUrl("/pwlogin") // 4번
                                //.permitAll() // 5번
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
        return new CustomLoginSuccessHandler("/");//default로 이동할 url
    }
}
 */