package epams.domain.com.admin.service;

import java.util.List;

import epams.domain.com.admin.dto.HtmlDTO;
import org.springframework.stereotype.Service;

import epams.domain.com.admin.repository.HtmlRepository;
import jakarta.transaction.Transactional;
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
     * @implNote LangRepository 주입
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
    @Transactional
    public void save(final List<HtmlDTO> added, final List<HtmlDTO> changed, final List<HtmlDTO> deleted) {    	
        // Handle added members
        for (final HtmlDTO dto : added) {
            log.warn("Added: {}", dto.toString());
        	htmlRepository.insertUpdate(dto);
        }

        // Handle changed members
        for (final HtmlDTO dto : changed) {
            log.warn("update: {}", dto.toString());
        	htmlRepository.update(dto);
        }

        // Handle deleted members
        for (final HtmlDTO dto : deleted) {
            log.warn("delete: {}", dto.toString());
        	htmlRepository.delete(dto);
        }
        
    }
    
}
