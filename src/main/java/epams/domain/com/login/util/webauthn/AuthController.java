package epams.domain.com.login.util.webauthn;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 간편인증 요청 처리를 위한 컨트롤러
 * @since 2024-04-26
 */
@Slf4j
@RequestMapping("/webauthn")
@RequiredArgsConstructor
@Controller
public class AuthController {

    /**
     * @author K140024
     * @implNote 기본 페이지 요청 처리
     * @since 2024-04-26
     * @return "webauthn/index" 뷰 이름
     */
    @GetMapping("/")
    public String index() {
        return "webauthn/index";
    }

    /**
     * @author K140024
     * @implNote 환영 페이지 요청 처리
     * @since 2024-04-26
     * @return "webauthn/welcome" 뷰 이름
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "webauthn/welcome";
    }

    /**
     * @author K140024
     * @implNote 사용자 등록 페이지 요청 처리
     * @since 2024-04-26
     * @param model 모델 객체
     * @return "webauthn/register" 뷰 이름
     */
    @GetMapping("/register")
    public String registerUser(final Model model) {
        return "webauthn/register";
    }

    /**
     * @author K140024
     * @implNote 로그인 페이지 요청 처리
     * @since 2024-04-26
     * @return "webauthn/login" 뷰 이름
     */
    @GetMapping("/login")
    public String loginPage() {
        return "webauthn/login";
    }

}