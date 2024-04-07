package com.kdb.common.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

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
    private final LoginService loginService;
    
	public Map<String, String> searchMember(String text) throws NoSuchAlgorithmException{
		
		
		Map<String, String> memberInfo = new HashMap<>();
		Map<String, String> membersInfo = new HashMap<>();
		
		memberInfo.put("username", "K140024");
		memberInfo.put("contact", "02-787-0550");
		memberInfo.put("dept", "디지털금융부");
		memberInfo.put("team", "지식정보팀");
		memberInfo.put("responsibility", "급여, 복리후생, 채용");
		
		return memberInfo;
	}
    
    private int generateOTP(int length) {
    	SecureRandom secureRandom = new SecureRandom();
		int upperLimit = (int) Math.pow(10, length);
		return secureRandom.nextInt(upperLimit);
    }
	
	public Map<String, String> requestMFA(MemberDTO memberDTO) throws NoSuchAlgorithmException{
		
		boolean isUUIDValid = loginService.isValidUUID(memberDTO);
		String OTP = String.format("%06d",generateOTP(6));	
		MfaEntity mfaEntity = MfaEntity.toSaveEntity(memberDTO);
		Map<String, String> mfaInfo = new HashMap<>();

		log.info("otp : {}", OTP);
		
		mfaEntity.setOTP(OTP);
        mfaRepository.save(mfaEntity);
		
        mfaInfo.put("username", memberDTO.getUsername());
        mfaInfo.put("MFA", memberDTO.getMFA());
        mfaInfo.put("OTP", OTP);
        mfaInfo.put("UUID", String.valueOf(isUUIDValid));
		
		return mfaInfo;
	}
}
