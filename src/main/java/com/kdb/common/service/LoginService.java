package com.kdb.common.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kdb.common.dto.MemberDTO;
import com.kdb.common.entity.MfaEntity;
import com.kdb.common.repository.LoginRepository;
import com.kdb.common.repository.MfaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final MfaRepository mfaRepository;
    private final EncShaService encshaService;
    private UUID uuid = UUID.randomUUID();

    
    public boolean otpLogin(MemberDTO memberDTO) {
    	
    	MemberDTO ismemberDTO = loginRepository.findByUserId(memberDTO.getUsername());
    	Optional<MfaEntity> otp = mfaRepository.findTop1ByUsernameOrderByIdDesc(memberDTO.getUsername());
    	
    	//////////////////////////////////////////////
    	// SMS, 카카오톡 ONEGUARD mOTP 연동 인증부 구현 필요 //
    	//////////////////////////////////////////////
    	
    	if (ismemberDTO != null && memberDTO.getOTP().equals(otp.get().getOTP())) return true;
        else return false;
    }  
    
    public boolean otpLogin(String username, String OTP) {
    	
    	MemberDTO ismemberDTO = loginRepository.findByUserId(username);
    	Optional<MfaEntity> otp = mfaRepository.findTop1ByUsernameOrderByIdDesc(username);
    	
    	//////////////////////////////////////////////
    	// SMS, 카카오톡 ONEGUARD mOTP 연동 인증부 구현 필요 //
    	//////////////////////////////////////////////
    	
    	if (ismemberDTO != null && OTP.equals(otp.get().getOTP())) return true;
        else return false;
    }  
    
    public boolean fidoLogin(MemberDTO memberDTO) {
    	
    	MemberDTO ismemberDTO = loginRepository.findByUserId(memberDTO.getUsername());
    	log.info("[LOG] fidoLogin : ", ismemberDTO);
    	
    	///////////////////////////////////
    	// ONEGUARD FIDO 연동 인증부 구현 필요 //
    	///////////////////////////////////
    	
    	if (ismemberDTO == null ) return false;
    	else return true;
    }  
    
    public boolean fidoLogin(String username) {
    	
    	MemberDTO ismemberDTO = loginRepository.findByUserId(username);
    	log.info("[LOG] fidoLogin : ", ismemberDTO);
    	
    	///////////////////////////////////
    	// ONEGUARD FIDO 연동 인증부 구현 필요 //
    	///////////////////////////////////
    	
    	if (ismemberDTO == null ) return false;
    	else return true;
    }  
	
    public boolean pwLogin(MemberDTO memberDTO) {

        memberDTO.setPassword(encshaService.encrypt(memberDTO.getPassword()));
    	MemberDTO ismemberDTO = loginRepository.login(memberDTO);        
        
        if (ismemberDTO != null)  return true;
        else return true;
    }
    
    public String idCheck(String userId) {
    	
        MemberDTO memberDTO = loginRepository.findByUserId(userId);
        
        if (memberDTO == null) return "ok";
        else return "no";
        
    }
    
    public boolean isValidUUID(String username, String uuid) {

    	String UUID = loginRepository.findUuid(username);   
    	System.out.println("저장된 UUID : " + UUID);  
    	System.out.println("보내온 UUID : " + uuid);  
    	
        if (UUID == null) return true;
        else if (UUID.equals(uuid)) return true;
        else return false;
    }
    
    public boolean isValidUUID(MemberDTO memberDTO) {

    	String UUID = loginRepository.findUuid(memberDTO.getUsername());   
    	System.out.println("저장된 UUID : " + UUID);
    	System.out.println("보내온 UUID : " + memberDTO.getUUID());        
    	
        if (UUID == null) return true;
        else if (UUID.equals(memberDTO.getUUID())) return true;
        else return false;
    }
    
    public String updateUUID(String username) {

    	uuid = UUID.randomUUID();
    	MemberDTO ismemberDTO = loginRepository.findByUserId(username);
    	ismemberDTO.setUUID(uuid.toString());
    	loginRepository.updateUuid(ismemberDTO);        
    	System.out.println("UUID 생성 : " + uuid); 
        return uuid.toString();
    }



}