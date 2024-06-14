package com.kdb.com.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kdb.com.dto.CodeDTO;
import com.kdb.com.entity.CodeEntity;
import com.kdb.com.repository.CodeRepository2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 코드 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {
	
    /**
     * @author K140024
     * @implNote CodeRepository 주입
     * @since 2024-04-26
     */
	private final CodeRepository2 codeRepository;
	
    public Map<String, String> getCode(CodeDTO codeDTO) {
    	List<CodeDTO> codeDTOList = codeRepository.findByHtml(codeDTO.toEntity());
    	return codeDTOList.stream()
                .collect(Collectors.toMap(CodeDTO::getCode, CodeDTO::getCodeName));
    }
}
