package com.kdb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kdb.common.service.EncShaService;
import com.kdb.common.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final EncShaService encshaService;
	private final LoginService loginService;

	@Autowired
	private UserDetailsService userDetailsService;

	@SuppressWarnings("serial")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();;
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		String UUID = customWebAuthenticationDetails.getUUID();
		String OTP = customWebAuthenticationDetails.getOTP();
		String MFA = customWebAuthenticationDetails.getMFA();		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		boolean uuidResult = loginService.isValidUUID(username, UUID); 
		boolean loginResult = false;
		
		switch(MFA) {
			case "SMS":
				loginResult = loginService.otpLogin(username, OTP);
				break;
			case "카카오톡":
				loginResult = loginService.otpLogin(username, OTP);
				break;				
			case "OTP":
				loginResult = loginService.otpLogin(username, OTP);
				break;				
			case "FIDO":
				loginResult = loginService.fidoLogin(username);
				break;
		}
		
		if (userDetails == null) 
			throw new UsernameNotFoundException("User not found");		

		if (!uuidResult && !(encshaService.encrypt(password)).equals(userDetails.getPassword())) 
			throw new AuthenticationException("Invalid credentials") {};
		
		if (!loginResult) 
			throw new AuthenticationException("Invalid MFA credentials") {}; 
		
		// Create a fully authenticated Authentication object
		Authentication authenticated = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());

		return authenticated;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// Return true if this AuthenticationProvider supports the provided authentication class
		log.info("[LOG] CustomAuthenticationProvider3");
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}