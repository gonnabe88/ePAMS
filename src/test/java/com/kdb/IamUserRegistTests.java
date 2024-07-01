package com.kdb;

import epams.EPamsApplication;
import epams.com.member.dto.IamUserDTO;
import epams.com.member.entity.IamUserEntity;
import epams.com.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = EPamsApplication.class)
class IamUserRegistTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @author K140024
     * @implNote 회원을 등록하는 테스트 메서드
     * @since 2024-06-11
     */
    @Test
    public void Register() throws IOException {

        // 만약 레코드가 1개라도 존재한다면 테스트를 수행하지 않음
        if (memberRepository.findAll().size() > 0) {
            return;
        }
        final Random random = new Random();
        final List<String> names = new ArrayList<>(Arrays.asList(
                "김철수", "이영희", "박민수", "정미영", "강철수",
                "홍길동", "최영희", "윤민수", "장미영", "임철수",
                "한길동", "송영희", "조민수", "배미영", "권철수",
                "황길동", "문영희", "손민수", "백미영", "허철수",
                "남길동", "심영희", "노민수", "하미영", "곽철수"));
        String temp = "K140024";

        for (int i = 1; i <= 200; i++) {
            for (int j = 1; j <= 25; j++) {
                temp = "K" + String.format("%02d", j) + String.format("%04d", i);
                if (temp == "K140024") {
                    final IamUserDTO dto = IamUserDTO.builder()
                            .username(temp)
                            .realname("박종훈")
                            .password(passwordEncoder.encode("kdb1234!"))
                            .createdDate(LocalDate.now())
                            .build();
                    memberRepository.insert(dto);
                } else {
                    final IamUserDTO dto = IamUserDTO.builder()
                            .username(temp)
                            .realname(names.get(random.nextInt(25)))
                            .password(passwordEncoder.encode("kdb1234!"))
                            .createdDate(LocalDate.now())
                            .build();
                    memberRepository.insert(dto);
                }
            }
        }
    }
}
