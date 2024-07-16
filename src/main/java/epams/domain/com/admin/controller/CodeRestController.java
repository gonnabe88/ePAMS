package epams.domain.com.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import epams.domain.com.admin.dto.CodeDTO;
import epams.domain.com.admin.service.CodeService;
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
public class CodeRestController {

    /***
     * @author 140024
     * @implNote 코드 서비스
     * @since 2024-06-09
     */
    private final CodeService codeService;

    /***
     * @author 140024
     * @implNote 모든 코드 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/code")
    public ResponseEntity<List<CodeDTO>> searchAllCode(final Model model) throws IOException {
        final List<CodeDTO> codeDTOs = codeService.findAll();
        return ResponseEntity.ok(codeDTOs);
    }

    /***
     * @author 140024
     * @implNote 모든 코드 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/codelist")
    public ResponseEntity<List<CodeDTO>> searchOnlyCode(final Model model) throws IOException {
        final List<CodeDTO> codeDTOs = codeService.findAll();
        return ResponseEntity.ok(codeDTOs);
    }

    /***
     * @author 140024
     * @implNote 코드 데이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/code/save")
    public ResponseEntity<Map<String, String>> saveCodes(@RequestBody final Map<String, List<CodeDTO>> payload) {
        final List<CodeDTO> added = payload.get("added");
        final List<CodeDTO> changed = payload.get("changed");
        final List<CodeDTO> deleted = payload.get("deleted");
        codeService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
}
