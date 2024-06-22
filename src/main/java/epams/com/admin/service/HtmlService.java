package epams.com.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import epams.com.admin.dto.HtmlDTO;
import epams.com.admin.repository.HtmlRepository;
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
    @Transactional
    public void save(final List<HtmlDTO> added, final List<HtmlDTO> changed, final List<HtmlDTO> deleted) {    	
        // Handle added members
        for (final HtmlDTO dto : added) {
        	htmlRepository.insertUpdate(dto);
        }

        // Handle changed members
        for (final HtmlDTO dto : changed) {
        	htmlRepository.update(dto);
        }

        // Handle deleted members
        for (final HtmlDTO dto : deleted) {
        	htmlRepository.delete(dto);
        }
        
    }
    
}
