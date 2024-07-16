package epams.domain.com.admin.controller;

import epams.domain.com.admin.dto.CodeHtmlMapDTO;
import epams.domain.com.admin.service.CodeHtmlMapService;
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
public class CodeHtmlMapRestController {

    /***
     * @author 140024
     * @implNote 코드 서비스
     * @since 2024-06-09
     */
    private final CodeHtmlMapService codeHtmlMapService;

    /***
     * @author 140024
     * @implNote 모든 코드 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/codeHtmlMap")
    public ResponseEntity<List<CodeHtmlMapDTO>> searchAllCode(final Model model) throws IOException {
        final List<CodeHtmlMapDTO> codeHtmlMapDTO = codeHtmlMapService.findAll();
        log.warn("codeHtmlMapDTO: {}", codeHtmlMapDTO);
        return ResponseEntity.ok(codeHtmlMapDTO);
    }

    /***
     * @author 140024
     * @implNote 코드 데이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/codeHtmlMap/save")
    public ResponseEntity<Map<String, String>> saveCodes(@RequestBody final Map<String, List<CodeHtmlMapDTO>> payload) {
        final List<CodeHtmlMapDTO> added = payload.get("added");
        final List<CodeHtmlMapDTO> changed = payload.get("changed");
        final List<CodeHtmlMapDTO> deleted = payload.get("deleted");
        codeHtmlMapService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
}
