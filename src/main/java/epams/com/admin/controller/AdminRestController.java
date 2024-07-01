package epams.com.admin.controller;

import epams.com.admin.dto.CodeDTO;
import epams.com.admin.dto.HtmlDTO;
import epams.com.admin.dto.LogLoginDTO;
import epams.com.admin.dto.LogViewDTO;
import epams.com.admin.service.*;
import epams.com.login.dto.LoginOTPDTO;
import epams.com.member.dto.IamUserDTO;
import epams.com.member.dto.RoleDTO;
import epams.com.member.service.MemberDetailsService;
import epams.com.member.service.MemberService;
import epams.com.member.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private final MemberService memberService2;

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
     * @implNote 로그인 OTP서비스
     * @since 2024-06-09
     */
    private final LoginOtpService loginOtpService;

        /***
     * @author 140024
     * @implNote 로그인 OTP서비스
     * @since 2024-06-09
     */
    private final ViewLogService viewLogService;

    /***
     * @author 140024
     * @implNote 역할 서비스
     * @since 2024-06-09
     */
    private final RoleService roleService;

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
    public ResponseEntity<List<IamUserDTO>> searchAllMember(final Model model) throws IOException {
        final List<IamUserDTO> memberList = memberService2.findAll();
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
     * @implNote 모든 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/loginotp")
    public ResponseEntity<List<LoginOTPDTO>> searchAllLoginOtp(final Model model) throws IOException {
        final List<LoginOTPDTO> dtos = loginOtpService.findAll();
        return ResponseEntity.ok(dtos);
    }

    /***
     * @author 140024
     * @implNote 변경된 테이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/loginotp/save")
    public ResponseEntity<Map<String, String>> saveLoginOtp(@RequestBody final Map<String, List<LoginOTPDTO>> payload) {
        final List<LoginOTPDTO> added = payload.get("added");
        final List<LoginOTPDTO> changed = payload.get("changed");
        final List<LoginOTPDTO> deleted = payload.get("deleted");
        loginOtpService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }

        /***
     * @author 140024
     * @implNote 모든 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/viewlog")
    public ResponseEntity<List<LogViewDTO>> searchAllViewLog(final Model model) throws IOException {
        final List<LogViewDTO> dtos = viewLogService.findAll();
        return ResponseEntity.ok(dtos);
    }

    /***
     * @author 140024
     * @implNote 변경된 테이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/viewlog/save")
    public ResponseEntity<Map<String, String>> saveViewLog(@RequestBody final Map<String, List<LogViewDTO>> payload) {
        final List<LogViewDTO> added = payload.get("added");
        final List<LogViewDTO> changed = payload.get("changed");
        final List<LogViewDTO> deleted = payload.get("deleted");
        viewLogService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }

    /***
     * @author 140024
     * @implNote 모든 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/role")
    public ResponseEntity<List<RoleDTO>> searchRole(final Model model) throws IOException {
        final List<RoleDTO> dtos = roleService.findAll();
        return ResponseEntity.ok(dtos);
    }

    /***
     * @author 140024
     * @implNote 변경된 테이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/role/save")
    public ResponseEntity<Map<String, String>> saveRole(@RequestBody final Map<String, List<RoleDTO>> payload) {
        final List<RoleDTO> added = payload.get("added");
        final List<RoleDTO> changed = payload.get("changed");
        final List<RoleDTO> deleted = payload.get("deleted");
        roleService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
}
