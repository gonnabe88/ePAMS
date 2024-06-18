package epams.com.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epams.com.admin.dto.CodeDTO;
import epams.com.admin.dto.HtmlDTO;
import epams.com.admin.dto.LogLoginDTO;
import epams.com.admin.service.CodeService;
import epams.com.admin.service.HtmlService;
import epams.com.admin.service.LogService;
import epams.com.member.entity.MemberEntity;
import epams.com.member.service.MemberDetailsService;
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
public class AdminRestController {

    /***
     * @author 140024
     * @implNote 멤버 서비스
     * @since 2024-06-09
     */
    private final MemberDetailsService memberService;

    /***
     * @author 140024
     * @implNote HTML 서비스
     * @since 2024-06-09
     */
    private final HtmlService htmlService;

    /***
     * @author 140024
     * @implNote 코드 서비스
     * @since 2024-06-09
     */
    private final CodeService codeService;

    /***
     * @author 140024
     * @implNote 로그 서비스
     * @since 2024-06-09
     */
    private final LogService logService;

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

    /***
     * @author 140024
     * @implNote 모든 로그인 로그 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/login")
    public ResponseEntity<List<LogLoginDTO>> searchAllLoginLog(final Model model) throws IOException {
        final List<LogLoginDTO> logloginDTOs = logService.findAll();
        return ResponseEntity.ok(logloginDTOs);
    }

    /***
     * @author 140024
     * @implNote 모든 멤버 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/member")
    public ResponseEntity<List<MemberEntity>> searchAllMember(final Model model) throws IOException {
        final List<MemberEntity> memberList = memberService.findAll();
        final Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("data", memberList);

        // 마지막 페이지 및 데이터 설정
        final Map<String, Object> response = new ConcurrentHashMap<>();
        //response.put("last_page", 10);
        response.put("data", memberList);

        return ResponseEntity.ok(memberList);
    }


    /***
     * @author 140024
     * @implNote 멤버 데이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/member/save")
    public ResponseEntity<Map<String, String>> saveMembers(@RequestBody final Map<String, List<MemberEntity>> payload) {
        final List<MemberEntity> added = payload.get("added");
        final List<MemberEntity> changed = payload.get("changed");
        final List<MemberEntity> deleted = payload.get("deleted");

        memberService.saveMembers(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
}