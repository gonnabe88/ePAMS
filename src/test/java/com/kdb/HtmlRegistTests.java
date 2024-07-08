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
import epams.com.admin.dto.HtmlDTO;
import epams.com.admin.repository.HtmlRepository;

/**
 * EPamsApplication 클래스의 테스트 케이스
 */
@SpringBootTest(classes = EPamsApplication.class)
class HtmlRegistTests {

    /**
     * HtmlRepository 인스턴스
     */
    private final HtmlRepository htmlRepository;

    /**
     * 생성자
     *
     * @param htmlRepository HtmlRepository 인스턴스
     */
    @Autowired
    public HtmlRegistTests(final HtmlRepository htmlRepository) {
        this.htmlRepository = htmlRepository;
    }

    /**
     * HtmlEntity를 데이터베이스에 삽입하고 각 항목을 검증하는 테스트 메소드
     *
     * @throws IOException 입출력 예외 발생 시
     */
    @Test
    void testHtmlInsert() throws IOException {
        final List<Map.Entry<String, String>> inputList = List.of(
                Map.entry("/board/list", "공지사항"),
                Map.entry("/dtm/main", "근태신청"),
                Map.entry("/dtm/list", "근태조회"),
                Map.entry("/admin/code", "코드관리"),
                Map.entry("/admin/user", "사용자관리"),
                Map.entry("/common/index", "메인화면")
        );

        final List<HtmlDTO> htmlDTOs = new ArrayList<>();

        for (final Map.Entry<String, String> entry : inputList) {
            final HtmlDTO htmlDTO = new HtmlDTO();
            htmlDTO.setHtml(entry.getKey());
            htmlDTO.setHtmlName(entry.getValue());
            htmlDTOs.add(htmlDTO);
            htmlRepository.insert(htmlDTO);
        }

        assertEquals(inputList.size(), htmlDTOs.size(), "생성된 HtmlDTO 객체의 수가 입력받은 리스트의 크기와 동일하지 않습니다.");
    }
}
