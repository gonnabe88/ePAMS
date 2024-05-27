package com.kdb.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kdb.common.entity.MemberEntity;
import com.kdb.common.entity.MfaEntity;
import com.kdb.common.entity.SearchMemberEntity;
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

    public void saveMembers(List<MemberEntity> added, List<MemberEntity> changed, List<MemberEntity> deleted) {

        // Handle added members
        for (MemberEntity newMember : added) {
        	log.warn("add : "+newMember.getUsername());
            memberRepository.save(newMember);
        }

        // Handle changed members
        for (MemberEntity changedMember : changed) {
        	log.warn("edit : "+changedMember.getUsername());
            Optional<MemberEntity> memberOpt = memberRepository.findByUsername(changedMember.getUsername());
            memberOpt.ifPresent(member -> {
                member.setUsername(changedMember.getUsername());
                member.setDept(changedMember.getDept());
                member.setResponsibility(changedMember.getResponsibility());
                member.setRole(changedMember.getRole());
                member.setTeam(changedMember.getTeam());
                memberRepository.save(member);
            });
        }

        // Handle deleted members
        for (MemberEntity deletedMember : deleted) {
        	log.warn("del : "+deletedMember.getUsername());
            Optional<MemberEntity> memberOpt = memberRepository.findByUsername(deletedMember.getUsername());
            memberOpt.ifPresent(memberRepository::delete);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> findMember = memberRepository.findByUsername(username);
        Optional<MfaEntity> mfa = mfaRepository.findTop1ByUsernameOrderByIdDesc(username);
        
        if (findMember.isEmpty())
        	throw new UsernameNotFoundException("존재하지 않는 username 입니다."+username);  
        //else if (mfa.get().getMFA().equals("FIDO"))
        //	member.setPassword("FIDO");
        //else
        //	member.setPassword(mfa.get().getOTP());
        MemberEntity member = findMember.get();
        
 //       if(log.isInfoEnabled())
 //       	log.info("findotpbyname password = {} otp = {} mfa = {}", member.getPassword(), mfa.get().getOTP(), mfa.get().getMFA());        
        
        return new User(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }    
    
    public List<SearchMemberEntity> findBySearchValue(String searchValue)
    {
    	return memberRepository.findBySearchValue(searchValue);
    }
    
    public List<MemberEntity> findAll()
    {
    	return memberRepository.findAll();
    }
}