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


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();;

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		String UUID = customWebAuthenticationDetails.getUUID();
		String OTP = customWebAuthenticationDetails.getOTP();
		String MFA = customWebAuthenticationDetails.getMFA();

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
		

		log.info("[LOG] CustomAuthenticationProvider UUID {}", customWebAuthenticationDetails.getUUID());
		// Perform your custom authentication logic here
		// Retrieve user details from userDetailsService and validate the credentials
		// You can throw AuthenticationException if authentication fails
		// Example: retrieving user details by username from UserDetailsService

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new UsernameNotFoundException("User not found");
		}

		if (!uuidResult) {
			log.warn("[인증] UUID 다름 & 패스워드 인증 실패");
			if (!(encshaService.encrypt(password)).equals(userDetails.getPassword())) {
					log.warn("[최종인증] UUID 다름 & 패스워드 인증 실패");
					throw new AuthenticationException("Invalid credentials") {}; 
			}
		}
		
		if (!loginResult) {
				log.warn("[최종인증] MFA 인증 실패");
				throw new AuthenticationException("Invalid MFA credentials") {}; 
		}
		
		
		
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