package epams.com.login.util.webauthn;

import org.owasp.encoder.Encode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 간편인증 요청 처리를 위한 Rest 컨트롤러
 * @since 2024-06-22
 */
@Slf4j
@RequestMapping("/api/webauthn")
@RequiredArgsConstructor
@RestController
public class AuthRestController {
	
    /**
     * @author K140024
     * @implNote 사용자 이름 파라미터 상수
     * @since 2024-06-22
     */
    private static final String USERNAME_PARAM = "username";

    /**
     * @author K140024
     * @implNote 자격 증명 파라미터 상수
     * @since 2024-06-22
     */
    private static final String CREDENTIAL_PARAM = "credential";

    /**
     * @author K140024
     * @implNote 간편인증 서비스
     * @since 2024-06-22
     */
    private final AuthService authService;

    /**
     * @author K140024
     * @implNote 기본 페이지 요청 처리
     * @since 2024-06-22
     * @return "webauthn/index" 뷰 이름
     */
    @GetMapping("/")
    public String index() {
        return "webauthn/index";
    }

    /**
     * @author K140024
     * @implNote 로그인 페이지 요청 처리
     * @since 2024-06-22
     * @return "webauthn/login" 뷰 이름
     */
    @GetMapping("/login")
    public String loginPage() {
        return "webauthn/login";
    }

    /**
     * @author K140024
     * @implNote 로그인 요청 처리
     * @since 2024-06-22
     * @param username 사용자 이름
     * @param session  HTTP 세션
     * @return 로그인 요청에 대한 JSON 응답
     */
    @PostMapping("/login")
    @ResponseBody
    public String startLogin(
        @RequestParam(USERNAME_PARAM) final String username,
        final HttpSession session
    ) {
        // 사용자 입력 검증 및 인코딩
        if (username == null || username.isEmpty() || !username.matches("^[a-zA-Z0-9._-]{3,}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username");
        }
        final String sanitizedUsername = Encode.forHtml(username);
        return authService.startLogin(sanitizedUsername, session);
    }

    /**
     * @author K140024
     * @implNote 환영 페이지 요청 처리
     * @since 2024-06-22
     * @return "webauthn/welcome" 뷰 이름
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "webauthn/welcome";
    }

    /**
     * @author K140024
     * @implNote 사용자 등록 페이지 요청 처리
     * @since 2024-06-22
     * @param model 모델 객체
     * @return "webauthn/register" 뷰 이름
     */
    @GetMapping("/register")
    public String registerUser(final Model model) {
        return "webauthn/register";
    }

    /**
     * @author K140024
     * @implNote 새로운 사용자 등록 요청 처리
     * @since 2024-06-22
     * @param username 사용자 이름
     * @param session  HTTP 세션
     * @return 등록 요청에 대한 JSON 응답
     */
    @PostMapping("/register")
    @ResponseBody
    public String newUserRegistration(
        @RequestParam(USERNAME_PARAM) final String username,
        final HttpSession session
    ) {
    	log.warn("newUserRegistration : username");
        return authService.newUserRegistration(username, session);
    }

    /**
     * @author K140024
     * @implNote 등록 완료 요청 처리
     * @since 2024-06-22
     * @param credential 자격 증명
     * @param session    HTTP 세션
     * @return 등록 완료에 대한 응답
     */
    @PostMapping("/finishauth")
    @ResponseBody
    public ResponseEntity<String> finishRegistration(
        @RequestParam(CREDENTIAL_PARAM) final String credential,
        final HttpSession session
    ) {
        return authService.finishRegistration(credential, session);
    }

    /**
     * @author K140024
     * @implNote 로그인 완료 요청 처리
     * @since 2024-06-22
     * @return 로그인 완료에 대한 응답
     */
    @PostMapping("/welcome")
    public ResponseEntity<?> finishLogin(
        @RequestParam(CREDENTIAL_PARAM) final String credential,
        @RequestParam(USERNAME_PARAM) final String username,
        final Model model,
        final HttpSession session
    ) {
        return authService.finishLogin(credential, username, model, session);
    }
}