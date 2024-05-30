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
    
    
	//SecureRandom 함수를 통해 안전하게 6자리 인증번호 생성
    private int generateOTP(int length) {
    	SecureRandom secureRandom = new SecureRandom();
		int upperLimit = (int) Math.pow(10, length);
		return secureRandom.nextInt(upperLimit);
    }
	
	public Map<String, String> requestMFA(MemberDTO memberDTO) throws NoSuchAlgorithmException{		
		
		//사용자가 보내온 UUID가 DB에 저장된 UUID(최근 접속 시 사용된)와 동일한지 확인(동일 시 TRUE)  
		boolean isUUIDValid = loginService.isValidUUID(memberDTO);

		//SMS, 카카오톡 인증 시 필요한 인증번호 Random 숫자 6자리를 생성
		String OTP = String.format("%06d",generateOTP(6));
		
		//로그인 사용자 정보에 해당하는 Entity 생성하여 DB에 저장(이 데이터는 최근 사용자가 시도한 MFA 정보를 의미)
		MfaEntity mfaEntity = MfaEntity.toSaveEntity(memberDTO);
		mfaEntity.setOTP(OTP);
        mfaRepository.save(mfaEntity);
		
        //Key-Value 형태로 리턴하고 있지만 실제로 중요한 정보는 UUID가 일치하는지임
        //그냥 mfaEntity 자체 또는 UUID 문자열을 리턴해도 될 것 같은데 왜 이렇게 했는지 모르겠음(나중에 수정해야지)
        //사용자 화면에 UUID를 보내주면 login.js에서 UUID가 거짓인 경우 추가인증(ESSO Password)까지 받아서 Authentication(인증) 요청을 보냄
        //만약 임의로 수정해서 추가인증(ESSO Password)을 수행하지 않는 경우 Login Fail
        Map<String, String> mfaInfo = new HashMap<>();
        mfaInfo.put("username", memberDTO.getUsername());
        mfaInfo.put("MFA", memberDTO.getMFA());
        mfaInfo.put("OTP", OTP);
        mfaInfo.put("UUID", String.valueOf(isUUIDValid));
		
        log.info("otp : {}", OTP);
		return mfaInfo;
	}
}
