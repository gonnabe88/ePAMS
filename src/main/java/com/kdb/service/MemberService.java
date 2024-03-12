/*package com.kdb.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdb.config.JWT.JwtTokenProvider;
import com.kdb.dto.MemberDTO;
import com.kdb.dto.TokenInfo;
import com.kdb.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EncShaService encshaService;
    private final String OTP = "111111";

    @Transactional
    public TokenInfo login(String userId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
 
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
 
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
 
        return tokenInfo;
    }
 
    public void register(MemberDTO memberDTO) {
        MemberDTO registMember = new MemberDTO();
        registMember.setUserId(memberDTO.getUserId());
        registMember.setMemberName(memberDTO.getMemberName());
        registMember.setMemberPassword(encshaService.encrypt(memberDTO.getMemberPassword()));
        memberRepository.registMember(registMember);
    }

	public boolean login(MemberDTO memberDTO) {
		MemberDTO loginMember = memberRepository.login(memberDTO);
		if (loginMember != null) {
			return true;
		} else {
			return false;
		}
	}

    public boolean otpcheck(MemberDTO memberDTO) {
        System.out.println("OTP 검증 : " + memberDTO.getOTP() +" " + OTP);
        if (memberDTO.getOTP().equals(OTP)) {
            System.out.println("TRUE");
            return true;
        } else {
            return false;
        }
    }  
	
    public boolean pwLogin(MemberDTO memberDTO) {

        memberDTO.setMemberPassword(encshaService.encrypt(memberDTO.getMemberPassword()));
        System.out.println(memberDTO.toString());
    	MemberDTO ismemberDTO = memberRepository.pwLogin(memberDTO);        
        
        if (ismemberDTO != null) {
            return true;
        } else {
            return false;
        }
    }
    public String idCheck(String userId) {
        MemberDTO memberDTO = memberRepository.findByUserId(userId);
        if (memberDTO == null) {
            return "ok";
        } else {
            return "no";
        }
    }

}
  */  