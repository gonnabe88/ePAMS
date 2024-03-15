package com.kdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        http
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests   /*             
                    .requestMatchers(
            		"/index", "index2",//
            		"/", "/login", "/registry", 
            		"/pwlogin", "/otplogin",
                    "/css/**",
                    "/js/**",
                    "/extensions/**",
                    "/fonts/**",
                    "/images/**",
                    "/svg/**", 
                    "/forbidden").permitAll() // 紐⑤몢 �젒�냽 �뿀�슜
                    
                    .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // 留ㅻ땲��, 愿�由ъ옄 �젒洹� 媛��뒫
                    .requestMatchers("/admin/**").hasRole("ADMIN") // 愿�由ъ옄留� �젒洹� 媛��뒫
                    .anyRequest().authenticated() // 洹몄쇅 紐⑤몢 �씪諛섏궗�슜�옄 �젒洹� 媛��뒫
                    */
                   // .exceptionHandling()
                   // .accessDeniedPage("/forbidden")
                    
                    .anyRequest() .permitAll()
            )
            /*
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
            */
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