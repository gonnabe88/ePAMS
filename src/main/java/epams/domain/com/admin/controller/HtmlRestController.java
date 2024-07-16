package epams.domain.com.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import epams.domain.com.admin.dto.HtmlDTO;
import epams.domain.com.admin.service.HtmlService;
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
public class HtmlRestController {

    /***
     * @author 140024
     * @implNote HTML 서비스
     * @since 2024-06-09
     */
    private final HtmlService htmlService;

    /***
     * @author 140024
     * @implNote 모든 HTML 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/html")
    public ResponseEntity<List<HtmlDTO>> searchAllHtml(Model model) throws IOException {
        final List<HtmlDTO> htmlDTOs = htmlService.findAll();
        return ResponseEntity.ok(htmlDTOs);
    }

    /***
     * @author 140024
     * @implNote HTML 데이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/html/save")
    public ResponseEntity<Map<String, String>> saveHtmls(@RequestBody final Map<String, List<HtmlDTO>> payload) {
        final List<HtmlDTO> added = payload.get("added");
        final List<HtmlDTO> changed = payload.get("changed");
        final List<HtmlDTO> deleted = payload.get("deleted");

        htmlService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
}
