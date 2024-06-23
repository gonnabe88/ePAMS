package epams.com.config.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import epams.com.login.repository.LoginRepository;
import epams.com.member.dto.MemberDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component(value = "authenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private final LoginRepository loginRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
	 // 유저 성공 로직을 추가 해준다.

    	log.info("ok");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	
    	MemberDTO memberDTO = new MemberDTO();
    	UUID uuid = UUID.randomUUID();
    	
    	memberDTO.setUsername(authentication.getName());
    	memberDTO.setUUID(uuid.toString());
    	loginRepository.updateUuid(memberDTO);
    	
    	//Cookie cookie = new Cookie("UUIDChk",uuid.toString());
		//cookie.setHttpOnly(true); 
		//cookie.setSecure(true);
		//cookie.setDomain(domain);
		//cookie.setPath("/");
		//cookie.setMaxAge(30*60*1000);
		//response.addCookie(cookie);	
		
    	log.info("[LOG] update uuid {}", memberDTO);
    	
    }

}