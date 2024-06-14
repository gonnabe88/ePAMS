package com.kdb.com.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kdb.com.dto.CodeDTO;
import com.kdb.com.dto.CodeHtmlDetailDTO;
import com.kdb.com.repository.CodeHtmlDetailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote html에서 사용하는 code 목록 반환을 위한 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeHtmlDetailService {
	
    /**
     * @author K140024
     * @implNote CodeRepository 주입
     * @since 2024-04-26
     */
	private final CodeHtmlDetailRepository codeHtmlDetailRepository;
	
    /**
     * @author K140024
     * @implNote codeHtmlDetailDTO 목록을 Map(Key-Value)으로 반환
     * @since 2024-04-26
     */
    public Map<String, String> getCodeHtmlMap(final String html) {
    	
    	final List<CodeHtmlDetailDTO> codeHtmlDetailDTOs = codeHtmlDetailRepository.findAllByHtml(html);
//    	log.warn("html : "+html);
    	//    	for(CodeHtmlDetailDTO dto : codeHtmlDetailDTOs) {
//    		log.warn("CodeList : "+ dto.toString());
//    	}
    	
    	return codeHtmlDetailDTOs.stream()
                .collect(Collectors.toMap(CodeHtmlDetailDTO::getCode, CodeHtmlDetailDTO::getCodeName));
    }
}
