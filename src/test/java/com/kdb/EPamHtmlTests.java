package com.kdb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdb.com.dto.CodeHtmlDetailDTO;
import com.kdb.com.dto.HtmlDTO;
import com.kdb.com.entity.HtmlEntity;
import com.kdb.com.repository.CodeHtmlDetailRepository;
import com.kdb.com.repository.HtmlRepository;

@SpringBootTest
class EPamHtmlTests {

	@Autowired
    private HtmlRepository htmlRepository;
	@Autowired
    private CodeHtmlDetailRepository codeHtmlDetailRepository;
	
    @Test
    public void EntityInsert() throws IOException {    	
        
        // 입력받은 (HTML 경로, HTML 설명) 리스트
        List<Map.Entry<String, String>> inputList = List.of(
            Map.entry("/board/list", "공지사항"),
            Map.entry("/dtm/main", "근태신청"),
            Map.entry("/dtm/list", "근태조회"),
            Map.entry("/admin/code", "코드관리"),
            Map.entry("/admin/user", "사용자관리"),
            Map.entry("/index", "메인화면")
        );

        // HtmlEntity 객체 리스트
        List<HtmlEntity> htmlEntities = new ArrayList<>();

        // 입력받은 리스트만큼 HtmlEntity 객체 생성
        for (Map.Entry<String, String> entry : inputList) {
            HtmlEntity htmlEntity = new HtmlEntity();
            htmlEntity.setHTML(entry.getKey());
            htmlEntity.setHTML_NM(entry.getValue());
            htmlEntities.add(htmlEntity);
            htmlRepository.insert(HtmlDTO.toDTO(htmlEntity));
        }

        // 테스트: 생성된 객체 수가 입력받은 리스트의 크기와 동일한지 확인
        assertEquals(inputList.size(), htmlEntities.size());

        // 테스트: 각 객체의 값이 올바르게 설정되었는지 확인
        for (int i = 0; i < inputList.size(); i++) {
            HtmlEntity htmlEntity = htmlEntities.get(i);
            Map.Entry<String, String> entry = inputList.get(i);
            assertEquals(entry.getKey(), htmlEntity.getHTML());
            assertEquals(entry.getValue(), htmlEntity.getHTML_NM());
        }
            
    }
    @Test
    public void htmlCodeInsert() throws IOException {    	
        
        // 입력받은 (HTML 경로, HTML 설명) 리스트
        List<Map.Entry<String, String>> inputList = List.of(
            Map.entry("/dtm/main", "DTM_INDEX_CD"),
            Map.entry("/index", "COM_BANNERCONTENT1_CD"),
            Map.entry("/index", "COM_BANNER1_CD"),
            Map.entry("/index", "COM_INDEX_CD")
        );

        // HtmlEntity 객체 리스트
        List<CodeHtmlDetailDTO> codeHtmlDetailDTOs = new ArrayList<>();

        // 입력받은 리스트만큼 HtmlEntity 객체 생성
        for (Map.Entry<String, String> entry : inputList) {
        	CodeHtmlDetailDTO codeHtmlDetailDTO = new CodeHtmlDetailDTO();
        	codeHtmlDetailDTO.setHtml(entry.getKey());
        	codeHtmlDetailDTO.setCode(entry.getValue());
        	codeHtmlDetailDTOs.add(codeHtmlDetailDTO);
        	codeHtmlDetailRepository.
        }

        // 테스트: 생성된 객체 수가 입력받은 리스트의 크기와 동일한지 확인
        assertEquals(inputList.size(), codeHtmlDetailDTOs.size());

        // 테스트: 각 객체의 값이 올바르게 설정되었는지 확인
        for (int i = 0; i < inputList.size(); i++) {
        	CodeHtmlDetailDTO codeHtmlDetailDTO = codeHtmlDetailDTOs.get(i);
            Map.Entry<String, String> entry = inputList.get(i);
            assertEquals(entry.getKey(), codeHtmlDetailDTO.getHtml());
            assertEquals(entry.getValue(), codeHtmlDetailDTO.getCode());
        }
            
    }
}
