package com.kdb.service;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;

import com.kdb.dto.MemberDTO;
import com.kdb.repository.LoginRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final EncShaService encshaService;
    private final String OTP = "111111";


    public boolean otpLogin(MemberDTO memberDTO) {
        System.out.println("OTP 검증 : " + memberDTO.getOTP() +" " + OTP);
        if (memberDTO.getOTP().equals(OTP)) {
            System.out.println("TRUE");
            return true;
        } else {
            return false;
        }
    }  
	
    public boolean pwLogin(MemberDTO memberDTO) {

        memberDTO.setPassword(encshaService.encrypt(memberDTO.getPassword()));
        System.out.println(memberDTO.toString());
    	MemberDTO ismemberDTO = loginRepository.login(memberDTO);        
        
        if (ismemberDTO != null) {
            return true;
        } else {
            return false;
        }
    }
    public String idCheck(String userId) {
        MemberDTO memberDTO = loginRepository.findByUserId(userId);
        if (memberDTO == null) {
            return "ok";
        } else {
            return "no";
        }
    }

}