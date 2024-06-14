package com.kdb.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kdb.com.dto.LogLoginDTO;
import com.kdb.com.repository.LogRepository;
import com.kdb.com.service.EncShaService;
import com.kdb.com.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final EncShaService encshaService;
	private final LoginService loginService;
	private final LogRepository logRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@SuppressWarnings("serial")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();;
		String username = authentication.getName();
		String password = null;
		try {
			// 입력받은 password HASH
			password = encshaService.encrypt(authentication.getCredentials().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String UUID = customWebAuthenticationDetails.getUUID();
		String OTP = customWebAuthenticationDetails.getOTP();
		String MFA = customWebAuthenticationDetails.getMFA();		
		
		// DB에 저장된 사용자 정보(패스워드 등) 가져옴
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
		
		// 사용자가 없는 경우
		if (userDetails == null) {
			logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, "Unknown User", false));
			throw new UsernameNotFoundException("User not found");
		}
					

		//패스워드가 일지하지 않는 경우
		log.warn("입력 패스워드(hash) : "+ password);
		log.warn("저장된 패스워드 : "+ userDetails.getPassword());
		if (!password.equals(userDetails.getPassword())) {
			log.warn("패스워드 불일치");
			logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, "패스워드", false));
			//임시로 테스트를 위해 패스워드 인증 예외처리
			//throw new AuthenticationException("Invalid credentials") {};
		}			
		
		// 2차 인증(SMS,카카오,OTP,FIDO) 실패
		if (!loginResult) {
			log.warn("2차 인증 실패");
			logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, MFA, false));
			throw new AuthenticationException("Invalid MFA credentials") {};
		}			 
		
		// Create a fully authenticated Authentication object
		Authentication authenticated = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());
		
		logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, MFA, true));
		return authenticated;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// Return true if this AuthenticationProvider supports the provided authentication class
		log.info("[LOG] CustomAuthenticationProvider > support");
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}