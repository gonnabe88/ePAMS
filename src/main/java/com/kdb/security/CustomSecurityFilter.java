package com.kdb.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kdb.common.service.MemberDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {

    private final MemberDetailsService userDetailsService;
    private final CustomPasswordEncoder passwordEncoder;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //System.out.println("username = " + username);
        //System.out.println("password = " + password);
        //System.out.println("request.getRequestURI() = " + request.getRequestURI());
        //log.info("CustomSecurityFilter : {} {} {}", username, password, request.getRequestURI());


        if(username != null && password  != null && (request.getRequestURI().equals("/authenticate"))){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            log.warn("CustomSecurityFilter : {} {}", username, password);
            
            // 비밀번호 확인
            if(!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new IllegalAccessError("비밀번호가 일치하지 않습니다.");
            }

            // 인증 객체 생성 및 등록
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request, response);
    }
}