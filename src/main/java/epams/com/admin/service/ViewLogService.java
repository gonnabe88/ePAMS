package epams.com.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import epams.com.admin.dto.LogViewDTO;
import epams.com.admin.repository.ViewLogRepository;
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
     * @implNote CodeRepository 주입
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
    public void save(List<LogViewDTO> added, List<LogViewDTO> changed, List<LogViewDTO> deleted) {    	
        // Handle added members
        for (LogViewDTO dto : added) {
        	viewLogRepository.insert(dto);
        }

        // Handle changed members
        for (LogViewDTO dto : changed) {
        	viewLogRepository.update(dto);
        }

        // Handle deleted members
        for (LogViewDTO dto : deleted) {
        	viewLogRepository.delete(dto);
        }
    }
}
