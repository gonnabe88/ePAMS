package epams.domain.com.admin.service;

import epams.domain.com.admin.dto.HtmlLangLangMapDTO;
import epams.domain.com.admin.repository.HtmlLangMapRepository;
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
public class HtmlLangMapService {
	
    /**
     * @author K140024
     * @implNote LangRepository 주입
     * @since 2024-04-26
     */
	private final HtmlLangMapRepository htmlLangMapRepository;
	
	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<HtmlLangLangMapDTO> findAll() {
    	return htmlLangMapRepository.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(final List<HtmlLangLangMapDTO> added, final List<HtmlLangLangMapDTO> changed, final List<HtmlLangLangMapDTO> deleted) {
        // Handle added members
        for (final HtmlLangLangMapDTO dto : added) {
            log.info("dto: {}", dto);
        	htmlLangMapRepository.insert(dto);
        }

        // Handle changed members
        for (final HtmlLangLangMapDTO dto : changed) {
        	htmlLangMapRepository.update(dto);
        }

        // Handle deleted members
        for (final HtmlLangLangMapDTO dto : deleted) {
        	htmlLangMapRepository.delete(dto);
        }
    }
}
