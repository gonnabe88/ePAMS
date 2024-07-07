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
import epams.com.admin.dto.CodeHtmlMapDTO;
import epams.com.admin.repository.CodeHtmlMapRepository;

/**
 * EPamsApplication 클래스의 테스트 케이스
 */
@SpringBootTest(classes = EPamsApplication.class)
class CodeHtmlMapRegistTests {

    /**
     * /common/index 상수값
     */
    private static final String COMMON_INDEX = "/common/index";
    
    /**
     * /dtm/main 상수값
     */
    private static final String DTM_MAIN = "/dtm/main";

    /**
     * CodeHtmlMapRepository 인스턴스
     */
    private final CodeHtmlMapRepository codeHtmlMapRepo;
    
    /**
     * 생성자
     *
     * @param codeHtmlMapRepo CodeHtmlMapRepository 인스턴스
     */
    @Autowired
    public CodeHtmlMapRegistTests(final CodeHtmlMapRepository codeHtmlMapRepo) {
        this.codeHtmlMapRepo = codeHtmlMapRepo;
    }
    
    /**
     * HtmlEntity와 CodeEntity의 매핑을 데이터베이스에 삽입하고 각 항목을 검증하는 테스트 메소드
     *
     * @throws IOException 입출력 예외 발생 시
     */
    @Test
    void testHtmlCodeMapInsert() throws IOException {
        if(!codeHtmlMapRepo.findAll().isEmpty()) {
            final List<Map.Entry<String, String>> inputList = List.of(
                    Map.entry(DTM_MAIN, "DTM_INDEX_CD"),
                    Map.entry(COMMON_INDEX, "COM_BANNERCONTENT1_CD"),
                    Map.entry(COMMON_INDEX, "COM_BANNER1_CD"),
                    Map.entry(COMMON_INDEX, "COM_INDEX_CD")
            );

            final List<CodeHtmlMapDTO> codeHtmlMapDTOs = new ArrayList<>();

            for (final Map.Entry<String, String> entry : inputList) {
                final CodeHtmlMapDTO codeHtmlMapDTO = new CodeHtmlMapDTO();
                codeHtmlMapDTO.setHtml(entry.getKey());
                codeHtmlMapDTO.setCode(entry.getValue());
                codeHtmlMapDTOs.add(codeHtmlMapDTO);
                codeHtmlMapRepo.insert(codeHtmlMapDTO);
            }
            assertEquals(inputList.size(), codeHtmlMapDTOs.size(), "생성된 CodeHtmlMapDTO 객체의 수가 입력받은 리스트의 크기와 동일하지 않습니다.");
        }
    }

    @Test
    void testHtmlCodeMapHtmlValues() throws IOException {
        final List<Map.Entry<String, String>> inputList = List.of(
                Map.entry(DTM_MAIN, "DTM_INDEX_CD"),
                Map.entry(COMMON_INDEX, "COM_BANNERCONTENT1_CD"),
                Map.entry(COMMON_INDEX, "COM_BANNER1_CD"),
                Map.entry(COMMON_INDEX, "COM_INDEX_CD")
        );

        final List<CodeHtmlMapDTO> codeHtmlMapDTOs = new ArrayList<>();

        for (final Map.Entry<String, String> entry : inputList) {
            final CodeHtmlMapDTO codeHtmlMapDTO = new CodeHtmlMapDTO();
            codeHtmlMapDTO.setHtml(entry.getKey());
            codeHtmlMapDTO.setCode(entry.getValue());
            codeHtmlMapDTOs.add(codeHtmlMapDTO);
            codeHtmlMapRepo.insert(codeHtmlMapDTO);
        }

        for (int i = 0; i < inputList.size(); i++) {
            final CodeHtmlMapDTO codeHtmlMapDTO = codeHtmlMapDTOs.get(i);
            final Map.Entry<String, String> entry = inputList.get(i);
            assertEquals(entry.getKey(), codeHtmlMapDTO.getHtml(), "CodeHtmlMapDTO 객체의 Html 값이 예상과 다릅니다. 인덱스: " + i);
        }
    }

    @Test
    void testHtmlCodeMapCodeValues() throws IOException {
        final List<Map.Entry<String, String>> inputList = List.of(
                Map.entry(DTM_MAIN, "DTM_INDEX_CD"),
                Map.entry(COMMON_INDEX, "COM_BANNERCONTENT1_CD"),
                Map.entry(COMMON_INDEX, "COM_BANNER1_CD"),
                Map.entry(COMMON_INDEX, "COM_INDEX_CD")
        );

        final List<CodeHtmlMapDTO> codeHtmlMapDTOs = new ArrayList<>();

        for (final Map.Entry<String, String> entry : inputList) {
            final CodeHtmlMapDTO codeHtmlMapDTO = new CodeHtmlMapDTO();
            codeHtmlMapDTO.setHtml(entry.getKey());
            codeHtmlMapDTO.setCode(entry.getValue());
            codeHtmlMapDTOs.add(codeHtmlMapDTO);
            codeHtmlMapRepo.insert(codeHtmlMapDTO);
        }

        for (int i = 0; i < inputList.size(); i++) {
            final CodeHtmlMapDTO codeHtmlMapDTO = codeHtmlMapDTOs.get(i);
            final Map.Entry<String, String> entry = inputList.get(i);
            assertEquals(entry.getValue(), codeHtmlMapDTO.getCode(), "CodeHtmlMapDTO 객체의 Code 값이 예상과 다릅니다. 인덱스: " + i);
        }
    }
}
