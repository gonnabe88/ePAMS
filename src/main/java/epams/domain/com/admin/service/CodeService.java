package epams.domain.com.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import epams.domain.com.admin.dto.CodeDTO;
import epams.domain.com.admin.repository.CodeRepository;
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
	private final CodeRepository codeRepository;
	
	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<CodeDTO> findAll() {    	
    	return codeRepository.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(final List<CodeDTO> added, final List<CodeDTO> changed, final List<CodeDTO> deleted) {    	
        // Handle added members
        for (final CodeDTO dto : added) {
        	codeRepository.insert(dto);
        }

        // Handle changed members
        for (final CodeDTO dto : changed) {
        	codeRepository.update(dto);
        }

        // Handle deleted members
        for (final CodeDTO dto : deleted) {
        	codeRepository.delete(dto);
        }
    }
}
