package com.kdb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import epams.EPamsApplication;
import epams.com.admin.dto.CodeDTO;
import epams.com.admin.dto.CodeHtmlMapDTO;
import epams.com.admin.dto.HtmlDTO;
import epams.com.admin.entity.CodeEntity;
import epams.com.admin.repository.CodeHtmlMapRepository;
import epams.com.admin.repository.CodeRepository;
import epams.com.admin.repository.HtmlRepository;

/**
 * @author K140024
 * @implNote EPamsApplication 클래스의 테스트 케이스
 * @since 2024-06-22
 */
@SpringBootTest(classes = EPamsApplication.class)
class EPamCodeHtmlTests {

    /**
     * CodeRepository 인스턴스
     */
    @Autowired
    private CodeRepository codeRepository;

    /**
     * HtmlRepository 인스턴스
     */
    @Autowired
    private HtmlRepository htmlRepository;

    /**
     * CodeHtmlMapRepository 인스턴스
     */
    @Autowired
    private CodeHtmlMapRepository codeHtmlMapRepository;

    /**
     * CodeEntity를 데이터베이스에 삽입하는 테스트 메소드
     * 
     * @throws IOException 입출력 예외 발생 시
     */
    //@Test
    public void CodeInsert() throws IOException {
        final CodeEntity code1 = CodeEntity.builder().CD_ID("COM_INDEX_CD").CD_NM("빠른 신청").CD_TYPE("text").build();
        codeRepository.insert(CodeDTO.toDTO(code1));

        final CodeEntity code2 = CodeEntity.builder().CD_ID("COM_BANNER1_CD").CD_NM("모바일 근태 시스템").CD_TYPE("text").build();
        codeRepository.insert(CodeDTO.toDTO(code2));

        final CodeEntity code3 = CodeEntity.builder().CD_ID("COM_BANNERCONTENT1_CD").CD_NM("ePAMS에 오신걸<br>진심으로 환영합니다")
                .CD_TYPE("html").build();
        codeRepository.insert(CodeDTO.toDTO(code3));

        final CodeEntity code4 = CodeEntity.builder().CD_ID("DTM_INDEX_CD").CD_NM("근태신청").CD_TYPE("text").build();
        codeRepository.insert(CodeDTO.toDTO(code4));
    }

    /**
     * HtmlEntity를 데이터베이스에 삽입하는 테스트 메소드
     * 
     * @throws IOException 입출력 예외 발생 시
     */
    //@Test
    public void HtmlInsert() throws IOException {
        // 입력받은 (HTML 경로, HTML 설명) 리스트
        final List<Map.Entry<String, String>> inputList = List.of(
                Map.entry("/board/list", "공지사항"),
                Map.entry("/dtm/main", "근태신청"),
                Map.entry("/dtm/list", "근태조회"),
                Map.entry("/admin/code", "코드관리"),
                Map.entry("/admin/user", "사용자관리"),
                Map.entry("/common/index", "메인화면")
        );

        // HtmlDTO 객체 리스트
        final List<HtmlDTO> htmlDTOs = new ArrayList<>();

        // 입력받은 리스트만큼 HtmlDTO 객체 생성
        for (final Map.Entry<String, String> entry : inputList) {
            final HtmlDTO htmlDTO = new HtmlDTO();
            htmlDTO.setHtml(entry.getKey());
            htmlDTO.setHtmlName(entry.getValue());
            htmlDTOs.add(htmlDTO);
            htmlRepository.insert(htmlDTO);
        }

        // 테스트: 생성된 객체 수가 입력받은 리스트의 크기와 동일한지 확인
        assertEquals(inputList.size(), htmlDTOs.size());

        // 테스트: 각 객체의 값이 올바르게 설정되었는지 확인
        for (int i = 0; i < inputList.size(); i++) {
            final HtmlDTO htmlDTO = htmlDTOs.get(i);
            final Map.Entry<String, String> entry = inputList.get(i);
            assertEquals(entry.getKey(), htmlDTO.getHtml());
            assertEquals(entry.getValue(), htmlDTO.getHtmlName());
        }
    }

    /**
     * HtmlEntity와 CodeEntity의 매핑을 데이터베이스에 삽입하는 테스트 메소드
     * 
     * @throws IOException 입출력 예외 발생 시
     */
    //@Test
    public void htmlCodeMapInsert() throws IOException {
        // 입력받은 (HTML 경로, HTML 설명) 리스트
        final List<Map.Entry<String, String>> inputList = List.of(
                Map.entry("/dtm/main", "DTM_INDEX_CD"),
                Map.entry("/common/index", "COM_BANNERCONTENT1_CD"),
                Map.entry("/common/index", "COM_BANNER1_CD"),
                Map.entry("/common/index", "COM_INDEX_CD")
        );

        // CodeHtmlMapDTO 객체 리스트
        final List<CodeHtmlMapDTO> codeHtmlMapDTOs = new ArrayList<>();

        // 입력받은 리스트만큼 CodeHtmlMapDTO 객체 생성
        for (final Map.Entry<String, String> entry : inputList) {
            final CodeHtmlMapDTO codeHtmlMapDTO = new CodeHtmlMapDTO();
            codeHtmlMapDTO.setHtml(entry.getKey());
            codeHtmlMapDTO.setCode(entry.getValue());
            codeHtmlMapDTOs.add(codeHtmlMapDTO);
            codeHtmlMapRepository.insert(codeHtmlMapDTO);
        }

        // 테스트: 생성된 객체 수가 입력받은 리스트의 크기와 동일한지 확인
        assertEquals(inputList.size(), codeHtmlMapDTOs.size());

        // 테스트: 각 객체의 값이 올바르게 설정되었는지 확인
        for (int i = 0; i < inputList.size(); i++) {
            final CodeHtmlMapDTO codeHtmlMapDTO = codeHtmlMapDTOs.get(i);
            final Map.Entry<String, String> entry = inputList.get(i);
            assertEquals(entry.getKey(), codeHtmlMapDTO.getHtml());
            assertEquals(entry.getValue(), codeHtmlMapDTO.getCode());
        }
    }
}