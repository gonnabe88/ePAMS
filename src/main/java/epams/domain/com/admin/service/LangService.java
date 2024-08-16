package epams.domain.com.admin.service;

import java.util.List;

import epams.domain.com.admin.repository.LangRepository;
import org.springframework.stereotype.Service;

import epams.domain.com.admin.dto.LangDTO;
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
public class LangService {
	
    /**
     * @author K140024
     * @implNote LangRepository 주입
     * @since 2024-04-26
     */
	private final LangRepository langRepository;
	
	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<LangDTO> findAll() {
    	return langRepository.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(final List<LangDTO> added, final List<LangDTO> changed, final List<LangDTO> deleted) {
        // Handle added members
        for (final LangDTO dto : added) {
        	langRepository.insert(dto);
        }

        // Handle changed members
        for (final LangDTO dto : changed) {
        	langRepository.update(dto);
        }

        // Handle deleted members
        for (final LangDTO dto : deleted) {
        	langRepository.delete(dto);
        }
    }
}
