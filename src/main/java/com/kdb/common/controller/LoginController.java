package com.kdb.common.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdb.common.dto.MemberDTO;
import com.kdb.common.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	private Authentication Authentication() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
	    return authentication;
	}
	
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception{
    	HttpSession session = request.getSession(false);
    	session.invalidate();
    	return "redirect:/common/login";     
    }   
    
    @GetMapping({"/", "/login"})
    public String login(@CookieValue(value="UUIDChk", required=false) String UUID, HttpServletResponse response)  throws Exception{    	
    	Authentication auth = Authentication();
    	if(auth != null && auth.isAuthenticated()) return "/common/index";
    	return "/common/login";	    	
    }
    
    @PostMapping("/pwlogin")
    public @ResponseBody String pwlogin(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = loginService.pwLogin(memberDTO);                 
        if (loginResult) return "success";
        else return "fail";
    }
	
    @PostMapping("/otplogin")
    public @ResponseBody String otplogin(@ModelAttribute MemberDTO memberDTO, @CookieValue(value="UUIDChk", required=false) String UUID, HttpServletResponse response) {    	
    	memberDTO.setUUID(UUID);
        boolean loginResult = loginService.otpLogin(memberDTO); 
        boolean uuidResult = loginService.isValidUUID(memberDTO); 
        if (loginResult && uuidResult) return "success"; // 동일기기, 인증성공
        else if (loginResult) return "newdevice"; // 다른기기, 인증성공
        else return "fail"; // 인증실패
    }
    
    @PostMapping("/fidologin")
    public @ResponseBody String fidologin(@ModelAttribute MemberDTO memberDTO, @CookieValue(value="UUIDChk", required=false) String UUID, HttpServletResponse response) {
    	memberDTO.setUUID(UUID);    	
    	boolean loginResult = loginService.fidoLogin(memberDTO); 
        boolean uuidResult = loginService.isValidUUID(memberDTO); 
        log.info("[LOG] fidologin controller : ", memberDTO);
        if (loginResult && uuidResult) return "success"; // 동일기기, 인증성공
        else if (loginResult) return "newdevice"; // 다른기기, 인증성공
        else return "fail"; // 인증실패
    } 	
}
