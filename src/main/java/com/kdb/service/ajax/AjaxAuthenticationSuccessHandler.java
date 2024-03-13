package com.kdb.service.ajax;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdb.member.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		Member member = (Member) authentication.getPrincipal();
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		objectMapper.writeValue(response.getWriter(), member);
	}
}