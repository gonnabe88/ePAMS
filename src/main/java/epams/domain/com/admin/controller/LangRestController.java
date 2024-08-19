package epams.domain.com.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import epams.domain.com.admin.dto.LangDTO;
import epams.domain.com.admin.service.LangService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote Rest API 요청처리를 위한 컨트롤러
 * @since 2024-06-09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class LangRestController {

    /***
     * @author 140024
     * @implNote 코드 서비스
     * @since 2024-06-09
     */
    private final LangService langService;

    /***
     * @author 140024
     * @implNote 모든 코드 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/lang")
    public ResponseEntity<List<LangDTO>> searchAlllang(final Model model) throws IOException {
        final List<LangDTO> langDTOs = langService.findAll();
        return ResponseEntity.ok(langDTOs);
    }

    /***
     * @author 140024
     * @implNote 모든 코드 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/langlist")
    public ResponseEntity<List<LangDTO>> searchOnlylang(final Model model) throws IOException {
        final List<LangDTO> langDTOs = langService.findAll();
        return ResponseEntity.ok(langDTOs);
    }

    /***
     * @author 140024
     * @implNote 코드 데이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/lang/save")
    public ResponseEntity<Map<String, String>> savelangs(@RequestBody final Map<String, List<LangDTO>> payload) {
        final List<LangDTO> added = payload.get("added");
        final List<LangDTO> changed = payload.get("changed");
        final List<LangDTO> deleted = payload.get("deleted");
        langService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
}
