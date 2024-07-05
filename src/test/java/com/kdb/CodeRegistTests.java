package com.kdb;

import epams.EPamsApplication;
import epams.com.admin.dto.CodeDTO;
import epams.com.admin.entity.CodeEntity;
import epams.com.admin.repository.CodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * EPamsApplication 클래스의 테스트 케이스
 */
@SpringBootTest(classes = EPamsApplication.class)
class CodeRegistTests {

    /**
     * CodeRepository 인스턴스
     */
    private final CodeRepository codeRepository;

    /**
     * 생성자
     *
     * @param codeRepository CodeRepository 인스턴스
     */
    @Autowired
    public CodeRegistTests(final CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    /**
     * CodeEntity를 데이터베이스에 삽입하는 테스트 메소드
     *
     * @throws IOException 입출력 예외 발생 시
     */
    @Test
    void testCodeInsertCode1() throws IOException {
        final CodeEntity code1 = CodeEntity.builder()
                .CDVA_ID("COM_INDEX_CD")
                .CDVA_NM("빠른 신청")
                .CDVA_KD_NM("text")
                .build();
        codeRepository.insert(CodeDTO.toDTO(code1));
        final Optional<CodeEntity> retrievedCode1 = codeRepository.findById(code1.getCDVA_ID());
        assertTrue(retrievedCode1.isPresent(), "Code1 삽입 실패");
    }

    @Test
    void testCodeNameCode1() {
        final Optional<CodeEntity> retrievedCode1 = codeRepository.findById("COM_INDEX_CD");
        assertEquals("빠른 신청", retrievedCode1.get().getCDVA_NM(), "Code1 이름 불일치");
    }

    @Test
    void testCodeInsertCode2() throws IOException {
        final CodeEntity code2 = CodeEntity.builder()
                .CDVA_ID("COM_BANNER1_CD")
                .CDVA_NM("모바일 근태 시스템")
                .CDVA_KD_NM("text")
                .build();
        codeRepository.insert(CodeDTO.toDTO(code2));
        final Optional<CodeEntity> retrievedCode2 = codeRepository.findById(code2.getCDVA_ID());
        assertTrue(retrievedCode2.isPresent(), "Code2 삽입 실패");
    }

    @Test
    void testCodeNameCode2() {
        final Optional<CodeEntity> retrievedCode2 = codeRepository.findById("COM_BANNER1_CD");
        assertEquals("모바일 근태 시스템", retrievedCode2.get().getCDVA_NM(), "Code2 이름 불일치");
    }

    @Test
    void testCodeInsertCode3() throws IOException {
        final CodeEntity code3 = CodeEntity.builder()
                .CDVA_ID("COM_BANNERCONTENT1_CD")
                .CDVA_NM("ePAMS에 오신걸<br>진심으로 환영합니다")
                .CDVA_KD_NM("html")
                .build();
        codeRepository.insert(CodeDTO.toDTO(code3));
        final Optional<CodeEntity> retrievedCode3 = codeRepository.findById(code3.getCDVA_ID());
        assertTrue(retrievedCode3.isPresent(), "Code3 삽입 실패");
    }

    @Test
    void testCodeNameCode3() {
        final Optional<CodeEntity> retrievedCode3 = codeRepository.findById("COM_BANNERCONTENT1_CD");
        assertEquals("ePAMS에 오신걸<br>진심으로 환영합니다", retrievedCode3.get().getCDVA_NM(), "Code3 이름 불일치");
    }

    @Test
    void testCodeInsertCode4() throws IOException {
        final CodeEntity code4 = CodeEntity.builder()
                .CDVA_ID("DTM_INDEX_CD")
                .CDVA_NM("근태신청")
                .CDVA_KD_NM("text")
                .build();
        codeRepository.insert(CodeDTO.toDTO(code4));
        final Optional<CodeEntity> retrievedCode4 = codeRepository.findById(code4.getCDVA_ID());
        assertTrue(retrievedCode4.isPresent(), "Code4 삽입 실패");
    }

    @Test
    void testCodeNameCode4() {
        final Optional<CodeEntity> retrievedCode4 = codeRepository.findById("DTM_INDEX_CD");
        assertEquals("근태신청", retrievedCode4.get().getCDVA_NM(), "Code4 이름 불일치");
    }
}
