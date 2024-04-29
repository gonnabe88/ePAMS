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

	private Authentication Authentication() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
	    return authentication;
	}
	
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception{
    	HttpSession session = request.getSession(false);
    	session.invalidate();
    	return "redirect:/login";
    }   
    
    @GetMapping({"/", "/login"})
    public String login(HttpServletResponse response)  throws Exception{    	
    	Authentication auth = Authentication();
    	if(auth != null && auth.isAuthenticated()) return "redirect:/index";
    	return "/common/login";	    	
    }
}
