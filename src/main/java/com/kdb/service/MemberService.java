package com.kdb.service;

import org.springframework.stereotype.Service;

import com.kdb.dto.MemberDTO;
import com.kdb.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

	public String login(MemberDTO memberDTO) {
		MemberDTO loginMember = memberRepository.login(memberDTO);
		if (loginMember != null) {
			return "1";
		} else {
			return "2";
		}
	}
	
    public String auth(MemberDTO memberDTO) {
    	MemberDTO ismemberDTO = memberRepository.auth(memberDTO);
        if (ismemberDTO != null) {
            return "ok";
        } else {
            return "no";
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
