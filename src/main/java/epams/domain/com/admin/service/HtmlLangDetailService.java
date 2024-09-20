package epams.domain.com.admin.service;

import java.util.Map;
import java.util.stream.Collectors;

import epams.domain.com.admin.dto.HtmlLangDetailDTO;
import org.springframework.stereotype.Service;

import epams.domain.com.admin.repository.HtmlLangMapRepository;
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
public class HtmlLangDetailService {
	
    /**
     * @author K140024
     * @implNote LangRepository 주입
     * @since 2024-04-26
     */
	private final HtmlLangMapRepository htmlLangMapRepo;
	
    /**
     * @author K140024
     * @implNote codeHtmlDetailDTO 목록을 Map(Key-Value)으로 반환
     * @since 2024-04-26
     */
    public Map<String, String> getCodeHtmlDetail(final String html) {    	
    	
    	return htmlLangMapRepo.findAllByHtml(html).stream()
                .collect(Collectors.toMap(HtmlLangDetailDTO::getLang, HtmlLangDetailDTO::getLangName));
    }

}
