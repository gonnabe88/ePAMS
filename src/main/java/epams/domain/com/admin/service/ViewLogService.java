package epams.domain.com.admin.service;

import java.util.List;

import epams.domain.com.admin.dto.LogViewDTO;
import org.springframework.stereotype.Service;

import epams.domain.com.admin.repository.ViewLogRepository;
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
public class ViewLogService {
	
    /**
     * @author K140024
     * @implNote LangRepository 주입
     * @since 2024-04-26
     */
	private final ViewLogRepository viewLogRepository;
	
	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<LogViewDTO> findAll() {
    	return viewLogRepository.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(final List<LogViewDTO> added, final List<LogViewDTO> changed, final List<LogViewDTO> deleted) {    	
        // Handle added members
        for (final LogViewDTO dto : added) {
        	viewLogRepository.insert(dto);
        }

        // Handle changed members
        for (final LogViewDTO dto : changed) {
        	viewLogRepository.update(dto);
        }

        // Handle deleted members
        for (final LogViewDTO dto : deleted) {
        	viewLogRepository.delete(dto);
        }
    }
}
