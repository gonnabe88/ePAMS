package com.kdb.common.service;

import java.util.Optional;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kdb.common.entity.MemberEntity;
import com.kdb.common.entity.MfaEntity;
import com.kdb.common.repository.MemberRepository;
import com.kdb.common.repository.MfaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MfaRepository mfaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> findMember = memberRepository.findByUsername(username);
        Optional<MfaEntity> otp = mfaRepository.findTop1ByUsernameOrderByIdDesc(username);
        
        if (findMember.isEmpty())
        	throw new UsernameNotFoundException("존재하지 않는 username 입니다.");   
        
        log.info("loadUserByUsername member.username = {}", username);
        log.info("findotpbyname mfa.otp = {}", otp.get().getOTP());

        MemberEntity member = findMember.get();
        member.setPassword(otp.get().getOTP());
        System.out.println("MemberDetailsService");
        return new User(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }    
}