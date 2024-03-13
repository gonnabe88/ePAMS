package com.kdb.service.ajax;

import static org.springframework.http.HttpMethod.POST;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdb.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public AjaxLoginProcessingFilter() {
    	// 작동 조건은 잘~~ 써야 한다!
		super(new AntPathRequestMatcher("/api/login", POST.name()));
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		
		if (!isAjax(request)) {
			throw new IllegalStateException("Authentication is not supported");
		}
		
		MemberDTO memberDTO = objectMapper.readValue(request.getReader(), MemberDTO.class);
		if (!StringUtils.hasText(memberDTO.getUsername())
			|| !StringUtils.hasText(memberDTO.getPassword())) {
			throw new IllegalArgumentException("username or password is empty");
		}
		
		AjaxAuthenticationToken ajaxAuthenticationToken
			= new AjaxAuthenticationToken(memberDTO.getUsername(), memberDTO.getPassword());
		
		return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
	}
	
	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}