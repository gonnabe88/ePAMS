package epams.domain.com.admin.service;

import java.util.Map;
import java.util.stream.Collectors;

import epams.domain.com.admin.dto.CodeHtmlDetailDTO;
import org.springframework.stereotype.Service;

import epams.domain.com.admin.repository.CodeHtmlMapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote html에서 사용하는 code 목록 반환을 위한 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeHtmlDetailService {
	
    /**
     * @author K140024
     * @implNote CodeRepository 주입
     * @since 2024-04-26
     */
	private final CodeHtmlMapRepository codeHtmlMapRepo;
	
    /**
     * @author K140024
     * @implNote codeHtmlDetailDTO 목록을 Map(Key-Value)으로 반환
     * @since 2024-04-26
     */
    public Map<String, String> getCodeHtmlDetail(final String html) {    	
    	
    	return codeHtmlMapRepo.findAllByHtml(html).stream()
                .collect(Collectors.toMap(CodeHtmlDetailDTO::getCode, CodeHtmlDetailDTO::getCodeName));
    }
}
