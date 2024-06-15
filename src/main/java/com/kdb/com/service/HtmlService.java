package com.kdb.com.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kdb.com.dto.HtmlDTO;
import com.kdb.com.dto.HtmlDTO;
import com.kdb.com.entity.MemberEntity;
import com.kdb.com.repository.HtmlRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote HTML 화면관리 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HtmlService {
	
    /**
     * @author K140024
     * @implNote CodeRepository 주입
     * @since 2024-04-26
     */
	private final HtmlRepository htmlRepository;

    /**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<HtmlDTO> findAll() {    	
    	return htmlRepository.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(List<HtmlDTO> added, List<HtmlDTO> changed, List<HtmlDTO> deleted) {    	
        // Handle added members
        for (HtmlDTO dto : added) {
        	htmlRepository.insert(dto);
        }

        // Handle changed members
        for (HtmlDTO dto : changed) {
        	htmlRepository.update(dto);
        }

        // Handle deleted members
        for (HtmlDTO dto : deleted) {
        	htmlRepository.delete(dto);
        }
    }
}
