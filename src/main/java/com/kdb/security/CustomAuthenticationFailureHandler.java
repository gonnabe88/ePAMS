package com.kdb.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(value = "authenticationFailureHandler")
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private String loginidname;
	private String loginpwdname;
	private String errormsgname;
	private String defaultFailureUrl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.warn("AuthenticationException exception : {}", exception.getMessage());
		// TODO Auto-generated method stub
		
	}

	
	
}
