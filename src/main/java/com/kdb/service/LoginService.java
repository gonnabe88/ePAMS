package com.kdb.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kdb.dto.MemberDTO;
import com.kdb.repository.LoginRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final EncShaService encshaService;
    private final String OTP = "111111"; // 임시 OTP 검증용
    private final UUID uuid = UUID.randomUUID();

    public boolean otpLogin(MemberDTO memberDTO) {
        if (memberDTO.getOTP().equals(OTP)) return true;
        else return false;
    }  
	
    public boolean pwLogin(MemberDTO memberDTO) {

        memberDTO.setPassword(encshaService.encrypt(memberDTO.getPassword()));
    	MemberDTO ismemberDTO = loginRepository.login(memberDTO);        
        
        if (ismemberDTO != null)  return true;
        else return false;
    }
    
    public String idCheck(String userId) {
    	
        MemberDTO memberDTO = loginRepository.findByUserId(userId);
        
        if (memberDTO == null) return "ok";
        else return "no";
        
    }
    
    public boolean isValidUUID(MemberDTO memberDTO) {

    	String UUID = loginRepository.findUuid(memberDTO.getUsername());   
    	System.out.println("저장된 UUID : " + UUID);
    	System.out.println("보내온 UUID : " + memberDTO.getUUID());        
        if (UUID == null || UUID == memberDTO.getUUID())  return true;
        else return false;
    }
    
    public String updateUUID(String username) {

    	MemberDTO ismemberDTO = loginRepository.findByUserId(username);
    	ismemberDTO.setUUID(uuid.toString());
    	loginRepository.updateUuid(ismemberDTO);        
    	System.out.println("UUID 생성 : " + uuid); 
        return uuid.toString();
    }

}