package com.kdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdb.dto.MemberDTO;
import com.kdb.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
    @PostMapping("/pwlogin")
    public @ResponseBody String pwlogin(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = loginService.pwLogin(memberDTO); 
        if (loginResult) {        	
        	System.out.println("PW login success!!");
            return "success";
        } else {
        	System.out.println("PW login fail!!");
            return "fail";
        }
    }
	
    @PostMapping("/otplogin")
    public @ResponseBody String otplogin(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = loginService.otpLogin(memberDTO); 
        if (loginResult) {        	
        	System.out.println("OTP login success!!");
            return "success";
        } else {
        	System.out.println("OTP login fail!!");
            return "fail";
        }
    }
	
}
