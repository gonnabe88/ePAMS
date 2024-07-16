package epams.domain.com.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import epams.domain.com.admin.service.LoginOtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epams.domain.com.login.dto.LoginOTPDTO;
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
public class LoginOTPRestController {

    /***
     * @author 140024
     * @implNote 로그인 OTP서비스
     * @since 2024-06-09
     */
    private final LoginOtpService loginOtpService;

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

}
