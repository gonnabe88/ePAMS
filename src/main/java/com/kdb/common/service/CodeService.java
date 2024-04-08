package com.kdb.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kdb.common.entity.CodeEntity;
import com.kdb.common.repository.CodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeService {
	
	private final CodeRepository CodeRepository;
	
    public List<CodeEntity> getCode(String html) {
    	List<CodeEntity> codeEntityList = CodeRepository.findByHtml(html);
    	return codeEntityList;
    	
    }
}
