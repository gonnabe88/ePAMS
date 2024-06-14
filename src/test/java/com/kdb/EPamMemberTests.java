package com.kdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdb.com.dto.MemberRole;
import com.kdb.com.entity.MemberEntity;
import com.kdb.com.repository.MemberRepository;
import com.kdb.com.security.CustomPasswordEncoder;

@SpringBootTest
class EPamMemberTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CustomPasswordEncoder passwordEncoder; 

    //@Test
    public void RegistOneMember() throws IOException {
    	Random random = new Random();
    	List<String> dept = new ArrayList<>(Arrays.asList(
    			"인사부", 	"디지털금융부", "IT기획부", 	"코어금융부", 	"디지털전략부",
    			"정보호보부", 	"하남지점", 	"여의도지점", 	"총무부", 	"안전관리부"
    			));
    	List<String> team = new ArrayList<>(Arrays.asList(
    			"인사팀", 	"지식정보팀", 	"IT기획팀", 	"여신팀", 	"데이터전략팀",
    			"보안인프라팀", "기업여신1팀", 	"기업여신2팀", 	"기획팀", 	"보안비상팀"
    			));
    	List<String> responsibility = new ArrayList<>(Arrays.asList(
    			"업무지원/통할", 	"업무지원", 	"통할", 				"여신지원시스템", 	"데이터분석",
    			"기업여신", 		"급여", 		"정보보호시스템 운영", 	"단신격지부임", 	"청경관리"
    			));
    	    
        
        for(int i=1; i<=200; i++) { 
        	for(int j=1; j<=25; j++) { 
	        	MemberEntity member3 = MemberEntity.builder()
	                .username("K" + String.format("%02d", j) + String.format("%04d", i))
	                .password(passwordEncoder.encode("kdb1234!"))
	                .dept(dept.get(random.nextInt(10)))
	                .team(team.get(random.nextInt(10)))
	                .responsibility(responsibility.get(random.nextInt(10)))
	                .role(MemberRole.ROLE_KDB)
	                .build();
	            memberRepository.save(member3);
        	}
        }
        
    	MemberEntity member = MemberEntity.builder()
            .username("K000000")
            .password(passwordEncoder.encode("kdb1234!"))
            .dept("디지털금융부")
            .team("지식정보팀")
            .responsibility("인사,근태,연수 등")
            .role(MemberRole.ROLE_KDB)
            .build();
        memberRepository.save(member);
            
    	MemberEntity member2 = MemberEntity.builder()
            .username("K140024")
            .password(passwordEncoder.encode("kdb1234!"))
            .dept("디지털금융부")
            .team("지식정보팀")
            .responsibility("급여,복리후생,채용 등")
            .role(MemberRole.ROLE_ADMIN)
            .build();
        memberRepository.save(member2);
            
    }

}
