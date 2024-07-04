package com.kdb;

import epams.EPamsApplication;
import epams.com.member.dto.IamUserDTO;
import epams.com.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author K140024
 * @implNote 회원을 등록하는 테스트 클래스
 * @since 2024-06-11
 */
@SpringBootTest(classes = EPamsApplication.class)
class IamUserRegistTests {

    /**
     * @author K140024
     * @implNote MemberRepository 의존성 주입
     * @since 2024-06-11
     */
    @Autowired
    private MemberRepository memberRepository;

    /**
     * @author K140024
     * @implNote PasswordEncoder 의존성 주입
     * @since 2024-06-11
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @author K140024
     * @implNote 사용자 정보를 받아올 DTO List
     * @since 2024-06-11
     */
    private final List<IamUserDTO> testUsers = new ArrayList<>();

    /**
     * @author K140024
     * @implNote 테스트 데이터 설정
     * @since 2024-06-11
     */
    @BeforeEach
    public void setUp() {
        if (memberRepository.findAll().isEmpty()) {
            final Random random = new Random();
            final List<String> names = new ArrayList<>(Arrays.asList(
                    "김철수", "이영희", "박민수", "정미영", "강철수",
                    "홍길동", "최영희", "윤민수", "장미영", "임철수",
                    "한길동", "송영희", "조민수", "배미영", "권철수",
                    "황길동", "문영희", "손민수", "백미영", "허철수",
                    "남길동", "심영희", "노민수", "하미영", "곽철수"));
            
            final String tester = "K140024";

            for (int i = 1; i <= 200; i++) {
                for (int j = 1; j <= 25; j++) {
                    final String temp = "K" + String.format("%02d", j) + String.format("%04d", i);
                    final IamUserDTO dto;
                    if (temp.equals(tester)) {
                        dto = IamUserDTO.builder()
                                .username(temp)
                                .realname("박종훈")
                                .password(passwordEncoder.encode("kdb1234!"))
                                .createdDate(LocalDate.now())
                                .build();
                    } else {
                        dto = IamUserDTO.builder()
                                .username(temp)
                                .realname(names.get(random.nextInt(25)))
                                .password(passwordEncoder.encode("kdb1234!"))
                                .createdDate(LocalDate.now())
                                .build();
                    }
                    memberRepository.insert(dto);
                    testUsers.add(dto);
                }
            }
        }
    }

    /**
     * @author K140024
     * @implNote 회원 등록 후 null 여부 테스트
     * @since 2024-06-11
     */
    @Test
    void testUserIsNotNull() {
        for (final IamUserDTO dto : testUsers) {
            final IamUserDTO retrievedDto = memberRepository.findByUsername(dto);
            assertNotNull(retrievedDto, "Inserted user should be found in the repository");
        }
    }

    /**
     * @author K140024
     * @implNote 회원 등록 후 username 매칭 테스트
     * @since 2024-06-11
     */
    @Test
    void testUsernamesMatch() {
        for (final IamUserDTO dto : testUsers) {
            final IamUserDTO retrievedDto = memberRepository.findByUsername(dto);
            assertEquals(dto.getUsername(), retrievedDto.getUsername(), "Usernames should match");
        }
    }

    /**
     * @author K140024
     * @implNote 회원 등록 후 realname 매칭 테스트
     * @since 2024-06-11
     */
    @Test
    void testRealNamesMatch() {
        for (final IamUserDTO dto : testUsers) {
            final IamUserDTO retrievedDto = memberRepository.findByUsername(dto);
            assertEquals(dto.getRealname(), retrievedDto.getRealname(), "Real names should match");
        }
    }

    /**
     * @author K140024
     * @implNote 회원 등록 후 password 매칭 테스트
     * @since 2024-06-11
     */
    @Test
    void testPasswordsMatch() {
        for (final IamUserDTO dto : testUsers) {
            final IamUserDTO retrievedDto = memberRepository.findByUsername(dto);
            assertTrue(passwordEncoder.matches("kdb1234!", retrievedDto.getPassword()), "Passwords should match");
        }
    }
}
