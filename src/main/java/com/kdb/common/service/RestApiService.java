package com.kdb.common.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kdb.common.dto.MemberDTO;
import com.kdb.common.entity.MfaEntity;
import com.kdb.common.repository.MfaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestApiService {
	
    private final MfaRepository mfaRepository;
    
    private int generateOTP(int length) {
    	SecureRandom secureRandom = new SecureRandom();
		int upperLimit = (int) Math.pow(10, length);
		return secureRandom.nextInt(upperLimit);
    }
	
	public Map<String, String> updateUUID(){
		
		Map<String, String> uuid = new HashMap<>();
		uuid.put("username", "K140024");
		uuid.put("uuid", UUID.randomUUID().toString());
		
		return uuid;
	}	
	
	public Map<String, String> requestMFA(MemberDTO memberDTO) throws NoSuchAlgorithmException{
		
		String OTP = String.format("%06d",generateOTP(6));
		log.info("otp : {}", OTP);
		
	
		MfaEntity mfaEntity = MfaEntity.toSaveEntity(memberDTO);
		Map<String, String> uuid = new HashMap<>();
		
		
		mfaEntity.setOTP(OTP);
		log.info("mfaEntity : {}", mfaEntity);
        mfaRepository.save(mfaEntity);		
		
		uuid.put("username", "K140024");
		uuid.put("mfa", "SMS");
		uuid.put("otp", OTP);
		
		return uuid;
	}
	

}
