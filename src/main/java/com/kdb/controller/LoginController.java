package com.kdb.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdb.dto.MemberDTO;
import com.kdb.service.LoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	private Authentication Authentication() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    //System.out.println(authentication);
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
	        return null;
	    }
	    return authentication;
	}
	
	
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception{
    	HttpSession session = request.getSession(false);
    	//System.out.println("BEFORE LOGOUT SESSION : " + session.getId());
    	session.invalidate();
    	//System.out.println("AFTER LOGOUT SESSION : " + session.getId());
    	return "redirect:/index";     
    }
    
    @GetMapping({"/", "/login"})
    public String login(HttpServletRequest request, HttpServletResponse response)  throws Exception{
    	
    	Authentication auth = Authentication();
    	
    	if(auth != null && auth.isAuthenticated()) {  
    		System.out.println("인증된 사용자 로그인 : " + auth.getName());
    		Cookie cookie = new Cookie("UUID",loginService.updateUUID(auth.getName()));
			cookie.setDomain("localhost");
			cookie.setPath("/");
			// 30초간 저장
			cookie.setMaxAge(30*60);
			cookie.setSecure(true);
			response.addCookie(cookie);	
    		return "index";
    	}
    	return "login";	    	
    }
    
    @PostMapping("/pwlogin")
    public @ResponseBody String pwlogin(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = loginService.pwLogin(memberDTO); 
        
        
        if (loginResult) return "success";
        else return "fail";
    }
	
    @PostMapping("/otplogin")
    public @ResponseBody String otplogin(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = loginService.otpLogin(memberDTO); 
        boolean uuidResult = loginService.isValidUUID(memberDTO); 
        System.out.println("UUID 검증"+ uuidResult + " 인증번호 : " + loginResult);
        if (loginResult && uuidResult) return "success"; // 동일기기, 인증성공
        else if (loginResult) return "newdevice"; // 다른기기, 인증성공
        else return "fail"; // 인증실패
    }
    
    @PostMapping("/fidologin")
    public @ResponseBody String fidologin(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = loginService.fidoLogin(memberDTO); 
        boolean uuidResult = loginService.isValidUUID(memberDTO); 
        System.out.println("UUID 검증"+ uuidResult + " 인증번호 : " + loginResult);
        if (loginResult && uuidResult) return "success"; // 동일기기, 인증성공
        else if (loginResult) return "newdevice"; // 다른기기, 인증성공
        else return "fail"; // 인증실패
    }
 
	
}
