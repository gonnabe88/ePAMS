package epams.domain.com.admin.controller;

import epams.domain.com.admin.dto.HtmlLangLangMapDTO;
import epams.domain.com.admin.service.HtmlLangMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/***
 * @author 140024
 * @implNote Rest API 요청처리를 위한 컨트롤러
 * @since 2024-06-09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class HtmlLangMapRestController {

    /***
     * @author 140024
     * @implNote 코드 서비스
     * @since 2024-06-09
     */
    private final HtmlLangMapService htmlLangMapService;

    /***
     * @author 140024
     * @implNote 모든 코드 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/htmlLangMap")
    public ResponseEntity<List<HtmlLangLangMapDTO>> searchAllCode(final Model model) throws IOException {
        final List<HtmlLangLangMapDTO> htmlLangMapDTO = htmlLangMapService.findAll();
        log.warn("htmlLangMapDTO: {}", htmlLangMapDTO);
        return ResponseEntity.ok(htmlLangMapDTO);
    }

    /***
     * @author 140024
     * @implNote 코드 데이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/htmlLangMap/save")
    public ResponseEntity<Map<String, String>> saveCodes(@RequestBody final Map<String, List<HtmlLangLangMapDTO>> payload) {
        final List<HtmlLangLangMapDTO> added = payload.get("added");
        final List<HtmlLangLangMapDTO> changed = payload.get("changed");
        final List<HtmlLangLangMapDTO> deleted = payload.get("deleted");
        htmlLangMapService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
}
