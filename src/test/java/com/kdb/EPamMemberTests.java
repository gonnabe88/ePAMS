package com.kdb;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdb.common.dto.MemberRole;
import com.kdb.common.entity.MemberEntity;
import com.kdb.common.repository.MemberRepository;
import com.kdb.security.CustomPasswordEncoder;

@SpringBootTest
class EPamMemberTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CustomPasswordEncoder passwordEncoder; 

    @Test
    public void RegistOneMember() throws IOException {
    	
    	MemberEntity member = MemberEntity.builder()
            .username("K000000")
            .password(passwordEncoder.encode("kdb1234!"))
            .role(MemberRole.ROLE_KDB)
            .build();
        memberRepository.save(member);
        
    	MemberEntity member2 = MemberEntity.builder()
                .username("K140024")
                .password(passwordEncoder.encode("kdb1234!"))
                .role(MemberRole.ROLE_KDB)
                .build();
        memberRepository.save(member2);
    }

}
