package epams.com.admin.service;

import epams.com.admin.dto.CodeDTO;
import epams.com.admin.dto.CodeHtmlMapDTO;
import epams.com.admin.repository.CodeHtmlMapRepository;
import epams.com.admin.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author K140024
 * @implNote 코드 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeHtmlMapService {
	
    /**
     * @author K140024
     * @implNote CodeRepository 주입
     * @since 2024-04-26
     */
	private final CodeHtmlMapRepository codeHtmlMapRepository;
	
	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<CodeHtmlMapDTO> findAll() {
    	return codeHtmlMapRepository.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(final List<CodeHtmlMapDTO> added, final List<CodeHtmlMapDTO> changed, final List<CodeHtmlMapDTO> deleted) {
        // Handle added members
        for (final CodeHtmlMapDTO dto : added) {
        	codeHtmlMapRepository.insert(dto);
        }

        // Handle changed members
        for (final CodeHtmlMapDTO dto : changed) {
        	codeHtmlMapRepository.update(dto);
        }

        // Handle deleted members
        for (final CodeHtmlMapDTO dto : deleted) {
        	codeHtmlMapRepository.delete(dto);
        }
    }
}
