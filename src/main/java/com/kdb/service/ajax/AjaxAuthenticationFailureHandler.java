package com.kdb.service.ajax;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
		String errMsg = null;
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		if (exception instanceof BadCredentialsException e) {
			errMsg = "Invalid Username Or Password";
		} else if (exception instanceof InsufficientAuthenticationException e) {
			errMsg = "Locked";
		} else if (exception instanceof CredentialsExpiredException) {
			errMsg = "Expired password";
		}
		
		objectMapper.writeValue(response.getWriter(), errMsg);
		
	}
}
